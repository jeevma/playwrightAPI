package com.qa.api.tests;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class APIDisposeTest {

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
    public void disposeResponseTest() {
        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users");
        int statusCode = apiResponse.status();
        System.out.println("Response status code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
        Assert.assertTrue(apiResponse.ok());
        String statusRestText = apiResponse.statusText();
        System.out.println(statusRestText);
        Assert.assertEquals(statusRestText, "OK");

        System.out.println("--------------Print API Response with plain text----------------");
        System.out.println(apiResponse.text());

        apiResponse.dispose();

        System.out.println("--------------Print API Response after diapose with plain text----------------");
        try {
            System.out.println(apiResponse.text());
        } catch (PlaywrightException e) {
            System.out.println(e.getMessage());
        }

        int statusCodeAfterDispose = apiResponse.status();
        System.out.println("Response status code: " + statusCodeAfterDispose);
        String statusRestTextAfterDispose = apiResponse.statusText();
        System.out.println(statusRestTextAfterDispose);
    }

    @AfterTest
    public void tearDown() {
        playwright.close();
    }
}
