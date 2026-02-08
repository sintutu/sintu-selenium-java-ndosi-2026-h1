import pages.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTests extends TestBase {
    @Test
    public void logIntoNdosiDevSite() throws InterruptedException {

        // Making page objects inside my tests is gross.
        HomePage homePage = new HomePage();
        PracticePage practicePage = new PracticePage();
        DashboardPage dashboardPage = new DashboardPage();

        // Instantiate driver. But why in the test?
        driver = new ChromeDriver();
        // I get why the manage interface exists. It manages the driver environment.
        driver.manage().window().maximize();

        // I prefer navigate().to(baseUri) over driver.get(baseUri) because users don't think in terms of get requests
        // They don't think in terms of navigation either. But this at least establishes they can hit the back button.
        driver.navigate().to(baseUri);
        assertThat(driver.findElement((homePage.heading)).getText())
                .isEqualTo("Master Test Automation");
        driver.findElement(homePage.loginButton).click();
        assertThat(driver.getCurrentUrl())
                .isEqualTo(baseUri + "#practice");
        driver.findElement(practicePage.loginEmail).sendKeys(practicePage.username);
        driver.findElement(practicePage.loginPassword).sendKeys(practicePage.password);
        driver.findElement(practicePage.loginButton).click();
        Thread.sleep(1000); // Added a wait. Didn't feel like adding a Wait.
        assertThat(driver.findElement(dashboardPage.welcomeMessage).getText())
                .isEqualTo("Welcome back, sintutu! \uD83D\uDC4B");

        // I still need to quit that driver at the end of the test. I don't want that Chrome window hanging around.
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