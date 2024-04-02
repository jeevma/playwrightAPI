package com.qa.ui.tests;

import com.microsoft.playwright.*;

public class BrowserContextConcept {

    public static void main(String[] args){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext brcx1 = browser.newContext();
        Page p1 = brcx1.newPage();
        p1.navigate("https://www.orangehrm.com/orangehrm-30-day-trial");




    }
}
