package com.maximus.imr.rest;

import com.maximus.imr.rest.criterion.CaseCriterion;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by tstockton on 7/26/16.
 */
public class IMRRESTRequestorTest extends BaseRESTRequestorTest {
    private static IMRRESTRequestor rr;
    private static final String USER = "";
    private static final String PW = "";
    protected static Gson gson = new GsonBuilder().create();


    @Before
    public void setup() {
        rr = new IMRRESTRequestor(RESTRequestorTest.userName, RESTRequestorTest.userPassword);
    }

    @After
    public void tearDown() {

    }

    @Test
    // see curl/login.sh
    public void getToken() {
        String jsonString = rr.getToken();
        assertNotNull("Token should not be null", jsonString);
        System.out.println("getToken()");
        System.out.println(jsonString);
        System.out.println("===============================================");
    }
    @Test
    // see curl/refresh.sh
    public void refresh() {
        rr.getToken();
        String jsonString = rr.refreshToken();
        assertNotNull("Token should not be null", jsonString);
        System.out.println("getToken()");
        System.out.println(jsonString);
        System.out.println("===============================================");
    }

    @Test
    // see curl/cases.sh
    public void searchCasesJson() {
        String jsonString = rr.casesSearch();
        assertNotNull("Looking for a non-null return", jsonString);
        System.out.println("searchCasesJson()");
        System.out.println(jsonString);
        System.out.println("===============================================");
    }

    @Test
    // see curl/cases.sh
    public void searchCasesCsv() {
        String csvString = rr.casesSearch(IMRRESTRequestor.RETURN_TYPE.CSV);
        assertNotNull("Looking for a non-null return", csvString);
        System.out.println("searchCasesCsv()");
        System.out.println(csvString);
        System.out.println("===============================================");
    }

    @Test
    //see curl/cases.sh
    public void searchCasesWithParams() {
        CaseCriterion json = new CaseCriterion().addSortProperties(CaseCriterion.SORT.dateOfInjury);
        String csvString = rr.casesSearch(json);
        assertNotNull("Looking for a non-null return", csvString);
        System.out.println("searchCasesCsv()");
        System.out.println(csvString);
        System.out.println("===============================================");
    }

    @Test
    // see curl/cases.sh
    public void searchCasesWithNullParams() {
        CaseCriterion json = null;
        String csvString = rr.casesSearch(json);
        assertNotNull("Looking for a non-null return", csvString);
        System.out.println("searchCasesCsv()");
        System.out.println(csvString);
        System.out.println("===============================================");
    }

    @Test
    // see curl/rfi.sh
    public void searchRfiJson() {
        String jsonString = rr.rfiSearch();
        assertNotNull("Looking for a non-null return", jsonString);
        System.out.println("searchRfiJson()");
        System.out.println(jsonString);
        System.out.println("===============================================");
    }

    @Test
    // see curl/rfi.sh
    public void searchRfiCsv() {
        String csvString = rr.rfiSearch(IMRRESTRequestor.RETURN_TYPE.CSV);
        assertNotNull("Looking for a non-null return", csvString);
        System.out.println("searchRfiCsv()");
        System.out.println(csvString);
        System.out.println("===============================================");
    }
    @Test
    public void testVersion() {
        String versionString = rr.version();
        assertNotNull("Looking for a non-null return", versionString);
        System.out.println("version()");
        System.out.println(versionString);
        System.out.println("===============================================");
    }

    @Test
    // see curl/document-list.sh
    public void getDocumentList() throws IOException {
        List<Map<String, String>> caseData = caseData();
        String caseNumber = caseData.get(0).get("caseNumber");
        String jsonString = rr.getDocumentList(caseNumber);
        assertNotNull("Looking for a non-null return", jsonString);
        System.out.println("getDocumentListJson()");
        System.out.println(jsonString);
        System.out.println("===============================================");
    }


