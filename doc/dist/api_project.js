define({
  "name": "DXCIMRCA",
  "version": "0.9.0",
  "description": "DXC IMR CA API",
  "title": "DXC IMR CA API",
  "url": "http://imr-ca-sandbox.maxird.com/",
  "header": {
    "title": "Overview",
    "content": "<h1>Overview</h1>\n<p>API services for the DXC IMR Portal are provided by two main back-end services: KeyCloak and DXC API Gateway.  The DXC Portal and the API Gateway are secured with OAuth2 provided by KeyCloak.  At the API level, KeyCloak provides the <a href=\"#api-Authentication-Login\">Login</a> and <a href=\"#api-Authentication-Refresh\">Refresh</a> APIs.  All other API srices are provided by the DXC API Gateway.  In order to access any of the DXC API Gateway services, you will need to pass the <code>Access Token</code> provided by KeyCloak in the Authorization HTTP header as the Bearer token.  See any of the DXC Request examples for addition clarity.</p>\n<p>For simplicity both APIs are document here and hosted on the same service endpoint in the sandbox.  In production these will be deployed at different end points.  The sample code provided in the SDK allows for them to be configured independently, as is recommended for developers coding up clients to this API.</p>\n"
  },
  "footer": {
    "title": "",
    "content": "<p>Copyright © 2016, MAXIMUS Inc. All Rights Reserved.</p>\n"
  },
  "sampleUrl": false,
  "apidoc": "0.2.0",
  "generator": {
    "name": "apidoc",
    "time": "2016-08-23T19:54:09.725Z",
    "url": "http://apidocjs.com",
    "version": "0.16.1"
  }
});
