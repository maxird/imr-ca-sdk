using System;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.IO;
using Newtonsoft.Json;
using Maximus.IRD.Samples.Common;

namespace Maximus.IRD.Samples.DXCClient
{
    public class Service
    {
        private string URL_SPEC = "{0}/apigw/webservices/rest/apigw/{1}";

        private IAuthTokenProvider tokenProvider = null;
        private string apiBaseUrl = null;

        public delegate void DXCEventDelegate(string eventText);
        public DXCEventDelegate DXCEvent { get; set; }

        public Service(IAuthTokenProvider tokenProvider, string apiBaseUrl)
        {
            // bind a noop callback for log messaging
            //
            this.DXCEvent = (msg) => { };

            this.tokenProvider = tokenProvider;
            this.apiBaseUrl = apiBaseUrl;
        }

        private void Log(string msg)
        {
            DXCEvent(msg);
        }

        private void CheckResponse(dynamic result)
        {
            if (result.status == null)
                return;

            int status = result.status;
            string msg = result.msg;
            string refId = result.refId;
            switch (status)
            {
                case 200: break;
                case 400:
                case 405:
                    throw new ParameterException(msg, status, refId);
                case 401:
                    throw new AuthorizationException(msg, status, refId);
                case 404:
                    throw new NotFoundException(msg, status, refId);
                case 406:
                    throw new ContentNotAcceptableException(msg, status, refId);
                default:
                    throw new DXCException(msg, status, refId);
            }
        }
        private dynamic DataPostRequest(string jsonText, string servicePath, string contentType)
        {
            // get the auth token
            //
            var bearerToken = tokenProvider.GetToken();

            // issue the request
            //
            var url = string.Format(URL_SPEC, apiBaseUrl, servicePath);
            Log(String.Format("remote request {0}", url));

            // construct the json request
            //
            StringContent content = null;
            if (jsonText != null)
            {
                content = new System.Net.Http.StringContent(jsonText);
                content.Headers.ContentType.MediaType = contentType;
            }

            // construct the client
            //
            var client = new HttpClient();
            client.DefaultRequestHeaders
                .Accept
                .Add(new MediaTypeWithQualityHeaderValue(contentType))
                ;
            client.DefaultRequestHeaders.Add("Authorization", $"Bearer {bearerToken}");

            // execute the task
            //
            Task<HttpResponseMessage> task;

            if (content != null)
                task = client.PostAsync(url, content);
            else
                task = client.GetAsync(url);
            var response = task.Result;
            Log(String.Format("response: [{0} / {1}]", response.StatusCode, response.Content.Headers.ContentType));

            var text = response.Content.ReadAsStringAsync().Result;
            if (!response.IsSuccessStatusCode)
                Log(String.Format("content:  [{0}]", text));

            dynamic result = JsonConvert.DeserializeObject(text);
            CheckResponse(result);
            return result;
        }

        private string GetMimeMapping(string filename)
        {
            var pattern = Path.GetExtension(filename).ToLower();
            Log($"pattern: {pattern}");
            string result;
            switch (pattern)
            {
                case ".pdf": result = "application/pdf"; break;
                case ".png": result = "image/png"; break;
                case ".jpg": result = "image/jpeg"; break;
                case ".docx": result = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"; break;
                case ".doc": result = "application/msword"; break;
                case ".xml": result = "text/xml"; break;
                case ".txt": result = "text/plain"; break;
                default: result = "application/octet-stream"; break;
            }
            return result;
        }

        private string GetMimeExtension(string mimeType)
        {
            var pattern = mimeType.ToLower();
            string result;
            switch (pattern)
            {
                case "application/pdf": result = "application/pdf"; break;
                case "image/png": result = "png"; break;
                case "image/jpeg": result = "jpg"; break;
                case "application/vnd.openxmlformats-officedocument.wordprocessingml.document": result = "docx"; break;
                case "application/msword": result = "doc"; break;
                case "text/xml": result = "xml"; break;
                case "text/plain": result = "txt"; break;
                default: result = "bin"; break;
            }
            return result;
        }

        private void FilePostRequest(string filename, string title, string servicePath)
        {
            // get the auth token
            //
            var bearerToken = tokenProvider.GetToken();

            // issue the request
            //
            var url = string.Format(URL_SPEC, apiBaseUrl, servicePath);
            var mimeType = GetMimeMapping(filename);
            var ext = Path.GetExtension(filename);
            var displayName = $"{title}{ext}";
            Log(String.Format("remote request {0}", url));
            Log($"file: {displayName} as {mimeType}");

            // prepare the file stream of the data to send up
            //
            var stream = new FileStream(filename, FileMode.Open, FileAccess.Read);
            var streamContent = new StreamContent(stream);
            streamContent.Headers.ContentDisposition = new ContentDispositionHeaderValue("form-data");
            streamContent.Headers.ContentDisposition.Name = "file";
            streamContent.Headers.ContentDisposition.FileName = $"{displayName}";
            streamContent.Headers.ContentType = new MediaTypeHeaderValue(mimeType);

            var content = new MultipartFormDataContent { streamContent };

            // construct the client
            //
            var client = new HttpClient();
            client.DefaultRequestHeaders
                .Accept
                .Add(new MediaTypeWithQualityHeaderValue("*/*"))
                ;
            client.DefaultRequestHeaders.Add("Authorization", $"Bearer {bearerToken}");

            // execute the task
            //
            var task = client.PostAsync(url, content);
            var response = task.Result;
            var text = response.Content.ReadAsStringAsync().Result;
            Log(String.Format("response: [{0} / {1}]", response.StatusCode, response.Content.Headers.ContentType));
            if (!response.IsSuccessStatusCode)
                Log(String.Format("content:  {0}", text));

            dynamic result = JsonConvert.DeserializeObject(text);
            CheckResponse(result);
        }

