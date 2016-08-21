# IMR CA .NET API Examples

These folders contain C# libaries along with
demonstration console applications for interacting
with the authentication and document exchange
services.

## TOOLING

This code was prepared such that it should be
directly usable on Windows, Linux and OS X. It relies
on [.NET Core](https://www.microsoft.com/net/core)
as the platform.

## SETTING UP

In order to make use of the SDK you need to have four
bits of information available:

1. Authentication server endpoint (`configuration.ini`)
1. API server endpoint (`configuration.ini`)
1. Username with service permissions (`configuration.ini`)
1. Password for the service account (`configuration.ini`)

For the samples in this folder the settings need to
be exposed in a file named `configuration.ini` in the
same folder as this readme file.

Make a copy of the `sample-configuration.ini` file and
populate it with the credentials and server information
that was provided to you.

```ini
[servers]
api=http://imr-ca-sandbox.maxird.com
auth=http://imr-ca-sandbox.maxird.com

[credentials]
username=service.zesty@maxird.com
password=DXC_AUTH_PASSWORD
```

## BUILD AND CLEAN

The same folder that has this README.md also contains
shell scripts and batch files to clean and build the
samples.

* `build.sh | build.cmd` - restores and build all projects
* `clean.sh | clean.cmd` - removes build assets

In order to have all dependencies built start with a terminal/console
session and execute the following:

```shellscript
## Windows
#
.\build.cmd

## OS X/Linux
#
./build.sh

```

## RUNNING SAMPLES

1. Open a terminal session
1. Change into the sample folder (e.g. `src/Auth.Login/`)
1. Run `dotnet restore` if you have not done so
1. Run `dotnet run`

Note that some of the samples require parameters and that
all samples require that you have provided a `configuration.ini`
file to supply server names and credentials.

## RUNNING TESTS

1. Open a terminal session
1. Change into the test folder (e.g. `tests/Maximus.IRD.Samples.Keycloak.Tests`)
1. Run `dotnet restore` if you have not done so
1. Run `dotnet test`

Note that unit tests only provide local testing. None of the
tests interact with the server.

## SAMPLE APPLICATIONS

There are multiple projects in this folder that are
targeted at showing how to use various services.

## LOGIN SAMPLE

Folder: [`./src/Auth.Login/`](src/Auth.Login/)

This sample performs a login against the authentication
server based on server information and credentials sourced
from a file called `configuration.ini` in the *parent*
folder.

It shows the persistence of the tokens (in a non-secure manner)
so that they can be re-used by other processes.

The token written to the console can be copied and pasted
into the [JWT.io](https://jwt.io) site to view the actual
JSON structure.

### TOKEN CACHE & REUSE

Both the `access_token` and `refresh_token` are cached locally
in the running user's `home` folder in a folder named `.keycloak-ird`.

It is preferable to establish an authenticated session and retain
that for the lifetime of the refresh token however you may wish
to terminate the session expressly using the `Logout()` method.

NOTE: If your implementation does not reuse tokens and also does not
properly terminate sessions your service account may be disabled
until the implementation issue is addressed.

### AUTHENTICATION LIFECYCLE

* First contact requires authentication with credentials
  * successful login returns both an `access_token` and
    `refresh_token`
* Tokens have a limited lifetime and expire
* The `access_token` is used as the `Bearer` token in service
  requests
* The `refresh_token` is used to request a new `access_token`
  from the authentication server
* Error conditions:
  * Attempts to use an expired token will fail with a `401`
  * `refresh_tokens` may be revoked
  * User account may be disabled or removed
  * User account may require attention (e.g. password change)

### RUNNING

    dotnet restore
    dotnet run

### SAMPLE OUTPUT

    [auth]: transfer to refresh on missing access token
    [auth]: transfer to login on missing refresh token
    [auth]: attempting login as service.ca1only@maxird.com
    [auth]: remote request http://localhost:9000/auth/realms/dxc-externals/protocol/openid-connect/token
    [auth]: login completed
    ------------ BEGIN TOKEN ------------
    ... token ...
    ------------  END TOKEN  ------------

### SAMPLE OUTPUT - (re-use access token)

    [auth]: returning cached access token
    ------------ BEGIN TOKEN ------------
    ... token ...
    ------------  END TOKEN  ------------

### SAMPLE OUTPUT - (re-use refresh token)

    [auth]: transfer to refresh on expiring access token: 95
    [auth]: attempting refresh
    [auth]: remote request http://localhost:9000/auth/realms/dxc-externals/protocol/openid-connect/token
    [auth]: token refresh completed
    ------------ BEGIN TOKEN ------------
    ... token ...
    ------------  END TOKEN  ------------

## LOGOUT SAMPLE

Folder: [`./src/Auth.Logout/`](src/Auth.Logout/)

This sample shows using the `Logout()` method to remove the
access and refresh tokens with notification to the authentication
server.

NOTE: this sample is provided for completeness. It is recommended
that you cache tokens and reuse them.

### RUNNING

    dotnet restore
    dotnet run

### SAMPLE OUTPUT

    [auth]: transfer to refresh on missing access token
    [auth]: transfer to login on missing refresh token
    [auth]: attempting login as service.ca1only@maxird.com
    [auth]: remote request http://localhost:9000/auth/realms/dxc-externals/protocol/openid-connect/token
    [auth]: login completed
    ------------ BEGIN TOKEN ------------
    ... token ...
    ------------  END TOKEN  ------------
    [auth]: logout request requested
    [auth]: remote request http://localhost:9000/auth/realms/dxc-externals/protocol/openid-connect/logout
    [auth]: removing in-memory cache
    [auth]: removing on-disk cache
    [auth]: logout completed

## REFRESH SAMPLE

Folder: [`./src/Auth.Refresh/`](src/Auth.Refresh/)

This sample demonstrates the behaviour when a refresh token must
be used to establish a new access token.

The sandbox environment has been configured with a short token
lifecycle so you can see this behaviour. The sample logs in
(or refreshes as necessary) and then waits for a set time to
ensure the active access token has expired. It then attempts
to use the token again which will result in an automatic request
cycle to refresh the access token or a login cycle.

### RUNNING

    dotnet restore
    dotnet run

### SAMPLE OUTPUT

    [auth]: transfer to refresh on missing access token
    [auth]: transfer to login on missing refresh token
    [auth]: attempting login as service.ca1only@maxird.com
    [auth]: remote request http://localhost:9000/auth/realms/dxc-externals/protocol/openid-connect/token
    [auth]: login completed
    ------------ BEGIN TOKEN ------------
    ... token ...
    ------------  END TOKEN  ------------

    Sleeping 5 minute(s) to wait for token to expire

    [auth]: transfer to refresh on expiring access token: 100
    [auth]: attempting refresh
    [auth]: remote request http://localhost:9000/auth/realms/dxc-externals/protocol/openid-connect/token
    [auth]: token refresh completed
    ------------ BEGIN TOKEN ------------
    ... token ...
    ------------  END TOKEN  ------------

## CASE SEARCH SAMPLE

Folder: [`./src/Case.Search/`](src/Case.Search/)

This sample shows a variety of ways to establish case
search criteria and the method of moving through the
paged responses returned from the case search service.

### RUNNING

    dotnet restore
    dotnet run

### SAMPLE OUTPUT

    {}
    {"IMRID":"CM16-1235678"}
    {"limit":500}
    {"status":["5","2"],"sort":[{"property":"assignDate","desc":true},{"property":"closedReason","desc":false},{"property":"status","desc":true}]}
    [auth]: returning cached access token
    caseNumber CM16-10000001
    [auth]: returning cached access token
    caseNumber CM16-10000002
    [auth]: returning cached access token
    caseNumber CM16-10000003
    [auth]: returning cached access token
    caseNumber CM16-10000004
    [auth]: returning cached access token
    caseNumber CM16-10000005
    [auth]: returning cached access token
    caseNumber CM16-10000006
    [auth]: returning cached access token
    caseNumber CM16-10000007

## DOCUMENT DOWNLOAD SAMPLE

Folder: [`./src/Document.Download/`](src/Document.Download/)

This sample shows how download a document from the
document service.

### RUNNING

    dotnet restore
    dotnet run <identifier> <filename>

### SAMPLE OUTPUT

    [dxc]: DownloadFile: [20.5000.214/eec7b788c47364777ba4 => out.pdf]
    [auth]: returning cached access token
    [dxc]: remote request http://localhost:9000/apigw/webservices/rest/apigw/docs/download/20.5000.214/eec7b788c47364777ba4
    document downloaded

### SAMPLE OUTPUT - bad identifier

    [dxc]: DownloadFile: [20.5000.214/eec7b788c47364777ba4G => out.pdf]
    [auth]: returning cached access token
    [dxc]: remote request http://localhost:9000/apigw/webservices/rest/apigw/docs/download/20.5000.214/eec7b788c47364777ba4G
    [dxc]: response: [NotFound]
    error: 404 Not Found

## DOCUMENT LIST SAMPLE

Folder: [`./src/Document.List/`](src/Document.List/)

This sample shows how to get the list of documents that
are available for a case.

### RUNNING

    dotnet restore
    dotnet run <caseNumber>

### SAMPLE OUTPUT - with results

    [dxc]: GetDocuments: [CM16-10000006]
    [auth]: returning cached access token
    [dxc]: remote request http://localhost:9000/apigw/webservices/rest/apigw/docs/search/cn/CM16-10000006
    20.5000.214/bd5528733ae70799c6ef        application/pdf "Additional Information"
    20.5000.214/d0c150c304b6e05a8d88        application/pdf "Medical Records"
    20.5000.214/eec7b788c47364777ba4        application/pdf "NOARFI"

### SAMPLE OUTPUT - case not found

    [dxc]: GetDocuments: [CM16-100000072]
    [auth]: returning cached access token
    [dxc]: remote request http://localhost:9000/apigw/webservices/rest/apigw/docs/search/cn/CM16-100000072
    [dxc]: response: [NotFound]
    error: Case Not Found

## DOCUMENT UPLOAD SAMPLE

Folder: [`./src/Document.Upload/`](src/Document.Upload/)

This sample perform an upload of a file to a case. To see
an example of getting a case number have a look at the
[Case Search Sample](#case-search-sample).

### SAMPLE OUTPUT

    dotnet restore
    dotnet run <caseNumber> <filename> <title>

### SAMPLE OUTPUT

    [auth]: returning cached access token
    [dxc]: pattern: .pdf
    [dxc]: remote request http://localhost:9000/apigw/webservices/rest/apigw/docs/upload/cn/CM16-10000007
    [dxc]: file: Error Upload Testing.pdf as application/pdf
    [dxc]: response: 200 == text/plain == SUCCESS
    document uploaded

## LIBRARIES

There are a few libraries in place to support the sample
applications. Each serves a specific purpose.

### Maximus.IRD.Samples.Common

Folder: [`./src/Maximus.IRD.Samples.Common/`](src/Maximus.IRD.Samples.Common/)

Externalizes the token and logout actions from the Keycloak
client library.

### Maximus.IRD.Samples.Config

Folder: [`./src/Maximus.IRD.Samples.Config/`](src/Maximus.IRD.Samples.Config/)

Provides configuration management for the sample applications.

### Maximus.IRD.Samples.DXCClient

Folder: [`./src/Maximus.IRD.Samples.DXCClient/`](src/Maximus.IRD.Samples.DXCClient/)

Provides a sample implementation of a client that operates
against the JSON-based API provided by the Document XChange.

### Maximus.IRD.Samples.KeycloakClient

Folder: [`./src/Maximus.IRD.Samples.KeycloakClient/`](src/Maximus.IRD.Samples.KeycloakClient/)

Provides a sample implementation of an OAuth 2.0-based client
that works with Keycloak. The library provides both automatic
token refresh and local caching of credentials stored in the
running users home folder.
