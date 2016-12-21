package com.maximus.imr.rest

import com.maximus.imr.criterion.CaseCriterion
import com.maximus.imr.rest.util.RETURN
import com.maximus.util.Properties._
import com.sun.jersey.api.client.{Client, ClientResponse, WebResource}

/**
 * CaseSearchService is a  scala/java implementation for the case search service in the IMR RESTFul access suite.
 * See: imr-ca-sdk/curl/cases.sh
 * It must have access to properties file (config.properties)
 * Use -Dcom.maximus.ird.configroot=<path to directory holding config.properties>
 * config.properties contains authentication information (url/realm).
 */
trait CaseSearchService extends IConnect {
  private def defaultCaseCriterion = new CaseCriterion()

  def caseSearch(): String = {
    caseSearch(defaultCaseCriterion, RETURN.JSON)
  }

  def caseSearch(params: CaseCriterion): String = {
    caseSearch(params, RETURN.JSON)
  }

  def caseSearch(returnType: RETURN.TYPE): String = {
    caseSearch(defaultCaseCriterion, returnType)
  }

  def caseSearch(params: CaseCriterion, returnType: RETURN.TYPE): String = {
    val url = apiBaseUrl + "/apigw/webservices/rest/apigw/cases/search"
    val client: Client = Client.create

    val webResource: WebResource = client.resource(url)


    val response: ClientResponse = webResource.header(CONTENT_TYPE, APP_JSON).
      header(ACCEPT, getDataType(returnType)).
      header(AUTH, bearerAuthorization).post(classOf[ClientResponse], if (params == null) null else params.toJsonString)
    convertPayload(response, returnType)
  }

}
