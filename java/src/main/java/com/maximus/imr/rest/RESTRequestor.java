package com.maximus.imr.rest;

/**
 * /**
 * Created by tstockton on 6/24/16.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.maximus.util.AsciiCrypt;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;


/**
 * RESTRequestor is an abstract class containing the convenience methods for making  RESTful calls into IMR REST services
 */
public abstract class RESTRequestor {
    public static final String APP_JSON = "application/json";
    public static final String APP_TEXT = "application/text";
    public static final String TEXT_PLAIN = "text/plain";

    private Properties configProperties = new Properties();

    protected String keycloakUrl;
    private String tokenUrl;
    protected static Gson gson = new GsonBuilder().create();

    private String userName;
    private String password;
    private String realm;

    protected static final String URL_ENCODED = "application/x-www-form-urlencoded";
    protected static final String AUTH = "Authorization";
    protected static final String CONTENT_TYPE = "Content-Type";
    protected static final String ACCEPT = "Accept";
    protected static final String BEARER = "Bearer";

    private final int MILLISECONDS_IN_A_SECOND = 1000;

    private final int EXPIRATION_BIAS = (1000*30);    // Bias of 30 seconds.

    protected static final int[] OK = {200, 201, 204};
    private static Map<String, Object> tokenMap;
    private static final Object TOKEN_GATE = new Object();
    private static File tokenCache = null;

    static {
        tokenCache = new File(getHomeFolder()+"/." + RESTRequestor.class.getName() + ".token.json");
        if (tokenCache.exists()) {
            // read the json string from the file
            try {
                FileInputStream fis = new FileInputStream(tokenCache);
                if (fis.available() > 2048) {
                    byte[] bytes = new byte[fis.available()];
                    fis.read(bytes);
                    String jsonString = AsciiCrypt.decrypt(bytes);
                    tokenMap = gson.fromJson(jsonString, HashMap.class);
                }
            }
            catch (Exception e) {
                System.err.println("Unable to read & Decode token Cache at " + tokenCache.getAbsolutePath());
                tokenMap = null;
            }
        }
        else {
            try {
                if (!(tokenCache.createNewFile() && tokenCache.canWrite())) {
                    throw new IOException("Unable to create writeable file ");
                }
            }
            catch (IOException e) {
                tokenCache = null;
            }
        }
        ;
    }
    public final String version () {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("config/version.txt");
        byte buffer[] = new byte[1024];
        String retval = null;
        try {
            int bytesRead = is.read(buffer);
            retval = new String(buffer,0,bytesRead);
        }
        catch (IOException e) {
            retval = "Unknown Version";
        }
        return retval;

    }

    /**
     * construct using configroot.  -Dcom.maximus.ird.configroot=/pathname/to/configuration directory containing api.properties
     * file and config.properties file
     *
     * @param userName credentials - username
     * @param password credentials - password
     */
    public RESTRequestor(String userName, String password) {
        String configRoot = System.getProperty("com.maximus.ird.configroot", "./config");
        try {
            configProperties.load(getStream(configRoot + "/config.properties"));

        }
        catch (IOException e) {
            throw new IllegalStateException("Cannot load configuration properties", e);
        }
        this.userName = userName;
        this.password = password;

        realm = configProperties.getProperty("authserver.realm", "dxc-services");
        keycloakUrl = configProperties.getProperty("authserver.base.url");
        if (keycloakUrl == null || keycloakUrl.length() < 20) {
            throw new IllegalStateException("keycloak url is bad or missing (" + keycloakUrl + ")");
        }
        tokenUrl = getTokenURL(realm);
    }

    /**
     * builds a url from a list of Strings
     *
     * @param root         base of url
     * @param pathElements list of the path entries
     * @return "http://keycloak:port/auth/a/url/from/the/path/elements/:
     */
    protected String urlBuilder(String root, String... pathElements) {
        String path = "";
        for (String pathElement : pathElements) {
            if (pathElement.startsWith("/")) {
                pathElement = pathElement.substring(1);
            }
            if (pathElement.endsWith("/")) {
                pathElement = pathElement.substring(0, pathElement.length() - 1);

            }
            path += "/" + pathElement;
        }
        return root + path;
    }

