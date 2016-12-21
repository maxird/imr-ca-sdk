package com.maximus.imr.rest.util;

/**
 * Java object corresponding to the token response
 */
public class TokenResponse {
    public String access_token;
    public String refresh_token;
    public double expires_in;
    public double refresh_expires_in;
    public long ird_token_expire_time;
    public long ird_refresh_expire_time;

}
