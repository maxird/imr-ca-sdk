#!/bin/bash

SRCDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

id=$1

if [ -z "$id" ]; then
  (>&2 echo "usage: download-case-files.sh <IMRID>")
  exit 1
fi

function map_type()
{
  type=$1
  case $type in
  *pdf*)
    echo 'pdf'
    ;;
  *xml*)
    echo 'xml'
    ;;
  *json*)
    echo 'json'
    ;;
  *png*)
    echo 'png'
    ;;
  *text*)
    echo 'txt'
    ;;
  *)
    echo 'bin'
    ;;
  esac
}

## get the list of documents and reshape it
#
filter='.results[] | {name: .name, id: .renderings[].identifier, type: .renderings[].type} | map(.) | @csv'
# filter='.results[] | {name: .name, id: .renderings[].identifier, type: .renderings[].type}'
list=$($SRCDIR/document-list.sh ${id} | jq -r "${filter}")

# echo $list
oldifs=$IFS
IFS=$'\n'
for line in $list; do
  name=$(echo $line | awk -F "\",\"*|\"" '{print $2}')
  identifier=$(echo $line | awk -F "\"*,\"*" '{print $2}')
  filetype=$(echo $line | awk -F "\"*,\"*" '{print $3}')
  ext=$(map_type $filetype)

  echo "Retrieving $identifier as \"$name.$ext\""
  $SRCDIR/download-file.sh $identifier "$name.$ext"
done
IFS=$oldifs
