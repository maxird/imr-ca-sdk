package com.maximus.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.PropertyValueEncryptionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;


/**
 * Encapsulates java.util.Properties.  Encrypts properties starting with 'encrypted'.  Warning!  changing the password will
 * render encrypted properties unreadable.
 * properites are assumed to exist under ./config unless overridden with -Dcom.maximus.ird.configroot=<path to directory></path>
 * jasypt library is used to encrypt properties beginning with "encrypt."
 */
public class JavaPropertyFile {
    private static String configRoot = System.getProperty("com.maximus.ird.configroot", "./config");
    // password incorporates additionalFactors to ensure that the encrypted file is not portable.
    private static String password =   System.getProperty("com.maximus.ird.property.password",
                                                       "ChangingThisWillRequireYouStartWithClearTextProperties")+additionalFactors();

    private java.util.Properties properties = new java.util.Properties();
    private HashMap<String, String> propertyMap = new HashMap<String,String>();

    private StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();


    /**
     * constructor
     * @param fileName  the file name to initialize on (assumed to be in ./config or directory pointed to with -Dcom.maximus.ird.configroot
     */
    public JavaPropertyFile(String fileName)  {
        encryptor.setPassword(password);
        File propertiesFile = _getFile(configRoot + "/" + fileName);
         propertyMap = _initPropertiesMap(propertiesFile);

    }

    /**
     * get a property value
     * @param key  the property identifier
     * @return  the property value (or default)
     */
    public String getValue(String key){
        return propertyMap.get(key);
    }

    /**
     * get a property value
     * @param key  the property identifier
     * @param defaultValue  a value to be returned if no value is found
     * @return  the property value (or default)
     */
    public String getValue(String key, String defaultValue){
        return propertyMap.getOrDefault(key, defaultValue);
    }
    private  HashMap<String, String> _initPropertiesMap(File propertiesFile) {

        boolean persist = false;
        java.util.HashMap<String, String> pMap = new HashMap<String, String>();
        try (InputStream propertyInputSteam = new FileInputStream(propertiesFile)) {
            properties.load(propertyInputSteam);
            Enumeration propertyEnumeration = properties.propertyNames();
            while (propertyEnumeration.hasMoreElements()) {
                String key = (String) propertyEnumeration.nextElement();
                String value = properties.getProperty(key);
                if (key.startsWith("encrypt.")) {
                    if (PropertyValueEncryptionUtils.isEncryptedValue(value)) {
                        value = PropertyValueEncryptionUtils.decrypt(value, encryptor);
                    }
                    else {
                        String encryptedValue = PropertyValueEncryptionUtils.encrypt(value, encryptor);
                        properties.setProperty(key, encryptedValue);
                        persist = true;
                    }
                }
                pMap.put(key, value);

            }
            if (persist){
                try (FileOutputStream fos = new FileOutputStream(propertiesFile)) {
                    properties.store(fos,"Credentials Redacted");
                    fos.close();
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("unable to load test.properties");
        }
        return   pMap;
    }
    private  File _getFile(String pathname) {
        File file = new File(pathname);
        if (file.exists() && file.canRead()) {
            return file;
        }
        else {
            throw new IllegalStateException(pathname + " does not exist or is not readable");
        }

    }
    /**
     * Generates a string that will be unique to a user+machine.
     * Handy for key generation!
     * @return
     */
    public static String  additionalFactors() {
        String computerName = System.getenv("COMPUTERNAME");
        String hostname = System.getenv("HOSTNAME") ;
        String  usrname = System.getenv("USERNAME");
        if  (hostname != null)
           return  usrname + hostname;
       return usrname + computerName ;
    }

}
