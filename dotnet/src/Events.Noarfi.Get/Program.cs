﻿using System;
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
            // retrieve the list of documents that are attached
            // to a specific case
            //
            try
            {
                var authProvider = LoadAuthService();
                var apiBaseUrl = LoadDxcServiceUrl();
                var caseService = new Service(authProvider, apiBaseUrl);
                caseService.DXCEvent = (msg) => Console.WriteLine("[dxc]: {0}", msg);

                if (args.Length != 1) {
                    Console.WriteLine("usage: get <yyyy-mm-dd>");
                    Environment.Exit(1);
                }

                var result = caseService.GetNoarfiEvents(args[0]);
                Console.WriteLine(result);
            }
            catch (DXCException e)
            {
                // service or call issue
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
