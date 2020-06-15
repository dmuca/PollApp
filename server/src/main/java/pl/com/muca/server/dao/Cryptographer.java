package pl.com.muca.server.dao;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Cryptographer {
  private static Cryptographer instance = null;
  private Cipher cipher;
  private SecretKey secretKey;

  private Cryptographer() {
    try {
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(128);
      secretKey = keyGenerator.generateKey();
      cipher = Cipher.getInstance("AES");
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      throw new Error("Unable to generate cipher key.");
    }
  }

  public static Cryptographer getInstance() {
    if (Cryptographer.instance == null){
      Cryptographer.instance = new Cryptographer();
    }
    return Cryptographer.instance;
  }

  public String encrypt(int userId) throws Exception {
    return encrypt(String.valueOf(userId));
  }

  public String encrypt(String plainText)
      throws Exception {
    byte[] plainTextByte = plainText.getBytes();
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encryptedByte = cipher.doFinal(plainTextByte);
    Base64.Encoder encoder = Base64.getEncoder();
    return encoder.encodeToString(encryptedByte);
  }
}