    @Test
    // see curl/upload.sh
    public void uploadCaseDocument() throws IOException {
        List<Map<String, String>> caseData = caseData();
        String caseNumber = caseData.get(0).get("caseNumber");
        File file = new File(dataDir + "/" + pdfFileName);
        if (!(file != null && file.exists() && file.canRead())) {
            System.out.println("WARNING: upload disabled, a file must be present to test upload");
        } else {
            int retval = rr.uploadDocument(caseNumber, file);
            assertTrue(retval >= 200 && retval < 300);
        }
    }
    @Test
    // see curl/noarfi.sh
    public void fetchNoarfiTest() throws IOException {
        String data = rr.noarfiFetch();
        assertNotNull(data);
        assertTrue(data.length() >0);
    }
    @Test
    // see curl/noarfi.sh
    public void fetchNoarfiDetail() throws IOException {
        String data = rr.noarfiDetail("2016-11-30");
        assertNotNull(data);
        assertTrue(data.length() >0);
    }

    @Test
    // see curl/download-file.sh
    // see curl/download-case-files.sh
    public void downloadDocuments() throws IOException {
        List<String> caseNumbers = new ArrayList<String>();
        // first get the caseNumbers
        for (Map<String, String> map : caseData()) {
            caseNumbers.add(map.get("caseNumber"));
        }
        Map<String, Boolean> downloadDocumentResults = new HashMap<>();
        // for each caseNumber, get the documentRenderings available
        for (String caseNumber : caseNumbers) {
            String jsonString = rr.getDocumentList(caseNumber);
            if (NO_MATCHING_DATA.equals(jsonString)) {
                System.err.println(caseNumber + " shows no documents");
            }
            else {
                HashMap jsonMap = gson.getAdapter(HashMap.class).fromJson(jsonString);
                List<Map<String, Object>> getDocumentListResults = (List<Map<String, Object>>) jsonMap.get("results");
                for (Map<String, Object> map : getDocumentListResults) {
                    for (Map<String, Object> rendering : (List<Map<String, Object>>) map.get("renderings")) {
                        File result;
                        String docId = null;
                        try {
                            docId = (String) rendering.get("identifier");
                            result = rr.downloadDocument((String) map.get("name"), rendering);
                            if (result.exists()) {
                                downloadDocumentResults.put(docId, true);
                            }
                            else {
                                downloadDocumentResults.put(docId, false);
                                System.out.println("Unable to download " + docId);
                            }
                        }
                        catch (Exception e) {
                            downloadDocumentResults.put(docId, false);
                            System.out.println("Unable to download " + docId + ":" + e.getMessage());
                        }
                    }
                }

            }
        }
        if (downloadDocumentResults.isEmpty()) {
            fail("No documents downloaded.  ");
        }
        else {
            boolean success = true;
            for (String docId : downloadDocumentResults.keySet()) {
                if (!downloadDocumentResults.get(docId)) {
                    success = false;
                }
            }
            assertTrue("There were unsuccessful file downloads", success);
        }

    }

    private String getFileExtensionForType(String type) {
        return "." + type.substring(type.lastIndexOf("/") + 1);
    }

    private void download(String id) {
        String fileName = id.replaceAll("/", "-");
        try {
            File file = rr.downloadDocument(id, fileName);
            file.delete();
        }
        catch (IOException e) {
            fail(id + ": filename:" + fileName);
        }

    }

    private List<Map<String, String>> caseData() throws IOException {
        String jsonString = rr.casesSearch();
        if (IMRRESTRequestor.NO_MATCHING_DATA.equals(jsonString)) {
            fail("No case data present");
        }
        HashMap jsonMap = gson.getAdapter(HashMap.class).fromJson(jsonString);
        List<Map<String, String>> results = (List<Map<String, String>>) jsonMap.get("results");
        List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();

        for (Map<String, String> map : results) {
            Map<String, String> entry = new HashMap<String, String>(2);
            entry.put("caseNumber", map.get("caseNumber"));
            entry.put("claimNumber", map.get("claimNumber"));
            returnList.add(entry);
        }
        return returnList;

    }
}
