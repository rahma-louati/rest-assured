package com.rest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Encoding {

    public static void main (String[] args){
         String udernameColonPassword = "myusername:password123";

         String base64Encoded = Base64.getEncoder().encodeToString(udernameColonPassword.getBytes());
         System.out.println("Encoder = " + base64Encoded);
         byte[] decodedBytes = Base64.getDecoder().decode(base64Encoded);
        System.out.println("Decoded = " + new String((decodedBytes)));

    }
}
