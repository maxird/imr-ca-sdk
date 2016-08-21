package com.maximus.imr.rest;

import com.maximus.imr.rest.criterion.CaseCriterion;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Map;

/**
 * This class implements the RESTFul access suite for IMR.  It must have access to properties files (api.properties and config.properties)
 * Use -Dcom.maximus.ird.configroot=<path to directory holding api.properties and config.properties>
 * config.properties contains authentication information (url/realm)
 * api.properties    contains properties relevant to the IMR REST api
 */
public class IMRRESTRequestor extends RESTRequestor {
    /**
     * @refer api.properties
     */
    private static final String API_ENDPOINT_BASE = "api.endpoint.base";
    private static final String CASE_DOCUMENT_LIST = "case.document.list";
    private static final String CASE_SEARCH = "case.search.api";
    private static final String CASE_UPLOAD_DOCUMENT = "case.upload.document";
    private static final String DOWNLOAD_DOCUMENT = "download.document";
    private static final String FILE_UPLOAD_DOCUMENT = "file.upload.document";
    private static final String RFI_SEARCH = "rfi.search.api";

    protected static final String NO_MATCHING_DATA = "NO_MATCHING_DATA";
    protected static NumberFormat numberFormat = NumberFormat.getInstance();

    static {
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumFractionDigits(0);
    }


    /**
     * {@inheritDoc}
     */
    public IMRRESTRequestor(String user, String password) {
        super(user, password);
    }

    public String casesSearch(CaseCriterion params) {
        return casesSearch(params, RETURN_TYPE.JSON);
    }

    public String casesSearch(CaseCriterion params, RETURN_TYPE return_type) {

        String url = urlBuilder(getConfigProperty(API_ENDPOINT_BASE), getApiProperty(CASE_SEARCH));

        Client client = Client.create();

        WebResource webResource = client.resource(url);


        ClientResponse response = webResource
                .header(CONTENT_TYPE, "application/json")
                .header(ACCEPT, _getDataType(return_type))
                .header(AUTH, bearerAuth())
                .post(ClientResponse.class, params == null ? null : params.toJsonString());

        checkStatus(response);
        return _getReturn(response, return_type);

    }

    /**
     * CASE SEARCH API
     * <p>
     * searchCases: Search cases that are available to the organization
     * <p>
     * POST <apigw-url>/webservices/rest/apigw/cases/search HTTP/1.1
     *
     * @return a JSON String
     */
    public String casesSearch() {
        return casesSearch(new CaseCriterion(), RETURN_TYPE.JSON);
    }

    /**
     * CASE SEARCH API
     * <p>
     * searchCases: Search cases that are available to the organization
     * <p>
     * POST <apigw-url>/webservices/rest/apigw/cases/search HTTP/1.1
     *
     * @param return_type {JSON | CSV }
     * @return a String corresponding to the RETURN_TYPE
     */
    public String casesSearch(RETURN_TYPE return_type) {
        return casesSearch(new CaseCriterion(), return_type);
    }


    /**
     * RFI SEARCH API
     * searchRfi search cases that are in request for information status and for which the claim admin has not indicated
     * completion of doc upload.
     *
     * @return a JSON String
     */
    public String rfiSearch() {
        return rfiSearch(RETURN_TYPE.JSON);
    }

    /**
     * RFI SEARCH API
     * searchRfi search cases that are in request for information status and for which the claim admin has not indicated
     * completion of doc upload.
     *
     * @return a CSV String
     */
    public String rfiSearch(RETURN_TYPE return_type) {

        String url = urlBuilder(getConfigProperty(API_ENDPOINT_BASE), getApiProperty(RFI_SEARCH));

        Client client = Client.create();

        WebResource webResource = client.resource(url);


        ClientResponse response = webResource
                .header(CONTENT_TYPE, "application/json")
                .header(ACCEPT, _getDataType(return_type))
                .header(AUTH, bearerAuth())
                .post(ClientResponse.class);

        checkStatus(response);
        return _getReturn(response, return_type);
    }

