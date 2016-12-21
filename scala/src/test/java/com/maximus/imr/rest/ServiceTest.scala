package com.maximus.imr.rest

import com.maximus.imr.rest.util.RETURN
import org.junit.Assert._
import org.junit.{Before, Test}


/**
 see curl/cases.sh
 */
class  ServiceTest extends CaseSearchService with RfiSearchService with DocumentService {
   var caseNumber : String = null

   @Before
   def initialize() {
      val csvString: String = caseSearch(RETURN.CSV)
      val fields  = csvString.split(",")
      caseNumber = fields(0)
   }
   @Test
   def getDocumentList() {
      assertTrue(caseNumber != null)
      val documentString = getDocumentList(caseNumber)
      assertNotNull(documentString)
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
   @Test
   def searchRfiJson()  {
      val jsonString: String = rfiSearch()
      System.out.println("searchCases()")
      assertNotNull("Looking for a non-null return", jsonString)
   }

   @Test
   def searchRfiCsv() {
      val csvSring: String  = rfiSearch(RETURN.CSV)
      assertNotNull("Looking for a non-null return", csvSring)
      System.out.println("searchCases(RETURN.CSV)")
      System.out.println(csvSring)
      System.out.println("===============================================")
   }
}
