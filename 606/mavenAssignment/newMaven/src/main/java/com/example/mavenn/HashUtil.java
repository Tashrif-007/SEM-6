package com.example.mavenn;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static String hash(String str) {
        try {
            MessageDigest digest  = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(str.getBytes());

            StringBuilder hexString = new StringBuilder();
            for(byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length()==1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(NoSuchAlgorithmException ex) {
            throw new RuntimeException("Hashing algo not available", ex);
        }
    }
}
