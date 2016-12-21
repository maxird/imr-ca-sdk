using System;
using Maximus.IRD.Samples.Common;
using Maximus.IRD.Samples.Config;
using Maximus.IRD.Samples.DXCClient;
using Maximus.IRD.Samples.KeycloakClient;

namespace ConsoleApplication
{
    public class Program
    {
        public static IAuthTokenProvider LoadAuthService()
        {
            // pull configuration out of the local configuration file
            //
            var config = Ini.Load();

            // construct the auth service
            //
            var authConfig = new AuthConfig(
                config["servers:auth"],
                config["credentials:username"],
                config["credentials:password"]
            );
            var authService = new AuthService(authConfig);

            // optional, display key steps of the process
            //
            authService.AuthEvent = (msg) => Console.WriteLine("[auth]: {0}", msg);

            // return the token provider
            //
            return authService;
        }

        public static string LoadDxcServiceUrl()
        {
            // pull configuration out of the local configuration file
            //
            var config = Ini.Load();

            var result = config["servers:api"];

            return result;
        }


        public static void Main(string[] args)
        {
            // download a single document from a specified case
            //
            try
            {
                var authProvider = LoadAuthService();
                var apiBaseUrl = LoadDxcServiceUrl();
                var caseService = new Service(authProvider, apiBaseUrl);
                caseService.DXCEvent = (msg) => Console.WriteLine("[dxc]: {0}", msg);

                if (args.Length != 2) {
                    Console.WriteLine("usage: download <identifier> <filename>");
                    Environment.Exit(1);
                }

                var identifier = args[0];
                var filename = args[1];

                caseService.DownloadFile(identifier, filename);
                Console.WriteLine("document downloaded");
            }
            catch (DXCException e)
            {
                // download issue
                //
                Console.Error.WriteLine("error[{0}/{1}]: {2}", e.Status, e.RefId, e.Message);
                Environment.Exit(1);
            }
            catch (LoginException e)
            {
                // credentials issue, non-recoverable
                //
                Console.Error.WriteLine("error: {0}", e.Message);
                Environment.Exit(1);
            }
            catch (UnknownResponseException e)
            {
                // could be some operational issue
                //
                Console.Error.WriteLine("error: {0}", e.Message);
                Environment.Exit(1);
            }
            catch (System.Exception e)
            {
                // other unspecified errors
                //
                Console.Error.WriteLine(e.ToString());
                Environment.Exit(1);
            }
        }
    }
}
