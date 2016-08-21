package com.maximus.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * Created by tstockton on 8/12/16.
 * Valid AES keys are 16, 24 or 32 byte.  This class works with 32 byte keys
 */
public class AsciiCrypt {
    private static String IV = "(j^K_Zh?-#Az0\"r7";
    private static final String KEY = "eB}Yq4-\"`@XtU2MP_:4lR@i)v0gl$x8S";
    static MessageDigest md = null;
    static {
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            md = null;
        }
    }

    public static byte[] encrypt(String plainText) throws Exception {
        return encrypt(plainText, KEY);
    }

    public static String decrypt(byte[] encryptedByteArray) throws Exception{

        return decrypt(encryptedByteArray, KEY);
    }
    public static byte[] encrypt(String plainText, String encodingKey) throws Exception {
        int textMod16 = plainText.length() %16;
        int textPadLength = (textMod16 == 0)? 0: 16-textMod16;

        byte[] plainTextByteArray = Arrays.copyOf(plainText.getBytes(), plainText.length()+textPadLength) ;
        byte[] encodingKeyByteArray = normalizeKey(encodingKey);

        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encodingKeyByteArray, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(plainTextByteArray);
    }

    public static String decrypt(byte[] encryptedByteArray, String encodingKey) throws Exception{
        byte[] encodingKeyByteArray = normalizeKey(encodingKey);

        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encodingKeyByteArray, "AES");
        cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        String retval = new  String(cipher.doFinal(encryptedByteArray), "UTF-8");
        return trimNull(retval);
    }
    protected static  byte[] normalizeKey(String key){
        String encodingKey = key + getAdditionalFactors();
        if (md != null){
            try {
                md.update(key.getBytes("UTF-8"));
                return md.digest();
            }
            catch (UnsupportedEncodingException e) {
                // yep, we will swallow this... it is handled in the if  block for key length
            }

        }

        if (key.length() > 32){
            return key.substring(0,31).getBytes();
        }
        else if (key.length() > 24){
            return key.substring(0,23).getBytes();

        } else if (key.length() > 16){
            return key.substring(0,15).getBytes();

        }  else {
            int keyMod16 = encodingKey.length() %16;
            int keyPadLength = (keyMod16 == 0)? 0: 16-keyMod16;
            byte[] encodingKeyByteArray = Arrays.copyOf(encodingKey.getBytes(), encodingKey.length()+keyPadLength);
            return encodingKeyByteArray;
        }
    }

    /**
     * eliminate null padding from end of string
     * @param in
     * @return
     */
    public static String trimNull(String in){
        for (int i=0; i < in.length();i++){
            if (in.charAt(i) == '\0') {
                return in.substring(0,i);
            }

        }
        return in;
    }
    private static String getAdditionalFactors() {
        String computerName = System.getenv("COMPUTERNAME");
        String hostname = System.getenv("HOSTNAME");
        String factors = MessageFormat.format("{0}{1}", System.getenv("USERNAME"), hostname == null?computerName:hostname);
        return factors;
    }

}
