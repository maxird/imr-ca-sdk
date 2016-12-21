package com.maximus.imr.rest

import java.util.Date
import javax.ws.rs.core.MultivaluedMap

import com.google.gson.{Gson, GsonBuilder, TypeAdapter}
import com.maximus.imr.rest.util.HttpUtil._
import com.maximus.imr.rest.util.TokenResponse
import com.maximus.util.Properties._
import com.maximus.util.{AsciiEncryption, Properties}
import com.sun.jersey.api.client.{Client, ClientResponse, WebResource}
import com.sun.jersey.core.util.MultivaluedMapImpl



/**
 * Login is a scala singleton that encapsulates all the nuances of getting connected to keycloak
 * Login expects populated configuration files in the either ./config or wherever the com.maximus.ird.configroot points.
 * Refer to sample-config.properties and sample.test.properties for creating these files.
 */
object Login {


  private val gson: Gson = new GsonBuilder().create
  private val FILENAME   =  home+"/.tokenCache"
  private var tokenMetaData: TokenResponse =  {
    val adapter: TypeAdapter[TokenResponse] = gson.getAdapter(classOf[TokenResponse])
    val jsonString = _readCache
    if (jsonString != null)
      adapter.fromJson(_readCache)
    else
      null
  }
  //todo synchronization concerns
  def getToken: String = {
    if (tokenMetaData == null) {
      val getTokenFormData: MultivaluedMap[String, String] = new MultivaluedMapImpl
      getTokenFormData.add("username", apiUserName)
      getTokenFormData.add("password", apiUserPassword)
      getTokenFormData.add("grant_type", "password")
      getTokenFormData.add("client_id", "dxc")

      val client: Client = Client.create

      val webResource: WebResource = client.resource(tokenURL(apiRealm))
      val response: ClientResponse = webResource.header("Content-Type", URL_ENCODED).
        header("Accept", APP_JSON).post(classOf[ClientResponse], getTokenFormData)
      checkStatus(response)
      tokenMetaData = _decorateTokenResponse(toTokenResponse(response))
      _persistTokenMetaData

     } else if (_tokenHasExpired){
      _refreshToken
    }

    tokenMetaData.access_token
  }

  //todo persistence * sycnronization
  private def _refreshToken: String = {
    if (_refreshHasExpired) {
      val response: ClientResponse = _getToken
      checkStatus(response)
      tokenMetaData = _decorateTokenResponse(toTokenResponse(response))
      _persistTokenMetaData

    }
    else {
      val refreshToken: String = tokenMetaData.refresh_token
      val refreshTokenFormData: MultivaluedMap[String, String] = new MultivaluedMapImpl
      refreshTokenFormData.add("client_id", "dxc")
      refreshTokenFormData.add("grant_type", "refresh_token")
      refreshTokenFormData.add("refresh_token", refreshToken)
      val client: Client = Client.create
      val webResource: WebResource = client.resource(tokenURL(apiRealm))
      val response: ClientResponse = webResource.header("Content-Type", URL_ENCODED).header("Accept", APP_JSON).post(classOf[ClientResponse], refreshTokenFormData)
      checkStatus(response)
      tokenMetaData = _decorateTokenResponse(toTokenResponse(response))
      _persistTokenMetaData
    }
    tokenMetaData.access_token
  }

  private def _getToken: ClientResponse = {
    val getTokenFormData: MultivaluedMap[String, String] = new MultivaluedMapImpl
    getTokenFormData.add("username", apiUserName)
    getTokenFormData.add("password", apiUserPassword)
    getTokenFormData.add("grant_type", "password")
    getTokenFormData.add("client_id", "dxc")

    val client: Client = Client.create

    val webResource: WebResource = client.resource(tokenURL(apiRealm))
    val response: ClientResponse = webResource.header("Content-Type", URL_ENCODED).
      header("Accept", APP_JSON).post(classOf[ClientResponse], getTokenFormData)
    response
  }

  private def _tokenHasExpired: Boolean = {
    if (tokenMetaData == null) {
      true
    } else {
      new Date().getTime > tokenMetaData.ird_token_expire_time
    }
  }

  private def _refreshHasExpired: Boolean = {
    if (tokenMetaData == null) {
      true
    } else {
      new Date().getTime > tokenMetaData.ird_refresh_expire_time
    }
  }

  //todo handle multithreading issues (synchronized port)
  private def _expireTokenMetaData = {
    // avoid null pointer exceptions when password is bad
    if (tokenMetaData != null) {

      val token = tokenMetaData.access_token

      tokenMetaData.ird_token_expire_time = 0
      tokenMetaData.ird_refresh_expire_time = 0
    }
  }

  private def _decorateTokenResponse(tokenResponse: TokenResponse): TokenResponse = {

    val timeNow: Long = new Date().getTime
    tokenResponse.ird_token_expire_time = timeNow + (tokenResponse.expires_in.longValue * MILLISECONDS_IN_A_SECOND) - EXPIRATION_BIAS
    tokenResponse.ird_refresh_expire_time = timeNow + (tokenResponse.refresh_expires_in.longValue * MILLISECONDS_IN_A_SECOND) - EXPIRATION_BIAS

    tokenResponse
  }

  private def _persistTokenMetaData = {
    if (tokenMetaData != null){
      //todo perist tokenMetaData
      _writeCache( gson.toJson(tokenMetaData))
    }
  }
  // read the encrypted token and return clear text
  private def _readCache: String = {
    if (com.maximus.util.JavaFileIO.exists(FILENAME))  {
      AsciiEncryption.decrypt(com.maximus.util.JavaFileIO.readBytes(FILENAME))
    }  else {
      null
    }

  }
  //write the clear text json string as encrypted string
  private def _writeCache(payload: String) {
    val encryptedPayload: Array[Byte] = AsciiEncryption.encrypt(payload)
    com.maximus.util.JavaFileIO.writeBytes(FILENAME,encryptedPayload)
  }

}
