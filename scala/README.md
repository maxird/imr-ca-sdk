# SCALA API Samples

This folder contains the scala classes to provide a working sample of scala interacting with the IMR CA RESTful API.
These classes provide working samples of a RESTful client.  The tests excercise the samples to provide practical examples
of working code.

## TOOLING

You will need the following installed in your development environment:

- [Maven 3.2.x](https://maven.apache.org) or above
- [Oracle Java SE JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [scala 2.11.7] or above
- [JCE Unlimited Strength policy files](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)

NOTE: issues were experienced with the encryption libraries with version `1.8.0_92`. Known working versions:

- 1.8.0_45
- 1.8.0_102

## SETTING UP

In order to make use of the SDK you need to have four bits of information available:

1. Authentication server endpoint (`config.properties`)
1. API server endpoint (`config.properties`)
1. Username with service permissions (`config.properties`)
1. Password for the service account (`config.properties`)

There are two configuration files that you will have to prepare in order to run the maven build
and execute the supplied code.

### CONFIG.PROPERTIES

Make a copy of the `config/sample-config.properties` and populate with the server information that
was provided to you.

    ## provide the location of the auth and api servers
    #
    authserver.base.url=https://imr-ca-sandbox.maxird.com/auth
    api.endpoint.base=https://imr-ca-sandbox.maxird.com
    authserver.realm=dxc-externals
    encrypt.rest.user.name=username@domain.com
    encrypt.rest.user.name=password


NOTE: the code will automatically encrypt the credentials (marked with cred. prefix) in the file when run. See below for details
on the library dependencies that are used to provide this functionality.

    encrypt.rest.user.name=ENC(3q+IhJiGhGTQQ7lpRFsllwqqFehYXOA9Gyp75VTpfRvHBMGzvu4lkg\=\=)
    encrypt.rest.user.name=ENC(3q+IhJiGhGTQQ7lpRFsllwqqFehYXOA9Gyp75VTpfRvHBMGzvu4lkg\=\=)

### TEST.PROPERTIES

Make a copy of the `config/sample-test.properties` and populate with the credentials that were provided
to you.

    ## unit test configuration file
    #
    test.data.dir=.
    pdf.file.name=SamplePdf.pdf
    zip.file.name=SampleZip.zip
    text.file.name=SampleText.txt


### API.PROPERTIES

This file contains the service endpoints and does not require modification for use.

### ALTERNATE CONFIGURATION LOCATION

The default configuration folder is `./config`. It can be changed using
`-Dcom.maximus.ird.configroot=/path/to/configuration`.

## BUILD AND CLEAN

Using a terminal/console session you can use maven to clean and build the samples.

```shellscript
## clean working copy
#
mvn clean

## build and run tests
#
mvn clean test

## build, test and package
mvn clean package

## use a custom configuration folder
#
mvn clean test -Dcom.maximus.ird.configroot=/path/to/configuration

```

The build output will produce a `tar` file with the following contents:

- `target/imr-scala-sdk.tar`

    1. dependencies (lib directory)
    1. compiled classes (lib/imr-webservices-sdk.jar)
    1. README.md (this file)
    1. source code (src/main)
    1. test code (src/test)

## THIRD PARTY DEPENDENCIES

Third party dependencies are captured in the maven project file (pom.xml).  These are limited to:

- junit -  Testing framework
- scalatest_2.11 Scala testing extensions
- jersey-client - Rest client framework
- jersey-multipart -Additional rest support for file transfers
- gson - son support
- jasypt Support for encrypted properties

These selections are implementation details that can be changed.  Use the test cases to ensure the continued viability of
any deviations from the selected libraries.

## TOKEN PERSISTENCE

To avoid the unnecessary proliferation of tokens,tokens and metadata are persisted.
It is necessary to have read/write permissions to the users home directory for the runtime.
The file that is created is .com.maximus.imr.restRequestor.token.json.  For security purposes, it is encrypted.
Errors persisting the tokens are ignored.  The side-effect will be an increase in token creations.

See com.maximus.imr.rest.RESTRequestor.

## RUNTIME CLASSES INDEX
- Java Classes
    - CaseApiResponse.java
    - CaseCriterion.java
    - DocumentListResponse.java
    - JavaFileIO.java
    - JavaPropertyFile.java
    - JsonToObject.java
    - TokenResponse.java
- Scala Classes
    - AsciiEncryption.scala
    - CaseSearchService.scala
    - DocumentService.scala
    - HttpUtil.scala
    - IConnect.scala
    - Login.scala
    - Properties.scala
    - RETURN.scala
    - RFICriterion.java
    - RfiSearchService.scala

## TEST CLASSES INDEX
- CaseSearchServiceTest.scala
- DocumentServiceTest.scala
- IConnectTest.scala
- RfiSearchServiceTest.scala
- ServiceTest.scala
- TestEncryption.scala
- TestProperties.scala

