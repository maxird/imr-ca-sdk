package com.maximus.imr.rest.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by tstockton on 9/2/16.
 */
public class JsonToObject  {
    private static GsonBuilder builder = new GsonBuilder();
    private static Gson gson = builder.create();

    public <T> T convert(String jsonString, Class<T> clazz)  {
        return gson.fromJson(jsonString, clazz);
    }
}
