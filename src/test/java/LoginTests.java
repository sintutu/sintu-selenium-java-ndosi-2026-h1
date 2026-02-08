import pages.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends TestBase {
    @Test
    public void logIntoNdosiDevSite() throws InterruptedException {

        HomePage homePage = new HomePage();
        PracticePage practicePage = new PracticePage();
        DashboardPage dashboardPage = new DashboardPage();

        // Instantiate driver. But why in the test?
        driver = new ChromeDriver();
        driver.navigate().to(baseUri); // driver.get(baseUri) would work. What's navigate() for?
        driver.manage().window().maximize(); // what's the manage interface for?
        Assert.assertEquals(
                driver.findElement((homePage.heading)).getText(),
                "Master Test Automation");
        driver.findElement(homePage.loginButton).click();
        Assert.assertEquals(driver.getCurrentUrl(), baseUri + "#practice");
        driver.findElement(practicePage.loginEmail).sendKeys(practicePage.username);
        driver.findElement(practicePage.loginPassword).sendKeys(practicePage.password);
        driver.findElement(practicePage.loginButton).click();
        Thread.sleep(1000); // Added a wait. Didn't feel like adding a Wait.
        Assert.assertEquals(
                driver.findElement(dashboardPage.welcomeMessage).getText(),
                "Welcome back, sintutu! \uD83D\uDC4B");
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
* */