    /**
     * builds a url from a list of Strings
     *
     * @param root           base of url
     * @param queryArguments a LinkedHashMap (ordered) of keys and their values
     * @param pathElements   list of the path entries
     * @return "http://keycloak:port/auth/a/url/from/the/path/elements/?arg1=val&arg2=val
     */
    protected String urlBuilder(String root, LinkedHashMap<String, String> queryArguments, String... pathElements) {
        String url = urlBuilder(root, pathElements);
        StringBuilder args = new StringBuilder();
        for (String paramName : queryArguments.keySet()) {
            args.append('&').append(paramName).append("=").append(queryArguments.get(paramName));
        }
        if (args.length() > 0) {
            args.replace(0, 1, "?");
        }
        return url + args.toString();
    }

    protected String probeForMimeType(File file) {
        try {
            Path source = Paths.get(file.getCanonicalPath());
            return Files.probeContentType(source);
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Unable to determine mime-type");
        }
    }

    /**
     * get the token
     *
     * @return the access token
     */
    protected String getToken() {
        if (tokenMap == null) {
            MultivaluedMap getTokenFormData = new MultivaluedMapImpl();
            getTokenFormData.add("username", userName);
            getTokenFormData.add("password", password);
            getTokenFormData.add("grant_type", "password");
            getTokenFormData.add("client_id", "dxc");

            Client client = Client.create();

            WebResource webResource = client.resource(tokenUrl);
            synchronized (TOKEN_GATE) {
                if (tokenMap == null) {

                    ClientResponse response = null;
                    response = webResource
                            .header("Content-Type", URL_ENCODED)
                            .header("Accept", APP_JSON)
                            .post(ClientResponse.class, getTokenFormData);

                    checkStatus(response.getStatus());
                    tokenMap = decorateTokenMapWithExpiration(getResponseMap(response));
                    _persistTokenCache();
                }
            }
        }
        else if (tokenHasExpired(tokenMap)) {
            return refreshToken();
        }
        return (String) tokenMap.get("access_token");

    }

    private ClientResponse _getToken() {
        MultivaluedMap getTokenFormData = new MultivaluedMapImpl();
        getTokenFormData.add("username", userName);
        getTokenFormData.add("password", password);
        getTokenFormData.add("grant_type", "password");
        getTokenFormData.add("client_id", "dxc");

        Client client = Client.create();

        WebResource webResource = client.resource(tokenUrl);
        ClientResponse response = null;
        response = webResource
                .header("Content-Type", URL_ENCODED)
                .header("Accept", APP_JSON)
                .post(ClientResponse.class, getTokenFormData);
        return response;

    }

    private void _persistTokenCache() {
        if (tokenCache != null) {
            try (FileOutputStream fos = new FileOutputStream(tokenCache)) {
                byte[] encryptedJsonBytes = AsciiCrypt.encrypt(gson.toJson(tokenMap));
                fos.write(encryptedJsonBytes);
                fos.flush();
                fos.close();
            }
            catch (Exception e) {
                // if this happens, you have not persisted your json map.  It is a very real resource leak as
                // you  will constantly create new tokens....
                tokenCache = null;
            }
        }

    }

    protected String refreshToken() {
        // if the refresh has expired, you cannot refresh  ... need to get the token
        if (refreshHasExpired(tokenMap)) {
            ClientResponse response = _getToken();
            checkStatus(response);
            synchronized (TOKEN_GATE) {
                tokenMap = decorateTokenMapWithExpiration(getResponseMap(response));
                _persistTokenCache();
            }

        }
        else {
            String refreshToken = (String) tokenMap.get("refresh_token");
            MultivaluedMap refreshTokenFormData = new MultivaluedMapImpl();
            refreshTokenFormData.add("client_id", "dxc");
            refreshTokenFormData.add("grant_type", "refresh_token");
            refreshTokenFormData.add("refresh_token", refreshToken);
            Client client = Client.create();
            synchronized (TOKEN_GATE) {

                WebResource webResource = client.resource(tokenUrl);

                ClientResponse response = webResource
                        .header("Content-Type", URL_ENCODED)
                        .header("Accept", APP_JSON)
                        .post(ClientResponse.class, refreshTokenFormData);

                checkStatus(response.getStatus());
                tokenMap = decorateTokenMapWithExpiration(getResponseMap(response));
                _persistTokenCache();
            }
        }
        return (String) tokenMap.get("access_token");

    }

