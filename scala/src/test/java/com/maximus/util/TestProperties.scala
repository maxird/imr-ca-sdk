package com.maximus.util

import org.junit.Assert._
import org.junit.Test

/**
 * Created by tstockton on 9/7/16.
 */
class TestProperties {
  @Test def testAuthServerBaseUrl {
    val retval: String = Properties.authServerBaseUrl
    assertNotNull("authServerBaseUrl should not be null", retval)
  }
}
