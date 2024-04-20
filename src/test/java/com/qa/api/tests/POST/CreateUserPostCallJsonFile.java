        package com.qa.api.tests.POST;

        import com.fasterxml.jackson.databind.JsonNode;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.microsoft.playwright.APIResponse;
        import com.microsoft.playwright.options.RequestOptions;
        import com.qa.api.tests.BaseAPITest;
        import com.api.utils.TestDataReader;
        import org.testng.Assert;

        import org.testng.annotations.Test;

        import java.io.IOException;
        import java.util.HashMap;
        import java.util.Map;
        import com.api.logger.Logger;

        public class CreateUserPostCallJsonFile extends BaseAPITest {

    //String emailId = CommonUtility.getRandomEmail();
    TestDataReader testDataReader = new TestDataReader();
    Map<String, Object> data = new HashMap<>();
    @Test
    public void createUserTest() throws IOException {
        Logger.info("Starting createUserTest...");
        data = testDataReader.readTestDataFromJSON("./src/test/data/user.json");
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
        //testDataReader.readValueFromJSON("./src/test/data/user.json", "email", "sanman1@tata.com");
        String emailId = testDataReader.readTestDataFromJSON("./src/test/data/user.json").get("email").toString();
        System.out.println(emailId);

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