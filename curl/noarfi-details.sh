#!/usr/bin/env bash
# A script to demonstrate accessing the list of NOARFI records for a given date. The date is one of the dates listed in
# the NOARFI Manifest date list service which has not been acknowledged yet.
# Results are sent as CSV. The first row will be a header row with the field names.
#
# To facilitate adoption with legacy users, an optional "legacy" mode is available.
# In legacy mode, the fields will retain their original names and an additional 10 filler fields will be appended to each
# row
#
# The dates format is ISO 8601 (i.e. yyyy-MM-dd).
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

# if $1 validates, the date variable is set
function validate {
    year=`echo ${1} |tr "-" " " |awk '{print $1}'`
    month=`echo ${1} |tr "-" " "|awk '{print $2}'`
    day=`echo ${1} |tr "-" " "  |awk '{print $3}'`
    if ! [ "$month" -ge 1 -a "$month" -le 12 ]; then echo month: ${month} is out of range; exit 1; fi
    if ! [ "$day"   -ge 1 -a "$day"   -le 31 ]; then echo day:   ${day}   is out of range; exit 1; fi
    # clear out the temp variables, don't need the noise
    year="";  month="";  day=""
    date=${1}
}
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

if [ "$#" -ne 1 ]; then
    echo "Illegal number of parameters"
    echo "Date argument required: The dates format is ISO 8601 (i.e. yyyy-MM-dd)"
    exit 1
else
    validate $1
fi

DATA_TYPE=text/plain
URL=${DXC_API_SERVER}/apigw/webservices/rest/apigw/events/noarfi/${date}

NOARFI=$( \
    curl -s \
    ${CURL_DEBUG} \
    -k -X GET \
    -H "Authorization: Bearer ${DXC_AUTH_ACCESS_TOKEN}" \
    -H "Content-Type: application/json" \
    -H "Accept: ${DATA_TYPE}" \
    ${URL}  \
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
   outputFile=noarfi-details-${date}.csv
   echo ${NOARFI} > noarfi-details-${date}.csv
   echo -e "SUCCESS:\n\t"[See ${outputFile}]
   if ! [ -z "${CURL_DEBUG}" ] ; then
        echo -e "\n${NOARFI}\n"
   fi
fi;


