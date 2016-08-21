#!/bin/bash

SRCDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

if [ ! -f $SRCDIR/servers.sh ]; then
  (>&2 echo "you need to populate servers.sh - see sample-servers.sh")
  exit 1
fi
source $SRCDIR/servers.sh

if [ -z "$DXC_API_SERVER" ]; then
  (>&2 echo "DXC API server must be set")
  exit 1
fi

if [ -z "$DXC_AUTH_ACCESS_TOKEN" ]; then
  (>&2 echo "you must login first - see login.sh")
  exit 1
fi

## Data type expected as result. Possible values are
## 1. application/json  -- Will get you JSON String
## 2. text/plain  -- Will get you csv, comma separated records separated by line feed.
if [ -z "$DATA_TYPE" ]; then
  DATA_TYPE=application/json
fi

## Search Criteria. Following are some sample criteria to filter and sort on.
CRITERIA='{"sort":[{"property":"dateOfInjury","desc":false}]}'
if [ $# = 1 ]; then
  CRITERIA="{\"IMRID\":\"$1\"}"
fi
CRITERIA2='{"start":0,"limit":25,"sort":[{"property":"dateOfInjury","desc":false}]}'

###Get All Cases for me
CASES=$( \
    curl -s \
    ${CURL_DEBUG} \
    -X POST \
    -H "Authorization: Bearer ${DXC_AUTH_ACCESS_TOKEN}" \
    -H "Content-Type: application/json" \
    -H "Accept: ${DATA_TYPE}" \
    --data ${CRITERIA} \
    ${DXC_API_SERVER}/apigw/webservices/rest/apigw/cases/search \
)
if [ $? -ne 0 ]; then
  (>&2 echo "error executing search request")
  exit 1
fi

result=$(echo $CASES | jq -r '.error' 1>&2 /dev/null)
if [ "$result" != "null" ] && [ "$result" != "" ]; then
  (>&2 echo "error: $result")
  exit 1
fi

if [ "$DATA_TYPE" = "application/json" ]; then
  echo $CASES | jq '.' 2> /dev/null
  if [ $? -ne 0 ]; then
    # what we got back isn't json
    (>&2 echo "error: $CASES")
    exit 1
  fi
else
  for line in $CASES; do
    echo $line
  done
fi
