package com.qa.api.tests.POST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.HashMap;
import java.util.Map;

public class CreateUserPostCallTest {
    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;

    static  String emailId;


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

    public static String getRandomEmail() {
        emailId = "testplaywrightautomation" + System.currentTimeMillis() + "@gmail.com";
        return emailId;
    }
    @Test
    public void createUserTest() throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "San Man");
        data.put("email", getRandomEmail());
        data.put("gender", "male");
        data.put("status", "active");
        APIResponse apiPostResponse = requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization",
                                "Bearer 1be8fc8ed9524e2acf2a66988487ef29f3ade0246bb715bb290aa521af017f7e")
                        .setData(data));
        System.out.println(apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(), 201);
        Assert.assertEquals(apiPostResponse.statusText(), "Created");
        System.out.println(apiPostResponse.text());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode postJsonResponse = objectMapper.readTree(apiPostResponse.body());
        System.out.println(postJsonResponse.toPrettyString());
        //capture id from the post json response:
        String UserId = postJsonResponse.get("id").toString();
        System.out.println(UserId);
        //Assert.assertEquals(id, "1103");

    //GET Call: fetch the same user by id:
     APIResponse apiGetResponse = requestContext.get("https://gorest.co.in/public/v2/users/" + UserId,
             RequestOptions.create()
                     .setHeader("Content-Type", "application/json")
                     .setHeader("Authorization",
                             "Bearer 1be8fc8ed9524e2acf2a66988487ef29f3ade0246bb715bb290aa521af017f7e"));
     System.out.println(apiGetResponse.status());
     Assert.assertEquals(apiGetResponse.status(), 200);
     Assert.assertEquals(apiGetResponse.statusText(), "OK");
     System.out.println(apiGetResponse.text());
     Assert.assertTrue(apiGetResponse.text().contains("San Man"));
     Assert.assertTrue(apiGetResponse.text().contains(UserId));
     Assert.assertTrue(apiGetResponse.text().contains(emailId));

    }
}