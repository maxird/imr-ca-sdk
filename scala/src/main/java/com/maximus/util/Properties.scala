package com.maximus.util

/**
 * A collection of configuration values.  The config.properties file is read and (potentially) written with encrypted values.
 */
object Properties {

  /**
   * CONSTANTS
   */
  val APP_JSON: String = "application/json"
  val APP_TEXT: String = "application/text"
  val TEXT_PLAIN: String = "text/plain"
  val URL_ENCODED: String = "application/x-www-form-urlencoded"
  val AUTH: String = "Authorization"
  val CONTENT_TYPE: String = "Content-Type"
  val ACCEPT: String = "Accept"
  val BEARER: String = "Bearer"
  val MILLISECONDS_IN_A_SECOND = 1000
  val EXPIRATION_BIAS = MILLISECONDS_IN_A_SECOND*30
  val NO_MATCHING_DATA: String = "NO_MATCHING_DATA"

  val properties = new JavaPropertyFile("config.properties")
  /**
   * Property based "constants"
   * see config.properties
   */
  def tokenURL(realm: String): String = url(authServerBaseUrl, "realms", apiRealm, "protocol/openid-connect/token")

  val apiBaseUrl: String = properties.getValue("api.endpoint.base")
  val apiRealm: String = properties.getValue("api.realm")
  val apiUserName: String = properties.getValue("encrypt.rest.user.name")
  val apiUserPassword: String = properties.getValue("encrypt.rest.user.pass")
  val authServerBaseUrl: String = properties.getValue("authserver.base.url")
  val authServerRealm: String = properties.getValue("authserver.realm")
  val additionalFactors = JavaPropertyFile.additionalFactors();
  /**
   * Provides the url as a combintation of the root/and/every/path/element
   * @param root  the root of the url
   * @param pathElements elements to build into the path
   * @return a valid url string made up of  root/and/every/path/elemen
   */
  def url(root: String, pathElements: String*): String = {
    var pathElement: String = ""
    var path: String = ""
    for (pathElement <- List(pathElements:_*)) {
      var newPathElement: String =""
      if (pathElement.startsWith("/")) {
        newPathElement = pathElement.substring(1)
      }
      if (pathElement.endsWith("/")) {
        newPathElement = pathElement.substring(0, path.length - 1)
      }
      if (newPathElement.length == 0){
        path = path +"/" + pathElement
      } else {
        path = path + "/" + newPathElement
      }
    }

    root + path
  }

  /**
   * A kludgey, but (hopefully) portable utility for identifying the home directory
   * @return  a string containing the user's home directory
   */
  def home :String =  {
    val unixFolder = System.getenv ("HOME")

    val winFolder = System.getenv ("HOMEDRIVE")+ System.getenv ("HOMEPATH")

    if ( unixFolder != null && unixFolder.length > 0  ) unixFolder else winFolder
  }

}
