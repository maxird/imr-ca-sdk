package com.maximus.util

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}
import com.maximus.util.Properties._

/**
 * A singleton encapsulating AES encryption/decryption of ascii strings
 */
object AsciiEncryption {
  private val IV: String = "(j^K_Zh?-#Az0\"r7"
  private val KEY: String = "eB}Yq4-\"`@XtU2MP_:4lR@i)v0gl$x8S"
  private val md: MessageDigest = {
    MessageDigest.getInstance("SHA-256")
  }

  def encrypt(plainText: String): Array[Byte] = {
    encrypt(plainText, KEY)
  }

  def decrypt(encryptedByteArray: Array[Byte]): String = {
    decrypt(encryptedByteArray, KEY)
  }

  def encrypt(plainText: String, encodingKey: String): Array[Byte] = {
    val textMod16: Int = plainText.length % 16
    val textPadLength: Int = if (textMod16 == 0) 0 else 16 - textMod16
    val plainTextByteArray: Array[Byte] = java.util.Arrays.copyOf(plainText.getBytes, plainText.length + textPadLength)
    val encodingKeyByteArray: Array[Byte] = _normalizeKey(encodingKey)
    val cipher: Cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE")
    val key: SecretKeySpec = new SecretKeySpec(encodingKeyByteArray, "AES")
    cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")))
    cipher.doFinal(plainTextByteArray)
  }

  def decrypt(encryptedByteArray: Array[Byte], encodingKey: String): String = {
    val encodingKeyByteArray: Array[Byte] = _normalizeKey(encodingKey)
    val cipher: Cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE")
    val key: SecretKeySpec = new SecretKeySpec(encodingKeyByteArray, "AES")
    cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")))
    val retval = new String(cipher.doFinal(encryptedByteArray), "UTF-8")
    _trimNull(retval)
  }

  /**
   * AES keys can be 16, 24, or 32 bytes in length
   * @param key  a candidate key string
   * @return  a key of valid length for AES
   */
  private  def _normalizeKey(key: String): Array[Byte] = {
    val encodingKey: String = key + additionalFactors
    if (md != null) {
      try {
        md.update(key.getBytes("UTF-8"))
        return md.digest
      }
      catch {
        case e: UnsupportedEncodingException =>
        // yep, we can swallow this... it is handled in the if  block for key length
      }
    }
    if (key.length > 32) {
      key.substring(0, 31).getBytes
    }
    else if (key.length > 24) {
      key.substring(0, 23).getBytes
    }
    else if (key.length > 16) {
      key.substring(0, 15).getBytes
    }
    else {
      val keyMod16: Int = encodingKey.length % 16
      val keyPadLength: Int = if (keyMod16 == 0) 0 else 16 - keyMod16
      val encodingKeyByteArray: Array[Byte] = java.util.Arrays.copyOf(encodingKey.getBytes, encodingKey.length + keyPadLength)
      encodingKeyByteArray
    }
  }

  /**
   * eliminate null padding from end of string
   * @param in  the string with binary 0 padding
   * @return
   */
  private def _trimNull(in: String): String = {
    for (i <-  0 to in.length-1) {
      if (in.charAt(i) ==  '\0') {
        return in.substring(0, i)
      }
    }
    in
  }


}
