package com.qa.api.tests.POST;

import com.api.data.User;
import com.api.data.Users;
import com.api.logger.Logger;
import com.api.utils.CommonUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.api.tests.BaseAPITest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class FetchToken extends BaseAPITest {

    @Test
    public void getTokenTest() throws IOException {
        Logger.info("Starting FetchToken...");

        //String json:
        String reqTokenJsonBody = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";


        System.out.println("=================================Post request=============================================");
        APIResponse apiPostResponse = requestContext.post("https://restful-booker.herokuapp.com/auth",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(reqTokenJsonBody));

        System.out.println(apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(), 200);
        Assert.assertEquals(apiPostResponse.statusText(), "OK");
        System.out.println(apiPostResponse.text());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode postJsonResponse = objectMapper.readTree(apiPostResponse.body());
        System.out.println(postJsonResponse.toPrettyString());
        //capture token from the post json response:
        String tokenID = postJsonResponse.get("token").asText();
        System.out.println("token: " + tokenID);

        Assert.assertNotNull(tokenID);

    }
}