using System;
using System.Globalization;
using System.IdentityModel.Tokens.Jwt;
using Microsoft.IdentityModel.Tokens;

namespace Maximus.IRD.Samples.KeycloakClient
{

    /// <summary>
    /// JwtToken provides a thin wrapper over the core
    /// provided JwtSecurityToken with additional logic to
    /// relate issue time with valid use time to permit
    /// applying a windowed expiry.
    /// This allows adapting to time sync issues between
    /// the caller and the authentication server as well
    /// as supporting automatic refresh of tokens as
    /// necessary.
    /// </summary>
    ///
    public class JwtToken
    {

        public JwtSecurityToken Token { get; }
        public DateTime IssuedAt { get; }
        public DateTime ValidTo { get; }

        public string BearerToken { get; }

        public JwtToken(string encoded)
        {
            this.BearerToken = encoded;
            this.Token = new System.IdentityModel.Tokens.Jwt.JwtSecurityToken(this.BearerToken);
            var secondsAfterBaseTime = Convert.ToInt64(
                this.Token.Payload.Iat,
                CultureInfo.InvariantCulture
                );
            this.IssuedAt = EpochTime.DateTime(secondsAfterBaseTime);
            this.ValidTo = this.Token.ValidTo;
        }

        public bool Expiring()
        {
            // use `iat` and `exp` to calculate a 90% window for when to
            // consider the token as expired this addresses clock skew and
            // network latency in a simple and easy way
            //
            return (Used() >= 90);
        }

        public bool Expired()
        {
            return (Used() >= 100);
        }

        public int Used()
        {
            var now = DateTime.UtcNow;
            if (now >= this.ValidTo)
                return 100;

            var total = (this.ValidTo - this.IssuedAt).TotalSeconds;
            var left = (this.ValidTo - now).TotalSeconds;
            var result = 100 - (int)Math.Truncate(left / total * 100);

            return result;
        }
    }
}
