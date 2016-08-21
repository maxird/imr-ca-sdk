#!/bin/bash

SRCDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

## try to make sure we are sourced
if [ "${BASH_SOURCE[0]}" == "${0}" ]; then
  (>&2 echo "run as 'source ./login.sh' or '. ./login.sh'")
  exit 1
fi

if [ ! -f $SRCDIR/servers.sh ]; then
  (>&2 echo "you need to populate servers.sh - see sample-servers.sh")
  return 1
fi
source $SRCDIR/servers.sh

if [ ! -f $SRCDIR/credentials.sh ]; then
  (>&2 echo "you need to populate credentials.sh - see sample-credentials.sh")
  return 1
fi
source $SRCDIR/credentials.sh

if [ -z "$DXC_AUTH_SERVER" ]; then
  (>&2 echo "auth server is not set")
  return 1
fi

if [ -z "$DXC_AUTH_USERNAME" ]; then
  (>&2 echo "auth username not set")
  return 1
fi

if [ -z "$DXC_AUTH_PASSWORD" ]; then
  (>&2 echo "auth password not set")
  return 1
fi

## Reset all our tokens
export DXC_AUTH_TOKENS=""
export DXC_AUTH_ACCESS_TOKEN=""
export DXC_AUTH_REFRESH_TOKEN=""
export DXC_AUTH_ID_TOKEN=""

##Realm name to login to
REALM=dxc-externals

###Get Access Token
DXC_AUTH_TOKENS=$( \
    curl \
        -s \
        ${CURL_DEBUG} \
        ${DXC_AUTH_SERVER}/auth/realms/${REALM}/protocol/openid-connect/token \
        --data "grant_type=password&client_id=dxc&username=${DXC_AUTH_USERNAME}&password=${DXC_AUTH_PASSWORD}"
)

if [ $? -ne 0 ]; then
  (>&2 echo "error attempting login")
  return 1
fi


result=$(echo $DXC_AUTH_TOKENS | jq -r '.error_description')
if [ "$result" != "null" ]; then
  (>&2 echo "error: $result")
  return 1
fi

#Export to the environment
export DXC_AUTH_TOKENS
export DXC_AUTH_ACCESS_TOKEN=$(echo $DXC_AUTH_TOKENS | jq -r .access_token)
export DXC_AUTH_REFRESH_TOKEN=$(echo $DXC_AUTH_TOKENS | jq -r .refresh_token)
export DXC_AUTH_ID_TOKEN=$(echo $DXC_AUTH_TOKENS | jq -r .id_token)
