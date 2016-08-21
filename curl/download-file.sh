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

if [ $# != 2 ]; then
  echo
  echo
  echo -e "\t Usage: download.sh <document identifier to download> <file name to save as>"
  echo
  echo
  exit
fi

identifier=$1
filename=$2

###Download and save the given docuemnt
curl \
  ${CURL_DEBUG} \
  --fail \
  -s \
  -H "Authorization: Bearer ${DXC_AUTH_ACCESS_TOKEN}" \
   -o "$filename" \
  ${DXC_API_SERVER}/apigw/webservices/rest/apigw/docs/download/$identifier
if [ $? -ne 0 ]; then
  (>&2 echo "error executing request")
  exit 1
fi
