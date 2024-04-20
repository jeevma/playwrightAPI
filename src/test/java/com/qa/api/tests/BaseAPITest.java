package com.qa.api.tests;

import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import com.api.logger.Logger;


public class BaseAPITest {
    protected Playwright playwright;
    protected APIRequest request;

    protected APIRequestContext requestContext;

    @BeforeTest
    public void setUp() {
        Logger.info("Initializing test setup...");
        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
    }

    @AfterTest
    public void tearDown() {
        Logger.info("Tearing down test resources...");
        playwright.close();
    }
}