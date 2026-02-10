package tests;

import org.openqa.selenium.WebDriver;
import pages.*;
import facade.NdosiDev;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTests {
    private WebDriver driver;

    private final String baseUri = "https://ndosisimplifiedautomation.vercel.app/";

    @Test
    public void logIntoNdosiDevSite() {
        NdosiDev app = new NdosiDev();
        app.loginAsOrdinaryUser();
        app.seeDashboard();
    }
}

/*
* https://ndosisimplifiedautomation.vercel.app is confusing.
* Is it using pages?
* I'm organising my locators by pages
* Yet things look like /#overview, /#practice, /#dashboard
* Are these pages? Does the page object model apply?
*
* Asserts are hard to understand.
* I was typing in assert and I didn't see a problem in the IDE.
* I had to google to find testNG asserts until I found I should have used Assert.
*
* Is the driver environment a whole browser window? Am I spinning up a whole new, RAM-hungry, Chrome window?
* */