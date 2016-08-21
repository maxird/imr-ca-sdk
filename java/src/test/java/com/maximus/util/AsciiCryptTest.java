package com.maximus.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * Created by tstockton on 8/12/16.
 */
public class AsciiCryptTest {
    public static final String TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI4YjM1ZjIxMS1mZTVjLTQ5NDQtODEyYS04OWU3ZjU5YTU1ZDYiLCJleHAiOjE0NzA5NTI2OTIsIm5iZiI6MCwiaWF0IjoxNDcwOTQ5MDkyLCJpc3MiOiJodHRwOi8vZHhjZGV2OjkwMDAvYXV0aC9yZWFsbXMvZHhjLWV4dGVybmFscyIsImF1ZCI6ImR4YyIsInN1YiI6IjViNWI2ZWZkLTEzNDItNDFjZi04YjJjLWUzYjJkZGM5NjJmMSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImR4YyIsInNlc3Npb25fc3RhdGUiOiI4ZTU5ODc0My0zNjg1LTQ2ODYtOTcxYi03ZTg3ODM0MTAwNDgiLCJjbGllbnRfc2Vzc2lvbiI6Ijk0YTI0YWZhLTFmNWYtNGRmYS04YTNlLTVkZGYyOTQ4ZDFlYiIsImFsbG93ZWQtb3JpZ2lucyI6W10sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJEZWZhdWx0IFNlcnZpY2UgUm9sZSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImR4YyI6eyJyb2xlcyI6WyJjYXNlLmFsbCIsImNhc2UuYXNzaWduIiwiY2FzZS5jb21tZW50IiwiY2FzZS5hcHBlbmQiLCJjYXNlLmFkbWluIiwiY2FzZS5zdGF0ZSIsImNhc2UuaWRlbnRpZmllcnMiLCJjYXNlLnRyYW5zZmVyIiwiY2FzZS52aWV3IiwiY2FzZS5pbnZpdGUiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJ2aWV3LXByb2ZpbGUiXX19LCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLmNhMW9ubHlAbWF4aXJkLmNvbSIsImdpdmVuX25hbWUiOiJDQTFPTkxZIiwiZmFtaWx5X25hbWUiOiJTZXJ2aWNlIiwiZW1haWwiOiJzZXJ2aWNlLmNhMW9ubHlAbWF4aXJkLmNvbSIsInBhcnRpY2lwYW50IjoiMjAuNTAwMC4yMTQvcGNhMW9ubHkifQ.KjU47SGtQY9lkdEUni88vb7L0R78JqqBxbUPRog0b9jG86wbNLhGJim6f_KLvmhhU5vTTIdjT36OUuShoJwsoHNRCSSGVGDpm_jzbXlKMsemm_Jc_pTZ9PCwSDnfMV0Os0JjveMiGNeWDPiCq5_QVRGVEgSOv4RWFlynoa_NQ7UWCvN9MQ8VwoMI7sJfcf_gH5bQjM0ZJPHutZ6dFlDh3hAGfXeZe3WCuK8PWCxbppCOxYMAUO8E3kgb6wfY2Odb8zVj40iPWy4zL4G6tJa5FH4lmlL5MVDn9wHBqq4pjQgfS0FWQrXFPHZAL6NRE_UTcz9RGshWPLATEXm-1RQx0A";


    @Test
    public void testTokenRoundTrip() throws Exception{
        byte[] encrypted = AsciiCrypt.encrypt(TOKEN);
        String newToken = AsciiCrypt.decrypt(encrypted);

        assertTrue("length should be the same.  Found "+ TOKEN.length()+" vs. "+newToken.length(), TOKEN.length() == newToken.length());
        assertTrue("tokens MUST be the same",TOKEN.equals(newToken));

    }
    @Test
    public void testSimpleRoundTrip() throws Exception{
        byte[] encrypted = AsciiCrypt.encrypt("Tom");
        String newTom = AsciiCrypt.decrypt(encrypted);

        assertTrue("length should be the same.  Found " + "Tom".length() + " vs. " + newTom.length(), "Tom".length() == newTom.length());
        assertTrue("Values MUST be the same","Tom".equals(newTom));

    }
    @Test
    public void testSimpleRoundTripWithCustomKey() throws Exception{
        byte[] encrypted = AsciiCrypt.encrypt("Tom","Password");
        String newTom = AsciiCrypt.decrypt(encrypted,"Password");

        assertTrue("length should be the same.  Found " + "Tom".length() + " vs. " + newTom.length(), "Tom".length() == newTom.length());
        assertTrue("Values MUST be the same","Tom".equals(newTom));

    }
    @Test
    public void testTokenRoundTripWithCustomKey() throws Exception{
        byte[] encrypted = AsciiCrypt.encrypt(TOKEN,"Password");
        String newToken = AsciiCrypt.decrypt(encrypted,"Password");

        assertTrue("length should be the same.  Found "+ TOKEN.length()+" vs. "+newToken.length(), TOKEN.length() == newToken.length());
        assertTrue("tokens MUST be the same",TOKEN.equals(newToken));

    }

}
