namespace Maximus.IRD.Samples.KeycloakClient
{
    /// Defines the configuration needs of the service
    public interface IAuthConfig
    {
        string Server { get; }
        string Username { get; }
        string Password { get; }
        string Realm { get; }
        string ClientId { get; }
    }

    /// Simple provider of the configuration needs
    public class AuthConfig : IAuthConfig
    {
        public string Server { get; }
        public string Username { get; }
        public string Password { get; }
        public string Realm { get; }
        public string ClientId { get; }

        public AuthConfig()
        {
            this.Realm = "dxc-externals";
            this.ClientId = "dxc";
        }

        public AuthConfig(string server, string username, string password)
        {
            this.Server = server;
            this.Username = username;
            this.Password = password;
            this.Realm = "dxc-externals";
            this.ClientId = "dxc";
        }
    }
}
