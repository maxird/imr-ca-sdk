/**
 * Copyright (C) 2016, MAXIMUS Inc. All Rights Reserved.
 */

/**
 * @author 11040
 *
 */

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // apiDefines to include later

    /**
    * @apiDefine ErrorResponse
    * @apiError (Error 4xx) {json} 400 Bad Request
    * @apiError (Error 4xx) {json} 401 Unauthorized
    * @apiError (Error 4xx) {json} 403 Forbidden
    * @apiError (Error 4xx) {json} 404 Not Found
    * @apiError (Error 4xx) {json} 413 Request Entity Too Large
    * @apiError (Error 4xx) {json} 415 Unsupported Media Type
    * @apiError (Error 5xx) {json} 500 Server Error
    * @apiError (Error 5xx) {json} 503 Service Unavailable

    * @apiErrorExample JSON Error Response
    * {"status": http_status_code, "msg": "some error description", "refId": "reference-identifier"}
    */

    /**
    * @apiDefine OAuthSuccessResponse
    * @apiSuccess {String} access_token JWT Access Token (required for all API requests)
    * @apiSuccess {Number} expires_in Number of seconds until access token expires
    * @apiSuccess {Number} refresh_expires_in Number of seconds until refresh token expires
    * @apiSuccess {String} refresh_token JWT Refresh Token (required to obtain a new access token)
    * @apiSuccess {String} token_type
    * @apiSuccess {String} id_token
    * @apiSuccess {Number} not-before-policy
    * @apiSuccess {String} session_state
    */


    /**
    * @apiDefine CaseSearchResults
    * @apiSuccess {Object[]} results Array of matching cases
    * @apiSuccess {String} results.claimNumber "10000001"
    * @apiSuccess {String} results.caseNumber "CM16-10000001"
    * @apiSuccess {String} results.dateOfInjury Date/Time (ISO 8601) expressed in UTC
    * @apiSuccess {String} results.priority Standard or Expedited
    * @apiSuccess {String} results.injuredWorkerPrefix
    * @apiSuccess {String} results.injuredWorkerFirstName
    * @apiSuccess {String} results.injuredWorkerLastName
    * @apiSuccess {String} results.injuredWorkerMiddleInitial
    * @apiSuccess {String} results.injuredWorkrSuffix
    * @apiSuccess {String} results.employerName
    * @apiSuccess {String} results.treatmentRequested
    * @apiSuccess {String} results.claimsAdministratorAddress1
    * @apiSuccess {String} results.claimsAdministratorCity
    * @apiSuccess {String} results.claimsAdministratorState
    * @apiSuccess {String} results.claimsAdministratorZipCode
    * @apiSuccess {String} results.claimsAdministratorCompanyName
    * @apiSuccess {String} results.providerPrefix
    * @apiSuccess {String} results.providerFirtName
    * @apiSuccess {String} results.providerLastName
    * @apiSuccess {String} results.providerMidleInitial
    * @apiSuccess {String} results.providerSuffix
    * @apiSuccess {String} results.organizationName
    * @apiSuccess {String} results.status
    * <div>Numeric strings sent with following meanings</div>
    * <div>
    * <ul style="list-style: none">
    * <li>1 - Received</li>
    * <li>2 - Eligibility Review</li>
    * <li>3 - Records Requested</li>
    * <li>4 - Clinical Review</li>
    * <li>5 - Closed</li>
    * <li>6 - NOARFI</li>
    * <li>7 - Insufficient Records</li>
    * </ul>
    * </div>
    * @apiSuccess {Number} matchCount Total number of cases matching search request <span style="font-style: italic; font-weight: 700;">Not sent with CSV responses</span>
    * @apiSuccess {Number} start The index of the search results you wish returned. If not supplied or invalid it will be replaced with 0.  <span style="font-style: italic; font-weight: 700;">Not sent with CSV responses</span>
    * @apiSuccess {Number} limit The number of results to return. If not supplied the specific API will select the maximum results to return. If the value is considered too large by the specific API it may alter this value in the response and ignore the supplied value.  <span style="font-style: italic; font-weight: 700;">Not sent with CSV responses</span>
    * @apiSuccess {String} results.dateofURDecision Date/Time (ISO 8601) expressed in UTC
    * @apiSuccess {String} results.receiveDate Date/Time (ISO 8601) expressed in UTC
    * @apiSuccess {String} results.modificationDate Date/Time (ISO 8601) expressed in UTC
    */

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // AUTHENTICATION

    /**
    * @api {post} auth/realms/dxc-externals/protocol/openid-connect/token 00.Login
    * @apiName Login
    * @apiDescription DXC web services use KeyCloak for OAuth2 (more information <a href="http://imrcasdk.maxird.com/oauthoverview.html">here</a>).
    * Call this KeyCloak API first to login with your valid system service account credentials (see <a href="http://imrcasdk.maxird.com/oauthoverview.html#imr-ca-oauth-account-link">here</a> on how to get credentials).
    * Securely cache the <code>Access Token</code> and <code>Refresh Token</code> returned in this response for use in subsequent DXC API requests.
    * Note the time you obtained the <code>Access Token</code> and the returned <code>expires_in</code> value to calculate the expiration time of this access token.
    * On all subsequent API requests with DXC, pass along the <code>Access Token</code> in the HTTP Authorization header field.
    * Be sure to refresh your <code>Access Token</code> with the Refresh API request before it expires.</li>
    * <div style="font-style: italic;">Service Provided by KeyCloak</div>
    *
    *
    * @apiParam {String} grant_type Always use "password"
    * @apiParam {String} client_id Always use "dxc"
    * @apiParam {String} username Send your <code>ServiceAccount</code>
    * @apiParam {String} password Send your Service Account <code>Password</code>
    *
    * @apiVersion 1.0.0
    *
    * @apiGroup Authentication
    *
    * @apiExample OAuth2 Request
    * POST /auth/realms/dxc-externals/protocol/openid-connect/token HTTP/1.1
    * User-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)
    * Host: imr-ca-sandbox.maxird.com
    * Content-Type: application/x-www-form-urlencoded
    * Content-Length: 90
    *
    * grant_type=password&client_id=dxc&username=service.zesty2@maxird.com&password=S0m3P@wOrd
    *
    * @apiUse OAuthSuccessResponse
    *
    * @apiSuccessExample {json} OAuth2 Response
    * HTTP/1.1 200 OK
    * X-Powered-By: Undertow/1
    * Server: WildFly/10
    * Content-Type: application/json
    * Content-Length: 3847
    * Date: Wed, 17 Aug 2016 19:13:04 GMT
    *
    * {
    *   "access_token": "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIyNTZjOTMyNC02MTg1LTQ5MWMtYmQ0Ni1hZGE0ZWIyNzY2YTIiLCJleHAiOjE0NzE0NjQ5MjIsIm5iZiI6MCwiaWF0IjoxNDcxNDYxMzIyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6IjkzZmQ4NmJiLTg5NjgtNGVkYi1hMTZiLWMzY2ZmOWRkZGMxZiIsImNsaWVudF9zZXNzaW9uIjoiYWJiN2NiOTQtMTRiYy00OWMzLWJhMmYtMTVjZGMyNmVmM2RiIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.IGnhJCaUpBMZ-f5ZScnzJcNfnTLuaA0KsYYkdyQMMgGMd_wAj1d5SoVfsYd_vQkaSUyueqoRNApLFwv3BtkXDzyOLKBGGWl24Rmrt1-6pdSNOCpWkZmraFx4x9DfR7mSK13ucEvH35S1O_b9C41_96s_zH27ss9yTp_Q-G_rBvllAEbBu-rKk9ug0HzTNwlIyvg9g8AIgOZZUO8jB0Cu8Y0OjgQzdJwmw-zktmQhq5jf3R_1z--Syoe3m4gZbVG_SFJf3i06VG3rkMCED3AUr7yrXAOseaPkDBdQnoF7ActcHZxZDhB7CBwoyBL6WhekN7Mq38W87H01nLz8AOALIg",
    *   "expires_in": 3600,
    *   "refresh_expires_in": 86400,
    *   "refresh_token": "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI0NWJkNGIyNy0yMmI0LTQxMTctOGMwOS02YTg5YjA4YzYzYTQiLCJleHAiOjE0NzE1NDc3MjIsIm5iZiI6MCwiaWF0IjoxNDcxNDYxMzIyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImR4YyIsInNlc3Npb25fc3RhdGUiOiI5M2ZkODZiYi04OTY4LTRlZGItYTE2Yi1jM2NmZjlkZGRjMWYiLCJjbGllbnRfc2Vzc2lvbiI6ImFiYjdjYjk0LTE0YmMtNDljMy1iYTJmLTE1Y2RjMjZlZjNkYiIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJEZWZhdWx0IFNlcnZpY2UgUm9sZSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImR4YyI6eyJyb2xlcyI6WyJjYXNlLmFsbCIsImNhc2UuYXBwZW5kIiwiY2FzZS5hc3NpZ24iLCJjYXNlLmNvbW1lbnQiLCJjYXNlLmFkbWluIiwiY2FzZS5zdGF0ZSIsImNhc2UuaWRlbnRpZmllcnMiLCJjYXNlLnRyYW5zZmVyIiwiY2FzZS52aWV3IiwiY2FzZS5pbnZpdGUiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJ2aWV3LXByb2ZpbGUiXX19fQ.AQlRqa3ZNd7tv1Lb5qfw9Cl1F1WYG6XJs5FPBy8rr7W_q4676Ias2Ew5u8k9exlmrJhqWvMdY85tzBrVhNq4So3ZGzG084v0t2F1n4LX9w1kyo1q6ka-6nhiATwJC0wVHy0q-RjCSMElr2j5z3oxKO4LyT37123sRNZ4pOs1vA2D-ddEOX980NdVsyqX_MBW9p4OPC9esY0jZYXRX1svmcidyB6wj26vgpg9V-xiec3qxJuYLz8kHbtaa4jtVlEB-eKr_ZhWzl4x04lY6VtQ_G5pxEq04R1JQWHgI0befkdwcA1ut005OdablGmp3g_Hbmh07cdz0_HaKFo-yWD63A",
    *   "token_type": "bearer",
    *   "id_token": "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI5OGE4MDVhMC03Y2QxLTQ2ODEtYjE0YS0xNjcxYzZhNmQyOTQiLCJleHAiOjE0NzE0NjQ5MjIsIm5iZiI6MCwiaWF0IjoxNDcxNDYxMzIyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiSUQiLCJhenAiOiJkeGMiLCJzZXNzaW9uX3N0YXRlIjoiOTNmZDg2YmItODk2OC00ZWRiLWExNmItYzNjZmY5ZGRkYzFmIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsImdpdmVuX25hbWUiOiJaRVNUWVpFQlJBIiwiZmFtaWx5X25hbWUiOiJTZXJ2aWNlIiwiZW1haWwiOiJzZXJ2aWNlLnplc3R5MkBtYXhpcmQuY29tIiwicGFydGljaXBhbnQiOiIyMC41MDAwLjIxNC9wemVzdHkifQ.fn7UnGDkPaV8uh9681ZQqAexY0GVCKXkxObQ0zV_eiVinnqBfHuZt9-QZnEy4SIUD4EI6QDtS3Bx-Y4iIXg4kg6RfZ712rAppzRsmpyVDWyCHP4jgm6LWzdxEFt4r0mINoYuSrtUJH1R5f_zxAizv2Uu3Ee2_9oqPskhbljSZXaPFl4K6LBp6YeGwANWNoi77gDzK-al-AXeXnqRKjVw2ASPxixDmdaGivrVsG9VZRjRZNLCfHckr7F_5Jt0MqwYwFD7PbE4FXkuHEEzi6PpgSI15Ve12wUyWxKGnYYANQBWd6MpfVC0FjA0pfmd0jiMigvx1rt_GRgApQLWDULOQA",
    *   "not-before-policy": 0,
    *   "session_state": "93fd86bb-8968-4edb-a16b-c3cff9dddc1f"
    * }
    *
    */

    /**
    * @api {post} auth/realms/:realm/protocol/openid-connect/token 01.Refresh
    * @apiName Refresh
    * @apiDescription This KeyCloak service is called to obtain a new <code>Access Token</code> before the current one expires.  After calling this refresh service, applications should retain (and sufficiently secure the new <code>Access Token</code> returned in this response and use it in subsequent DXC API calls in place of the prior <code>Access Token</code>.  Failing to refresh an <code>Access Token</code> will result in DXC rejected requests with Access Denied once the Access Token has expired.    For security reasons its preferable that applications login only as often as necessary and rely on this refresh mechanism to obtain new <code>Access Tokens</code> rather than issue login request more frequently.
    *
    * <div style="font-style: italic;">Service Provided by KeyCloak</div>
    *
    * @apiParam {String} realm Always use "dxc-externals" <span style="font-style: italic">(sent in path)</span>
    * @apiParam {String} grant_type Always use "refresh_token"
    * @apiParam {String} client_id Always use "dxc"
    * @apiParam {String} refresh_token Send your JWT <code>Refresh Token</code>
    *
    * @apiVersion 1.0.0
    * @apiGroup Authentication
    *
    * @apiExample OAuth2 Request
    * POST /auth/realms/dxc-externals/protocol/openid-connect/token HTTP/1.1
    * User-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)
    * Host: imr-ca-sandbox.maxird.com
    * Content-Length: 1263
    * Content-Type: application/x-www-form-urlencoded
    * Expect: 100-continue
    *
    * grant_type=refresh_token&client_id=dxc&refresh_token=eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI0NWJkNGIyNy0yMmI0LTQxMTctOGMwOS02YTg5YjA4YzYzYTQiLCJleHAiOjE0NzE1NDc3MjIsIm5iZiI6MCwiaWF0IjoxNDcxNDYxMzIyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImR4YyIsInNlc3Npb25fc3RhdGUiOiI5M2ZkODZiYi04OTY4LTRlZGItYTE2Yi1jM2NmZjlkZGRjMWYiLCJjbGllbnRfc2Vzc2lvbiI6ImFiYjdjYjk0LTE0YmMtNDljMy1iYTJmLTE1Y2RjMjZlZjNkYiIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJEZWZhdWx0IFNlcnZpY2UgUm9sZSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImR4YyI6eyJyb2xlcyI6WyJjYXNlLmFsbCIsImNhc2UuYXBwZW5kIiwiY2FzZS5hc3NpZ24iLCJjYXNlLmNvbW1lbnQiLCJjYXNlLmFkbWluIiwiY2FzZS5zdGF0ZSIsImNhc2UuaWRlbnRpZmllcnMiLCJjYXNlLnRyYW5zZmVyIiwiY2FzZS52aWV3IiwiY2FzZS5pbnZpdGUiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJ2aWV3LXByb2ZpbGUiXX19fQ.AQlRqa3ZNd7tv1Lb5qfw9Cl1F1WYG6XJs5FPBy8rr7W_q4676Ias2Ew5u8k9exlmrJhqWvMdY85tzBrVhNq4So3ZGzG084v0t2F1n4LX9w1kyo1q6ka-6nhiATwJC0wVHy0q-RjCSMElr2j5z3oxKO4LyT37123sRNZ4pOs1vA2D-ddEOX980NdVsyqX_MBW9p4OPC9esY0jZYXRX1svmcidyB6wj26vgpg9V-xiec3qxJuYLz8kHbtaa4jtVlEB-eKr_ZhWzl4x04lY6VtQ_G5pxEq04R1JQWHgI0befkdwcA1ut005OdablGmp3g_Hbmh07cdz0_HaKFo-yWD63A
    *
    * @apiUse OAuthSuccessResponse
    * @apiSuccessExample {json} OAuth2 Response
    * HTTP/1.1 100 Continue
    * Content-Length: 0

    * HTTP/1.1 200 OK
    * X-Powered-By: Undertow/1
    * Server: WildFly/10
    * Content-Type: application/json
    * Content-Length: 3847
    * Date: Wed, 17 Aug 2016 20:43:28 GMT
    * {
    *   "access_token": "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIzMjk0MjU0Yy05OTQxLTQ0YzgtYmQwYS02N2E2YzVhYjhlZDgiLCJleHAiOjE0NzE0NzAyMDgsIm5iZiI6MCwiaWF0IjoxNDcxNDY2NjA4LCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6IjkzZmQ4NmJiLTg5NjgtNGVkYi1hMTZiLWMzY2ZmOWRkZGMxZiIsImNsaWVudF9zZXNzaW9uIjoiYWJiN2NiOTQtMTRiYy00OWMzLWJhMmYtMTVjZGMyNmVmM2RiIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.NmE2qD0JZsnqvyDuMyN2-pRD263mqxFUh5wWYJCornNafUzmHKqO_OZ3iXCbVUgM5UDB-rQFpUEOq-4VxF3trVfXYDdJ2SEB2M67fsP_vqjeSUrdxTaZIyMGRIF-Jf3ir7HQtbVbc73rIPvvPn8MhQkBxzdeg6WEXSSFMwEvP5z2f1SRhKH56LEmwG54XS5fsrGhTLtRkBpmS-fOQbyB-kS4sRzJHFqHXqdPGVgE3cRf7zvlnlAc3hj28QKCW6coy4jfZ3LmLtAp1_4Lzq8YlxkYxOPH63qGQhxWpYOxQgbrHMN5H7UQCGdT_sm8p6UwUgOMIPesV6F1hMwuS_UZIw",
    *   "expires_in": 3600,
    *   "refresh_expires_in": 86400,
    *   "refresh_token": "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIxMTJkYjFiMS0wYjU2LTRiMjctYmYyYS1hNGMyNjRhOTlmOWIiLCJleHAiOjE0NzE1NTMwMDgsIm5iZiI6MCwiaWF0IjoxNDcxNDY2NjA4LCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImR4YyIsInNlc3Npb25fc3RhdGUiOiI5M2ZkODZiYi04OTY4LTRlZGItYTE2Yi1jM2NmZjlkZGRjMWYiLCJjbGllbnRfc2Vzc2lvbiI6ImFiYjdjYjk0LTE0YmMtNDljMy1iYTJmLTE1Y2RjMjZlZjNkYiIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJEZWZhdWx0IFNlcnZpY2UgUm9sZSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImR4YyI6eyJyb2xlcyI6WyJjYXNlLmFsbCIsImNhc2UuYXBwZW5kIiwiY2FzZS5hc3NpZ24iLCJjYXNlLmNvbW1lbnQiLCJjYXNlLmFkbWluIiwiY2FzZS5zdGF0ZSIsImNhc2UuaWRlbnRpZmllcnMiLCJjYXNlLnRyYW5zZmVyIiwiY2FzZS52aWV3IiwiY2FzZS5pbnZpdGUiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJ2aWV3LXByb2ZpbGUiXX19fQ.kW40TUz9qAWF-mBBextDjGcYN78Pj0AcGDaio2PyMMZ9p-lQYaeBfAWjVctBJ6c_0--GKvb9SSoLWTJcelIZoWhundGDp44YlciNbVFTUV6TyTqr0-b051rIuaAErkdS470iYRPUQkyN6oje3GvSvIIKcBuQBWPc1_Y_bJfVhoWBDnVPsbVxNkk4svyCLak7ekr0eeMvhOUrHvLbKSoF4Ky4__ic2IlkaFeU_pTXRjy4ru09Lf19s_AufvPbHIf04vwyT99Y14wBdlZywt5erF0mpBivXQ8LBQUhHT1nilPnUskAOHBGCh0hqkuV-TZv4j4jQUS8HNPITAg_F82VVA",
    *   "token_type": "bearer",
    *   "id_token": "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJiOTU5MGFkMy01OWU4LTRkMjYtYmNjMS02OGVkN2M5M2JkMTUiLCJleHAiOjE0NzE0NzAyMDgsIm5iZiI6MCwiaWF0IjoxNDcxNDY2NjA4LCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiSUQiLCJhenAiOiJkeGMiLCJzZXNzaW9uX3N0YXRlIjoiOTNmZDg2YmItODk2OC00ZWRiLWExNmItYzNjZmY5ZGRkYzFmIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsImdpdmVuX25hbWUiOiJaRVNUWVpFQlJBIiwiZmFtaWx5X25hbWUiOiJTZXJ2aWNlIiwiZW1haWwiOiJzZXJ2aWNlLnplc3R5MkBtYXhpcmQuY29tIiwicGFydGljaXBhbnQiOiIyMC41MDAwLjIxNC9wemVzdHkifQ.NFcxVC7KJSZL453kFYhHFY_sbyGryT-Lo4y_5BVQ0Ark8JH1ttMBAd5tCKHC0vSuNJ_Qv57iMcBfsdGFK83w2K_ba3BQEVTa3ec1KxGJXVqmYJ3F6xuoh9CdHv0sw9VYtTBdSt_YLfoFBiVKtpzPWRwMTYY6o4HnHa_ViI8XLSux5MioJejnVVsBNxGFjGaJT7Z5P5BKSWSOHUjBzPzT3pqDYcm30fibm6j1Y3xwj39825yTVTmEmIzugXszuaCa_25fl8p8RerWgj1GEGkjhd4bRV5oR2av1RAnbR1Ot_xQLIeFkpEqsPQVThLy5F9U0sNo6Alj3afg0EMCglxDHg",
    *   "not-before-policy": 0,
    *   "session_state": "93fd86bb-8968-4edb-a16b-c3cff9dddc1f"
    * }
    *
    *
    */

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Cross Case Methods (Case Searches)

    /**
    * @api {post} apigw/webservices/rest/apigw/cases/search 00.Search
    * @apiName SearchCases
    * @apiGroup Cases
    *
    * @apiDescription
    * <h2 style="margin-top: 0.75em; margin-bottom:0.3em;">Overview</h2>
    * <div>Conducts a search across all cases in your realm.  You can search by IMRID, CAID or an array of statuses.</div>
    * <h2 style="margin-top: 0.75em; margin-bottom: 0.3em;">Paging</h2>
    * <div>When search results get large, DXC searches provide a paging mechanism.  To page through a result set, simply pass <code>start</code> and <code>limit</code> parameters as part of the search request.  For example to get just the first 10 results, specify a <code>start=0&amp;limit=10</code>.  To get the next 10 results send <code>start=1&amp;limit=10</code>. The `start` parameter represents the *page* of results.  Its always a good idea to also include some sort criteria when using paging.  For example to see the ten most recent changes to the cases in your realm use: <code>{"start":0,"limit":10,"sort":[{"property":"modificationDate","desc":true}]}</code>.
<span style="font-style: italic;">For large result sets, paging is strongly encouraged and may be enforced in the future.</span>
</div>
    * <h2 style="margin-top: 0.75em; margin-bottom: 0.3em;">Output Formats</h2>
    * <div>This API request can provide repsonses in either JSON or CSV.  The response format is determined by the Accept HTTP header ("application/json" for JSON and "text/plain" for CSV).  When returning CSV only the <code>results</code> array is returned in CSV format (<code>matchCount</code>, <code>start</code> and <code>limit</code> are not included).  Additionally (as demonstrated in the sample CSV Response), some fields may contain embedded line breaks.  These fields will be enclosed in quotes.  This behavior may cause some records to span multiple lines.</div>
    *
    * @apiHeader {String} Authorization Bearer <code>JWT Access Token</code>
    * @apiHeader {String} Content-Type application/json
    * @apiHeader {String} Accepts application/json (for JSON response) or text/plain (for CSV response)
    *
    * @apiParam {Object[]} sort Sort order criteria
    * @apiParam {String} sort.property property name to sort on
    * @apiParam {Boolean} sort.desc whether a property sort is descending
    * @apiParam {String} CAID Claims Administrator Case Number
    * @apiParam {String} IMRID IMR Case Number
    * @apiParam {String[]} statuses array of string values for statuses (e.g.: ["1", "3"])
    * <ul style="list-style: none">
    * <li>"1" - Received</li>
    * <li>"2" - Eligibility Review</li>
    * <li>"3" - Records Requested</li>
    * <li>"4" - Clinical Review</li>
    * <li>"5" - Closed</li>
    * <li>"6" - NOARFI</li>
    * <li>"7" - Insufficient Records</li>
    * </ul>
    * @apiParam {Number} start The index of the search results you wish returned. If not supplied or invalid it will be replaced with 0.
    * @apiParam {Number} limit The number of results to return. If not supplied the specific API will select the maximum results to return. If the value is considered too large by the specific API it may alter this value in the response and ignore the supplied value.
    * @apiUse CaseSearchResults
    *
    * @apiUse ErrorResponse
    * @apiVersion 1.0.0
    *
    * @apiExample {json} Search Request
    * POST /apigw/webservices/rest/apigw/cases/search HTTP/1.1
    * User-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)
    * Host: imr-ca-sandbox.maxird.com
    * Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIyNTZjOTMyNC02MTg1LTQ5MWMtYmQ0Ni1hZGE0ZWIyNzY2YTIiLCJleHAiOjE0NzE0NjQ5MjIsIm5iZiI6MCwiaWF0IjoxNDcxNDYxMzIyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6IjkzZmQ4NmJiLTg5NjgtNGVkYi1hMTZiLWMzY2ZmOWRkZGMxZiIsImNsaWVudF9zZXNzaW9uIjoiYWJiN2NiOTQtMTRiYy00OWMzLWJhMmYtMTVjZGMyNmVmM2RiIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.IGnhJCaUpBMZ-f5ZScnzJcNfnTLuaA0KsYYkdyQMMgGMd_wAj1d5SoVfsYd_vQkaSUyueqoRNApLFwv3BtkXDzyOLKBGGWl24Rmrt1-6pdSNOCpWkZmraFx4x9DfR7mSK13ucEvH35S1O_b9C41_96s_zH27ss9yTp_Q-G_rBvllAEbBu-rKk9ug0HzTNwlIyvg9g8AIgOZZUO8jB0Cu8Y0OjgQzdJwmw-zktmQhq5jf3R_1z--Syoe3m4gZbVG_SFJf3i06VG3rkMCED3AUr7yrXAOseaPkDBdQnoF7ActcHZxZDhB7CBwoyBL6WhekN7Mq38W87H01nLz8AOALIg
    * Content-Type: application/json
    * Accept: application/json
    * Content-Length: 51
    *
    * {"start":0,"limit":25,"sort":[{"property":"dateOfInjury","desc":false}]}
    *
    *
    * @apiSuccessExample {json} JSON Response
    * HTTP/1.1 200 OK
    * Server: Apache-Coyote/1.1
    * X-Content-Type-Options: nosniff
    * X-XSS-Protection: 1; mode=block
    * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
    * Pragma: no-cache
    * Expires: 0
    * X-Frame-Options: DENY
    * Date: Wed, 17 Aug 2016 19:18:41 GMT
    * Content-Type: application/json
    * Transfer-Encoding: chunked
    *
    * {
    *   "matchCount": 3,
    *   "start": 0,
    *   "limit": 10,
    *   "results": [
    *     {
    *       "claimNumber": "10000001",
    *       "caseNumber": "CM16-10000001",
    *       "dateOfInjury": "2013-01-01T09:00:00+0500",
    *       "priority": "Expedited",
    *       "injuredWorkerPrefix": "Mrs.",
    *       "injuredWorkerFirstName": "Jane",
    *       "injuredWorkerLastName": "Smith",
    *       "injuredWorkerMiddleInitial": "M",
    *       "injuredWorkrSuffix": "III",
    *       "employerName": "Broadway Busters",
    *       "treatmentRequested": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et tellus sapien. Nam sit amet ullamcorper lectus. Curabitur eu dolor dictum lorem eleifend laoreet. Pellentesque odio eros, maximus vitae purus sit amet, elementum facilisis nunc. Integer velit metus, fringilla id elit lobortis, ullamcorper sollicitudin nibh. Duis ac pulvinar libero, ac hendrerit turpis. Duis sollicitudin nunc quis mi ultricies pharetra.\n\nNullam bibendum vel nisi sed pulvinar. Nam semper venenatis ultricies. Pellentesque quis lectus nisi. Nam eu nibh condimentum, fermentum nibh vitae, lacinia magna. Etiam a orci tincidunt, mollis nunc ut, ullamcorper ex. Aenean faucibus porttitor sapien quis lobortis. Donec rutrum molestie justo, sed pharetra lorem pulvinar sit amet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed pulvinar iaculis sodales. Etiam dolor nibh, bibendum eget risus sed, interdum volutpat nisl. Curabitur suscipit, est vel ultricies fringilla, libero orci pretium augue, vel tincidunt tortor lectus sit amet mauris. Praesent a luctus diam.\n\nMaecenas luctus erat ut erat tincidunt fringilla. Vestibulum vel venenatis dui, quis lobortis augue. Quisque rhoncus vel tellus sollicitudin tristique. Nunc sit amet lectus sed augue aliquet congue sit amet quis turpis. Etiam id efficitur lacus. Mauris eleifend purus id suscipit congue. Donec vitae quam ac nunc aliquet euismod. Curabitur aliquet mi sit amet mollis semper.\n\nCras facilisis vestibulum nunc sed eleifend. Etiam viverra, enim quis elementum egestas, nisl nibh ullamcorper ipsum, hendrerit lacinia nisi neque eget magna. Suspendisse scelerisque auctor aliquam. Duis a maximus metus. Vivamus vel fermentum velit. Nullam rutrum ante quis est ultrices, et tempus ex pellentesque. Mauris mollis libero in diam accumsan, vitae sagittis tortor accumsan. In lacus erat, pellentesque sit amet ante a, ullamcorper vulputate felis. Nunc iaculis finibus arcu, eget sollicitudin elit dapibus vel.\n\n",
    *       "claimsAdministratorAddress1": "2309 Tulare St",
    *       "claimsAdministratorCity": "Fresno",
    *       "claimsAdministratorState": "California",
    *       "claimsAdministratorZipCode": "93721",
    *       "claimsAdministratorCompanyName": "Zesty Zebra",
    *       "providerPrefix": "Mrs.",
    *       "providerFirtName": "Anna",
    *       "providerLastName": "Jones",
    *       "providerMidleInitial": "M",
    *       "providerSuffix": "Esq",
    *       "organizationName": "Provider Org",
    *       "status": "1",
    *       "dateofURDecision": "2012-11-27T09:00:00+0500",
    *       "receiveDate": "2016-06-21T09:00:00+0500",
    *       "modificationDate": "2016-08-15T13:03:16.031Z"
    *     },
    *     {
    *       "claimNumber": "10000003",
    *       "caseNumber": "CM16-10000003",
    *       "dateOfInjury": "2013-01-01T09:00:00+0500",
    *       "priority": "Standard",
    *       "injuredWorkerPrefix": "Ms.",
    *       "injuredWorkerFirstName": "Terri",
    *       "injuredWorkerLastName": "Talker",
    *       "injuredWorkerMiddleInitial": "T",
    *       "injuredWorkrSuffix": "",
    *       "employerName": "Talking Tall",
    *       "treatmentRequested": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et tellus sapien. Nam sit amet ullamcorper lectus. Curabitur eu dolor dictum lorem eleifend laoreet. Pellentesque odio eros, maximus vitae purus sit amet, elementum facilisis nunc. Integer velit metus, fringilla id elit lobortis, ullamcorper sollicitudin nibh. Duis ac pulvinar libero, ac hendrerit turpis. Duis sollicitudin nunc quis mi ultricies pharetra.\n\nNullam bibendum vel nisi sed pulvinar. Nam semper venenatis ultricies. Pellentesque quis lectus nisi. Nam eu nibh condimentum, fermentum nibh vitae, lacinia magna. Etiam a orci tincidunt, mollis nunc ut, ullamcorper ex. Aenean faucibus porttitor sapien quis lobortis. Donec rutrum molestie justo, sed pharetra lorem pulvinar sit amet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed pulvinar iaculis sodales. Etiam dolor nibh, bibendum eget risus sed, interdum volutpat nisl. Curabitur suscipit, est vel ultricies fringilla, libero orci pretium augue, vel tincidunt tortor lectus sit amet mauris. Praesent a luctus diam.\n\nMaecenas luctus erat ut erat tincidunt fringilla. Vestibulum vel venenatis dui, quis lobortis augue. Quisque rhoncus vel tellus sollicitudin tristique. Nunc sit amet lectus sed augue aliquet congue sit amet quis turpis. Etiam id efficitur lacus. Mauris eleifend purus id suscipit congue. Donec vitae quam ac nunc aliquet euismod. Curabitur aliquet mi sit amet mollis semper.\n\nCras facilisis vestibulum nunc sed eleifend. Etiam viverra, enim quis elementum egestas, nisl nibh ullamcorper ipsum, hendrerit lacinia nisi neque eget magna. Suspendisse scelerisque auctor aliquam. Duis a maximus metus. Vivamus vel fermentum velit. Nullam rutrum ante quis est ultrices, et tempus ex pellentesque. Mauris mollis libero in diam accumsan, vitae sagittis tortor accumsan. In lacus erat, pellentesque sit amet ante a, ullamcorper vulputate felis. Nunc iaculis finibus arcu, eget sollicitudin elit dapibus vel.\n\n",
    *       "claimsAdministratorAddress1": "342 Shoe Circle",
    *       "claimsAdministratorCity": "Fresno",
    *       "claimsAdministratorState": "California",
    *       "claimsAdministratorZipCode": "93721",
    *       "claimsAdministratorCompanyName": "Zesty Zebra",
    *       "providerPrefix": "Mr.",
    *       "providerFirtName": "Peter",
    *       "providerLastName": "Puffer",
    *       "providerMidleInitial": "P",
    *       "providerSuffix": "II",
    *       "organizationName": "Provider Org",
    *       "status": "3",
    *       "dateofURDecision": "2012-11-27T09:00:00+0500",
    *       "receiveDate": "2016-06-21T09:00:00+0500",
    *       "modificationDate": "2016-08-15T13:03:22.865Z"
    *     },
    *     {
    *       "claimNumber": "10000002",
    *       "caseNumber": "CM16-10000002",
    *       "dateOfInjury": "2013-01-01T09:00:00+0500",
    *       "priority": "Standard",
    *       "injuredWorkerPrefix": "Mr.",
    *       "injuredWorkerFirstName": "Waldo",
    *       "injuredWorkerLastName": "Wrangler",
    *       "injuredWorkerMiddleInitial": "M",
    *       "injuredWorkrSuffix": "III",
    *       "employerName": "Magic Suppliers",
    *       "treatmentRequested": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et tellus sapien. Nam sit amet ullamcorper lectus. Curabitur eu dolor dictum lorem eleifend laoreet. Pellentesque odio eros, maximus vitae purus sit amet, elementum facilisis nunc. Integer velit metus, fringilla id elit lobortis, ullamcorper sollicitudin nibh. Duis ac pulvinar libero, ac hendrerit turpis. Duis sollicitudin nunc quis mi ultricies pharetra.\n\nNullam bibendum vel nisi sed pulvinar. Nam semper venenatis ultricies. Pellentesque quis lectus nisi. Nam eu nibh condimentum, fermentum nibh vitae, lacinia magna. Etiam a orci tincidunt, mollis nunc ut, ullamcorper ex. Aenean faucibus porttitor sapien quis lobortis. Donec rutrum molestie justo, sed pharetra lorem pulvinar sit amet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed pulvinar iaculis sodales. Etiam dolor nibh, bibendum eget risus sed, interdum volutpat nisl. Curabitur suscipit, est vel ultricies fringilla, libero orci pretium augue, vel tincidunt tortor lectus sit amet mauris. Praesent a luctus diam.\n\nMaecenas luctus erat ut erat tincidunt fringilla. Vestibulum vel venenatis dui, quis lobortis augue. Quisque rhoncus vel tellus sollicitudin tristique. Nunc sit amet lectus sed augue aliquet congue sit amet quis turpis. Etiam id efficitur lacus. Mauris eleifend purus id suscipit congue. Donec vitae quam ac nunc aliquet euismod. Curabitur aliquet mi sit amet mollis semper.\n\nCras facilisis vestibulum nunc sed eleifend. Etiam viverra, enim quis elementum egestas, nisl nibh ullamcorper ipsum, hendrerit lacinia nisi neque eget magna. Suspendisse scelerisque auctor aliquam. Duis a maximus metus. Vivamus vel fermentum velit. Nullam rutrum ante quis est ultrices, et tempus ex pellentesque. Mauris mollis libero in diam accumsan, vitae sagittis tortor accumsan. In lacus erat, pellentesque sit amet ante a, ullamcorper vulputate felis. Nunc iaculis finibus arcu, eget sollicitudin elit dapibus vel.\n\n",
    *       "claimsAdministratorAddress1": "2309 Sunset St",
    *       "claimsAdministratorCity": "Fresno",
    *       "claimsAdministratorState": "California",
    *       "claimsAdministratorZipCode": "93721",
    *       "claimsAdministratorCompanyName": "Zesty Zebra",
    *       "providerPrefix": "Mrs.",
    *       "providerFirtName": "Mary",
    *       "providerLastName": "Melish",
    *       "providerMidleInitial": "M",
    *       "providerSuffix": "Esq",
    *       "organizationName": "Provider Org",
    *       "status": "2",
    *       "dateofURDecision": "2012-11-27T09:00:00+0500",
    *       "receiveDate": "2016-06-21T09:00:00+0500",
    *       "modificationDate": "2016-08-15T13:03:19.525Z"
    *     }
    *   ]
    * }
    *
    * @apiSuccessExample {csv} CSV Response
    * HTTP/1.1 200 OK
    * Server: Apache-Coyote/1.1
    * X-Content-Type-Options: nosniff
    * X-XSS-Protection: 1; mode=block
    * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
    * Pragma: no-cache
    * Expires: 0
    * X-Frame-Options: DENY
    * Date: Wed, 17 Aug 2016 19:18:41 GMT
    * Content-Type: text/plain
    * Transfer-Encoding: chunked
    *
    * 10000001,CM16-10000001,2013-01-01T09:00:00+0500,Expedited,Mrs.,Jane,Smith,M,III,Broadway Busters,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et tellus sapien. Nam sit amet ullamcorper lectus. Curabitur eu dolor dictum lorem eleifend laoreet. Pellentesque odio eros, maximus vitae purus sit amet, elementum facilisis nunc. Integer velit metus, fringilla id elit lobortis, ullamcorper sollicitudin nibh. Duis ac pulvinar libero, ac hendrerit turpis. Duis sollicitudin nunc quis mi ultricies pharetra.
    *
    * Nullam bibendum vel nisi sed pulvinar. Nam semper venenatis ultricies. Pellentesque quis lectus nisi. Nam eu nibh condimentum, fermentum nibh vitae, lacinia magna. Etiam a orci tincidunt, mollis nunc ut, ullamcorper ex. Aenean faucibus porttitor sapien quis lobortis. Donec rutrum molestie justo, sed pharetra lorem pulvinar sit amet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed pulvinar iaculis sodales. Etiam dolor nibh, bibendum eget risus sed, interdum volutpat nisl. Curabitur suscipit, est vel ultricies fringilla, libero orci pretium augue, vel tincidunt tortor lectus sit amet mauris. Praesent a luctus diam.
    *
    * Maecenas luctus erat ut erat tincidunt fringilla. Vestibulum vel venenatis dui, quis lobortis augue. Quisque rhoncus vel tellus sollicitudin tristique. Nunc sit amet lectus sed augue aliquet congue sit amet quis turpis. Etiam id efficitur lacus. Mauris eleifend purus id suscipit congue. Donec vitae quam ac nunc aliquet euismod. Curabitur aliquet mi sit amet mollis semper.
    *
    * Cras facilisis vestibulum nunc sed eleifend. Etiam viverra, enim quis elementum egestas, nisl nibh ullamcorper ipsum, hendrerit lacinia nisi neque eget magna. Suspendisse scelerisque auctor aliquam. Duis a maximus metus. Vivamus vel fermentum velit. Nullam rutrum ante quis est ultrices, et tempus ex pellentesque. Mauris mollis libero in diam accumsan, vitae sagittis tortor accumsan. In lacus erat, pellentesque sit amet ante a, ullamcorper vulputate felis. Nunc iaculis finibus arcu, eget sollicitudin elit dapibus vel.
    *
    * ",2309 Tulare St,,Fresno,California,93721,Zesty Zebra,Mrs.,Anna,Jones,M,Provider Org,1,,,,,2012-11-27T09:00:00+0500,2016-06-21T09:00:00+0500
    * 10000003,CM16-10000003,2013-01-01T09:00:00+0500,Standard,Ms.,Terri,Talker,T,,Talking Tall,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et tellus sapien. Nam sit amet ullamcorper lectus. Curabitur eu dolor dictum lorem eleifend laoreet. Pellentesque odio eros, maximus vitae purus sit amet, elementum facilisis nunc. Integer velit metus, fringilla id elit lobortis, ullamcorper sollicitudin nibh. Duis ac pulvinar libero, ac hendrerit turpis. Duis sollicitudin nunc quis mi ultricies pharetra.
    *
    * Nullam bibendum vel nisi sed pulvinar. Nam semper venenatis ultricies. Pellentesque quis lectus nisi. Nam eu nibh condimentum, fermentum nibh vitae, lacinia magna. Etiam a orci tincidunt, mollis nunc ut, ullamcorper ex. Aenean faucibus porttitor sapien quis lobortis. Donec rutrum molestie justo, sed pharetra lorem pulvinar sit amet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed pulvinar iaculis sodales. Etiam dolor nibh, bibendum eget risus sed, interdum volutpat nisl. Curabitur suscipit, est vel ultricies fringilla, libero orci pretium augue, vel tincidunt tortor lectus sit amet mauris. Praesent a luctus diam.
    *
    * Maecenas luctus erat ut erat tincidunt fringilla. Vestibulum vel venenatis dui, quis lobortis augue. Quisque rhoncus vel tellus sollicitudin tristique. Nunc sit amet lectus sed augue aliquet congue sit amet quis turpis. Etiam id efficitur lacus. Mauris eleifend purus id suscipit congue. Donec vitae quam ac nunc aliquet euismod. Curabitur aliquet mi sit amet mollis semper.
    *
    * Cras facilisis vestibulum nunc sed eleifend. Etiam viverra, enim quis elementum egestas, nisl nibh ullamcorper ipsum, hendrerit lacinia nisi neque eget magna. Suspendisse scelerisque auctor aliquam. Duis a maximus metus. Vivamus vel fermentum velit. Nullam rutrum ante quis est ultrices, et tempus ex pellentesque. Mauris mollis libero in diam accumsan, vitae sagittis tortor accumsan. In lacus erat, pellentesque sit amet ante a, ullamcorper vulputate felis. Nunc iaculis finibus arcu, eget sollicitudin elit dapibus vel.
    *
    * ",342 Shoe Circle,,Fresno,California,93721,Zesty Zebra,Mr.,Peter,Puffer,P,Provider Org,3,,,,,2012-11-27T09:00:00+0500,2016-06-21T09:00:00+0500
    *
    */


    /**
    * @api {post} apigw/webservices/rest/apigw/cases/searchrfi 01.Search NOARFI
    * @apiName SearchCasesNOARFI
    *
    * @apiGroup Cases
    *
    * @apiHeader {String} Authorization Bearer <code>JWT Access Token</code>
    * @apiHeader {String} Content-Type application/json
    * @apiHeader {String} Accepts application/json (for JSON response) or text/plain (for CSV response)

    * @apiUse CaseSearchResults
    *
    * @apiUse ErrorResponse
    * @apiVersion 1.0.0
    */

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Case Scoped Methods (document search/upload/download)

    /**
    * @api {get} apigw/webservices/rest/apigw/docs/search/cn/:imrCaseNumber 00.Search
    * @apiName SearchDocs
    * @apiDescription This request returns a list of documents (and their renderings) for a specified case.  Each logical document will have one or more document renderings.  These renderings represent the actual files that have been uploaded.  The first content rendering listed for a document is the original artifact that was provided.  Occassionally additional renderings may be available for a document item.  These represent alternate presenations of the same logical information (all document renderings for a document item are semantically idential).  <span style="font-style: italic;">When attempting a document download you will need to use the </span><code>identifier</code><span style="font-style: italic;"> of the specific </span><span style="font-style: italic; font-weight: 700">rendering</span><span style="font-style: italic;"> you are looking to retrieve.</span>
    *
    * @apiGroup Documents
    *
    * @apiHeader {String} Authorization Bearer <code>JWT Access Token</code>
    * @apiHeader {String} Accepts application/json
    * 
    * @apiParam {String} imrCaseNumber IMR Case Number <span style="font-style: italic">(sent in path)</span>
    * @apiUse ErrorResponse
    * @apiVersion 1.0.0
    * 
    * @apiExample {json} Document Search
    * GET /apigw/webservices/rest/apigw/docs/search/cn/CM16-10000005 HTTP/1.1
    * User-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)
    * Host: imr-ca-sandbox.maxird.com
    * Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJmMmQxN2ZlZC0yZGI4LTQ2NjItYjQzNy0zZjgzYzIzM2NlN2IiLCJleHAiOjE0NzE1ODUwODIsIm5iZiI6MCwiaWF0IjoxNDcxNTgxNDgyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6ImM0ZThmODFiLTdhY2QtNGU5OC1iNjc3LTUzYmYxYjIzMzc1OSIsImNsaWVudF9zZXNzaW9uIjoiNDA3MzQxMzMtOGQ0Mi00ZDI3LTg2MmYtMTdmOTI4MjQ2ZTliIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.OD-S5raMHug0x8pMO7zHcHwzZuosSWx4cH1ktZ557itofxNUWinQgfvngdSg5cvNYpoEChqwQSz-Vd8GYjkjKrE5E-AK94kTLPE3MfzQ5X4KzRMovChshGVJK-sQCrnZONS77G-_UVXL5fITeWKUZnrarvM1X3kr-O0yMIRaB-DOs_hFK972-hB_zo-QMej0XIrTth-yjl5NwtRXXpKrAjqY-em7EWDL--k1Gd-Q4mj9wCTCxsKnIiElexVTaboz0WSBFxKd43EeCTdRC5bLwBVQvS3USCYBnioRnY79sd3GxELM7Zg8F6zgprlQl1P_lQ_5J8y4jO42qxhei92IlQ
    *
    * @apiSuccess {Object[]} results array of documents for case
    * @apiSuccess {String} results.identifier handle for this document item
    * @apiSuccess {String} results.type logical type of this document
    * @apiSuccess {String[]} results.types additional types for this document
    * @apiSuccess {String} results.case handle to the case
    * @apiSuccess {String} results.dcn document control number
    * @apiSuccess {String} results.name document name
    * @apiSuccess {Object[]} results.renderings each document will have one or more renderings
    * @apiSuccess {String} results.renderings.identifier handle for this document rendering (<span style="font-style: italic; font-weight:700">use this for downloads</span>)
    * @apiSuccess {String} results.renderings.type MIME type of this rendering
    * @apiSuccess {Number} results.renderings.size size in bytes of this rendering

    * @apiSuccess {Number} matchCount total number of documents matching search request
    * @apiSuccess {Number} start The page index of the search results you wish returned. If not supplied or invalid it will be replaced with 0.
    * @apiSuccess {Number} limit The number of results to return. If not supplied the specific API will select the maximum results to return. If the value is considered too large by the specific API it may alter this value in the response and ignore the supplied value.
    *
    * @apiSuccessExample {json} Response
    * HTTP/1.1 200 OK
    * Server: Apache-Coyote/1.1
    * X-Content-Type-Options: nosniff
    * X-XSS-Protection: 1; mode=block
    * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
    * Pragma: no-cache
    * Expires: 0
    * X-Frame-Options: DENY
    * Date: Fri, 19 Aug 2016 04:39:30 GMT
    * Content-Type: application/json
    * Transfer-Encoding: chunked
    *
    * {
    *   "matchCount": 3,
    *   "start": 0,
    *   "limit": 999,
    *   "results": [
    *     {
    *       "identifier": "20.5000.214/def25dc90f1682989e6a",
    *       "type": "MedicalRecords",
    *       "types": [
    *         "MedicalRecords"
    *       ],
    *       "case": "20.5000.214/87aa5e9deadbc32ea1e2",
    *       "dcn": "900003373",
    *       "name": "Medical Records",
    *       "renderings": [
    *         {
    *           "identifier": "20.5000.214/5fdb80cbb2b3a1619d93",
    *           "type": "application/pdf",
    *           "size": 7234
    *         }
    *       ]
    *     },
    *     {
    *       "identifier": "20.5000.214/b1ae4cf28ed2a760a633",
    *       "type": "NOARFI",
    *       "types": [
    *         "NOARFI"
    *       ],
    *       "case": "20.5000.214/87aa5e9deadbc32ea1e2",
    *       "dcn": "900003372",
    *       "name": "NOARFI",
    *       "renderings": [
    *         {
    *           "identifier": "20.5000.214/645559a095fe20ce8c53",
    *           "type": "application/pdf",
    *           "size": 6837
    *         }
    *       ]
    *     },
    *     {
    *       "identifier": "20.5000.214/65e63994e053ca5b6668",
    *       "type": "code.other",
    *       "types": [
    *         "code.other"
    *       ],
    *       "case": "20.5000.214/87aa5e9deadbc32ea1e2",
    *       "dcn": "900003374",
    *       "name": "Additional Information",
    *       "renderings": [
    *         {
    *           "identifier": "20.5000.214/6baef007ce0dd9b9d61d",
    *           "type": "application/pdf",
    *           "size": 18320
    *         }
    *       ]
    *     }
    *   ]
    * }
    *
    * @apiUse ErrorResponse
    * @apiVersion 1.0.0
    */


    /**
    * @api {post} apigw/webservices/rest/apigw/docs/upload/cn/:imrCaseNumber 01.Upload (by IMR Case Number)
    * @apiName UploadDoc
    * @apiDescription
    * This request allows users to upload a document for a case.  Once the upload had been fully processed by the DXC storage services, a new document item with one rendering (the file uploaded with this request) will appear in the document search for this case.  In addition to the <code>imrCaseNumber</code>, this request should include the <code>filename</code> (in the Content-Disposition of the first part and the MIME type (in the Content-Type) in the first part). See the Request example.
    *
    * <span style="font-weight:700">Note on specifying Document Size</span> <br/>
    * Upload requests SHOULD include a filesize header that specifies the precise size in bytes of the file being uploaded.  Additionally, the file size can not exceed 500MB.
    * 
    *
    * @apiGroup Documents
    *
    * @apiHeader {String} Authorization Bearer <code>JWT Access Token</code>
    * @apiHeader {String} Content-Type multipart/form-data
    * @apiHeader {String} Accepts application/json
    * @apiHeader {String} filesize File size in bytes
    *
    * @apiParam {String} imrCaseNumber IMR Case Number <span style="font-style: italic;">(sent in path)</span>
    * @apiParam {String} Content-Disposition (in header of part1) includes filename (must include <code>.pdf</code> extention)
    * @apiParam {String} Content-Type (in header of part1) MIME type of the file.   
    * (<span style="font-style: italic;">only </span><code>application/pdf</code><span style="font-style: italic;"> supported</span>)

    * @apiUse ErrorResponse
    * @apiVersion 1.0.0

    * @apiSuccess {String} status 200
    * @apiSuccess {String} msg OK

    * @apiExample Upload Request
    * POST /apigw/webservices/rest/apigw/docs/upload/cn/CM16-10000005 HTTP/1.1
    * User-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)
    * Host: imr-ca-sandbox.maxird.com
    * Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI5MzFmNGQwNy0wNzhlLTRlNjUtOWFhMC1lOTI1MzE3NDY5YjgiLCJleHAiOjE0NzE3NTQ2MzksIm5iZiI6MCwiaWF0IjoxNDcxNzUxMDM5LCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6ImFkNjU2NDU0LTFjZjgtNGVhNC05NzE3LWM5OGQ3MDczMzI5MyIsImNsaWVudF9zZXNzaW9uIjoiYTJiOTdhZDEtM2M3OC00NWUzLTg5YzItZDVjYTE5YzQyMWRhIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.WmQd3b0bo7gntuU4pu4EYr8T4cVzz59z6FTOi3Kx61kpb-XSJsYp8nNFab3ZiKQ6MPmxPXyF7sUFWO4pQlAazoyZvov77_EHx9f4sPlfCWoJ6s0kCvOTT-bTlHV85td5qaWZ-cSB9MrTBLovBYsiezFwOnxZbVLgdzJp8Zw3LaDhKzYs4Xz-PQfhgTyx2Oh1owxqfTRAoYXtBMgB6SEtAPQRI_TOiHm5iZ79sBy_YY_S0w49u1kHCDKxLt9yNfhnytaWDSRpTkWogwsC_5Cdui2oadsxaZQzgY8GG8oewZyYpdLiNDHsQDbVBU3WL3upT0HBJutzCmBWrP-6JPAiSA
    * filesize: 7243
    * Content-Length: 7435
    * Expect: 100-continue
    * Content-Type: multipart/form-data; boundary=----------------------------54835f8f6a22
    * 
    * Content-Disposition: form-data; name="file"; filename="medical_record.pdf"
    * Content-Type: application/pdf
    *
    * @apiSuccessExample {json} Response
    * HTTP/1.1 200 OK
    * Server: Apache-Coyote/1.1
    * X-Content-Type-Options: nosniff
    * X-XSS-Protection: 1; mode=block
    * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
    * Pragma: no-cache
    * Expires: 0
    * X-Frame-Options: DENY
    * Date: Sun, 21 Aug 2016 04:22:26 GMT
    * Content-Type: application/json
    * Transfer-Encoding: chunked
    *
    * {"status":200,"msg":"OK"}
    */


    /**
    * @api {post} apigw/webservices/rest/apigw/docs/upload 02.Upload (legacy)
    * @apiName UploadDocLegacy
    * @apiDescription
    * For legacy MOVEit users.  The IMR case id is inferred from the file name.   The <code>filename</code> and MIME type are specified in the first part Content-Disposition and Content-Type headers respectively.  Except for the imrCaseNumber pattern in the file name, this request is handled the same as a normal file upload.
    *
    * <span style="font-weight:700">Note on specifying Document Size</span> <br/>
    * Upload requests SHOULD include a filesize header that specifies the precise size in bytes of the file being uploaded.  Additionally, the file size can not exceed 500MB.
    *
    * @apiGroup Documents
    *
    * @apiHeader {String} Authorization Bearer <code>JWT Access Token</code>
    * @apiHeader {String} Content-Type multipart/form-data
    * @apiHeader {String} Accepts application/json
    * @apiHeader {String} filesize File size in bytes

    * @apiParam {String} Content-Disposition (in header of part1) includes filename (must be of a recognized format such as: <code>{IMRCaseNumber}_{NNN}.pdf</code>)
    * @apiParam {String} Content-Type (in header of part1) MIME type of the file. 
    * (<span style="font-style: italic;">only </span><code>application/pdf</code><span style="font-style: italic;"> supported</span>)

    * @apiUse ErrorResponse
    *
    * @apiVersion 1.0.0
    */


    /**
    * @api {get} apigw/webservices/rest/apigw/docs/download/:handle 03.Download
    * @apiName DownloadDoc
    * @apiDescription
    *
    * This service allows downloading files for cases you are authorized to view.  In production, these files will likely contain PHI and the recieving application is cautioned to take appropriate security measures.
    * Please note that the <code>handle</code> parameter will likely contain a forward slash character '/' (e.g. a <code>handle</code> may look like: <code>20.5000.214/6baef007ce0dd9b9d61d</code>).  Since <code>handle</code>s are sent as part of the request path, it might be tempting to URL encode them prior to building the path.  Please <span style="font-weight:700">DON'T</span> do this.  See the GET at the begining of the example to see how the service should be called.
    *
    *
    * @apiGroup Documents
    *
    * @apiHeader {String} Authorization Bearer <code>JWT Access Token</code>
    * @apiHeader {String} Accepts application/octet-stream
    *
    * @apiParam {String} handle the handle of the particular document <em>rendering</em> requested<br /><span style="font-style: italic">(sent in path - <span style="font-weight: 700">do not encode</span>)</span>

    * @apiExample Request
    * GET /apigw/webservices/rest/apigw/docs/download/20.5000.214/6baef007ce0dd9b9d61d HTTP/1.1
    * User-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)
    * Host: imr-ca-sandbox.maxird.com
    * Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJkZjIxZTg4MS1kMzhhLTQ5OWUtYjU1Zi03MTIyMGY2MWExNTAiLCJleHAiOjE0NzE1ODgzMTAsIm5iZiI6MCwiaWF0IjoxNDcxNTg0NzEwLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6ImM0ZThmODFiLTdhY2QtNGU5OC1iNjc3LTUzYmYxYjIzMzc1OSIsImNsaWVudF9zZXNzaW9uIjoiNDA3MzQxMzMtOGQ0Mi00ZDI3LTg2MmYtMTdmOTI4MjQ2ZTliIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.GY7utwi53CcjEDVPGoJJZAqSa-MiewayZl06gpb8qp96oZ1mW4Rz2NBEIR8V4z1zeOtWZYbdYXfkhYk4nWNc2Y8FXi-YNYHxm_SSNGCl1nzXljzthfCX-_oQ6CoKX8JeZa-J57Y4DXHdSwp14mkGVvWn4hnnHkjilAAqDh_5YbNwdijiw7w9DEyMyCR3OEFLXYgvICYXRYoQjjdSVQw2PKKKVa1xmkjOtnqjodKmijCFB4gJtmh1QUssleZK8X64acAMDU-tn5w2IlVrt-2vWKu3Kz2FNApbAOxa6rgJpl_dgRIhdO0NAfMkmztINjSMG6Cdxxh8X3FhINthyRB_FA
    *
    * @apiSuccessExample Response
    * HTTP/1.1 200 OK
    * Server: Apache-Coyote/1.1
    * X-Content-Type-Options: nosniff
    * X-XSS-Protection: 1; mode=block
    * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
    * Pragma: no-cache
    * Expires: 0
    * X-Frame-Options: DENY
    * Date: Fri, 19 Aug 2016 05:36:22 GMT
    * Content-Type: application/octet-stream
    * Transfer-Encoding: chunked
    *
    * @apiUse ErrorResponse
    *
    * @apiVersion 1.0.0
    */


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // NOARFI requests

    /**
    * @api {get} apigw/webservices/rest/apigw/events/noarfi/ 00.Manifest DateList
    * @apiName NOARFIDateList
    * @apiDescription Returns a list of (zero or more) dates for which unacknowledged NOARFI manifest exists. 
    * The dates reflect the system date when the NOARFI letter was generated. The dates format is ISO 8601 (i.e. yyyy-MM-dd).
    *
    * @apiGroup NOARFI
    *
    * @apiHeader {String} Authorization Bearer <code>JWT Access Token</code>
    * @apiHeader {String} Accepts application/json
    *
    * @apiExample {json} DateList Search
    * GET /apigw/webservices/rest/apigw/events/noarfi/ HTTP/1.1
    * User-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)
    * Host: imr-ca-sandbox.maxird.com
    * Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJmMmQxN2ZlZC0yZGI4LTQ2NjItYjQzNy0zZjgzYzIzM2NlN2IiLCJleHAiOjE0NzE1ODUwODIsIm5iZiI6MCwiaWF0IjoxNDcxNTgxNDgyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6ImM0ZThmODFiLTdhY2QtNGU5OC1iNjc3LTUzYmYxYjIzMzc1OSIsImNsaWVudF9zZXNzaW9uIjoiNDA3MzQxMzMtOGQ0Mi00ZDI3LTg2MmYtMTdmOTI4MjQ2ZTliIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.OD-S5raMHug0x8pMO7zHcHwzZuosSWx4cH1ktZ557itofxNUWinQgfvngdSg5cvNYpoEChqwQSz-Vd8GYjkjKrE5E-AK94kTLPE3MfzQ5X4KzRMovChshGVJK-sQCrnZONS77G-_UVXL5fITeWKUZnrarvM1X3kr-O0yMIRaB-DOs_hFK972-hB_zo-QMej0XIrTth-yjl5NwtRXXpKrAjqY-em7EWDL--k1Gd-Q4mj9wCTCxsKnIiElexVTaboz0WSBFxKd43EeCTdRC5bLwBVQvS3USCYBnioRnY79sd3GxELM7Zg8F6zgprlQl1P_lQ_5J8y4jO42qxhei92IlQ
    *
    * @apiSuccess {String[]} datelist array of ISO 8601 dates with NOARFIs

    * @apiSuccessExample {json} Response
    * HTTP/1.1 200 OK
    * Server: Apache-Coyote/1.1
    * X-Content-Type-Options: nosniff
    * X-XSS-Protection: 1; mode=block
    * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
    * Pragma: no-cache
    * Expires: 0
    * X-Frame-Options: DENY
    * Date: Fri, 19 Aug 2016 04:39:30 GMT
    * Content-Type: application/json
    * Transfer-Encoding: chunked
    *
    * {
    *   "datelist": [
    *     “2016-09-05”, 
    *     “2016-09-06”
    *   ]
    * }
    *     
    * @apiUse ErrorResponse
    * @apiVersion 1.0.0
    */

    /**
    * @api {get} apigw/webservices/rest/apigw/events/noarfi/:date 01.Details
    * @apiName NOARFIDetails
    * @apiDescription Returns the list of NOARFI records for a given date. The date is one of the dates listed in the NOARFI Manifest date list service which has not been acknowledged yet.
    * Results are sent as CSV.  The first row will be a header row with the field names.  To facilitate adoption with legacy users, an optional "<code>legacy</code>" mode is available.  In legacy mode, the fields will retain their original names and an additional 10 filler fields will be appended to each row.
    *
    * <table>
    *   <thead>
    *     <tr>
    *       <th>Default Name</th>
    *       <th>Legacy Name</th>
    *       <th>Type</th>
    *       <th>Comment</th>
    *     </tr>
    *   </thead>
    *   <tbody>
    *     <tr>
    *       <td>claimsExaminerFirstName</td>
    *       <td>C_PARTICIPANT_CONTACT_F_NAME</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>claimsExaminerLastName</td>
    *       <td>C_PARTICIPANT_CONTACT_L_NAME</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>claimsExaminerSuffix</td>
    *       <td>C_PARTICIPANT_CONTACT_SUFFIX</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>injuredWorkerSuffix</td>
    *       <td>IW_SUFFIX</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>claimsAdministratorAddress1</td>
    *       <td>C_PARTICIPANT_1ST_STREET_ADDRE</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>claimsAdministratorAddress2</td>
    *       <td>C_PARTICIPANT_2ND_STREET_ADDRE</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>claimsAdministratorCity</td>
    *       <td>C_PARTICIPANT_CITY</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>claimsAdministratorState</td>
    *       <td>C_PARTICIPANT_STATE</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>claimsAdministratorZipCode</td>
    *       <td>C_PARTICIPANT_POSTAL_CODE</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>injuredWorkerFirstName</td>
    *       <td>IW_F_NAME</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>injuredWorkerLastName</td>
    *       <td>IW_L_NAME</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>caAppealNumber</td>
    *       <td>C_CA_APPEAL_NUMBER</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>caseNumber</td>
    *       <td>C_CASE_NUMBER</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>systemDateIso</td>
    *       <td>SYSTEM_DATE</td>
    *       <td>String</td>
    *       <td>MM/DD/YYYY</td>
    *     </tr>
    *     <tr>
    *       <td>participantType</td>
    *       <td>PARTICIPANT_TYPE</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>providerName</td>
    *       <td>PROVIDER</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>claimsAdministratorCompanyName</td>
    *       <td>C_PARTICIPANT_ORGAN_NAME</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>claimNumber</td>
    *       <td>C_CLAIMS_NUMBER</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>priority</td>
    *       <td>PRIORITY_TYPE</td>
    *       <td>String</td>
    *       <td>Standard or Expedited</td>
    *     </tr>
    *     <tr>
    *       <td>dateOfInjury</td>
    *       <td>C_INJURY_DATE</td>
    *       <td>String</td>
    *       <td>MM/DD/YYYY</td>
    *     </tr>
    *     <tr>
    *       <td>dateofURDecision</td>
    *       <td>C_UR_DEC_DATE</td>
    *       <td>String</td>
    *       <td>MM/DD/YYYY</td>
    *     </tr>
    *     <tr>
    *       <td>treatmentRequested</td>
    *       <td>C_TXREQ</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td>receiveDate</td>
    *       <td>C_RCVDATE</td>
    *       <td>String</td>
    *       <td>MM/DD/YYYY</td>
    *     </tr>
    *     <tr>
    *       <td><span style="font-style: italic">Not Sent</span></td>
    *       <td>C_ACKSENTDATE</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td><span style="font-style: italic">Not Sent</span></td>
    *       <td>SEQUENCE_NUMBER</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td><span style="font-style: italic">Not Sent</span></td>
    *       <td>FILENAME</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td><span style="font-style: italic">Not Sent</span></td>
    *       <td>CASECOUNT</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td><span style="font-style: italic">Not Sent</span></td>
    *       <td>ODD_EVEN</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td><span style="font-style: italic">Not Sent</span></td>
    *       <td>DOI_COUNT</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td><span style="font-style: italic">Not Sent</span></td>
    *       <td>C_BARCODE_CASE_NUMBER</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td><span style="font-style: italic">Not Sent</span></td>
    *       <td>C_BARCODE_PART_TYPE</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td><span style="font-style: italic">Not Sent</span></td>
    *       <td>C_BARCODE_DOC_TYPE</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *     <tr>
    *       <td><span style="font-style: italic">Not Sent</span></td>
    *       <td>C_DOC_TYPE_MR</td>
    *       <td>String</td>
    *       <td></td>
    *     </tr>
    *   </tbody>
    * </table>
    * 
    * @apiGroup NOARFI
    *
    * @apiHeader {String} Authorization Bearer <code>JWT Access Token</code>
    * @apiHeader {String} Accepts text/plain (response will be CSV)
    *
    * @apiParam {String} ManifestDate (yyyy-MM-dd)<span style="font-style: italic">(sent in path)</span>
    * @apiParam {String} option "<code>legacy</code>"<span style="font-style: italic">(optional url parameter to request legacy format)</span>
    *
    *
    * @apiExample {json} NOARFI Detail 
    * GET /apigw/webservices/rest/apigw/events/noarfi/datelist/2016-09-27 HTTP/1.1
    * User-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)
    * Host: imr-ca-sandbox.maxird.com
    * Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJmMmQxN2ZlZC0yZGI4LTQ2NjItYjQzNy0zZjgzYzIzM2NlN2IiLCJleHAiOjE0NzE1ODUwODIsIm5iZiI6MCwiaWF0IjoxNDcxNTgxNDgyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6ImM0ZThmODFiLTdhY2QtNGU5OC1iNjc3LTUzYmYxYjIzMzc1OSIsImNsaWVudF9zZXNzaW9uIjoiNDA3MzQxMzMtOGQ0Mi00ZDI3LTg2MmYtMTdmOTI4MjQ2ZTliIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.OD-S5raMHug0x8pMO7zHcHwzZuosSWx4cH1ktZ557itofxNUWinQgfvngdSg5cvNYpoEChqwQSz-Vd8GYjkjKrE5E-AK94kTLPE3MfzQ5X4KzRMovChshGVJK-sQCrnZONS77G-_UVXL5fITeWKUZnrarvM1X3kr-O0yMIRaB-DOs_hFK972-hB_zo-QMej0XIrTth-yjl5NwtRXXpKrAjqY-em7EWDL--k1Gd-Q4mj9wCTCxsKnIiElexVTaboz0WSBFxKd43EeCTdRC5bLwBVQvS3USCYBnioRnY79sd3GxELM7Zg8F6zgprlQl1P_lQ_5J8y4jO42qxhei92IlQ
    *
    * @apiSuccess {String[]} datelist array of ISO 8601 dates with NOARFIs

    * @apiSuccessExample Default Response
    * HTTP/1.1 200 OK
    * Server: Apache-Coyote/1.1
    * X-Content-Type-Options: nosniff
    * X-XSS-Protection: 1; mode=block
    * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
    * Pragma: no-cache
    * Expires: 0
    * X-Frame-Options: DENY
    * Date: Fri, 19 Aug 2016 04:39:30 GMT
    * Content-Type: text/plain
    * Transfer-Encoding: chunked
    *
    * claimsExaminerFirstName,claimsExaminerLastName,claimsExaminerSuffix,injuredWorkrSuffix,claimsAdministratorAddress1,claimsAdministratorAddress2,claimsAdministratorCity,claimsAdministratorState,claimsAdministratorZipCode,injuredWorkerFirstName,injuredWorkerLastName,caAppealNumber,caseNumber,systemDateIso,participantType,providerName,claimsAdministratorCompanyName,claimNumber,priority,dateOfInjury,dateofURDecision,treatmentRequested,receiveDate
    * June,Winds,,,17 Westerly Lane,,Fresno,California,93721,John,Smith,,CM16-10000007,09/27/2016,CLAIMS ADMINISTRATOR,Fred Fraggle,Zesty Zebra,10000007,Standard,12/31/2012,11/26/2012,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et tellus sapien. ",06/25/2016
    * June,Winds,,,17 Westerly Lane,,Fresno,California,93721,Steve,Smith,,CM16-10000025,09/27/2016,CLAIMS ADMINISTRATOR,Fred Fraggle,Zesty Zebra,10000025,Standard,2/1/2014,11/26/2014,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et tellus sapien. Nam sit amet ullamcorper lectus. Curabitur eu dolor dictum lorem eleifend laoreet.",06/25/2016
    *     
    * @apiSuccessExample Legacy Response
    * HTTP/1.1 200 OK
    * Server: Apache-Coyote/1.1
    * X-Content-Type-Options: nosniff
    * X-XSS-Protection: 1; mode=block
    * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
    * Pragma: no-cache
    * Expires: 0
    * X-Frame-Options: DENY
    * Date: Fri, 19 Aug 2016 04:39:30 GMT
    * Content-Type: text/plain
    * Transfer-Encoding: chunked
    *
    * C_PARTICIPANT_CONTACT_F_NAME,C_PARTICIPANT_CONTACT_L_NAME,C_PARTICIPANT_CONTACT_SUFFIX,IW_SUFFIX,C_PARTICIPANT_1ST_STREET_ADDRE,C_PARTICIPANT_2ND_STREET_ADDRE,C_PARTICIPANT_CITY,C_PARTICIPANT_STATE,C_PARTICIPANT_POSTAL_CODE,IW_F_NAME,IW_L_NAME,C_CA_APPEAL_NUMBER,C_CASE_NUMBER,SYSTEM_DATE,PARTICIPANT_TYPE,PROVIDER,C_PARTICIPANT_ORGAN_NAME,C_CLAIMS_NUMBER,PRIORITY_TYPE,C_INJURY_DATE,C_UR_DEC_DATE,C_TXREQ,C_RCVDATE,C_ACKSENTDATE,SEQUENCE_NUMBER,FILENAME,CASECOUNT,ODD_EVEN,DOI_COUNT,C_BARCODE_CASE_NUMBER,C_BARCODE_PART_TYPE,C_BARCODE_DOC_TYPE,C_DOC_TYPE_MR
    * June,Winds,,,17 Westerly Lane,,Fresno,California,93721,John,Smith,,CM16-10000007,09/27/2016,CLAIMS ADMINISTRATOR,Fred Fraggle,Zesty Zebra,10000007,Standard,12/31/2012,11/26/2012,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et tellus sapien. ",06/25/2016,,,,,,,,,,
    * June,Winds,,,17 Westerly Lane,,Fresno,California,93721,Steve,Smith,,CM16-10000025,09/27/2016,CLAIMS ADMINISTRATOR,Fred Fraggle,Zesty Zebra,10000025,Standard,2/1/2014,11/26/2014,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et tellus sapien. Nam sit amet ullamcorper lectus. Curabitur eu dolor dictum lorem eleifend laoreet.",06/25/2016,,,,,,,,,,
    *
    * @apiUse ErrorResponse
    * @apiVersion 1.0.0
    */


    /**
    * @api {delete} apigw/webservices/rest/apigw/events/noarfi/:date 02.Acknowledge Manifest Date
    * @apiName NOARFIAck
    * @apiDescription Indicate to the system that you have successfully retrieved the NOARFI list for the specified date and to no longer show these records as being available for a manifest download.
    *
    * @apiGroup NOARFI
    *
    * @apiHeader {String} Authorization Bearer <code>JWT Access Token</code>
    * @apiHeader {String} Accepts application/json 
    *
    * @apiParam {String} ManifestDate (yyyy-MM-dd)<span style="font-style: italic">(sent in path)</span>
    *
    * @apiExample Acknowledgement Request
    * DELETE /apigw/webservices/rest/apigw/events/noarfi/2016-09-27 HTTP/1.1
    * User-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)
    * Host: imr-ca-sandbox.maxird.com
    * Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI5MzFmNGQwNy0wNzhlLTRlNjUtOWFhMC1lOTI1MzE3NDY5YjgiLCJleHAiOjE0NzE3NTQ2MzksIm5iZiI6MCwiaWF0IjoxNDcxNzUxMDM5LCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6ImFkNjU2NDU0LTFjZjgtNGVhNC05NzE3LWM5OGQ3MDczMzI5MyIsImNsaWVudF9zZXNzaW9uIjoiYTJiOTdhZDEtM2M3OC00NWUzLTg5YzItZDVjYTE5YzQyMWRhIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.WmQd3b0bo7gntuU4pu4EYr8T4cVzz59z6FTOi3Kx61kpb-XSJsYp8nNFab3ZiKQ6MPmxPXyF7sUFWO4pQlAazoyZvov77_EHx9f4sPlfCWoJ6s0kCvOTT-bTlHV85td5qaWZ-cSB9MrTBLovBYsiezFwOnxZbVLgdzJp8Zw3LaDhKzYs4Xz-PQfhgTyx2Oh1owxqfTRAoYXtBMgB6SEtAPQRI_TOiHm5iZ79sBy_YY_S0w49u1kHCDKxLt9yNfhnytaWDSRpTkWogwsC_5Cdui2oadsxaZQzgY8GG8oewZyYpdLiNDHsQDbVBU3WL3upT0HBJutzCmBWrP-6JPAiSA
    * Accept: application/json
    *
    * @apiSuccessExample Acknoledgement Response
    * HTTP/1.1 200 OK
    * Server: Apache-Coyote/1.1
    * X-Content-Type-Options: nosniff
    * X-XSS-Protection: 1; mode=block
    * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
    * Pragma: no-cache
    * Expires: 0
    * X-Frame-Options: DENY
    * Date: Sun, 21 Aug 2016 04:22:26 GMT
    * Content-Type: application/json
    * Transfer-Encoding: chunked
    *
    * {"status":200,"msg":"OK"}
    *
    * @apiUse ErrorResponse
    * @apiVersion 1.0.0
    *
    */
