package pl.com.muca.server.encryption;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Cryptographer {
  public static String encrypt(String key, int userId) throws Exception {
    return encrypt(key, String.valueOf(userId));
  }

  public static String encrypt(String key, String textToEncrypt) throws Exception {
    try {
      String customKey =
          Objects.requireNonNull(
              Stream.generate(() -> key)
                  .limit((32 / key.length()) + 1)
                  .reduce(String::concat)
                  .orElse(null))
              .substring(0, 32);
      SecretKey secretKey =
          new SecretKeySpec(customKey.getBytes(), 0, customKey.getBytes().length, "AES");
      String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
      Cipher cipher = Cipher.getInstance("AES");
      byte[] plainTextByte = textToEncrypt.getBytes();
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      byte[] encryptedByte = cipher.doFinal(plainTextByte);
      Base64.Encoder encoder = Base64.getEncoder();
      return encoder.encodeToString(encryptedByte);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      throw new Error("Unable to generate cipher key.");
    }
  }
}
