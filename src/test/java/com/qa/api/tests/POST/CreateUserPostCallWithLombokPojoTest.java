package com.qa.api.tests.POST;

import com.api.data.User;
import com.api.data.Users;
import com.api.logger.Logger;
import com.api.utils.CommonUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.api.tests.BaseAPITest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CreateUserPostCallWithLombokPojoTest extends BaseAPITest {

    String emailId = CommonUtility.getRandomEmail();

    @Test
    public void createUserTest() throws IOException {
        Logger.info("Starting createUserTest...");

        //Create the user object using the builder pattern
        Logger.info("Created user object using builder pattern...");
        Users users = Users.builder()
                .name("sachin")
                .email(emailId)
                .status("active")
                .gender("male").build();

        Logger.info("Post request...");

        APIResponse apiPostResponse = requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization",
                                "Bearer 1be8fc8ed9524e2acf2a66988487ef29f3ade0246bb715bb290aa521af017f7e")
                        .setData(users));
        Logger.info("Print response and assertions...");
        System.out.println(apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(), 201);
        Assert.assertEquals(apiPostResponse.statusText(), "Created");
        String responseText = apiPostResponse.text();
        System.out.println(responseText);

        Logger.info("convert response text/json to POJO - Deserialization...");
        //convert response text/json to POJO - Deserialization
        ObjectMapper objectMapper = new ObjectMapper();
        User actuser = objectMapper.readValue(responseText, User.class);

        Logger.info("response pojo assertions...");
        System.out.println(actuser.getName());
        System.out.println(actuser.getEmail());
        //capture id from the post json response:
        String UserId = actuser.getId();
        System.out.println(UserId);
        Assert.assertEquals(actuser.getName(), users.getName());
        Assert.assertEquals(actuser.getEmail(), users.getEmail());
        Assert.assertEquals(actuser.getStatus(), users.getStatus());

        Logger.info("GET Call: fetch the same user by id capturd from pojo response object...");
        //GET Call: fetch the same user by id:
        APIResponse apiGetResponse = requestContext.get("https://gorest.co.in/public/v2/users/" + UserId,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization",
                                "Bearer 1be8fc8ed9524e2acf2a66988487ef29f3ade0246bb715bb290aa521af017f7e"));

        Logger.info("Assertions for GET Call...");
        Assert.assertEquals(apiGetResponse.status(), 200);
        Assert.assertEquals(apiGetResponse.statusText(), "OK");
        Assert.assertTrue(apiGetResponse.text().contains("sachin"));
        Assert.assertTrue(apiGetResponse.text().contains(UserId));
        Assert.assertTrue(apiGetResponse.text().contains(emailId));

    }
}