namespace Maximus.IRD.Samples.Common
{
    /// Interface used to supply bearer tokens to services
    /// <seealso cref="Maximus.IRD.Samples.KeycloakClient"/>
    ///
    public interface IAuthTokenProvider
    {
        /// Returns the bearer token used to talk to the
        /// service. Note that requesting the token may
        /// result in one or more network requests being
        /// issued to the IdP (auth) server
        ///
        string GetToken();

        /// Optionally issues a logout request to the authorization
        /// server to revoke the active access and refresh tokens.
        ///
        void Logout();
    }
}
