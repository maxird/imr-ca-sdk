using System;
using System.IO;
using System.Text;

namespace Maximus.IRD.Samples.KeycloakClient
{
    /// <summary>
    /// Provides a simple (and insecure) data store for the refresh
    /// token. Since it is cross-platform we cannot make use of the
    /// DPAPI services - if you are Windows use DPAPI. If you are not
    /// use your approved best practices for on-server secret storage.
    /// The store is created in your user home folder in a sub-folder
    /// called <code>.keycloak-ird</code>. An entry is created for each
    /// combination of auth server and username.
    /// </summary>
    ///
    public static class Store
    {
        private static string GetHomeFolder()
        {
            // .NET Core ootb does not include OSVersion or PlatformID so
            // rely on environment variable presence
            //
            var unixFolder = Environment.GetEnvironmentVariable("HOME");
            var winFolder = Environment.ExpandEnvironmentVariables("%HOMEDRIVE%%HOMEPATH%");

            return ((unixFolder != null) && (unixFolder.Length > 0)) ? unixFolder : winFolder;
        }

        private static string GetStoreFolder()
        {
            var homeFolder = GetHomeFolder();
            var storeFolder = Path.Combine(homeFolder, ".keycloak-ird");

            if (!Directory.Exists(storeFolder))
                Directory.CreateDirectory(storeFolder);

            return storeFolder;
        }

        private static string ComputeFilename(IAuthConfig config, string tokenType)
        {
            var sn = config.Server.ToLower();
            var un = config.Username.ToLower();
            var fn = $"{sn}-{un}";

            var bytes = Encoding.UTF8.GetBytes(fn);
            var hash = Convert.ToBase64String(bytes);

            return Path.Combine(GetStoreFolder(), hash + "-" + tokenType);
        }


        public static void SerializeToken(IAuthConfig config, JwtToken token, string tokenType)
        {
            var fn = ComputeFilename(config, tokenType);

            if (token != null)
                File.WriteAllText(fn, AsciiCrypt.EncryptText(token.BearerToken));
            else if (File.Exists(fn))
                File.Delete(fn);
        }

        public static JwtToken DeserializeToken(IAuthConfig config, string tokenType)
        {
            var fn = ComputeFilename(config, tokenType);

            if (!File.Exists(fn))
                return null;

            var text = AsciiCrypt.DecryptText(File.ReadAllText(fn));

            return new JwtToken(text);
        }
    }
}
