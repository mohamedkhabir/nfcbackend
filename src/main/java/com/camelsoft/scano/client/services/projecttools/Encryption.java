package com.camelsoft.scano.client.services.projecttools;

import com.camelsoft.scano.client.Controller.Public.PublicAPI;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class Encryption {

    @Value("${Encryption.password}")
    private String password;
    @Value("${Encryption.salt}")
    private String salt;
    private SecureRandom random = new SecureRandom();
    private String ALGORITHM = "AES/CBC/PKCS5Padding";
    private String KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
    private int KEY_LENGTH = 256;
    private int ITERATIONS = 65536;

    private SecretKey regenerateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, KEY_LENGTH);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    public IvParameterSpec generateIv() {
        byte[] iv = {
                (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78,
                (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF,
                (byte) 0xFE, (byte) 0xDC, (byte) 0xBA, (byte) 0x98,
                (byte) 0x76, (byte) 0x54, (byte) 0x32, (byte) 0x10
        };

        return new IvParameterSpec(iv);
    }

//    public String encryptUrlSafe(String input) throws Exception {
//        Cipher cipher = Cipher.getInstance(ALGORITHM);
//        cipher.init(Cipher.ENCRYPT_MODE, regenerateKey(), generateIv());
//
//        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//            outputStream.write(generateIv().getIV());
//            byte[] encryptedBytes = cipher.doFinal(input.getBytes());
//
//            // Use URL-safe Base64 encoding
//            String encodedEncryptedData = Base64.getUrlEncoder().withoutPadding().encodeToString(encryptedBytes);
//
//            return encodedEncryptedData;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public String decrypt(String input) throws Exception {
//        byte[] inputBytes = Base64.getUrlDecoder().decode(input);
//        if (inputBytes.length < 16) {
//            throw new IllegalArgumentException("Invalid input length: IV buffer too short");
//        }
//        Cipher cipher = Cipher.getInstance(ALGORITHM);
//        cipher.init(Cipher.DECRYPT_MODE, regenerateKey(), generateIv());
//        byte[] plaintext = cipher.doFinal(inputBytes, 16, inputBytes.length - 16);
//        return new String(plaintext);
//    }

    public boolean isValidBase64(String input) {
        try {
            Base64.getUrlDecoder().decode(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
