package com.qa.api.tests;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.HttpHeader;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;


public class APIResponseHeadersTest {

    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;
    @BeforeTest
    public void setup() {
        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
    }

    @AfterTest
    public void tearDown() {
        playwright.close();
    }

    @Test
    public void getHeadersTest() {
        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users");
        int statusCode = apiResponse.status();
        System.out.println("Response status code: " + statusCode);
        Assert.assertEquals(statusCode, 200);

        //Using Map
        Map<String, String> headers = apiResponse.headers();
        headers.forEach((k, v) -> System.out.println(k + " : " + v));
        System.out.println("Total number of headers in the response: " + headers.size());
        Assert.assertEquals(headers.size(), 29);
        Assert.assertEquals(headers.get("content-type"), "application/json; charset=utf-8");
        Assert.assertEquals(headers.get("server"), "cloudflare");

        System.out.println("===============================");
        //Using list

        List<HttpHeader> headersList = apiResponse.headersArray();
        for(HttpHeader e : headersList){
            System.out.println(e.name + " : " + e.value);
        }
        System.out.println("Total number of headers in the response: " + headersList.size());
        Assert.assertEquals(headersList.size(), 29);
        Assert.assertEquals(headersList.get(1).value, "application/json; charset=utf-8");
        //Assert.assertEquals(headersList.get(1).value, "nginx/1.14.1");

    }
}
