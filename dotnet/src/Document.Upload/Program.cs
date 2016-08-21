using System;
using System.IO;
using Maximus.IRD.Samples.Common;
using Maximus.IRD.Samples.Config;
using Maximus.IRD.Samples.DXCClient;
using Maximus.IRD.Samples.KeycloakClient;
using static Maximus.IRD.Samples.DXCClient.CaseSearchCriteria;

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
            // upload a file to the specified case
            //
            try
            {
                var authProvider = LoadAuthService();
                var apiBaseUrl = LoadDxcServiceUrl();
                var caseService = new Service(authProvider, apiBaseUrl);
                caseService.DXCEvent = (msg) => Console.WriteLine("[dxc]: {0}", msg);

                if (args.Length < 2) {
                    Console.WriteLine("usage: upload <caseNumber> <filename> [title]");
                    Environment.Exit(1);
                }

                var caseNumber = args[0];
                var filename = args[1];
                string title = null;
                if (args.Length > 2)
                    title = args[2];

                if (!File.Exists(args[1])) {
                    Console.WriteLine($"error: {filename} does not exist");
                    Environment.Exit(1);
                }

                caseService.UploadFile(caseNumber, filename, title);
                Console.WriteLine("document uploaded");
            }
            catch (DXCException e)
            {
                // upload issue
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
