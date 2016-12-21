package com.maximus.imr.rest.util;

import java.util.List;
import java.util.Map;

/**
 * Java object for the json response
 */
public class CaseApiResponse {
    public Integer start;
    public Integer limit;
    public Integer matchCount;
    public List<Map<String,String>> results;
}
