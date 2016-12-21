package com.maximus.imr.rest.util;

import java.util.List;
import java.util.Map;

/**
 * Created by tstockton on 9/1/16.
 */
public class DocumentListResponse {
    public int callIndex;
    public int matchCount;
    public int start;
    public int limit;
    public List<Map<String,Object>> results;

}
