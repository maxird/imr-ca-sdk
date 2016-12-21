package com.maximus.imr.rest.util

import com.google.gson.{Gson, GsonBuilder, TypeAdapter}
import com.sun.jersey.api.client.ClientResponse

/**
 * Created by tstockton on 8/24/16.
 * todo: google.gson is native java... find a scala replacement
 * todo: jersey is native java... find a replacement
 */
object HttpUtil {
  val gson: Gson = new GsonBuilder().create()

  /**
   * check response error code.  Throw a runtime exception if error code is not in the "OK range" (200s and 300s)
   * @param clientResponse response from a jesery http request
   **/
  def checkStatus(clientResponse: ClientResponse) = {
    val httpStatus : Integer = clientResponse.getStatus
    if (httpStatus < 200 || httpStatus > 399){
      throw new RuntimeException("Failed : HTTP error code : " + httpStatus)
    }
  }

  /**
   * extract the token data from the ClientResponse
   * @param clientResponse response from a keycload request for an access
   * @return  Keycloak taoken data as a java object
   */
  def toTokenResponse(clientResponse: ClientResponse): TokenResponse = {
    val output: String = clientResponse.getEntity(classOf[String])
    //gson only works on the java class.
    val adapter: TypeAdapter[TokenResponse] = gson.getAdapter(classOf[TokenResponse])
    val retval  = adapter.fromJson(output)
    retval
  }
  /**
   * extract string output from client response
   * @param response The client response from the IMR restful request
   * @return string entity from response
   */
  def responsePayloadToString(response: ClientResponse): String = {
    val output = response.getEntity(classOf[String])
    val adapter = gson.getAdapter(classOf[ClientResponse])
    output
  }



}
