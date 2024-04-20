package com.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtility {
    static String emailId;
   // responseToPojo jsonResponseToPojo;

    public static String getRandomEmail() {
        emailId = "testplaywrightautomation" + System.currentTimeMillis() + "@gmail.com";
        return emailId;
    }

    /*
    public static jsonResponseToPojo(String responseText) {
        ObjectMapper objectMapper = new ObjectMapper();
        responseToPojo jsonResponseToPojo = objectMapper.readValue(responseText, jsonResponseToPojo.class);
        return jsonResponseToPojo;
    }

     */

}