        private dynamic DataGetRequest(string servicePath, bool json = true)
        {
            // get the auth token
            //
            var bearerToken = tokenProvider.GetToken();

            // issue the request
            //
            var url = string.Format(URL_SPEC, apiBaseUrl, servicePath);
            Log(String.Format("remote request {0}", url));

            // construct the client
            //
            var client = new HttpClient();
            client.DefaultRequestHeaders
                .Accept
                .Add(new MediaTypeWithQualityHeaderValue("*/*"))
                ;
            client.DefaultRequestHeaders.Add("Authorization", $"Bearer {bearerToken}");

            // execute the task
            //
            var task = client.GetAsync(url, HttpCompletionOption.ResponseHeadersRead);
            var response = task.Result;
            var text = response.Content.ReadAsStringAsync().Result;
            Log(String.Format("response: [{0} / {1}]", response.StatusCode, response.Content.Headers.ContentType));
            if (!response.IsSuccessStatusCode)
                Log(String.Format("content:  {0}", text));

            if (json) {
                dynamic result = JsonConvert.DeserializeObject(text);
                CheckResponse(result);
                return result;
            }
            return text;
        }

        private void FileGetRequest(string filename, string servicePath)
        {
            // get the auth token
            //
            var bearerToken = tokenProvider.GetToken();

            // issue the request
            //
            var url = string.Format(URL_SPEC, apiBaseUrl, servicePath);
            Log(String.Format("remote request {0}", url));

            // construct the client
            //
            var client = new HttpClient();
            client.DefaultRequestHeaders
                .Accept
                .Add(new MediaTypeWithQualityHeaderValue("*/*"))
                ;
            client.DefaultRequestHeaders.Add("Authorization", $"Bearer {bearerToken}");

            // execute the task
            //
            var task = client.GetAsync(url, HttpCompletionOption.ResponseHeadersRead);
            var response = task.Result;
            Log(String.Format("response: [{0} / {1}]", response.StatusCode, response.Content.Headers.ContentType));

            if (!response.IsSuccessStatusCode)
            {
                var text = response.Content.ReadAsStringAsync().Result;
                Log($"content: [{text}]");
                dynamic result = JsonConvert.DeserializeObject(text);
                CheckResponse(result);
            } else {
                var dest = File.Create(filename);
                try
                {
                    var streamTask = response.Content.ReadAsStreamAsync();
                    response.Content.CopyToAsync(dest).Wait();
                }
                finally
                {
                    dest.Flush();
                    dest.Dispose();
                }
            }
        }

        public dynamic CaseSearch(CaseSearchCriteria criteria)
        {
            var text = criteria.ToString();
            Log($"CaseSearch criteria: [{text}]");

            // pick the service endpoint to use
            //
            var path = criteria.IsRfi() ? "cases/searchrfi" : "cases/search";

            // issue the data request
            //
            var result = DataPostRequest(text, path, "application/json");

            // it is important to ensure we update our criteria to
            // reflect any enforced limits returned by the service.
            // If we request more records than the service is willing
            // to return it will return the permitted limit as a replacement
            // for the limit property
            //
            criteria.UpdateFromResponse(result);

            // send the response back to the caller
            //
            return result;
        }

        public dynamic GetCase(string caseNumber)
        {
            return JsonConvert.DeserializeObject("{}");
        }

        public dynamic GetDocuments(string caseNumber)
        {
            Log($"GetDocuments: [{caseNumber}]");

            // pick the service endpoint to use
            //
            var path = $"docs/search/cn/{caseNumber}";

            // issue the data request
            //
            var result = DataPostRequest(null, path, "application/json");

            // send the response back to the caller
            //
            return result;
        }

        public void DownloadFile(string identifier, string filename)
        {
            Log($"DownloadFile: [{identifier} => {filename}]");

            // pick the service endpoint to use
            //
            var path = $"docs/download/{identifier}";

            // issue the data request
            //
            FileGetRequest(filename, path);
        }

        public void UploadFile(string caseNumber, string filename, string title = null)
        {
            var servicePath = $"docs/upload/cn/{caseNumber}";

            if (title == null)
            {
                title = Path.GetFileNameWithoutExtension(filename);
            }

            FilePostRequest(filename, title, servicePath);
        }

        public String[] GetNoarfiList()
        {
            var servicePath = $"events/noarfi/";
            var result = DataGetRequest(servicePath);

            Log($"response: [{result}]");
            return result.datelist.ToObject<string[]>();
        }

        public dynamic GetNoarfiEvents(string date)
        {
            var servicePath = $"events/noarfi/{date}";
            var result = DataGetRequest(servicePath, false);

            Log($"response: [{result}]");
            return result;
        }
    }
}
