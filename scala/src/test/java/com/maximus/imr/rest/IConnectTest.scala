package com.maximus.imr.rest

import org.junit.Assert._
import org.junit.{Before, Test}



/**
 * Created by tstockton on 8/24/16.
 */
class  IConnectTest extends IConnect {
   @Before
   def initialize() {

   }
   @Test
   def connectTest() {
        val tokenString: String = bearerAuthorization()
      assertTrue(tokenString != null)
   }
   @Test
   def versionTest(): Unit = {
      val versionText = version
      assertTrue(versionText != null)
   }
}
