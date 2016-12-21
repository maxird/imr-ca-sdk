package com.maximus.imr.rest.util

/**
 * Valid return types for RMI RESTful requests.  We understand two return types, CSV & JSON
 */
object RETURN extends Enumeration {
  type TYPE = Value
  val JSON, CSV = Value
}