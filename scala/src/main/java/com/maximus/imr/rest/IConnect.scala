package com.maximus.imr.rest

import com.maximus.imr.rest.util.HttpUtil._
import com.maximus.imr.rest.util.{JsonToObject, CaseApiResponse, RETURN}
import com.maximus.util.Properties._
import com.sun.jersey.api.client.ClientResponse

/**
 * IConnect encapsulates connection logic and common response logic.
 */
trait IConnect {
  /**
   * create the bearer string for authentication
   * response.header(AUTH, bearerAuthorization)
   * @return  "BEARER <TOKEN>"
   */
  def bearerAuthorization():String = {   BEARER + " " + Login.getToken
  }

  /**
   * Convert RETURN.TYPE to a HTML return type
   * @param returnType
   * @return {"text/plain" | "application/json" }
   */
  protected def getDataType(returnType: RETURN.TYPE): String = {
    if (returnType == RETURN.CSV) {
      TEXT_PLAIN
    } else {
      APP_JSON
    }
  }
  def version():String = {
    import scala.io.Source
    val source =  Source.fromInputStream(getClass().getClassLoader().getResourceAsStream("config/version.txt"))
    source.mkString
  }

  /**
   * Convert the payload to a string
   * @param response The client response from the IMR restful request
   * @param returnType  {RETURN.JSON | RETURN.CSV}.  In the event of an empty return, "NO_MATCHING_DATA" is returned
   * @return  String containing the payload data
   */
  protected def convertPayload(response: ClientResponse, returnType: RETURN.TYPE): String = {
    checkStatus(response)
    val responseString = responsePayloadToString(response)
    returnType match {
      case RETURN.JSON => {
        val converter = new JsonToObject
        val apiReturn = converter.convert(responseString, classOf[CaseApiResponse])

        if (apiReturn.matchCount == 0) {
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

}
