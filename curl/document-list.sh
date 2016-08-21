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

args=("$@")
caseid=${args[0]}
if [ -z "$caseid" ]; then
  (>&2 echo "usage: $(basename ${0}) <IMRID>")
  exit 1
fi

data=$( \
    curl \
    -s \
    ${CURL_DEBUG} \
    -H "Authorization: Bearer ${DXC_AUTH_ACCESS_TOKEN}" \
    ${DXC_API_SERVER}/apigw/webservices/rest/apigw/docs/search/cn/${caseid} \
)
if [ $? -ne 0 ]; then
  (>&2 echo "error executing request")
  exit 1
fi

## failed requests return no content
if [ -z "$data" ]; then
  (>&2 echo "error: request returned no data")
  exit 1
fi

echo $data | jq '.' 2> /dev/null
if [ $? -ne 0 ]; then
  # what we got back isn't json
  (>&2 echo "error: $data")
  exit 1
fi