    private void expireTokens() {
        // avoid null pointer exceptions when password is bad
        if (tokenMap == null) {
            return;
        }

        String token = (String) tokenMap.get("access_token");

        synchronized (TOKEN_GATE) {
            // if the token has changed, there is no reason to expire
            if (token.equals(tokenMap.get("access_token"))) {
                tokenMap.put("ird.token.expire.time", 0L);
                tokenMap.put("ird.refresh.expire.time", 0L);
            }
            _persistTokenCache();

        }
    }

    private InputStream getStream(String pathname) {
        InputStream retval = null;
        File file = new File(pathname);
        if (file.exists() && file.canRead()) {
            try {
                retval = new FileInputStream(file);
            }
            catch (FileNotFoundException e) {
                throw new IllegalStateException(pathname + " not found");
            }
        }
        else {
            throw new IllegalStateException(pathname + " does not exist or is not readable");
        }

        return retval;
    }

    private boolean tokenHasExpired(Map<String, Object> tokenMap) {
        Object o = tokenMap.get("ird.token.expire.time");
        long expiresTime = 0;
        if (o instanceof Long) {
            expiresTime = (Long) o;
        }
        else {
            expiresTime = ((Double) o).longValue();
        }
        long now = new Date().getTime();
        return now > expiresTime;
    }

    private boolean refreshHasExpired(Map<String, Object> tokenMap) {
        Object o = tokenMap.get("ird.refresh.expire.time");
        long expiresTime = 0;
        if (o instanceof Long) {
            expiresTime = (Long) o;
        }
        else {
            expiresTime = ((Double) o).longValue();
        }
        long now = new Date().getTime();
        return now > expiresTime;
    }

    /**
     * decorates the tokenMap with the expire time
     *
     * @param tokenMap the return from the restful credential challenge
     * @return the tokenMap with a newvalues:  ird.expires and refresh expires ;
     */
    private Map<String, Object> decorateTokenMapWithExpiration(Map<String, Object> tokenMap) {
        Double tokenExpiresIn = (Double) tokenMap.get("expires_in");
        Double refreshExpiresIn = (Double) tokenMap.get("refresh_expires_in");
        long timeNow = new Date().getTime();
        Long tokenExpireTime = timeNow + (tokenExpiresIn.longValue() * MILLISECONDS_IN_A_SECOND) - EXPIRATION_BIAS;
        Long refreshExpireTime = timeNow + (refreshExpiresIn.longValue() * MILLISECONDS_IN_A_SECOND) - EXPIRATION_BIAS;
        tokenMap.put("ird.token.expire.time", tokenExpireTime);
        tokenMap.put("ird.refresh.expire.time", refreshExpireTime);
        return tokenMap;
    }

    private String getTokenURL(String realm) {
        return urlBuilder(keycloakUrl, "realms", realm, "protocol/openid-connect/token");
    }


    protected Map<String, Object> getResponseMap(ClientResponse response) {
        String output = response.getEntity(String.class);
        TypeAdapter<HashMap> adapter = gson.getAdapter(HashMap.class);
        try {
            return adapter.fromJson(output);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to convert response ");
        }

    }


    protected String bearerAuth() {
        return BEARER + " " + getToken();
    }

    protected void checkStatus(int httpStatus) {
        for (int okStatus : OK) {
            if (httpStatus == okStatus) {
                return;
            }
        }
        // ensure you don't continue operationg with an expired/corrupt token
        expireTokens();
        throw new RuntimeException("Failed : HTTP error code : "
                                           + httpStatus);

    }

    /**
     * Provides a simple (and insecure) data store for the refresh
     *    token. Since it is cross-platform we cannot make use of the
     *    DPAPI services - if you are Windows use DPAPI. If you are not
     *    use your approved best practices for on-server secret storage.
     *    The store is created in your user home folder in a sub-folder
     *    called <code>.keycloak-ird</code>. An entry is created for each
     *    combination of auth server and username.
     *    </summary>
     *  
     */
    protected static String getHomeFolder() {
        String unixFolder = System.getenv("HOME");
        String winFolder = MessageFormat.format("{0}{1}", System.getenv("HOMEDRIVE"), System.getenv("HOMEPATH"));

        return ((unixFolder != null) && (unixFolder.length() > 0)) ? unixFolder : winFolder;
    }

    protected void checkStatus(ClientResponse response) {
        checkStatus(response.getStatus());

    }

    protected String getConfigProperty(String key) {
        String retval = configProperties.getProperty(key);
        return retval;
    }

    public enum RETURN_TYPE {JSON, CSV}

    ;
}

