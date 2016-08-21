# CURL API Samples

This folder contains a set of bash shell scripts that
utilize `curl` to interact with the IMR CA API.

The curl scripts provided as part of the SDK are provided
solely for education purposes. These samples demonstrate
the basic interactions that occur without implying a
further dependency on 3rd party libraries that you may
prefer to use in your implementation tool choice.

NOTE: these samples are only available for OS X and Linux
environments. They have not been tested for use in a Windows
bash environment.

Windows Subsystem for Linux (Beta)

## TOOLING

In order to run these scripts you need the following:

- an activated service account
- Bash shell (OS X/Linux only)
- [jq 1.5](https://stedolan.github.io/jq/)
- [curl](https://curl.haxx.se)

## SETTING UP

In order to make use of the SDK you need to have four
bits of information available:

1. Authentication server endpoint (`servers.sh`)
1. API server endpoint (`servers.sh`)
1. Username with service permissions (`credentials.sh`)
1. Password for the service account (`credentials.sh`)

For the scripts in this folder these settings need to
be exposed as environment variables. To enable this
please make use of the `sample-credentials.sh` and
`sample-servers.sh` files as templates to establish
the values. Copy these files to `credentials.sh` and
`servers.sh` respectively.

### SERVERS.SH

```shellscript
#!/bin/bash
export DXC_AUTH_SERVER=http://imr-ca-sandbox.maxird.com
export DXC_API_SERVER=$DXC_AUTH_SERVER
```

### CREDENTIALS.SH

```shellscript
#!/bin/bash
export DXC_AUTH_USERNAME=service.zesty@maxird.com
export DXC_AUTH_PASSWORD=<YOUR USER PASSWORD>
```

## RUNNING SAMPLES

1. Open a terminal session
1. Change into the sample folder (e.g. this folder)
1. Run `source ./login.sh` to login
1. Run `./SAMPLE-NAME.sh` to execute sample

## SAMPLES INDEX

- `login.sh` - performs a login to enable other scripts to be used
- `refresh.sh` - performs a token refresh to extend your session
- `cases.sh` - performs an example case search
- `rfi.sh` - performs an example request for information case search
- `upload.sh` - uploads single or multiple files to a case
- `document-list.sh` - retrieves a list of documents for a case
- `download-file.sh` - retrieve the contents of a single document
- `download-case-files.sh` - retrieve all documents on a case

## OTHER FILES

- `servers.sh` - export file you put pointers to the servers
   you are talking to
- `credentials.sh` - export file you put your username and
   password in to be used by the `login.sh` scripts

## LOGIN AND GET LIST OF CASES

```shellscript
## log into the API
#
source ./login.sh

./cases.sh
```

## REFRESH YOUR ACCESS TOKEN

```shellscript
## if you get a 401 after logging in your token may of expired
## refresh it
#
source ./refresh.sh
```

## GETTING LIST OF CASES

```shellscript
## get the list of cases
#
./cases.sh
```

## GETTING LIST OF RFI CASES

```shellscript
## get the list of cases in RFI status
#
./rfi.sh
```

## UPLOAD DOCUMENT TO A CASE

```shellscript
## get the first case in the list
#
id=$(./cases.sh | jq -r '.results[].caseNumber' | head -1)
if [ -z "$id" ]; then
  echo "no cases available"
  exit 1
fi

## push the document to the case
#
./upload.sh my-medical-record-file.pdf ${id}
```

## DOWNLOAD ALL DOCUMENT FOR A CASE

```shellscript
## grab the first case returned
#
id=$(./cases.sh | jq -r '.results[].caseNumber' | head -1)
if [ -z "$id" ]; then
  echo "no cases available"
  exit 1
fi

## create a folder and download the files to it
#
mkdir -f output
cd output
../download-case-files.sh $id
cd ..
```
