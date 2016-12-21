package com.maximus.imr.rest

import com.maximus.imr.rest.util.RETURN
import org.junit.Assert._
import org.junit.{Before, Test}


/**
 * Created by tstockton on 8/24/16.
 *
 */
class  RfiSearchServiceTest extends RfiSearchService {
   var rfiSearchService: RfiSearchService   = null
   @Before
   def initialize() {

   }

   @Test
   def searchRfiJson()  {
      val jsonString: String = rfiSearch()
      System.out.println("rfiSearch()")
      assertNotNull("Looking for a non-null return", jsonString)
   }

   @Test
   def searchRfiCsv() {
      val csvSring: String  = rfiSearch(RETURN.CSV)
      assertNotNull("Looking for a non-null return", csvSring)
      System.out.println("rfiSearch(RETURN.CSV)")
      System.out.println(csvSring)
      System.out.println("===============================================")
   }
}
