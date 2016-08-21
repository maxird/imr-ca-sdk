package com.maximus.imr.rest;

import org.junit.Ignore;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


/**
 * Created by tstockton on 7/26/16.
 */
public class RESTRequestorTest extends BaseRESTRequestorTest {

    @Test
    public void getToken() {
        String jsonString = rr.getToken();
        assertNotNull("Token should not be null", jsonString);
        System.out.println("getToken()");
        System.out.println(jsonString);
        System.out.println("===============================================");
    }

    @Test
    public void testUrlBuilder() {
        String retval = rr.urlBuilder("http://localhost:8081/a", "/b/c", "d", "e", "f/g");
        assertEquals(retval, "http://localhost:8081/a/b/c/d/e/f/g");
    }

    @Test
    public void testUrlBuilderWithMap() {
        LinkedHashMap<String, String> args = new LinkedHashMap<>();
        args.put("one", "1");
        args.put("two", "2");
        args.put("three", "3");
        args.put("four", "4");
        String retval = rr.urlBuilder("http://localhost:8081/a", args, "/b/c", "d", "e", "f/g");
        assertEquals(retval, "http://localhost:8081/a/b/c/d/e/f/g?one=1&two=2&three=3&four=4");
    }

    @Test
    public void testGetHomeFolder(){
        String folder = rr.getHomeFolder();
        assertNotNull("Home folder should not be null", folder);
        assertFalse("Folder should not start with  the word 'null'", folder.startsWith("null"));
        assertFalse("Folder should not end with  the word 'null'", folder.endsWith("null"));
    }
    @Ignore
    public void testFails() {
        fail("Not implemented");
    }

}
