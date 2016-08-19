define({ "api": [
  {
    "type": "post",
    "url": "auth/realms/dxc-externals/protocol/openid-connect/token",
    "title": "00.Login",
    "name": "Login",
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
    "version": "1.0.0",
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
    "filename": "api/docs.java",
    "groupTitle": "Authentication"
  },
  {
    "type": "post",
    "url": "auth/realms/:realm/protocol/openid-connect/token",
    "title": "01.Refresh",
    "name": "Refresh",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "realm",
            "description": "<p>Always use &quot;dxc-externals&quot;</p>"
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
            "description": "<p>Send your <code>JWT_REFRESH_TOKEN</code></p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "group": "Authentication",
    "examples": [
      {
        "title": "OAuth2 Request",
        "content": "POST /auth/realms/dxc-externals/protocol/openid-connect/token HTTP/1.1\nUser-Agent: curl/7.19.7 (x86_64-redhat-linux-gnu)\nHost: imr-ca-sandbox.maxird.com\nContent-Length: 1263\nContent-Type: application/x-www-form-urlencoded\nExpect: 100-continue\n\ngrant_type=refresh_token&client_id=dxc&refresh_token=JWT_REFRESH_TOKEN=eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI0NWJkNGIyNy0yMmI0LTQxMTctOGMwOS02YTg5YjA4YzYzYTQiLCJleHAiOjE0NzE1NDc3MjIsIm5iZiI6MCwiaWF0IjoxNDcxNDYxMzIyLCJpc3MiOiJodHRwOi8vaW1yLWNhLXNhbmRib3gubWF4aXJkLmNvbS9hdXRoL3JlYWxtcy9keGMtZXh0ZXJuYWxzIiwiYXVkIjoiZHhjIiwic3ViIjoiODYxNTI5OWQtMzY5MC00ZThhLWJjYmEtNzlmNTNmYjQ5ODA0IiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImR4YyIsInNlc3Npb25fc3RhdGUiOiI5M2ZkODZiYi04OTY4LTRlZGItYTE2Yi1jM2NmZjlkZGRjMWYiLCJjbGllbnRfc2Vzc2lvbiI6ImFiYjdjYjk0LTE0YmMtNDljMy1iYTJmLTE1Y2RjMjZlZjNkYiIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJEZWZhdWx0IFNlcnZpY2UgUm9sZSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImR4YyI6eyJyb2xlcyI6WyJjYXNlLmFsbCIsImNhc2UuYXBwZW5kIiwiY2FzZS5hc3NpZ24iLCJjYXNlLmNvbW1lbnQiLCJjYXNlLmFkbWluIiwiY2FzZS5zdGF0ZSIsImNhc2UuaWRlbnRpZmllcnMiLCJjYXNlLnRyYW5zZmVyIiwiY2FzZS52aWV3IiwiY2FzZS5pbnZpdGUiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJ2aWV3LXByb2ZpbGUiXX19fQ.AQlRqa3ZNd7tv1Lb5qfw9Cl1F1WYG6XJs5FPBy8rr7W_q4676Ias2Ew5u8k9exlmrJhqWvMdY85tzBrVhNq4So3ZGzG084v0t2F1n4LX9w1kyo1q6ka-6nhiATwJC0wVHy0q-RjCSMElr2j5z3oxKO4LyT37123sRNZ4pOs1vA2D-ddEOX980NdVsyqX_MBW9p4OPC9esY0jZYXRX1svmcidyB6wj26vgpg9V-xiec3qxJuYLz8kHbtaa4jtVlEB-eKr_ZhWzl4x04lY6VtQ_G5pxEq04R1JQWHgI0befkdwcA1ut005OdablGmp3g_Hbmh07cdz0_HaKFo-yWD63A",
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
    "filename": "api/docs.java",
    "groupTitle": "Authentication"
  },
  {
    "type": "post",
    "url": "cases/search",
    "title": "00.Search",
    "name": "SearchCases",
    "group": "Cases",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Bearer <code>JWT-ACCESS-TOKEN</code></p>"
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
            "description": "<p>application/json | text/plain</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
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
            "description": "<p>Date/Time (ISO 8601)</p>"
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
            "description": "<div>Numeric strings sent with following meanings</div> <div> <ul style=\"list-style: none\"> <li>1 - Received</li> <li>2 - Eligibility Review</li> <li>3 - Records Requested</li> <li>4 - Clinical Review</li> <li>5 - Closed</li> <li>6 - NOARFI</li> </ul> </div>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "matchCount",
            "description": "<p>Number of cases matching search request</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "start",
            "description": "<p>The index of the search results you wish returned. If not supplied or invalid it will be replaced with 0.</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "limit",
            "description": "<p>The number of results to return. If not supplied the specific API will select the maximum results to return. If the value is considered too large by the specific API it may alter this value in the response and ignore the supplied value.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.dateofURDecision",
            "description": "<p>Date/Time (ISO 8601)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.receiveDate",
            "description": "<p>Date/Time (ISO 8601)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.modificationDate",
            "description": "<p>Date/Time (ISO 8601)</p>"
          }
        ]
      }
    },
    "filename": "api/docs.java",
    "groupTitle": "Cases",
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
            "description": "<p>Name of property to sort on</p>"
          },
          {
            "group": "Parameter",
            "type": "Boolean",
            "optional": false,
            "field": "sort.desc",
            "description": "<p>Sort descending</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "IMRID",
            "description": ""
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
    "error": {
      "fields": {
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "Server",
            "description": "<p>Error</p>"
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "cases/searchrfi",
    "title": "01.Search RFI",
    "name": "SearchCasesRFI",
    "group": "Cases",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Bearer <code>JWT-ACCESS-TOKEN</code></p>"
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
            "description": "<p>application/json | text/plain</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/docs.java",
    "groupTitle": "Cases",
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
            "description": "<p>Name of property to sort on</p>"
          },
          {
            "group": "Parameter",
            "type": "Boolean",
            "optional": false,
            "field": "sort.desc",
            "description": "<p>Sort descending</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "IMRID",
            "description": ""
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
            "description": "<p>Date/Time (ISO 8601)</p>"
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
            "description": "<div>Numeric strings sent with following meanings</div> <div> <ul style=\"list-style: none\"> <li>1 - Received</li> <li>2 - Eligibility Review</li> <li>3 - Records Requested</li> <li>4 - Clinical Review</li> <li>5 - Closed</li> <li>6 - NOARFI</li> </ul> </div>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "matchCount",
            "description": "<p>Number of cases matching search request</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "start",
            "description": "<p>The index of the search results you wish returned. If not supplied or invalid it will be replaced with 0.</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "limit",
            "description": "<p>The number of results to return. If not supplied the specific API will select the maximum results to return. If the value is considered too large by the specific API it may alter this value in the response and ignore the supplied value.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.dateofURDecision",
            "description": "<p>Date/Time (ISO 8601)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.receiveDate",
            "description": "<p>Date/Time (ISO 8601)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "results.modificationDate",
            "description": "<p>Date/Time (ISO 8601)</p>"
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "Server",
            "description": "<p>Error</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "docs/download/:handle",
    "title": "03.Download",
    "name": "DownloadDoc",
    "group": "Documents",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Bearer <code>JWT-ACCESS-TOKEN</code></p>"
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
            "description": "<p>the handle of the particular document <em>rendering</em> requested</p>"
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
    "version": "1.0.0",
    "filename": "api/docs.java",
    "groupTitle": "Documents"
  },
  {
    "type": "get",
    "url": "docs/search/cn/:imrCaseNumber",
    "title": "00.Search",
    "name": "SearchDocs",
    "group": "Documents",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Bearer <code>JWT-ACCESS-TOKEN</code></p>"
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
            "description": "<p>IMR Case Number</p>"
          }
        ]
      }
    },
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
            "description": "<p>the handle for this document</p>"
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
            "description": ""
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
            "description": "<p>handle for this rendering</p>"
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
            "field": "callIndex",
            "description": "<p>Numeric value that is carried from the request to the response so callers can handle out-of-order delivery or ignoring results when multiple concurrent requests have been issued.</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "matchCount",
            "description": "<p>Number of documents matching search request</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "start",
            "description": "<p>The index of the search results you wish returned. If not supplied or invalid it will be replaced with 0.</p>"
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
          "content": "HTTP/1.1 200 OK\nServer: Apache-Coyote/1.1\nX-Content-Type-Options: nosniff\nX-XSS-Protection: 1; mode=block\nCache-Control: no-cache, no-store, max-age=0, must-revalidate\nPragma: no-cache\nExpires: 0\nX-Frame-Options: DENY\nDate: Fri, 19 Aug 2016 04:39:30 GMT\nContent-Type: application/json\nTransfer-Encoding: chunked\n\n{\n  \"callIndex\": 0,\n  \"matchCount\": 3,\n  \"start\": 0,\n  \"limit\": 999,\n  \"results\": [\n    {\n      \"identifier\": \"20.5000.214/def25dc90f1682989e6a\",\n      \"type\": \"MedicalRecords\",\n      \"types\": [\n        \"MedicalRecords\"\n      ],\n      \"case\": \"20.5000.214/87aa5e9deadbc32ea1e2\",\n      \"dcn\": \"900003373\",\n      \"name\": \"Medical Records\",\n      \"renderings\": [\n        {\n          \"identifier\": \"20.5000.214/5fdb80cbb2b3a1619d93\",\n          \"type\": \"application/pdf\",\n          \"size\": 7234\n        }\n      ]\n    },\n    {\n      \"identifier\": \"20.5000.214/b1ae4cf28ed2a760a633\",\n      \"type\": \"NOARFI\",\n      \"types\": [\n        \"NOARFI\"\n      ],\n      \"case\": \"20.5000.214/87aa5e9deadbc32ea1e2\",\n      \"dcn\": \"900003372\",\n      \"name\": \"NOARFI\",\n      \"renderings\": [\n        {\n          \"identifier\": \"20.5000.214/645559a095fe20ce8c53\",\n          \"type\": \"application/pdf\",\n          \"size\": 6837\n        }\n      ]\n    },\n    {\n      \"identifier\": \"20.5000.214/65e63994e053ca5b6668\",\n      \"type\": \"code.other\",\n      \"types\": [\n        \"code.other\"\n      ],\n      \"case\": \"20.5000.214/87aa5e9deadbc32ea1e2\",\n      \"dcn\": \"900003374\",\n      \"name\": \"Additional Information\",\n      \"renderings\": [\n        {\n          \"identifier\": \"20.5000.214/6baef007ce0dd9b9d61d\",\n          \"type\": \"application/pdf\",\n          \"size\": 18320\n        }\n      ]\n    }\n  ]\n}",
          "type": "json"
        }
      ]
    },
    "version": "1.0.0",
    "filename": "api/docs.java",
    "groupTitle": "Documents"
  },
  {
    "type": "post",
    "url": "docs/upload/cn/:imrCaseNumber",
    "title": "01.Upload (by IMR Case Number)",
    "name": "UploadDoc",
    "group": "Documents",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Bearer <code>JWT-ACCESS-TOKEN</code></p>"
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
            "description": "<p>IMR Case Number</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/docs.java",
    "groupTitle": "Documents",
    "error": {
      "fields": {
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "json",
            "optional": false,
            "field": "Server",
            "description": "<p>Error</p>"
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "docs/upload/cn",
    "title": "02.Upload (legacy)",
    "name": "UploadDocLegacy",
    "description": "<p>For legacy MOVEit users.  The IMR case id is inferred from the file name.</p>",
    "group": "Documents",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Bearer <code>JWT-ACCESS-TOKEN</code></p>"
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
            "field": "file",
            "description": "<p>The file name must be of a recognized format such as: <code>{IMRCaseNumber}_{NNN}.pdf</code></p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/docs.java",
    "groupTitle": "Documents"
  }
] });