package pl.com.muca.server.encryption;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encrypts user IDs, to ensure anonymity for users.
 * This prevents users from being associated with answers.
 */
public class Cryptographer {
  /**
   * Overloaded method of {@link pl.com.muca.server.encryption.Cryptographer#encrypt(String, String)}.
   * @param key
   * @param userId
   * @return
   * @throws Exception
   */
  public static String encrypt(String key, int userId) throws Exception {
    return encrypt(key, String.valueOf(userId));
  }

  /**
   * Encrypts a given {@link String} according to the given key.
   * The method uses symmetric key for encryption algorithm available as
   * part of the Sun's JCE(Java Cryptography Extension).
   * @param key
   * @param textToEncrypt
   * @return encryptedText
   * @throws Exception
   */
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
