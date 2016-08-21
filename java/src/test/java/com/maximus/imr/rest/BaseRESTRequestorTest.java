package com.maximus.imr.rest;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by tstockton on 8/9/16.
 */
public class BaseRESTRequestorTest {
    public static final String NO_MATCHING_DATA = "NO_MATCHING_DATA";

    protected static String userName = null;
    protected static String userPassword = null;
    protected static String dataDir = null;
    protected static String zipFileName = null;
    protected static String pdfFileName = null;
    protected static String textFileName = null;
    protected RESTRequestor rr;

    @BeforeClass
    public static void bootstrap() {
        String configRoot = System.getProperty("com.maximus.ird.configroot", "./config");

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(System.getProperty("com.maximus.ird.property.password", "ChangingThisWillRequireYouStartWithClearTextProperties"));
        Properties testProperties = new Properties();

        File propertiesFile = getFile(configRoot + "/test.properties");
        boolean persist = false;
        try (InputStream propertyInputSteam = new FileInputStream(propertiesFile)) {

            testProperties.load(propertyInputSteam);
            userName = (String) testProperties.get("rest.user.name");
            if (userName == null || userName.length() == 0) {
                throw new IllegalStateException("rest.user.name is not present in test.properties.\nSee src/test/resources/sample.test.properties.");

            }
            else {
                if (PropertyValueEncryptionUtils.isEncryptedValue(userName))
                    userName = PropertyValueEncryptionUtils.decrypt(userName,encryptor);
                else {
                    String val = PropertyValueEncryptionUtils.encrypt(userName, encryptor);
                    testProperties.setProperty("rest.user.name", val);
                    persist = true;
                }
            }
            userPassword = (String) testProperties.get("rest.user.pass");
            if (userPassword == null || userPassword.length() == 0) {
                throw new IllegalStateException("rest.user.pass is not present in test.properties.\nSee src/test/resources/sample.test.properties.");

            }
            else {
                if (PropertyValueEncryptionUtils.isEncryptedValue(userPassword))
                    userPassword = PropertyValueEncryptionUtils.decrypt(userPassword, encryptor);
                else {
                    testProperties.setProperty("rest.user.pass", "ENC(" + encryptor.encrypt(userPassword) + ")");
                    persist = true;
                }
            }
            dataDir = (String) testProperties.get("test.data.dir");
            if (dataDir == null || dataDir.length() == 0) {
                throw new IllegalStateException("test.data.dir is not present in test.properties.\nSee src/test/resources/sample.test.properties.");

            }
            pdfFileName = (String) testProperties.get("pdf.file.name");
            if (pdfFileName == null || pdfFileName.length() == 0) {
                throw new IllegalStateException("pdf.file.name is not present in test.properties.\nSee src/test/resources/sample.test.properties.");

            }
            textFileName = (String) testProperties.get("text.file.name");
            if (textFileName == null || textFileName.length() == 0) {
                throw new IllegalStateException("text.file.name is not present in test.properties.\nSee src/test/resources/sample.test.properties.");

            }
            zipFileName = (String) testProperties.get("zip.file.name");
            if (zipFileName == null || zipFileName.length() == 0) {
                throw new IllegalStateException("zip.file.name is not present in test.properties.\nSee src/test/resources/sample.test.properties.");

            }
            propertyInputSteam.close();
            if (persist) {
                try (FileOutputStream fos = new FileOutputStream(propertiesFile)) {
                    testProperties.store(fos,"Credentials Redacted");
                    fos.close();
                }
            }

        }

        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("unable to load test.properties");
        }
    }

    private static File getFile(String pathname) {
        File file = new File(pathname);
        if (file.exists() && file.canRead()) {
            return file;
        }
        else {
            throw new IllegalStateException(pathname + " does not exist or is not readable");
        }

    }


    @Before
    public void setup() {
        rr = new RESTRequestor(BaseRESTRequestorTest.userName, BaseRESTRequestorTest.userPassword) {

        };

    }

    @After
    public void tearDown() {

    }
}
