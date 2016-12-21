package com.maximus.imr.rest

import com.maximus.imr.rest.util.RETURN
import org.junit.Assert._
import org.junit.{Before, Test}


/**
 * Created by tstockton on 8/24/16.
 */
class  DocumentServiceTest extends CaseSearchService with DocumentService  {
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
  def downloadDocument() {
    fail("not implemented")
  }
  @Test
  def uploadDocument() {
    fail("not implemented")
  }
}
