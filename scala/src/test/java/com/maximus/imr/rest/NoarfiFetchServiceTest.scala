package com.maximus.imr.rest

import java.text.SimpleDateFormat
import java.util.Date

import com.maximus.imr.rest.util.RETURN
import org.junit.Assert._
import org.junit.{Before, Ignore, Test}


/**
 * Created by tstockton on 8/24/16.
 *
 */
class  NoarfiFetchServiceTest extends NoarfiService {
   val sdf = new SimpleDateFormat("yyyy-MM-dd")
   @Before
   def initialize() {

   }

   @Test
   def testList()  {
      val jsonString: String = list()
      System.out.println("noarfiFetch()")
      assertNotNull("Looking for a non-null return", jsonString)
   }

   @Test
   def testDetails() {
      val csvSring: String  = detail(sdf.parse("2016-11-30"))
      assertNotNull("Looking for a non-null return", csvSring)
      System.out.println("rfiSearch(RETURN.CSV)")
      System.out.println(csvSring)
      System.out.println("===============================================")
   }
   @Test
   def testAcknowledge() {
      val csvSring: String  = acknowledge(sdf.parse("2016-11-30"))
      assertNotNull("Looking for a non-null return", csvSring)
      System.out.println("rfiSearch(RETURN.CSV)")
      System.out.println(csvSring)
      System.out.println("===============================================")
   }
}
