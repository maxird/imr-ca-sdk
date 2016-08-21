using System;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace Maximus.IRD.Samples.DXCClient
{
    /// <summary>
    /// Criteria builder for executing case searches
    /// </summary>
    public class CaseSearchCriteria
    {
        /// <summary>
        /// Internal repesentation of JSON sort parameter
        /// </summary>
        protected internal class CaseSortItem
        {
            public string property;
            public bool desc;
        }

        /// <summary>
        /// Internal representation of JSON structure for case
        /// searches
        /// </summary>
        protected internal class CaseSearchParams
        {
            [JsonProperty(DefaultValueHandling = DefaultValueHandling.Ignore)]
            public int start;
            [JsonProperty(DefaultValueHandling = DefaultValueHandling.Ignore)]
            public int limit;
            [JsonProperty(NullValueHandling = NullValueHandling.Ignore)]
            public string IMRID;
            [JsonProperty(NullValueHandling = NullValueHandling.Ignore)]
            public string CAID;
            [JsonProperty(NullValueHandling = NullValueHandling.Ignore)]
            public string[] status;
            [JsonProperty(NullValueHandling = NullValueHandling.Ignore)]
            public CaseSortItem[] sort;
        }

        public enum FilterStatus
        {
            Received = 1,
            EligibilityReview,
            RecordsRequested,
            ClinicalReview,
            Closed,
            NOARFI,
            InsufficientRecords
        }

        public enum SortFields
        {
            claimNumber,
            caseNumber,
            dateOfInjury,
            priority,
            injuredWorkerPrefix,
            injuredWorkerFirstName,
            injuredWorkerLastName,
            injuredWorkerMiddleInitial,
            injuredWorkrSuffix,
            employerName,
            treatmentRequested,
            claimsAdministratorAddress1,
            claimsAdministratorAddress2,
            claimsAdministratorCity,
            claimsAdministratorState,
            claimsAdministratorZipCode,
            claimsAdministratorCompanyName,
            providerPrefix,
            providerFirtName,
            providerLastName,
            providerMidleInitial,
            providerSuffix,
            organizationName,
            status,
            extStatus,
            terminationReason,
            closedReason,
            assignDate,
            dateofURDecision,
            receiveDate,
            modificationDate
        }

        private Dictionary<string, bool> sortFields;
        private Dictionary<string, string> identifiers;
        private List<FilterStatus> _status;
        private bool _rfi = false;
        private int _size = 0;
        private int _page = 0;

        public CaseSearchCriteria()
        {
            this.sortFields = new Dictionary<string, bool>();
            this.identifiers = new Dictionary<string, string>();
            this._status = new List<FilterStatus>();
        }

        public CaseSearchCriteria Clear()
        {
            this.sortFields.Clear();
            this.identifiers.Clear();
            this._status.Clear();
            this._page = this._size = 0;

            return this;
        }

        public CaseSearchCriteria All()
        {
            this._rfi = false;
            return this;
        }
        public CaseSearchCriteria Rfi()
        {
            this._rfi = true;
            return this;
        }
        public bool IsRfi()
        {
            return this._rfi;
        }
    public CaseSearchCriteria Sort(SortFields field, bool descending = true)
        {
            var sz = $"{field}";
            if (sortFields.ContainsKey(sz))
                throw new Exception($"Duplicate sort request on {sz}");
            sortFields.Add(sz, descending);
            return this;
        }

        public CaseSearchCriteria Status(FilterStatus st)
        {
            if (!this._status.Contains(st))
                this._status.Add(st);
            return this;
        }

        public CaseSearchCriteria PageIndex(int page)
        {
            this._page = page;
            return this;
        }

        public CaseSearchCriteria PageSize(int size)
        {
            if (size <= 0)
                throw new Exception($"Page size of {size} is invalid");
            this._size = size;
            return this;
        }

        public CaseSearchCriteria Identifier(string name, string value)
        {
            if (identifiers.ContainsKey(name))
                throw new Exception($"Duplicate identifier request on {name}");
            identifiers.Add(name, Encode(value));
            return this;
        }

        public CaseSearchCriteria CaseNumber(string value)
        {
            return Identifier("IMRID", value);
        }
        public CaseSearchCriteria ClaimNumber(string value)
        {
            return Identifier("CAID", value);
        }

        public CaseSearchCriteria UpdateFromResponse(dynamic response)
        {
            if (response.limit != null) {
                int limit = Int32.Parse($"{response.limit}");
                PageSize(limit);
            }
            return this;
        }

        public CaseSearchCriteria NextPage()
        {
            this._page++;
            return this;
        }

        private static string Encode(string text)
        {
            if (text == null)
                return "";
            return text.Replace("\"", "\\\"");
        }

        public override string ToString()
        {
            var csp = new CaseSearchParams();
            csp.start = _page;
            csp.limit = _size;
            int i;

            // identifier criteria does not apply to rfi search
            //
            if (identifiers.Count > 0 && !this._rfi) {
                foreach (var item in identifiers) {
                    switch (item.Key) {
                        case "IMRID": csp.IMRID = item.Value; break;
                        case "CAID": csp.CAID = item.Value; break;
                        default: throw new Exception("Unknown identifier type");
                    }
                }
            }

            // status criteria does not apply to rfi search
            //
            if (_status.Count > 0 && !this._rfi) {
                csp.status = new string[_status.Count];
                i = 0;
                foreach (var item in _status) {
                    csp.status[i] = $"{(int)item}";
                    i++;
                }
            }

            if (sortFields.Count > 0) {
                csp.sort = new CaseSortItem[sortFields.Count];
                i = 0;
                foreach (var item in sortFields) {
                    csp.sort[i] = new CaseSortItem();
                    csp.sort[i].property = item.Key;
                    csp.sort[i].desc = item.Value;
                    i++;
                }
            }

            var jsonText = JsonConvert.SerializeObject(csp);
            return jsonText;
        }
    }
}