    /**
     * DOCUMENT API
     * getDocumentList: To Get document list for a given the Case Number.
     * curl -i -H "Authorization: Bearer <Token>" http://10.23.1.75:8090/webservices/rest/apigw/docs/search/cn/CM16-00010043
     *
     * @return a JSON String
     */
    public String getDocumentList(String caseNumber) {
        String url = urlBuilder(getConfigProperty(API_ENDPOINT_BASE), getApiProperty(CASE_DOCUMENT_LIST), caseNumber);

        Client client = Client.create();

        WebResource webResource = client.resource(url);


        ClientResponse response = webResource
                .header(CONTENT_TYPE, "application/json")
                .header(ACCEPT, _getDataType(RETURN_TYPE.JSON))
                .header(AUTH, bearerAuth())
                .get(ClientResponse.class);
        checkStatus(response);
        Map<String, Object> responseMap = getResponseMap(response);
        int matchCount = ((Double) responseMap.get("matchCount")).intValue();
        if (matchCount == 0) {
            return NO_MATCHING_DATA;
        }
        return gson.toJson(responseMap);
    }
    /**
     * DOCUMENT API
     * http://10.23.1.75:8090/webservices/rest/apigw/docs/download/20.5000.214/07ec0c0348e1f49a87a3 -o file1.zip
     *
     * @param documentId the identifier for the document to be downloaded
     * @param fileName   the root name of the file (less extension) e.g "FileName", not "FileName.pdf"
     * @return a File containing the content of the document
     * @throws IOException problem creating/writing the output file
     */
    public File downloadDocument(String documentId, String fileName) throws IOException {
        File file = new File(fileName);
        String url = urlBuilder(getConfigProperty(API_ENDPOINT_BASE), getApiProperty(DOWNLOAD_DOCUMENT), documentId);

        WebResource webResource = Client.create().resource(url);
        ClientResponse response = webResource
                .header(AUTH, bearerAuth())
                .get(ClientResponse.class);
        checkStatus(response);
        InputStream inputStream = response.getEntityInputStream();
        FileOutputStream outputStream = new FileOutputStream(file);
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        return file;

    }

    /**
     */
    /**
     * DOCUMENT API
     * http://10.23.1.75:8090/webservices/rest/apigw/docs/download/20.5000.214/07ec0c0348e1f49a87a3 -o file1.zip
     *
     * @param fileName  the root name of the file (less extension) e.g "FileName", not "FileName.pdf"
     * @param rendering Map<String, String> the rendering map from getDocumentList return.  It is assumed that rendering contains the keys "identifier" and "type"
     * @return a File containing the content of the document
     * @throws IOException problem creating/writing the output file
     */
    public File downloadDocument(String fileName, Map<String, Object> rendering) throws IOException {
        return downloadDocument((String) rendering.get("identifier"), fileName + getFileExtensionForType((String) rendering.get("type")));
    }

    /**
     * Upload a document to a case
     *
     * @param caseNumber the case identifier for the file
     * @param file       the File to be uploaded
     * @return http status
     */
    public int uploadDocument(String caseNumber, File file) {
        String url = urlBuilder(getConfigProperty(API_ENDPOINT_BASE), getApiProperty(CASE_UPLOAD_DOCUMENT), caseNumber);

       return _uploadDocument(url, file);

    }
    /**
     * Upload a document to a case
     *
     * @param file       the File to be uploaded
     * @return http status
     */
    public int uploadDocument(File file) {
        String url = urlBuilder(getConfigProperty(API_ENDPOINT_BASE), getApiProperty(FILE_UPLOAD_DOCUMENT));
         return _uploadDocument(url, file);

    }

    /**
     * returns the .type from the type string
     *
     * @param type string of the form: "Application/pdf"
     * @return String of the form: ".pdf" or empty string if type is not oc correct form.
     */
    protected String getFileExtensionForType(String type) {
        if (type == null || type.length() < 4 || type.lastIndexOf("/") < 0) {
            return "";
        }
        return "." + type.substring(type.lastIndexOf("/") + 1);
    }
    private int _uploadDocument(String url, File file){
        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.bodyPart(new FileDataBodyPart("file", file));
        WebResource webResource = Client.create().resource(url);
        ClientResponse response = webResource
                .header(AUTH, bearerAuth())
                .header(CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                .post(ClientResponse.class, multiPart);
        checkStatus(response);
        return response.getStatus();

    }

    private String _getDataType(RETURN_TYPE type){
        switch (type){
            case CSV:
                return TEXT_PLAIN;
            case JSON:
                return APP_JSON;
            default:
                throw new IllegalArgumentException(type+ "is unknown");
        }
    }
    private String _getReturn(ClientResponse response, RETURN_TYPE return_type){
        switch (return_type) {
            case CSV: {
                String retval =  response.getEntity(String.class);
                if (retval.length() == 0){
                    return NO_MATCHING_DATA;
                }
                return retval;
            }

            case JSON:
            default: {
                Map<String, Object> responseMap = getResponseMap(response);
                int matchCount = ((Double) responseMap.get("matchCount")).intValue();
                if (matchCount == 0) {
                    return NO_MATCHING_DATA;
                }
                return gson.toJson(responseMap);
            }
        }

    }


}
