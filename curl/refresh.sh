#!/bin/bash

SRCDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

## try to make sure we are sourced
if [ "${BASH_SOURCE[0]}" == "${0}" ]; then
  (>&2 echo "run as 'source ./refresh.sh' or '. ./refresh.sh'")
  exit 1
fi

if [ ! -f $SRCDIR/servers.sh ]; then
  (>&2 echo "you need to populate servers.sh - see sample-servers.sh")
  return 1
fi
source $SRCDIR/servers.sh

if [ -z "$DXC_AUTH_REFRESH_TOKEN" ]; then
  (>&2 echo "no token to refresh - use login.sh first")
  return 1
fi

##Realm name to login to
REALM=dxc-externals

###Get Access Token
TOKEN=$(
  curl \
    ${CURL_DEBUG} \
    -s \
    --data "client_id=dxc&grant_type=refresh_token&refresh_token=${DXC_AUTH_REFRESH_TOKEN}" ${DXC_AUTH_SERVER}/auth/realms/${REALM}/protocol/openid-connect/token
)
if [ $? -ne 0 ]; then
  (>&2 echo "error executing refresh request")
  return 1
fi

result=$(echo $TOKEN | jq -r '.error_description')
if [ "$result" != "null" ]; then
  (>&2 echo "error: $result")
  return 1
fi

export DXC_AUTH_ACCESS_TOKEN=$(echo $TOKEN | jq -r .access_token)

(>&2 echo "token refreshed")
