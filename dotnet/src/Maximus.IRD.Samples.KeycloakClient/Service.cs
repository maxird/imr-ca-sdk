using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using Newtonsoft.Json;
using Maximus.IRD.Samples.Common;

namespace Maximus.IRD.Samples.KeycloakClient
{
    /// <summary>
    /// AuthService provides an automatic login process
    /// based on username and password with caching of
    /// access and refresh tokens to reduce the number of
    /// interactions with the IdP to a minimum.
    /// The service automatically re-issues either a request
    /// to use the refresh token to renew the access token
    /// or performs a full direct login.
    /// </summary>
    public class AuthService : IAuthTokenProvider
    {
        private const string URL_SPEC = "{0}/auth/realms/{1}/protocol/openid-connect/{2}";
        private IAuthConfig config = null;
        private JwtToken at = null;
        private JwtToken rt = null;

        public delegate void AuthEventDelegate(string eventText);
        public AuthEventDelegate AuthEvent { get; set; }

        public AuthService(IAuthConfig config)
        {
            // bind a noop callback for log messaging
            //
            this.AuthEvent = (msg) => { };

            // store the config and build the auth server endpoint
            //
            this.config = config;

            // retrieve any locally cached tokens for re-use
            //
            this.at = Store.DeserializeToken(config, "at");
            this.rt = Store.DeserializeToken(config, "rt");

            // guard on a missing token cache file
            //
            if (this.rt == null)
                this.at = null;
        }

        private void Log(string msg)
        {
            AuthEvent(msg);
        }

        /// <summary>
        /// Return an access token suitable for use as a bearer token.
        /// Throws multiple exceptions in the event of failure to
        /// authenticate to to the authentication server.
        /// </summary>
        public string GetToken()
        {
            // first time or reset, use refresh to cause login chain
            //
            if (this.at == null)
            {
                Log("transfer to refresh on missing access token");
                return Refresh();
            }

            // if the token is not expiring (windowed) give it back
            //
            if (this.at.Expiring())
            {
                Log($"transfer to refresh on expiring access token: {this.at.Used()}");
                return Refresh();
            }

            // we have a token ready to use already
            //
            Log("returning cached access token");
            return this.at.BearerToken;
        }

        public void Logout()
        {
            Log("logout request requested");

            // we do not have enough information to formally
            // ask for a session close
            //
            if (this.rt == null)
                return;

            // notify the auth server we are abandoning the session
            //
            LogoutRequest();

            // remove in-memory cache
            //
            Log("removing in-memory cache");
            this.at = null;
            this.rt = null;

            // remove the on-disk cache
            //
            Log("removing on-disk cache");
            Store.SerializeToken(config, null, "at");
            Store.SerializeToken(config, null, "rt");

            Log("logout completed");
        }

        private string AuthRequest(List<KeyValuePair<string, string>> formParams, string action = "token")
        {
            try
            {
                formParams.Add(new KeyValuePair<string, string>("client_id", config.ClientId));

                // issue the request
                //
                var url = string.Format(URL_SPEC, config.Server, config.Realm, action);
                Log(String.Format("remote request {0}", url));
                var content = new System.Net.Http.FormUrlEncodedContent(formParams);
                var client = new HttpClient();
                var task = client.PostAsync(url, content);
                var response = task.Result;
                var text = response.Content.ReadAsStringAsync().Result;

                // logout returns 204 with no content
                //
                if ((response.StatusCode == HttpStatusCode.NoContent) &&
                    (action == "logout"))
                {
                    return "";
                }

                // parse the object content
                //
                dynamic obj = JsonConvert.DeserializeObject(text);
                string access_token = obj.access_token;
                string refresh_token = obj.refresh_token;
                string errorType = obj.error;
                string errorText = obj.error_description;
                if (!response.IsSuccessStatusCode)
                    Log(String.Format("response: [{0}/{1}/{2}]", response.StatusCode, errorType, errorText));

                // response was what we wanted
                //
                if ((access_token != null) && (refresh_token != null))
                {
                    this.at = new JwtToken(access_token);
                    this.rt = new JwtToken(refresh_token);

                    Store.SerializeToken(this.config, this.at, "at");
                    Store.SerializeToken(this.config, this.rt, "rt");

                    return this.at.BearerToken;
                }

                // did not get back anything we recognize
                //
                if ((errorType == null) || (errorType != "invalid_grant"))
                {
                    throw new UnknownResponseException();
                }

                // decide how we will throw, some errors can be automatically
                // recovered by logging in again
                //
                var switchText = errorText.ToLower();
                switch (switchText)
                {
                    case "invalid user credentials":
                    case "account disabled":
                    case "account is not fully set up":
                        throw new LoginException(errorText);
                    case "refresh token expired":
                    case "session not active":
                    case "stale refresh token":
                        throw new CanRetryException();
                    default:
                        throw new UnknownResponseException(errorText);
                }
            }
            catch (System.AggregateException)
            {
                throw new UnknownResponseException("Unable to reach auth server");
            }
            catch (Newtonsoft.Json.JsonReaderException)
            {
                throw new UnknownResponseException("Unable to parse auth server response");
            }
        }


        private string RefreshRequest()
        {
            var formParams = new List<KeyValuePair<string, string>>();
            formParams.Add(new KeyValuePair<string, string>("grant_type", "refresh_token"));
            formParams.Add(new KeyValuePair<string, string>("refresh_token", this.rt.BearerToken));

            return AuthRequest(formParams);
        }

        private string LoginRequest()
        {
            var formParams = new List<KeyValuePair<string, string>>();
            formParams.Add(new KeyValuePair<string, string>("grant_type", "password"));
            formParams.Add(new KeyValuePair<string, string>("username", config.Username));
            formParams.Add(new KeyValuePair<string, string>("password", config.Password));

            return AuthRequest(formParams);
        }

        private void LogoutRequest()
        {
            var formParams = new List<KeyValuePair<string, string>>();
            formParams.Add(new KeyValuePair<string, string>("refresh_token", this.rt.BearerToken));

            AuthRequest(formParams, "logout");
        }


        private string Login()
        {
            Log($"attempting login as {config.Username}");
            this.at = null;
            this.rt = null;

            var result = LoginRequest();
            Log("login completed");
            return result;
        }

        private string Refresh()
        {
            // do a login if we don't have a viable refresh token
            //
            if (this.rt == null)
            {
                Log("transfer to login on missing refresh token");
                return Login();
            }

            if (this.rt.Expiring())
            {
                Log("transfer to login on expiring refresh token");
                return Login();
            }

            Log("attempting refresh");
            try
            {
                this.at = null;
                var result = RefreshRequest();
                Log("token refresh completed");
                return result;
            }
            catch (CanRetryException)
            {
                Log("falling back to login on failed refresh");
                return Login();
            }
        }
    }
}
