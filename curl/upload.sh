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

### Function to upload the given file. File name has to follow case naming convention.
upload_file(){
  filename=$1
  mime=$(file -b --mime-type "$filename")
  if [ -z "$mime" ]; then
    mime=application/octet-stream
  fi
  result=$( \
      curl \
      ${CURL_DEBUG} \
      -s \
      --fail \
      -o /dev/null -w "%{http_code}" \
      -H "Authorization: Bearer ${DXC_AUTH_ACCESS_TOKEN}" \
      -F "file=@$filename; type=$mime" \
      ${DXC_API_SERVER}/apigw/webservices/rest/apigw/docs/upload
  )
  if [ -z "$result" ]; then
    result="UNKNOWN"
  fi
  echo "Upload of $filename ...$result"
}

### Function to upload a file and associate the document to the givem IMR Case Number
upload_file_to_case(){
  filename=$1
  caseid=$2
  mime=$(file -b --mime-type "$filename")
  if [ -z "$mime" ]; then
    mime=application/octet-stream
  fi
  result=$(curl \
      ${CURL_DEBUG} \
      -s \
      --fail \
      -o /dev/null -w "%{http_code}" \
      -H "Authorization: Bearer ${DXC_AUTH_ACCESS_TOKEN}" \
      -F "file=@$filename; type=$mime" \
      ${DXC_API_SERVER}/apigw/webservices/rest/apigw/docs/upload/cn/${caseid} \
  )
  if [ -z "$result" ]; then
    result="UNKNOWN"
  fi
  echo "Upload of $filename to case $caseid ...$result"
}

if [ $# = 0 ]; then
  echo
  echo
  echo -e "\t Usage: upload.sh \<folder or file name to upload\> \<optional IMR Case number to associate docuemnts with\>"
  echo
  echo
  exit
fi

file_or_folder="${1/%\//}"
caseid=$2

### If given file is a folder, then iterate over and upload individual files
if [ -d "$file_or_folder" ]; then

  for file in ${file_or_folder}/*; do
    ### Get access token refreshed for each document upload
    source $SRCDIR/refresh.sh 2> /dev/null

    if [ -f "$file" ]; then
      if [ $# = 2 ]; then
        upload_file_to_case "$file" "$caseid"
      else
        upload_file "$file"
      fi
    fi
  done
else
  if [ ! -f "$file_or_folder" ]; then
    (>&2 echo "error: file or folder named $file_or_folder not present")
    exit 1
  fi
  if [ $# = 2 ]; then
    upload_file_to_case "$file_or_folder" $caseid
  else
    upload_file "$file_or_folder"
  fi
fi
