package com.qa.api.tests.POST;

import com.api.data.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

import com.api.logger.Logger;
import com.qa.api.tests.BaseAPITest;
import com.api.utils.CommonUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CreateUserPostCallWithPOJOTest extends BaseAPITest {

    String emailId = CommonUtility.getRandomEmail();
    @Test
    public void createUserTest() throws IOException {
        Logger.info("Starting createUserTest...");
        //Create the user object
        User user = new User("San Man", emailId, "male", "active");

        APIResponse apiPostResponse = requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization",
                                "Bearer 1be8fc8ed9524e2acf2a66988487ef29f3ade0246bb715bb290aa521af017f7e")
                        .setData(user));
        System.out.println(apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(), 201);
        Assert.assertEquals(apiPostResponse.statusText(), "Created");
        String responseText = apiPostResponse.text();
        System.out.println(responseText);

        //convert response text/json to POJO - Deserialization
        ObjectMapper objectMapper = new ObjectMapper();
        User actuser = objectMapper.readValue(responseText, User.class);


        System.out.println(actuser.getName());
        System.out.println(actuser.getEmail());
        //capture id from the post json response:
        String UserId = actuser.getId().toString();
        System.out.println(UserId);
        Assert.assertEquals(actuser.getName(), user.getName());
        Assert.assertEquals(actuser.getEmail(), user.getEmail());
        Assert.assertEquals(actuser.getStatus(), user.getStatus());



        //GET Call: fetch the same user by id:
        APIResponse apiGetResponse = requestContext.get("https://gorest.co.in/public/v2/users/" + UserId,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization",
                                "Bearer 1be8fc8ed9524e2acf2a66988487ef29f3ade0246bb715bb290aa521af017f7e"));
        Assert.assertEquals(apiGetResponse.status(), 200);
        Assert.assertEquals(apiGetResponse.statusText(), "OK");
        Assert.assertTrue(apiGetResponse.text().contains("San Man"));
        Assert.assertTrue(apiGetResponse.text().contains(UserId));
        Assert.assertTrue(apiGetResponse.text().contains(emailId));

    }
}