using System;
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
            // this first sequence demonstrates various sets of
            // search criteria usage
            //
            var criteria = new CaseSearchCriteria();

            // no criteria
            //
            Console.WriteLine(criteria.ToString());

            // a single case number
            //
            criteria
                .Clear()
                .CaseNumber("CM16-1235678")
                ;
            Console.WriteLine(criteria.ToString());

            // setting *suggested* size of responses
            //
            criteria
                .Clear()
                .PageSize(500)
                ;
            Console.WriteLine(criteria.ToString());

            // multiple filters and sorting
            //
            criteria
                .Clear()
                .Status(FilterStatus.Closed)
                .Status(FilterStatus.EligibilityReview)
                .Sort(SortFields.assignDate)
                .Sort(SortFields.closedReason, false)
                .Sort(SortFields.status, true)
                ;
            Console.WriteLine(criteria.ToString());

            // execute a case search and ensure we only
            // get a single result back each time to show the
            // method of moving through the paged results
            //
            try
            {
                var authProvider = LoadAuthService();
                var apiBaseUrl = LoadDxcServiceUrl();
                var caseService = new Service(authProvider, apiBaseUrl);

                criteria
                    .Clear()
                    .PageIndex(0)
                    .PageSize(3)
                    ;

                var result = caseService.CaseSearch(criteria);
                int matchCount = result.matchCount;
                int count = 0;
                while (count < matchCount) {
                    // process each response item
                    //
                    foreach (var item in result.results) {
                        Console.WriteLine($"caseNumber {item.caseNumber}");
                        count++;
                    }

                    // Move to the next page and execute the query again
                    //
                    if (count < matchCount) {
                        criteria.NextPage();
                        result = caseService.CaseSearch(criteria);
                    }
                }
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
