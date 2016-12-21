#!/usr/bin/env bash
# A script to demonstrate getting a list of (zero or more) dates for which unacknowledged NOARFI manifest exists.
# The dates reflect the system date when the NOARFI letter was generated. The dates format is ISO 8601 (i.e. yyyy-MM-dd).
#
# This script depends on a fully populated servers.sh - see sample-servers.sh
#
# login sets up tokens for secure access.  Typically, login or is sourced in the parent shell to take advantage of the
# relatively long lifespan of the token. login.sh populates a set of tokens:
#  DXC_AUTH_TOKENS
#  DXC_AUTH_ACCESS_TOKEN
#  DXC_AUTH_REFRESH_TOKEN
#  DXC_AUTH_ID_TOKEN
#
# Repeatedly sourcing login overwrites existing tokens and is a dangerours resource leak.
# source ./login.sh

# Similarly, refresh will update DXC_AUTH_ACCESS_TOKEN, using the DXC_AUTH_REFRESH_TOKEN (established during login) as credentials.
# refresh should be sourced in the parent shell
# source ./refresh.sh

# DATA_TYPE of text/plain creates CSV output and is intended to create output similar to the existing FTP file transfer
# If DATA_TYPE is not populated, output format will default to JSON (application/json)

# by default, output will go to stdout.  To capture the result in a predictable file, build an output filename
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

## Data type for list is only JSON

DATA_TYPE=application/json
URL=${DXC_API_SERVER}/apigw/webservices/rest/apigw/events/noarfi/
NOARFI=$( \
    curl -s \
    ${CURL_DEBUG} \
    -k -X GET \
    -H "Authorization: Bearer ${DXC_AUTH_ACCESS_TOKEN}" \
    -H "Content-Type: application/json" \
    -H "Accept: ${DATA_TYPE}" \
    ${URL} \
)

NOMATCH=`echo ${NOARFI} |grep NO_MATCHING_DATA `
AUTH_ERROR=`echo ${NOARFI}|grep ":401," ` 
NF_ERROR=`echo ${NOARFI}|grep ":404," `

if [ -n "${NOMATCH}" ]; then
   echo ${NOMATCH}
   exit 0
elif [ -n  "${AUTH_ERROR}" ]; then
   echo Unable to authenticate.  source { login.sh | refresh.sh } to update your access tokens.
   exit -1
elif [ -n  "${NF_ERROR}" ]; then
   echo NOARFI detail for ${date} was not found
   echo ${NF_ERROR}
   echo  ${DXC_AUTH_USERNAME} using ${URL}
   exit -1
else
   echo -e "SUCCESS:\n\t"[${NOARFI}]
fi;


