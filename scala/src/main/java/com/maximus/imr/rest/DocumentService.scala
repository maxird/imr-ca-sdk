package com.maximus.imr.rest

import java.io.File
import javax.ws.rs.core.MediaType
import com.maximus.imr.criterion.RFICriterion
import com.maximus.imr.rest.Login._
import com.maximus.imr.rest.util.HttpUtil._
import com.maximus.imr.rest.util._
import com.maximus.util.{Properties, JavaFileIO}
import com.maximus.util.Properties._
import com.sun.jersey.api.client.{Client, ClientResponse, WebResource}
import com.sun.jersey.multipart.FormDataMultiPart
import com.sun.jersey.multipart.file.FileDataBodyPart

/**
 * DocumentService is a scala/java implementation for the RESTFul access suite for IMR related to documents
 * See document-list.sh
 *     download-case-files.sh
 *     download-file.sh
 *     upload.sh
 * It must have access to properties file (config.properties)
 * Use -Dcom.maximus.ird.configroot=<path to directory holding api.properties and config.properties>
 * config.properties contains authentication information (url/realm)
 */
trait                                                                  DocumentService extends IConnect {


  def getDocumentList(caseNumber: String): String = {
    val url = apiBaseUrl + "/apigw/webservices/rest/apigw/docs/search/cn/"+caseNumber
    val client: Client = Client.create

    val webResource: WebResource = client.resource(url)


    val response: ClientResponse = webResource.header(CONTENT_TYPE, "application/json").
      header(ACCEPT, getDataType(RETURN.JSON)).header(AUTH, bearerAuthorization).get(classOf[ClientResponse])
    convertPayload(response, RETURN.JSON)
  }

  def downloadDocument(documentId: String, fileName: String): File = {
    val url = apiBaseUrl + "/apigw/webservices/rest/apigw/docs/download"
    val client: Client = Client.create
    val webResource: WebResource = Client.create.resource(url)
    val response: ClientResponse = webResource.header(AUTH, bearerAuthorization).get(classOf[ClientResponse])
    checkStatus(response)
    JavaFileIO.create(fileName, response.getEntityInputStream)
  }
  def uploadDocument(file: File) : Integer = {
    val url = apiBaseUrl + "/apigw/webservices/rest/apigw/docs/upload"
    _uploadDocument(url, file)
  }
  def uploadDocument(caseNumber: String, file: File) : Integer = {
    val url = apiBaseUrl + "/apigw/webservices/rest/apigw/docs/upload/cn/"+caseNumber
    _uploadDocument(url, file)
  }
  /**
   * Convert the payload to a string
   * @param response The client response from the IMR restful request
   * @param returnType  {RETURN.JSON | RETURN.CSV}.  In the event of an empty return, "NO_MATCHING_DATA" is returned
   * @return  String containing the payload data
   */
  override protected def convertPayload(response: ClientResponse, returnType: RETURN.TYPE): String = {
    checkStatus(response)
    val responseString = responsePayloadToString(response)
    returnType match {
      case RETURN.JSON => {
        val converter = new JsonToObject
        val listResponse = converter.convert(responseString, classOf[DocumentListResponse])
        if (listResponse.matchCount == 0) {
          NO_MATCHING_DATA
        } else {
          responseString
        }

      }
      case RETURN.CSV => {
        if (responseString != null && responseString.length > 0) {
          responseString
        } else {
          NO_MATCHING_DATA
        }
      }
      case _ => {
        throw new IllegalStateException("Invalid RETURN.TYPE: " + returnType)
      }
    }

  }

  private def _uploadDocument(url: String, file: File) : Integer = {
    val multiPart = new FormDataMultiPart()
    multiPart.bodyPart(new FileDataBodyPart("file", file))

    val client: Client = Client.create
    val webResource: WebResource = client.resource(url)

    val response: ClientResponse = webResource.header(CONTENT_TYPE, "application/json").
      header(ACCEPT, getDataType(RETURN.JSON)).header(AUTH, bearerAuthorization).
      header(CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA).
      post(classOf[ClientResponse], multiPart)
    response.getStatus
  }
}


