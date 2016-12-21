package com.maximus.imr.rest

import org.junit.Assert._
import org.junit.{Before, Test}
import com.maximus.imr.rest.util.RETURN


/**
 see curl/cases.sh
 */
class  CaseSearchServiceTest extends CaseSearchService {
   @Before
   def initialize() {

   }

   @Test
   def searchCasesJson()  {
      val jsonString: String = caseSearch()
      System.out.println("searchCases()")
      assertNotNull("Looking for a non-null return", jsonString)
   }

   @Test
   def searchCasesCsv() {
      val csvSring: String  = caseSearch(RETURN.CSV)
      assertNotNull("Looking for a non-null return", csvSring)
      System.out.println("searchCases(RETURN.CSV)")
      System.out.println(csvSring)
      System.out.println("===============================================")
   }
}
