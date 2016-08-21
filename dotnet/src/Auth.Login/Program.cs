using System;
using Maximus.IRD.Samples.Config;
using Maximus.IRD.Samples.Common;
using Maximus.IRD.Samples.KeycloakClient;

namespace ConsoleApplication
{

    public class Program
    {
        private static void WriteToken(string token)
        {
            Console.WriteLine("------------ BEGIN TOKEN ------------");
            Console.WriteLine(token);
            Console.WriteLine("------------  END TOKEN  ------------");
        }

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

        public static void Main(string[] args)
        {
            try
            {
                var provider = LoadAuthService();

                // use the auth service, you either get a
                // bearer token or a typed exception is raised
                // to indicate the type of failure
                //
                var token = provider.GetToken();
                WriteToken(token);
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
