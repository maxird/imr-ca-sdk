package com.maximus.imr.rest

import com.maximus.imr.criterion.RFICriterion
import com.maximus.imr.rest.util.RETURN
import com.maximus.util.Properties._
import com.sun.jersey.api.client.{Client, ClientResponse, WebResource}

/**
  * RfiSearchService is a  scala/java implementation for the rfi search service in the IMR RESTFul access suite.
  * See: imr-ca-sdk/curl/rfi.sh
  * It must have access to properties file (config.properties)
  * Use -Dcom.maximus.ird.configroot=<path to directory holding config.properties>
  * config.properties contains authentication information (url/realm).
  */
trait RfiSearchService extends IConnect {
   private def defaultRfiCriterion = new RFICriterion()

   def rfiSearch(): String = {
     rfiSearch(defaultRfiCriterion, RETURN.JSON)
   }

   def rfiSearch(params: RFICriterion): String = {
     rfiSearch(params, RETURN.JSON)
   }

   def rfiSearch(returnType: RETURN.TYPE): String = {
     rfiSearch(defaultRfiCriterion, returnType)
   }

   def rfiSearch(params: RFICriterion, returnType: RETURN.TYPE): String = {
     val url = apiBaseUrl + "/apigw/webservices/rest/apigw/cases/searchrfi"
     val client: Client = Client.create

     val webResource: WebResource = client.resource(url)


     val response: ClientResponse = webResource.header(CONTENT_TYPE, APP_JSON).
       header(ACCEPT, getDataType(returnType)).
       header(AUTH, bearerAuthorization).post(classOf[ClientResponse], if (params == null) null else params.toJsonString)
     convertPayload(response, returnType)
   }

 }
