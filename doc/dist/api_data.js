define({ "api": [
  {
    "type": "post",
    "url": "auth/realms/dxc-externals/protocol/openid-connect/token",
    "title": "00.Login",
    "name": "Login",
    "description": "<p>DXC web services use KeyCloak for OAuth2 (more information <a href=\"http://imrcasdk.maxird.com/oauthoverview.html\">here</a>). Call this KeyCloak API first to login with your valid system service account credentials (see <a href=\"http://imrcasdk.maxird.com/oauthoverview.html#imr-ca-oauth-account-link\">here</a> on how to get credentials). Securely cache the <code>Access Token</code> and <code>Refresh Token</code> returned in this response for use in subsequent DXC API requests. Note the time your obtained the <code>Access Token</code> and the returned <code>expires_in</code> value to calculate the expiration time of this access token. On all subsequent API requests with DXC, pass along the <code>Access Token</code> in the HTTP Authorization header field. Be sure to refresh your <code>Access Token</code> with the Refresh API request before it expires.</li></p> <div style=\"font-style: italic;\">Service Provided by KeyCloak</div>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "grant_type",
            "description": "<p>Always use &quot;password&quot;</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "client_id",
            "description": "<p>Always use &quot;dxc&quot;</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>Send your <code>ServiceAccount</code></p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>Send your Service Account <code>Password</code></p>"
          }
        ]
      }
    },
    "version": "0.9.0",
    "group": "Authentication",
    "examples": [
      {
        "title": "OAuth2 Request",
        "content": "POST /auth/realms/dxc-externals/protocol/openid-connect/token HTTP/1.1\nUser-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)\nHost: imr-ca-sandbox.maxird.com\nContent-Type: application/x-www-form-urlencoded\nContent-Length: 90\n\ngrant_type=password&client_id=dxc&username=service.zesty2@maxird.com&password=S0m3P@wOrd",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "OAuth2 Response",
          "content": "HTTP/1.1 200 OK\nX-Powered-By: Undertow/1\nServer: WildFly/10\nContent-Type: application/json\nContent-Length: 3847\nDate: Wed, 17 Aug 2016 19:13:04 GMT\n\n{\n  \"access_token\": \"eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIyNTZjOTMyNC02MTg1LTQ5MWMtYmQ0Ni1hZGE0ZWIyNzY2YTIiLCJleHAiOjE0NzE0NjQ5MjIsIm5iZiI6MCwiaWF0IjoxNDcxNDYxMzIyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6IjkzZmQ4NmJiLTg5NjgtNGVkYi1hMTZiLWMzY2ZmOWRkZGMxZiIsImNsaWVudF9zZXNzaW9uIjoiYWJiN2NiOTQtMTRiYy00OWMzLWJhMmYtMTVjZGMyNmVmM2RiIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.IGnhJCaUpBMZ-f5ZScnzJcNfnTLuaA0KsYYkdyQMMgGMd_wAj1d5SoVfsYd_vQkaSUyueqoRNApLFwv3BtkXDzyOLKBGGWl24Rmrt1-6pdSNOCpWkZmraFx4x9DfR7mSK13ucEvH35S1O_b9C41_96s_zH27ss9yTp_Q-G_rBvllAEbBu-rKk9ug0HzTNwlIyvg9g8AIgOZZUO8jB0Cu8Y0OjgQzdJwmw-zktmQhq5jf3R_1z--Syoe3m4gZbVG_SFJf3i06VG3rkMCED3AUr7yrXAOseaPkDBdQnoF7ActcHZxZDhB7CBwoyBL6WhekN7Mq38W87H01nLz8AOALIg\",\n  \"expires_in\": 3600,\n  \"refresh_expires_in\": 86400,\n  \"refresh_token\": \"eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI0NWJkNGIyNy0yMmI0LTQxMTctOGMwOS02YTg5YjA4YzYzYTQiLCJleHAiOjE0NzE1NDc3MjIsIm5iZiI6MCwiaWF0IjoxNDcxNDYxMzIyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImR4YyIsInNlc3Npb25fc3RhdGUiOiI5M2ZkODZiYi04OTY4LTRlZGItYTE2Yi1jM2NmZjlkZGRjMWYiLCJjbGllbnRfc2Vzc2lvbiI6ImFiYjdjYjk0LTE0YmMtNDljMy1iYTJmLTE1Y2RjMjZlZjNkYiIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJEZWZhdWx0IFNlcnZpY2UgUm9sZSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImR4YyI6eyJyb2xlcyI6WyJjYXNlLmFsbCIsImNhc2UuYXBwZW5kIiwiY2FzZS5hc3NpZ24iLCJjYXNlLmNvbW1lbnQiLCJjYXNlLmFkbWluIiwiY2FzZS5zdGF0ZSIsImNhc2UuaWRlbnRpZmllcnMiLCJjYXNlLnRyYW5zZmVyIiwiY2FzZS52aWV3IiwiY2FzZS5pbnZpdGUiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJ2aWV3LXByb2ZpbGUiXX19fQ.AQlRqa3ZNd7tv1Lb5qfw9Cl1F1WYG6XJs5FPBy8rr7W_q4676Ias2Ew5u8k9exlmrJhqWvMdY85tzBrVhNq4So3ZGzG084v0t2F1n4LX9w1kyo1q6ka-6nhiATwJC0wVHy0q-RjCSMElr2j5z3oxKO4LyT37123sRNZ4pOs1vA2D-ddEOX980NdVsyqX_MBW9p4OPC9esY0jZYXRX1svmcidyB6wj26vgpg9V-xiec3qxJuYLz8kHbtaa4jtVlEB-eKr_ZhWzl4x04lY6VtQ_G5pxEq04R1JQWHgI0befkdwcA1ut005OdablGmp3g_Hbmh07cdz0_HaKFo-yWD63A\",\n  \"token_type\": \"bearer\",\n  \"id_token\": \"eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI5OGE4MDVhMC03Y2QxLTQ2ODEtYjE0YS0xNjcxYzZhNmQyOTQiLCJleHAiOjE0NzE0NjQ5MjIsIm5iZiI6MCwiaWF0IjoxNDcxNDYxMzIyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiSUQiLCJhenAiOiJkeGMiLCJzZXNzaW9uX3N0YXRlIjoiOTNmZDg2YmItODk2OC00ZWRiLWExNmItYzNjZmY5ZGRkYzFmIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsImdpdmVuX25hbWUiOiJaRVNUWVpFQlJBIiwiZmFtaWx5X25hbWUiOiJTZXJ2aWNlIiwiZW1haWwiOiJzZXJ2aWNlLnplc3R5MkBtYXhpcmQuY29tIiwicGFydGljaXBhbnQiOiIyMC41MDAwLjIxNC9wemVzdHkifQ.fn7UnGDkPaV8uh9681ZQqAexY0GVCKXkxObQ0zV_eiVinnqBfHuZt9-QZnEy4SIUD4EI6QDtS3Bx-Y4iIXg4kg6RfZ712rAppzRsmpyVDWyCHP4jgm6LWzdxEFt4r0mINoYuSrtUJH1R5f_zxAizv2Uu3Ee2_9oqPskhbljSZXaPFl4K6LBp6YeGwANWNoi77gDzK-al-AXeXnqRKjVw2ASPxixDmdaGivrVsG9VZRjRZNLCfHckr7F_5Jt0MqwYwFD7PbE4FXkuHEEzi6PpgSI15Ve12wUyWxKGnYYANQBWd6MpfVC0FjA0pfmd0jiMigvx1rt_GRgApQLWDULOQA\",\n  \"not-before-policy\": 0,\n  \"session_state\": \"93fd86bb-8968-4edb-a16b-c3cff9dddc1f\"\n}",
          "type": "json"
        }
      ],
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "access_token",
            "description": "<p>JWT Access Token (required for all API requests)</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "expires_in",
            "description": "<p>Number of seconds until access token expires</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "refresh_expires_in",
            "description": "<p>Number of seconds until refresh token expires</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "refresh_token",
            "description": "<p>JWT Refresh Token (required to obtain a new access token)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "token_type",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id_token",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "not-before-policy",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "session_state",
            "description": ""
          }
        ]
      }
    },
    "filename": "src/docs.java",
    "groupTitle": "Authentication"
  },
  {
    "type": "post",
    "url": "auth/realms/:realm/protocol/openid-connect/token",
    "title": "01.Refresh",
    "name": "Refresh",
    "description": "<p>This KeyCloak service is called to obtain a new <code>Access Token</code> before the current one expires.  After calling this refresh service, applications should retain (and sufficiently secure the new <code>Access Token</code> returned in this response and use it in subsequent DXC API calls in place of the prior <code>Access Token</code>.  Failing to refresh an <code>Access Token</code> will result in DXC rejected requests with Access Denied once the Access Token has expired.    For security reasons its preferable that applications login only as often as necessary and rely on this refresh mechanism to obtain new <code>Access Tokens</code> rather than issue login request more frequently.</p> <div style=\"font-style: italic;\">Service Provided by KeyCloak</div>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "realm",
            "description": "<p>Always use &quot;dxc-externals&quot; <span style=\"font-style: italic\">(sent in path)</span></p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "grant_type",
            "description": "<p>Always use &quot;refresh_token&quot;</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "client_id",
            "description": "<p>Always use &quot;dxc&quot;</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "refresh_token",
            "description": "<p>Send your JWT <code>Refresh Token</code></p>"
          }
        ]
      }
    },
    "version": "0.9.0",
    "group": "Authentication",
    "examples": [
      {
        "title": "OAuth2 Request",
        "content": "POST /auth/realms/dxc-externals/protocol/openid-connect/token HTTP/1.1\nUser-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)\nHost: imr-ca-sandbox.maxird.com\nContent-Length: 1263\nContent-Type: application/x-www-form-urlencoded\nExpect: 100-continue\n\ngrant_type=refresh_token&client_id=dxc&refresh_token=eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI0NWJkNGIyNy0yMmI0LTQxMTctOGMwOS02YTg5YjA4YzYzYTQiLCJleHAiOjE0NzE1NDc3MjIsIm5iZiI6MCwiaWF0IjoxNDcxNDYxMzIyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImR4YyIsInNlc3Npb25fc3RhdGUiOiI5M2ZkODZiYi04OTY4LTRlZGItYTE2Yi1jM2NmZjlkZGRjMWYiLCJjbGllbnRfc2Vzc2lvbiI6ImFiYjdjYjk0LTE0YmMtNDljMy1iYTJmLTE1Y2RjMjZlZjNkYiIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJEZWZhdWx0IFNlcnZpY2UgUm9sZSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImR4YyI6eyJyb2xlcyI6WyJjYXNlLmFsbCIsImNhc2UuYXBwZW5kIiwiY2FzZS5hc3NpZ24iLCJjYXNlLmNvbW1lbnQiLCJjYXNlLmFkbWluIiwiY2FzZS5zdGF0ZSIsImNhc2UuaWRlbnRpZmllcnMiLCJjYXNlLnRyYW5zZmVyIiwiY2FzZS52aWV3IiwiY2FzZS5pbnZpdGUiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJ2aWV3LXByb2ZpbGUiXX19fQ.AQlRqa3ZNd7tv1Lb5qfw9Cl1F1WYG6XJs5FPBy8rr7W_q4676Ias2Ew5u8k9exlmrJhqWvMdY85tzBrVhNq4So3ZGzG084v0t2F1n4LX9w1kyo1q6ka-6nhiATwJC0wVHy0q-RjCSMElr2j5z3oxKO4LyT37123sRNZ4pOs1vA2D-ddEOX980NdVsyqX_MBW9p4OPC9esY0jZYXRX1svmcidyB6wj26vgpg9V-xiec3qxJuYLz8kHbtaa4jtVlEB-eKr_ZhWzl4x04lY6VtQ_G5pxEq04R1JQWHgI0befkdwcA1ut005OdablGmp3g_Hbmh07cdz0_HaKFo-yWD63A",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "OAuth2 Response",
          "content": "HTTP/1.1 100 Continue\nContent-Length: 0\nHTTP/1.1 200 OK\nX-Powered-By: Undertow/1\nServer: WildFly/10\nContent-Type: application/json\nContent-Length: 3847\nDate: Wed, 17 Aug 2016 20:43:28 GMT\n{\n  \"access_token\": \"eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIzMjk0MjU0Yy05OTQxLTQ0YzgtYmQwYS02N2E2YzVhYjhlZDgiLCJleHAiOjE0NzE0NzAyMDgsIm5iZiI6MCwiaWF0IjoxNDcxNDY2NjA4LCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6IjkzZmQ4NmJiLTg5NjgtNGVkYi1hMTZiLWMzY2ZmOWRkZGMxZiIsImNsaWVudF9zZXNzaW9uIjoiYWJiN2NiOTQtMTRiYy00OWMzLWJhMmYtMTVjZGMyNmVmM2RiIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.NmE2qD0JZsnqvyDuMyN2-pRD263mqxFUh5wWYJCornNafUzmHKqO_OZ3iXCbVUgM5UDB-rQFpUEOq-4VxF3trVfXYDdJ2SEB2M67fsP_vqjeSUrdxTaZIyMGRIF-Jf3ir7HQtbVbc73rIPvvPn8MhQkBxzdeg6WEXSSFMwEvP5z2f1SRhKH56LEmwG54XS5fsrGhTLtRkBpmS-fOQbyB-kS4sRzJHFqHXqdPGVgE3cRf7zvlnlAc3hj28QKCW6coy4jfZ3LmLtAp1_4Lzq8YlxkYxOPH63qGQhxWpYOxQgbrHMN5H7UQCGdT_sm8p6UwUgOMIPesV6F1hMwuS_UZIw\",\n  \"expires_in\": 3600,\n  \"refresh_expires_in\": 86400,\n  \"refresh_token\": \"eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIxMTJkYjFiMS0wYjU2LTRiMjctYmYyYS1hNGMyNjRhOTlmOWIiLCJleHAiOjE0NzE1NTMwMDgsIm5iZiI6MCwiaWF0IjoxNDcxNDY2NjA4LCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImR4YyIsInNlc3Npb25fc3RhdGUiOiI5M2ZkODZiYi04OTY4LTRlZGItYTE2Yi1jM2NmZjlkZGRjMWYiLCJjbGllbnRfc2Vzc2lvbiI6ImFiYjdjYjk0LTE0YmMtNDljMy1iYTJmLTE1Y2RjMjZlZjNkYiIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJEZWZhdWx0IFNlcnZpY2UgUm9sZSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImR4YyI6eyJyb2xlcyI6WyJjYXNlLmFsbCIsImNhc2UuYXBwZW5kIiwiY2FzZS5hc3NpZ24iLCJjYXNlLmNvbW1lbnQiLCJjYXNlLmFkbWluIiwiY2FzZS5zdGF0ZSIsImNhc2UuaWRlbnRpZmllcnMiLCJjYXNlLnRyYW5zZmVyIiwiY2FzZS52aWV3IiwiY2FzZS5pbnZpdGUiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJ2aWV3LXByb2ZpbGUiXX19fQ.kW40TUz9qAWF-mBBextDjGcYN78Pj0AcGDaio2PyMMZ9p-lQYaeBfAWjVctBJ6c_0--GKvb9SSoLWTJcelIZoWhundGDp44YlciNbVFTUV6TyTqr0-b051rIuaAErkdS470iYRPUQkyN6oje3GvSvIIKcBuQBWPc1_Y_bJfVhoWBDnVPsbVxNkk4svyCLak7ekr0eeMvhOUrHvLbKSoF4Ky4__ic2IlkaFeU_pTXRjy4ru09Lf19s_AufvPbHIf04vwyT99Y14wBdlZywt5erF0mpBivXQ8LBQUhHT1nilPnUskAOHBGCh0hqkuV-TZv4j4jQUS8HNPITAg_F82VVA\",\n  \"token_type\": \"bearer\",\n  \"id_token\": \"eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJiOTU5MGFkMy01OWU4LTRkMjYtYmNjMS02OGVkN2M5M2JkMTUiLCJleHAiOjE0NzE0NzAyMDgsIm5iZiI6MCwiaWF0IjoxNDcxNDY2NjA4LCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiSUQiLCJhenAiOiJkeGMiLCJzZXNzaW9uX3N0YXRlIjoiOTNmZDg2YmItODk2OC00ZWRiLWExNmItYzNjZmY5ZGRkYzFmIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsImdpdmVuX25hbWUiOiJaRVNUWVpFQlJBIiwiZmFtaWx5X25hbWUiOiJTZXJ2aWNlIiwiZW1haWwiOiJzZXJ2aWNlLnplc3R5MkBtYXhpcmQuY29tIiwicGFydGljaXBhbnQiOiIyMC41MDAwLjIxNC9wemVzdHkifQ.NFcxVC7KJSZL453kFYhHFY_sbyGryT-Lo4y_5BVQ0Ark8JH1ttMBAd5tCKHC0vSuNJ_Qv57iMcBfsdGFK83w2K_ba3BQEVTa3ec1KxGJXVqmYJ3F6xuoh9CdHv0sw9VYtTBdSt_YLfoFBiVKtpzPWRwMTYY6o4HnHa_ViI8XLSux5MioJejnVVsBNxGFjGaJT7Z5P5BKSWSOHUjBzPzT3pqDYcm30fibm6j1Y3xwj39825yTVTmEmIzugXszuaCa_25fl8p8RerWgj1GEGkjhd4bRV5oR2av1RAnbR1Ot_xQLIeFkpEqsPQVThLy5F9U0sNo6Alj3afg0EMCglxDHg\",\n  \"not-before-policy\": 0,\n  \"session_state\": \"93fd86bb-8968-4edb-a16b-c3cff9dddc1f\"\n}",
          "type": "json"
        }
      ],
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "access_token",
            "description": "<p>JWT Access Token (required for all API requests)</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "expires_in",
            "description": "<p>Number of seconds until access token expires</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "refresh_expires_in",
            "description": "<p>Number of seconds until refresh token expires</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "refresh_token",
            "description": "<p>JWT Refresh Token (required to obtain a new access token)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "token_type",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id_token",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "not-before-policy",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "session_state",
            "description": ""
          }
        ]
      }
    },
    "filename": "src/docs.java",
    "groupTitle": "Authentication"
  },
  {
    "type": "post",
    "url": "apigw/webservices/rest/apigw/cases/search",
    "title": "00.Search",
    "name": "SearchCases",
    "group": "Cases",
    "description": "<p><h2 style=\"margin-top: 0.75em; margin-bottom:0.3em;\">Overview</h2></p> <div>Conducts a search across all cases in your realm.  You can search by IMRID, CAID or an array of statuses.</div> <h2 style=\"margin-top: 0.75em; margin-bottom: 0.3em;\">Paging</h2> <div>When search results get large, DXC searches provide a paging mechanism.  To page through a result set, simply pass <code>start</code> and <code>limit</code> parameters as part of the search request.  For example to get just the first 10 results, specify a <code>start=0&amp;limit=10</code>.  To get the next 10 results send <code>start=1&amp;limit=10</code>. The `start` parameter represents the *page* of results.  Its always a good idea to also include some sort criteria when using paging.  For example to see the ten most recent changes to the cases in your realm use: <code>{\"start\":0,\"limit\":10,\"sort\":[{\"property\":\"modificationDate\",\"desc\":true}]}</code>. <span style=\"font-style: italic;\">For large result sets, paging is strongly encouraged and may be enforced in the future.</span> </div> <h2 style=\"margin-top: 0.75em; margin-bottom: 0.3em;\">Output Formats</h2> <div>This API request can provide repsonses in either JSON or CSV.  The response format is determined by the Accept HTTP header (\"application/json\" for JSON and \"text/plain\" for CSV).  When returning CSV only the <code>results</code> array is returned in CSV format (<code>matchCount</code>, <code>start</code> and <code>limit</code> are not included).  Additionally (as demonstrated in the sample CSV Response), some fields may contain embedded line breaks.  These fields will be enclosed in quotes.  This behavior may cause some records to span multiple lines.</div>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Bearer <code>JWT Access Token</code></p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accepts",
            "description": "<p>application/json (for JSON response) or text/plain (for CSV response)</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Object[]",
            "optional": false,
            "field": "sort",
            "description": "<p>Sort order criteria</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "sort.property",
            "description": "<p>property name to sort on</p>"
          },
          {
            "group": "Parameter",
            "type": "Boolean",
            "optional": false,
            "field": "sort.desc",
            "description": "<p>whether a property sort is descending</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "CAID",
            "description": "<p>Claims Administrator Case Number</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "IMRID",
            "description": "<p>IMR Case Number</p>"
          },
          {
            "group": "Parameter",
            "type": "String[]",
            "optional": false,
            "field": "statuses",
            "description": "<p>array of string values for statuses (e.g.: [&quot;1&quot;, &quot;3&quot;])</p> <ul style=\"list-style: none\"> <li>\"1\" - Received</li> <li>\"2\" - Eligibility Review</li> <li>\"3\" - Records Requested</li> <li>\"4\" - Clinical Review</li> <li>\"5\" - Closed</li> <li>\"6\" - NOARFI</li> <li>\"7\" - Insufficient Records</li> </ul>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "start",
            "description": "<p>The index of the search results you wish returned. If not supplied or invalid it will be replaced with 0.</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "limit",
            "description": "<p>The number of results to return. If not supplied the specific API will select the maximum results to return. If the value is considered too large by the specific API it may alter this value in the response and ignore the supplied value.</p>"
          }
        ]
      }
    },
    "version": "0.9.0",
    "examples": [
      {
        "title": "Search Request",
        "content": "POST /apigw/webservices/rest/apigw/cases/search HTTP/1.1\nUser-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)\nHost: imr-ca-sandbox.maxird.com\nAuthorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIyNTZjOTMyNC02MTg1LTQ5MWMtYmQ0Ni1hZGE0ZWIyNzY2YTIiLCJleHAiOjE0NzE0NjQ5MjIsIm5iZiI6MCwiaWF0IjoxNDcxNDYxMzIyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6IjkzZmQ4NmJiLTg5NjgtNGVkYi1hMTZiLWMzY2ZmOWRkZGMxZiIsImNsaWVudF9zZXNzaW9uIjoiYWJiN2NiOTQtMTRiYy00OWMzLWJhMmYtMTVjZGMyNmVmM2RiIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.IGnhJCaUpBMZ-f5ZScnzJcNfnTLuaA0KsYYkdyQMMgGMd_wAj1d5SoVfsYd_vQkaSUyueqoRNApLFwv3BtkXDzyOLKBGGWl24Rmrt1-6pdSNOCpWkZmraFx4x9DfR7mSK13ucEvH35S1O_b9C41_96s_zH27ss9yTp_Q-G_rBvllAEbBu-rKk9ug0HzTNwlIyvg9g8AIgOZZUO8jB0Cu8Y0OjgQzdJwmw-zktmQhq5jf3R_1z--Syoe3m4gZbVG_SFJf3i06VG3rkMCED3AUr7yrXAOseaPkDBdQnoF7ActcHZxZDhB7CBwoyBL6WhekN7Mq38W87H01nLz8AOALIg\nContent-Type: application/json\nAccept: application/json\nContent-Length: 51\n\n{\"start\":0,\"limit\":25,\"sort\":[{\"property\":\"dateOfInjury\",\"desc\":false}]}",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "JSON Response",
          "content": "HTTP/1.1 200 OK\nServer: Apache-Coyote/1.1\nX-Content-Type-Options: nosniff\nX-XSS-Protection: 1; mode=block\nCache-Control: no-cache, no-store, max-age=0, must-revalidate\nPragma: no-cache\nExpires: 0\nX-Frame-Options: DENY\nDate: Wed, 17 Aug 2016 19:18:41 GMT\nContent-Type: application/json\nTransfer-Encoding: chunked\n\n{\n  \"matchCount\": 3,\n  \"start\": 0,\n  \"limit\": 10,\n  \"results\": [\n    {\n      \"claimNumber\": \"10000001\",\n      \"caseNumber\": \"CM16-10000001\",\n      \"dateOfInjury\": \"2013-01-01T09:00:00+0500\",\n      \"priority\": \"Expedited\",\n      \"injuredWorkerPrefix\": \"Mrs.\",\n      \"injuredWorkerFirstName\": \"Jane\",\n      \"injuredWorkerLastName\": \"Smith\",\n      \"injuredWorkerMiddleInitial\": \"M\",\n      \"injuredWorkrSuffix\": \"III\",\n      \"employerName\": \"Broadway Busters\",\n      \"treatmentRequested\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et tellus sapien. Nam sit amet ullamcorper lectus. Curabitur eu dolor dictum lorem eleifend laoreet. Pellentesque odio eros, maximus vitae purus sit amet, elementum facilisis nunc. Integer velit metus, fringilla id elit lobortis, ullamcorper sollicitudin nibh. Duis ac pulvinar libero, ac hendrerit turpis. Duis sollicitudin nunc quis mi ultricies pharetra.\\n\\nNullam bibendum vel nisi sed pulvinar. Nam semper venenatis ultricies. Pellentesque quis lectus nisi. Nam eu nibh condimentum, fermentum nibh vitae, lacinia magna. Etiam a orci tincidunt, mollis nunc ut, ullamcorper ex. Aenean faucibus porttitor sapien quis lobortis. Donec rutrum molestie justo, sed pharetra lorem pulvinar sit amet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed pulvinar iaculis sodales. Etiam dolor nibh, bibendum eget risus sed, interdum volutpat nisl. Curabitur suscipit, est vel ultricies fringilla, libero orci pretium augue, vel tincidunt tortor lectus sit amet mauris. Praesent a luctus diam.\\n\\nMaecenas luctus erat ut erat tincidunt fringilla. Vestibulum vel venenatis dui, quis lobortis augue. Quisque rhoncus vel tellus sollicitudin tristique. Nunc sit amet lectus sed augue aliquet congue sit amet quis turpis. Etiam id efficitur lacus. Mauris eleifend purus id suscipit congue. Donec vitae quam ac nunc aliquet euismod. Curabitur aliquet mi sit amet mollis semper.\\n\\nCras facilisis vestibulum nunc sed eleifend. Etiam viverra, enim quis elementum egestas, nisl nibh ullamcorper ipsum, hendrerit lacinia nisi neque eget magna. Suspendisse scelerisque auctor aliquam. Duis a maximus metus. Vivamus vel fermentum velit. Nullam rutrum ante quis est ultrices, et tempus ex pellentesque. Mauris mollis libero in diam accumsan, vitae sagittis tortor accumsan. In lacus erat, pellentesque sit amet ante a, ullamcorper vulputate felis. Nunc iaculis finibus arcu, eget sollicitudin elit dapibus vel.\\n\\n\",\n      \"claimsAdministratorAddress1\": \"2309 Tulare St\",\n      \"claimsAdministratorCity\": \"Fresno\",\n      \"claimsAdministratorState\": \"California\",\n      \"claimsAdministratorZipCode\": \"93721\",\n      \"claimsAdministratorCompanyName\": \"Zesty Zebra\",\n      \"providerPrefix\": \"Mrs.\",\n      \"providerFirtName\": \"Anna\",\n      \"providerLastName\": \"Jones\",\n      \"providerMidleInitial\": \"M\",\n      \"providerSuffix\": \"Esq\",\n      \"organizationName\": \"Provider Org\",\n      \"status\": \"1\",\n      \"dateofURDecision\": \"2012-11-27T09:00:00+0500\",\n      \"receiveDate\": \"2016-06-21T09:00:00+0500\",\n      \"modificationDate\": \"2016-08-15T13:03:16.031Z\"\n    },\n    {\n      \"claimNumber\": \"10000003\",\n      \"caseNumber\": \"CM16-10000003\",\n      \"dateOfInjury\": \"2013-01-01T09:00:00+0500\",\n      \"priority\": \"Normal\",\n      \"injuredWorkerPrefix\": \"Ms.\",\n      \"injuredWorkerFirstName\": \"Terri\",\n      \"injuredWorkerLastName\": \"Talker\",\n      \"injuredWorkerMiddleInitial\": \"T\",\n      \"injuredWorkrSuffix\": \"\",\n      \"employerName\": \"Talking Tall\",\n      \"treatmentRequested\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et tellus sapien. Nam sit amet ullamcorper lectus. Curabitur eu dolor dictum lorem eleifend laoreet. Pellentesque odio eros, maximus vitae purus sit amet, elementum facilisis nunc. Integer velit metus, fringilla id elit lobortis, ullamcorper sollicitudin nibh. Duis ac pulvinar libero, ac hendrerit turpis. Duis sollicitudin nunc quis mi ultricies pharetra.\\n\\nNullam bibendum vel nisi sed pulvinar. Nam semper venenatis ultricies. Pellentesque quis lectus nisi. Nam eu nibh condimentum, fermentum nibh vitae, lacinia magna. Etiam a orci tincidunt, mollis nunc ut, ullamcorper ex. Aenean faucibus porttitor sapien quis lobortis. Donec rutrum molestie justo, sed pharetra lorem pulvinar sit amet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed pulvinar iaculis sodales. Etiam dolor nibh, bibendum eget risus sed, interdum volutpat nisl. Curabitur suscipit, est vel ultricies fringilla, libero orci pretium augue, vel tincidunt tortor lectus sit amet mauris. Praesent a luctus diam.\\n\\nMaecenas luctus erat ut erat tincidunt fringilla. Vestibulum vel venenatis dui, quis lobortis augue. Quisque rhoncus vel tellus sollicitudin tristique. Nunc sit amet lectus sed augue aliquet congue sit amet quis turpis. Etiam id efficitur lacus. Mauris eleifend purus id suscipit congue. Donec vitae quam ac nunc aliquet euismod. Curabitur aliquet mi sit amet mollis semper.\\n\\nCras facilisis vestibulum nunc sed eleifend. Etiam viverra, enim quis elementum egestas, nisl nibh ullamcorper ipsum, hendrerit lacinia nisi neque eget magna. Suspendisse scelerisque auctor aliquam. Duis a maximus metus. Vivamus vel fermentum velit. Nullam rutrum ante quis est ultrices, et tempus ex pellentesque. Mauris mollis libero in diam accumsan, vitae sagittis tortor accumsan. In lacus erat, pellentesque sit amet ante a, ullamcorper vulputate felis. Nunc iaculis finibus arcu, eget sollicitudin elit dapibus vel.\\n\\n\",\n      \"claimsAdministratorAddress1\": \"342 Shoe Circle\",\n      \"claimsAdministratorCity\": \"Fresno\",\n      \"claimsAdministratorState\": \"California\",\n      \"claimsAdministratorZipCode\": \"93721\",\n      \"claimsAdministratorCompanyName\": \"Zesty Zebra\",\n      \"providerPrefix\": \"Mr.\",\n      \"providerFirtName\": \"Peter\",\n      \"providerLastName\": \"Puffer\",\n      \"providerMidleInitial\": \"P\",\n      \"providerSuffix\": \"II\",\n      \"organizationName\": \"Provider Org\",\n      \"status\": \"3\",\n      \"dateofURDecision\": \"2012-11-27T09:00:00+0500\",\n      \"receiveDate\": \"2016-06-21T09:00:00+0500\",\n      \"modificationDate\": \"2016-08-15T13:03:22.865Z\"\n    },\n    {\n      \"claimNumber\": \"10000002\",\n      \"caseNumber\": \"CM16-10000002\",\n      \"dateOfInjury\": \"2013-01-01T09:00:00+0500\",\n      \"priority\": \"Normal\",\n      \"injuredWorkerPrefix\": \"Mr.\",\n      \"injuredWorkerFirstName\": \"Waldo\",\n      \"injuredWorkerLastName\": \"Wrangler\",\n      \"injuredWorkerMiddleInitial\": \"M\",\n      \"injuredWorkrSuffix\": \"III\",\n      \"employerName\": \"Magic Suppliers\",\n      \"treatmentRequested\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et tellus sapien. Nam sit amet ullamcorper lectus. Curabitur eu dolor dictum lorem eleifend laoreet. Pellentesque odio eros, maximus vitae purus sit amet, elementum facilisis nunc. Integer velit metus, fringilla id elit lobortis, ullamcorper sollicitudin nibh. Duis ac pulvinar libero, ac hendrerit turpis. Duis sollicitudin nunc quis mi ultricies pharetra.\\n\\nNullam bibendum vel nisi sed pulvinar. Nam semper venenatis ultricies. Pellentesque quis lectus nisi. Nam eu nibh condimentum, fermentum nibh vitae, lacinia magna. Etiam a orci tincidunt, mollis nunc ut, ullamcorper ex. Aenean faucibus porttitor sapien quis lobortis. Donec rutrum molestie justo, sed pharetra lorem pulvinar sit amet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed pulvinar iaculis sodales. Etiam dolor nibh, bibendum eget risus sed, interdum volutpat nisl. Curabitur suscipit, est vel ultricies fringilla, libero orci pretium augue, vel tincidunt tortor lectus sit amet mauris. Praesent a luctus diam.\\n\\nMaecenas luctus erat ut erat tincidunt fringilla. Vestibulum vel venenatis dui, quis lobortis augue. Quisque rhoncus vel tellus sollicitudin tristique. Nunc sit amet lectus sed augue aliquet congue sit amet quis turpis. Etiam id efficitur lacus. Mauris eleifend purus id suscipit congue. Donec vitae quam ac nunc aliquet euismod. Curabitur aliquet mi sit amet mollis semper.\\n\\nCras facilisis vestibulum nunc sed eleifend. Etiam viverra, enim quis elementum egestas, nisl nibh ullamcorper ipsum, hendrerit lacinia nisi neque eget magna. Suspendisse scelerisque auctor aliquam. Duis a maximus metus. Vivamus vel fermentum velit. Nullam rutrum ante quis est ultrices, et tempus ex pellentesque. Mauris mollis libero in diam accumsan, vitae sagittis tortor accumsan. In lacus erat, pellentesque sit amet ante a, ullamcorper vulputate felis. Nunc iaculis finibus arcu, eget sollicitudin elit dapibus vel.\\n\\n\",\n      \"claimsAdministratorAddress1\": \"2309 Sunset St\",\n      \"claimsAdministratorCity\": \"Fresno\",\n      \"claimsAdministratorState\": \"California\",\n      \"claimsAdministratorZipCode\": \"93721\",\n      \"claimsAdministratorCompanyName\": \"Zesty Zebra\",\n      \"providerPrefix\": \"Mrs.\",\n      \"providerFirtName\": \"Mary\",\n      \"providerLastName\": \"Melish\",\n      \"providerMidleInitial\": \"M\",\n      \"providerSuffix\": \"Esq\",\n      \"organizationName\": \"Provider Org\",\n      \"status\": \"2\",\n      \"dateofURDecision\": \"2012-11-27T09:00:00+0500\",\n      \"receiveDate\": \"2016-06-21T09:00:00+0500\",\n      \"modificationDate\": \"2016-08-15T13:03:19.525Z\"\n    }\n  ]\n}",
          "type": "json"
        },
        {
          "title": "CSV Response",
          "content": "HTTP/1.1 200 OK\nServer: Apache-Coyote/1.1\nX-Content-Type-Options: nosniff\nX-XSS-Protection: 1; mode=block\nCache-Control: no-cache, no-store, max-age=0, must-revalidate\nPragma: no-cache\nExpires: 0\nX-Frame-Options: DENY\nDate: Wed, 17 Aug 2016 19:18:41 GMT\nContent-Type: text/plain\nTransfer-Encoding: chunked\n\n10000001,CM16-10000001,2013-01-01T09:00:00+0500,Expedited,Mrs.,Jane,Smith,M,III,Broadway Busters,\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et tellus sapien. Nam sit amet ullamcorper lectus. Curabitur eu dolor dictum lorem eleifend laoreet. Pellentesque odio eros, maximus vitae purus sit amet, elementum facilisis nunc. Integer velit metus, fringilla id elit lobortis, ullamcorper sollicitudin nibh. Duis ac pulvinar libero, ac hendrerit turpis. Duis sollicitudin nunc quis mi ultricies pharetra.\n\nNullam bibendum vel nisi sed pulvinar. Nam semper venenatis ultricies. Pellentesque quis lectus nisi. Nam eu nibh condimentum, fermentum nibh vitae, lacinia magna. Etiam a orci tincidunt, mollis nunc ut, ullamcorper ex. Aenean faucibus porttitor sapien quis lobortis. Donec rutrum molestie justo, sed pharetra lorem pulvinar sit amet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed pulvinar iaculis sodales. Etiam dolor nibh, bibendum eget risus sed, interdum volutpat nisl. Curabitur suscipit, est vel ultricies fringilla, libero orci pretium augue, vel tincidunt tortor lectus sit amet mauris. Praesent a luctus diam.\n\nMaecenas luctus erat ut erat tincidunt fringilla. Vestibulum vel venenatis dui, quis lobortis augue. Quisque rhoncus vel tellus sollicitudin tristique. Nunc sit amet lectus sed augue aliquet congue sit amet quis turpis. Etiam id efficitur lacus. Mauris eleifend purus id suscipit congue. Donec vitae quam ac nunc aliquet euismod. Curabitur aliquet mi sit amet mollis semper.\n\nCras facilisis vestibulum nunc sed eleifend. Etiam viverra, enim quis elementum egestas, nisl nibh ullamcorper ipsum, hendrerit lacinia nisi neque eget magna. Suspendisse scelerisque auctor aliquam. Duis a maximus metus. Vivamus vel fermentum velit. Nullam rutrum ante quis est ultrices, et tempus ex pellentesque. Mauris mollis libero in diam accumsan, vitae sagittis tortor accumsan. In lacus erat, pellentesque sit amet ante a, ullamcorper vulputate felis. Nunc iaculis finibus arcu, eget sollicitudin elit dapibus vel.\n\n\",2309 Tulare St,,Fresno,California,93721,Zesty Zebra,Mrs.,Anna,Jones,M,Provider Org,1,,,,,2012-11-27T09:00:00+0500,2016-06-21T09:00:00+0500\n10000003,CM16-10000003,2013-01-01T09:00:00+0500,Normal,Ms.,Terri,Talker,T,,Talking Tall,\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et tellus sapien. Nam sit amet ullamcorper lectus. Curabitur eu dolor dictum lorem eleifend laoreet. Pellentesque odio eros, maximus vitae purus sit amet, elementum facilisis nunc. Integer velit metus, fringilla id elit lobortis, ullamcorper sollicitudin nibh. Duis ac pulvinar libero, ac hendrerit turpis. Duis sollicitudin nunc quis mi ultricies pharetra.\n\nNullam bibendum vel nisi sed pulvinar. Nam semper venenatis ultricies. Pellentesque quis lectus nisi. Nam eu nibh condimentum, fermentum nibh vitae, lacinia magna. Etiam a orci tincidunt, mollis nunc ut, ullamcorper ex. Aenean faucibus porttitor sapien quis lobortis. Donec rutrum molestie justo, sed pharetra lorem pulvinar sit amet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed pulvinar iaculis sodales. Etiam dolor nibh, bibendum eget risus sed, interdum volutpat nisl. Curabitur suscipit, est vel ultricies fringilla, libero orci pretium augue, vel tincidunt tortor lectus sit amet mauris. Praesent a luctus diam.\n\nMaecenas luctus erat ut erat tincidunt fringilla. Vestibulum vel venenatis dui, quis lobortis augue. Quisque rhoncus vel tellus sollicitudin tristique. Nunc sit amet lectus sed augue aliquet congue sit amet quis turpis. Etiam id efficitur lacus. Mauris eleifend purus id suscipit congue. Donec vitae quam ac nunc aliquet euismod. Curabitur aliquet mi sit amet mollis semper.\n\nCras facilisis vestibulum nunc sed eleifend. Etiam viverra, enim quis elementum egestas, nisl nibh ullamcorper ipsum, hendrerit lacinia nisi neque eget magna. Suspendisse scelerisque auctor aliquam. Duis a maximus metus. Vivamus vel fermentum velit. Nullam rutrum ante quis est ultrices, et tempus ex pellentesque. Mauris mollis libero in diam accumsan, vitae sagittis tortor accumsan. In lacus erat, pellentesque sit amet ante a, ullamcorper vulputate felis. Nunc iaculis finibus arcu, eget sollicitudin elit dapibus vel.\n\n\",342 Shoe Circle,,Fresno,California,93721,Zesty Zebra,Mr.,Peter,Puffer,P,Provider Org,3,,,,,2012-11-27T09:00:00+0500,2016-06-21T09:00:00+0500",
          "type": "csv"
        }
      ],
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "results",
            "description": "<p>Array of matching cases</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.claimNumber",
            "description": "<p>&quot;10000001&quot;</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.caseNumber",
            "description": "<p>&quot;CM16-10000001&quot;</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.dateOfInjury",
            "description": "<p>Date/Time (ISO 8601) expressed in UTC</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.priority",
            "description": "<p>&quot;Normal&quot; or &quot;Expedited&quot;</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.injuredWorkerPrefix",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.injuredWorkerFirstName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.injuredWorkerLastName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.injuredWorkerMiddleInitial",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.injuredWorkrSuffix",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.employerName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.treatmentRequested",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.claimsAdministratorAddress1",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.claimsAdministratorCity",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.claimsAdministratorState",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.claimsAdministratorZipCode",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.claimsAdministratorCompanyName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.providerPrefix",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.providerFirtName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.providerLastName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.providerMidleInitial",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.providerSuffix",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.organizationName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.status",
            "description": "<div>Numeric strings sent with following meanings</div> <div> <ul style=\"list-style: none\"> <li>1 - Received</li> <li>2 - Eligibility Review</li> <li>3 - Records Requested</li> <li>4 - Clinical Review</li> <li>5 - Closed</li> <li>6 - NOARFI</li> <li>7 - Insufficient Records</li> </ul> </div>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "matchCount",
            "description": "<p>Total number of cases matching search request <span style=\"font-style: italic; font-weight: 700;\">Not sent with CSV responses</span></p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "start",
            "description": "<p>The index of the search results you wish returned. If not supplied or invalid it will be replaced with 0.  <span style=\"font-style: italic; font-weight: 700;\">Not sent with CSV responses</span></p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "limit",
            "description": "<p>The number of results to return. If not supplied the specific API will select the maximum results to return. If the value is considered too large by the specific API it may alter this value in the response and ignore the supplied value.  <span style=\"font-style: italic; font-weight: 700;\">Not sent with CSV responses</span></p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.dateofURDecision",
            "description": "<p>Date/Time (ISO 8601) expressed in UTC</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.receiveDate",
            "description": "<p>Date/Time (ISO 8601) expressed in UTC</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.modificationDate",
            "description": "<p>Date/Time (ISO 8601) expressed in UTC</p>"
          }
        ]
      }
    },
    "filename": "src/docs.java",
    "groupTitle": "Cases",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "415",
            "description": "<p>Unsupported Media Type</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "500",
            "description": "<p>Server Error</p>"
          },
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "503",
            "description": "<p>Service Unavailable</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "JSON Error Response",
          "content": "{\"status\": http_status_code, \"msg\": \"some error description\", \"refId\": \"reference-identifier\"}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "post",
    "url": "apigw/webservices/rest/apigw/cases/searchrfi",
    "title": "01.Search NOARFI",
    "name": "SearchCasesNOARFI",
    "group": "Cases",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Bearer <code>JWT Access Token</code></p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accepts",
            "description": "<p>application/json (for JSON response) or text/plain (for CSV response)</p>"
          }
        ]
      }
    },
    "version": "0.9.0",
    "filename": "src/docs.java",
    "groupTitle": "Cases",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "results",
            "description": "<p>Array of matching cases</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.claimNumber",
            "description": "<p>&quot;10000001&quot;</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.caseNumber",
            "description": "<p>&quot;CM16-10000001&quot;</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.dateOfInjury",
            "description": "<p>Date/Time (ISO 8601) expressed in UTC</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.priority",
            "description": "<p>&quot;Normal&quot; or &quot;Expedited&quot;</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.injuredWorkerPrefix",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.injuredWorkerFirstName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.injuredWorkerLastName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.injuredWorkerMiddleInitial",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.injuredWorkrSuffix",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.employerName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.treatmentRequested",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.claimsAdministratorAddress1",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.claimsAdministratorCity",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.claimsAdministratorState",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.claimsAdministratorZipCode",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.claimsAdministratorCompanyName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.providerPrefix",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.providerFirtName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.providerLastName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.providerMidleInitial",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.providerSuffix",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.organizationName",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.status",
            "description": "<div>Numeric strings sent with following meanings</div> <div> <ul style=\"list-style: none\"> <li>1 - Received</li> <li>2 - Eligibility Review</li> <li>3 - Records Requested</li> <li>4 - Clinical Review</li> <li>5 - Closed</li> <li>6 - NOARFI</li> <li>7 - Insufficient Records</li> </ul> </div>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "matchCount",
            "description": "<p>Total number of cases matching search request <span style=\"font-style: italic; font-weight: 700;\">Not sent with CSV responses</span></p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "start",
            "description": "<p>The index of the search results you wish returned. If not supplied or invalid it will be replaced with 0.  <span style=\"font-style: italic; font-weight: 700;\">Not sent with CSV responses</span></p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "limit",
            "description": "<p>The number of results to return. If not supplied the specific API will select the maximum results to return. If the value is considered too large by the specific API it may alter this value in the response and ignore the supplied value.  <span style=\"font-style: italic; font-weight: 700;\">Not sent with CSV responses</span></p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.dateofURDecision",
            "description": "<p>Date/Time (ISO 8601) expressed in UTC</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.receiveDate",
            "description": "<p>Date/Time (ISO 8601) expressed in UTC</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.modificationDate",
            "description": "<p>Date/Time (ISO 8601) expressed in UTC</p>"
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "415",
            "description": "<p>Unsupported Media Type</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "500",
            "description": "<p>Server Error</p>"
          },
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "503",
            "description": "<p>Service Unavailable</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "JSON Error Response",
          "content": "{\"status\": http_status_code, \"msg\": \"some error description\", \"refId\": \"reference-identifier\"}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "get",
    "url": "apigw/webservices/rest/apigw/docs/download/:handle",
    "title": "03.Download",
    "name": "DownloadDoc",
    "description": "<p>This service allows downloading files for cases you are authorized to view.  In production, these files will likely contain PHI and the recieving application is cautioned to take appropriate security measures. Please note that the <code>handle</code> parameter will likely contain a forward slash character '/' (e.g. a <code>handle</code> may look like: <code>20.5000.214/6baef007ce0dd9b9d61d</code>).  Since <code>handle</code>s are sent as part of the request path, it might be tempting to URL encode them prior to building the path.  Please <span style=\"font-weight:700\">DON'T</span> do this.  See the GET at the begining of the example to see how the service should be called.</p>",
    "group": "Documents",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Bearer <code>JWT Access Token</code></p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accepts",
            "description": "<p>application/octet-stream</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "handle",
            "description": "<p>the handle of the particular document <em>rendering</em> requested<br /><span style=\"font-style: italic\">(sent in path - <span style=\"font-weight: 700\">do not encode</span>)</span></p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Request",
        "content": "GET /apigw/webservices/rest/apigw/docs/download/20.5000.214/6baef007ce0dd9b9d61d HTTP/1.1\nUser-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)\nHost: imr-ca-sandbox.maxird.com\nAuthorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJkZjIxZTg4MS1kMzhhLTQ5OWUtYjU1Zi03MTIyMGY2MWExNTAiLCJleHAiOjE0NzE1ODgzMTAsIm5iZiI6MCwiaWF0IjoxNDcxNTg0NzEwLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6ImM0ZThmODFiLTdhY2QtNGU5OC1iNjc3LTUzYmYxYjIzMzc1OSIsImNsaWVudF9zZXNzaW9uIjoiNDA3MzQxMzMtOGQ0Mi00ZDI3LTg2MmYtMTdmOTI4MjQ2ZTliIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.GY7utwi53CcjEDVPGoJJZAqSa-MiewayZl06gpb8qp96oZ1mW4Rz2NBEIR8V4z1zeOtWZYbdYXfkhYk4nWNc2Y8FXi-YNYHxm_SSNGCl1nzXljzthfCX-_oQ6CoKX8JeZa-J57Y4DXHdSwp14mkGVvWn4hnnHkjilAAqDh_5YbNwdijiw7w9DEyMyCR3OEFLXYgvICYXRYoQjjdSVQw2PKKKVa1xmkjOtnqjodKmijCFB4gJtmh1QUssleZK8X64acAMDU-tn5w2IlVrt-2vWKu3Kz2FNApbAOxa6rgJpl_dgRIhdO0NAfMkmztINjSMG6Cdxxh8X3FhINthyRB_FA",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Response",
          "content": "HTTP/1.1 200 OK\nServer: Apache-Coyote/1.1\nX-Content-Type-Options: nosniff\nX-XSS-Protection: 1; mode=block\nCache-Control: no-cache, no-store, max-age=0, must-revalidate\nPragma: no-cache\nExpires: 0\nX-Frame-Options: DENY\nDate: Fri, 19 Aug 2016 05:36:22 GMT\nContent-Type: application/octet-stream\nTransfer-Encoding: chunked",
          "type": "json"
        }
      ]
    },
    "version": "0.9.0",
    "filename": "src/docs.java",
    "groupTitle": "Documents",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "415",
            "description": "<p>Unsupported Media Type</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "500",
            "description": "<p>Server Error</p>"
          },
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "503",
            "description": "<p>Service Unavailable</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "JSON Error Response",
          "content": "{\"status\": http_status_code, \"msg\": \"some error description\", \"refId\": \"reference-identifier\"}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "get",
    "url": "apigw/webservices/rest/apigw/docs/search/cn/:imrCaseNumber",
    "title": "00.Search",
    "name": "SearchDocs",
    "description": "<p>This request returns a list of documents (and their renderings) for a specified case.  Each logical document will have one or more document renderings.  These renderings represent the actual files that have been uploaded.  The first content rendering listed for a document is the original artifact that was provided.  Occassionally additional renderings may be available for a document item.  These represent alternate presenations of the same logical information (all document renderings for a document item are semantically idential).  <span style=\"font-style: italic;\">When attempting a document download you will need to use the </span><code>identifier</code><span style=\"font-style: italic;\"> of the specific </span><span style=\"font-style: italic; font-weight: 700\">rendering</span><span style=\"font-style: italic;\"> you are looking to retrieve.</span></p>",
    "group": "Documents",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Bearer <code>JWT Access Token</code></p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accepts",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "imrCaseNumber",
            "description": "<p>IMR Case Number <span style=\"font-style: italic\">(sent in path)</span></p>"
          }
        ]
      }
    },
    "version": "0.9.0",
    "examples": [
      {
        "title": "Document Search",
        "content": "GET /apigw/webservices/rest/apigw/docs/search/cn/CM16-10000005 HTTP/1.1\nUser-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)\nHost: imr-ca-sandbox.maxird.com\nAuthorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJmMmQxN2ZlZC0yZGI4LTQ2NjItYjQzNy0zZjgzYzIzM2NlN2IiLCJleHAiOjE0NzE1ODUwODIsIm5iZiI6MCwiaWF0IjoxNDcxNTgxNDgyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6ImM0ZThmODFiLTdhY2QtNGU5OC1iNjc3LTUzYmYxYjIzMzc1OSIsImNsaWVudF9zZXNzaW9uIjoiNDA3MzQxMzMtOGQ0Mi00ZDI3LTg2MmYtMTdmOTI4MjQ2ZTliIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.OD-S5raMHug0x8pMO7zHcHwzZuosSWx4cH1ktZ557itofxNUWinQgfvngdSg5cvNYpoEChqwQSz-Vd8GYjkjKrE5E-AK94kTLPE3MfzQ5X4KzRMovChshGVJK-sQCrnZONS77G-_UVXL5fITeWKUZnrarvM1X3kr-O0yMIRaB-DOs_hFK972-hB_zo-QMej0XIrTth-yjl5NwtRXXpKrAjqY-em7EWDL--k1Gd-Q4mj9wCTCxsKnIiElexVTaboz0WSBFxKd43EeCTdRC5bLwBVQvS3USCYBnioRnY79sd3GxELM7Zg8F6zgprlQl1P_lQ_5J8y4jO42qxhei92IlQ",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "results",
            "description": "<p>array of documents for case</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.identifier",
            "description": "<p>handle for this document item</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.type",
            "description": "<p>logical type of this document</p>"
          },
          {
            "group": "Success 200",
            "type": "String[]",
            "optional": false,
            "field": "results.types",
            "description": "<p>additional types for this document</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.case",
            "description": "<p>handle to the case</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.dcn",
            "description": "<p>document control number</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.name",
            "description": "<p>document name</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "results.renderings",
            "description": "<p>each document will have one or more renderings</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.renderings.identifier",
            "description": "<p>handle for this document rendering (<span style=\"font-style: italic; font-weight:700\">use this for downloads</span>)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.renderings.type",
            "description": "<p>MIME type of this rendering</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "results.renderings.size",
            "description": "<p>size in bytes of this rendering</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "matchCount",
            "description": "<p>total number of documents matching search request</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "start",
            "description": "<p>The page index of the search results you wish returned. If not supplied or invalid it will be replaced with 0.</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "limit",
            "description": "<p>The number of results to return. If not supplied the specific API will select the maximum results to return. If the value is considered too large by the specific API it may alter this value in the response and ignore the supplied value.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Response",
          "content": "HTTP/1.1 200 OK\nServer: Apache-Coyote/1.1\nX-Content-Type-Options: nosniff\nX-XSS-Protection: 1; mode=block\nCache-Control: no-cache, no-store, max-age=0, must-revalidate\nPragma: no-cache\nExpires: 0\nX-Frame-Options: DENY\nDate: Fri, 19 Aug 2016 04:39:30 GMT\nContent-Type: application/json\nTransfer-Encoding: chunked\n\n{\n  \"matchCount\": 3,\n  \"start\": 0,\n  \"limit\": 999,\n  \"results\": [\n    {\n      \"identifier\": \"20.5000.214/def25dc90f1682989e6a\",\n      \"type\": \"MedicalRecords\",\n      \"types\": [\n        \"MedicalRecords\"\n      ],\n      \"case\": \"20.5000.214/87aa5e9deadbc32ea1e2\",\n      \"dcn\": \"900003373\",\n      \"name\": \"Medical Records\",\n      \"renderings\": [\n        {\n          \"identifier\": \"20.5000.214/5fdb80cbb2b3a1619d93\",\n          \"type\": \"application/pdf\",\n          \"size\": 7234\n        }\n      ]\n    },\n    {\n      \"identifier\": \"20.5000.214/b1ae4cf28ed2a760a633\",\n      \"type\": \"NOARFI\",\n      \"types\": [\n        \"NOARFI\"\n      ],\n      \"case\": \"20.5000.214/87aa5e9deadbc32ea1e2\",\n      \"dcn\": \"900003372\",\n      \"name\": \"NOARFI\",\n      \"renderings\": [\n        {\n          \"identifier\": \"20.5000.214/645559a095fe20ce8c53\",\n          \"type\": \"application/pdf\",\n          \"size\": 6837\n        }\n      ]\n    },\n    {\n      \"identifier\": \"20.5000.214/65e63994e053ca5b6668\",\n      \"type\": \"code.other\",\n      \"types\": [\n        \"code.other\"\n      ],\n      \"case\": \"20.5000.214/87aa5e9deadbc32ea1e2\",\n      \"dcn\": \"900003374\",\n      \"name\": \"Additional Information\",\n      \"renderings\": [\n        {\n          \"identifier\": \"20.5000.214/6baef007ce0dd9b9d61d\",\n          \"type\": \"application/pdf\",\n          \"size\": 18320\n        }\n      ]\n    }\n  ]\n}",
          "type": "json"
        }
      ]
    },
    "filename": "src/docs.java",
    "groupTitle": "Documents",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "415",
            "description": "<p>Unsupported Media Type</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "500",
            "description": "<p>Server Error</p>"
          },
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "503",
            "description": "<p>Service Unavailable</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "JSON Error Response",
          "content": "{\"status\": http_status_code, \"msg\": \"some error description\", \"refId\": \"reference-identifier\"}",
          "type": "json"
        },
        {
          "title": "JSON Error Response",
          "content": "{\"status\": http_status_code, \"msg\": \"some error description\", \"refId\": \"reference-identifier\"}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "post",
    "url": "apigw/webservices/rest/apigw/docs/upload/cn/:imrCaseNumber",
    "title": "01.Upload (by IMR Case Number)",
    "name": "UploadDoc",
    "description": "<p>This request allows users to upload a document for a case.  Once the upload had been fully processed by the DXC storage services, a new document item with one rendering (the file uploaded with this request) will appear in the document search for this case.  In addition to the <code>imrCaseNumber</code>, this request should include the <code>filename</code> (in the Content-Disposition of the first part and the MIME type (in the Content-Type) in the first part). See the Request example.</p>",
    "group": "Documents",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Bearer <code>JWT Access Token</code></p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>multipart/form-data</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accepts",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "imrCaseNumber",
            "description": "<p>IMR Case Number <span style=\"font-style: italic;\">(sent in path)</span></p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Content-Disposition",
            "description": "<p>(in header of part1) includes filename (see Request example)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>(in header of part1) MIME type of the file. <span style=\"font-style: italic;\"></span> Supported types include:</p> <ul style=\"list-style: none\"> <li>application/pdf</li> <li>image/jpeg</li> <li>application/vnd.openxmlformats-officedocument.wordprocessingml.document</li> <li>application/msword</li> </ul>"
          }
        ]
      }
    },
    "version": "0.9.0",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "status",
            "description": "<p>200</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>OK</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Response",
          "content": "HTTP/1.1 200 OK\nServer: Apache-Coyote/1.1\nX-Content-Type-Options: nosniff\nX-XSS-Protection: 1; mode=block\nCache-Control: no-cache, no-store, max-age=0, must-revalidate\nPragma: no-cache\nExpires: 0\nX-Frame-Options: DENY\nDate: Sun, 21 Aug 2016 04:22:26 GMT\nContent-Type: application/json\nTransfer-Encoding: chunked\n\n{\"status\":200,\"msg\":\"OK\"}",
          "type": "json"
        }
      ]
    },
    "examples": [
      {
        "title": "Upload Request",
        "content": "POST /apigw/webservices/rest/apigw/docs/upload/cn/CM16-10000005 HTTP/1.1\nUser-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)\nHost: imr-ca-sandbox.maxird.com\nAuthorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI5MzFmNGQwNy0wNzhlLTRlNjUtOWFhMC1lOTI1MzE3NDY5YjgiLCJleHAiOjE0NzE3NTQ2MzksIm5iZiI6MCwiaWF0IjoxNDcxNzUxMDM5LCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZHhjIiwic2Vzc2lvbl9zdGF0ZSI6ImFkNjU2NDU0LTFjZjgtNGVhNC05NzE3LWM5OGQ3MDczMzI5MyIsImNsaWVudF9zZXNzaW9uIjoiYTJiOTdhZDEtM2M3OC00NWUzLTg5YzItZDVjYTE5YzQyMWRhIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIkRlZmF1bHQgU2VydmljZSBSb2xlIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZHhjIjp7InJvbGVzIjpbImNhc2UuYWxsIiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFzc2lnbiIsImNhc2UuY29tbWVudCIsImNhc2UuYWRtaW4iLCJjYXNlLnN0YXRlIiwiY2FzZS5pZGVudGlmaWVycyIsImNhc2UudHJhbnNmZXIiLCJjYXNlLnZpZXciLCJjYXNlLmludml0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UuemVzdHkyQG1heGlyZC5jb20iLCJnaXZlbl9uYW1lIjoiWkVTVFlaRUJSQSIsImZhbWlseV9uYW1lIjoiU2VydmljZSIsImVtYWlsIjoic2VydmljZS56ZXN0eTJAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcHplc3R5In0.WmQd3b0bo7gntuU4pu4EYr8T4cVzz59z6FTOi3Kx61kpb-XSJsYp8nNFab3ZiKQ6MPmxPXyF7sUFWO4pQlAazoyZvov77_EHx9f4sPlfCWoJ6s0kCvOTT-bTlHV85td5qaWZ-cSB9MrTBLovBYsiezFwOnxZbVLgdzJp8Zw3LaDhKzYs4Xz-PQfhgTyx2Oh1owxqfTRAoYXtBMgB6SEtAPQRI_TOiHm5iZ79sBy_YY_S0w49u1kHCDKxLt9yNfhnytaWDSRpTkWogwsC_5Cdui2oadsxaZQzgY8GG8oewZyYpdLiNDHsQDbVBU3WL3upT0HBJutzCmBWrP-6JPAiSA\nContent-Length: 7435\nExpect: 100-continue\nContent-Type: multipart/form-data; boundary=----------------------------54835f8f6a22\n\nContent-Disposition: form-data; name=\"file\"; filename=\"medical_record.pdf\"\nContent-Type: application/pdf",
        "type": "json"
      }
    ],
    "filename": "src/docs.java",
    "groupTitle": "Documents",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "415",
            "description": "<p>Unsupported Media Type</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "500",
            "description": "<p>Server Error</p>"
          },
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "503",
            "description": "<p>Service Unavailable</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "JSON Error Response",
          "content": "{\"status\": http_status_code, \"msg\": \"some error description\", \"refId\": \"reference-identifier\"}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "post",
    "url": "apigw/webservices/rest/apigw/docs/upload",
    "title": "02.Upload (legacy)",
    "name": "UploadDocLegacy",
    "description": "<p>For legacy MOVEit users.  The IMR case id is inferred from the file name.   The <code>filename</code> and MIME type are specified in the first part Content-Disposition and Content-Type headers respectively.  Except for the imrCaseNumber pattern in the file name, this request is handled the same as a normal file upload.</p>",
    "group": "Documents",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Bearer <code>JWT Access Token</code></p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>multipart/form-data</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accepts",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Content-Disposition",
            "description": "<p>(in header of part1) includes filename (must be of a recognized format such as: <code>{IMRCaseNumber}_{NNN}.pdf</code>)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>(in header of part1) MIME type of the file. <span style=\"font-style: italic;\"></span> Supported types include:</p> <ul style=\"list-style: none\"> <li>application/pdf</li> <li>image/jpeg</li> <li>application/vnd.openxmlformats-officedocument.wordprocessingml.document</li> <li>application/msword</li> </ul>"
          }
        ]
      }
    },
    "version": "0.9.0",
    "filename": "src/docs.java",
    "groupTitle": "Documents",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found</p>"
          },
          {
            "group": "Error 4xx",
            "type": "json",
            "optional": false,
            "field": "415",
            "description": "<p>Unsupported Media Type</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "500",
            "description": "<p>Server Error</p>"
          },
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "503",
            "description": "<p>Service Unavailable</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "JSON Error Response",
          "content": "{\"status\": http_status_code, \"msg\": \"some error description\", \"refId\": \"reference-identifier\"}",
          "type": "json"
        }
      ]
    }
  }
] });
