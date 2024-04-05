package com.qa.api.tests.GET;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class GetAPICall {
    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;

    @BeforeTest
    public void setup() {
        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
    }

    @Test
    public void getSpecificUserApiTest() {
        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setQueryParam("gender", "male")
                .setQueryParam("status", "active"));
        System.out.println(apiResponse.status());
        Assert.assertEquals(apiResponse.status(), 200);
        Assert.assertTrue(apiResponse.ok());
        String statusRestText = apiResponse.statusText();
        System.out.println(statusRestText);
        Assert.assertEquals(statusRestText, "OK");
        System.out.println("--------------Print API Response with plain text----------------");
        System.out.println(apiResponse.text());
        System.out.println("--------------Print API Response as json object using databind library----------------");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(apiResponse.body());
            ArrayNode jsonArray = (ArrayNode) jsonNode;
            System.out.println(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getUserApiTest() {

        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users");
        System.out.println(apiResponse.status());

        Assert.assertEquals(apiResponse.status(), 200);
        Assert.assertTrue(apiResponse.ok());
        String statusRestText = apiResponse.statusText();
        System.out.println(statusRestText);
        Assert.assertEquals(statusRestText, "OK");

        System.out.println("--------------Print API Response with plain text----------------");
        System.out.println(apiResponse.text());

        System.out.println("--------------Print API Response with byte array----------------");

        System.out.println(apiResponse.body()); // Returns a byte array
        //Use jackson databind library to convert byte array to json object

        System.out.println("--------------Print API Response as json object using databind library----------------");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(apiResponse.body());
            ArrayNode jsonArray = (ArrayNode) jsonNode;
            System.out.println(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Print the URL from the API response
        System.out.println(apiResponse.url());

        System.out.println("--------------Print Response Headers----------------");
        Map<String, String> headersMap = apiResponse.headers();
        System.out.println(headersMap);
        Assert.assertEquals(headersMap.get("content-type"), "application/json; charset=utf-8");
        Assert.assertEquals(headersMap.get("x-download-options"), "noopen");
    }

    @AfterTest
    public void tearDown() {
        playwright.close();
    }

}
