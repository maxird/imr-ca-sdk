package com.maximus.imr.rest

import java.text.SimpleDateFormat
import java.util.Date

import com.maximus.imr.rest.util.RETURN
import com.maximus.util.Properties._
import com.sun.jersey.api.client.{Client, ClientResponse, WebResource}

/**
  * NoarfiService is a  scala/java implementation for the noafi services in the IMR RESTFul access suite.
  * See: imr-ca-sdk/curl/noarfi.sh,noarfi-details.sh, noarifi-acknowledge.sh
  * It must have access to properties file (config.properties)
  * Use -Dcom.maximus.ird.configroot=<path to directory holding config.properties>
  * config.properties contains authentication information (url/realm).
  */
trait NoarfiService extends IConnect {
  private val iso8601DateFormat = new SimpleDateFormat("yyyy-MM-dd")

  /**
    * Fetch dates with noarfi with outstanding requests
    *
    * @return json string containing dates in ISO 8601 (i.e. yyyy-MM-dd).
    */
  def list(): String = {
    val url = apiBaseUrl + "/apigw/webservices/rest/apigw/events/noarfi/"
    val client: Client = Client.create

    val webResource: WebResource = client.resource(url)


    val response: ClientResponse = webResource.header(CONTENT_TYPE, APP_JSON).
      header(AUTH, bearerAuthorization).get(classOf[ClientResponse])
    convertPayload(response, RETURN.JSON)
  }

  /**
    * noarfiDetail fetch noarfi detail for a given date
    *
    * @param noarfiDate the date for which noarfi detail is returned
    * @return noarfi detail in csv format
    */
  def detail(noarfiDate: Date): String = {
    detail(iso8601DateFormat.format(noarfiDate))
  }
    /**
    * noarfiDetail fetch noarfi detail for a given date
    *
    * @param noarfiDate the date for which noarfi detail is returned
    * @return noarfi detail in csv format
    */
  def detail(noarfiDate: String): String = {
    // validate the incoming date
    iso8601DateFormat.parse(noarfiDate)

    val url = apiBaseUrl + s"/apigw/webservices/rest/apigw/events/noarfi/${noarfiDate}"
    val client: Client = Client.create

    val webResource: WebResource = client.resource(url)


    val response: ClientResponse = webResource.header(CONTENT_TYPE, APP_JSON).
      header(AUTH, bearerAuthorization).get(classOf[ClientResponse])
    convertPayload(response, RETURN.CSV)
  }

  def acknowledge(noarfiDate: Date): String = {
    acknowledge(iso8601DateFormat.format(noarfiDate))
  }
  /**
    * acknowledge noarfi.  Note!  Acknowledging the noarfi data eliminates it as data points for noarfi/noarfiDetail
    *
    * @param noarfiDate date gor noarfi request
    * @return the noarfi detail @see detail(String noarfiDate)
    */
  def acknowledge(noarfiDate: String): String = {
    val noarfiDetails = detail(noarfiDate)
    val url = apiBaseUrl + s"/apigw/webservices/rest/apigw/events/noarfi/${noarfiDate}"
    val client: Client = Client.create

    val webResource: WebResource = client.resource(url)

    //    val response: ClientResponse = webResource.header(CONTENT_TYPE, APP_JSON).
    //      header(AUTH, bearerAuthorization).delete(classOf[ClientResponse])
    //    checkStatus(response)
    noarfiDetails
  }


}
