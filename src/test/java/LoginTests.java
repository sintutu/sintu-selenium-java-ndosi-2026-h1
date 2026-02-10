import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.*;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTests extends TestBase {
    @Test
    public void logIntoNdosiDevSite() throws InterruptedException {

        // Why must my test know how to make page objects?
        HomePage homePage = new HomePage();
        PracticePage practicePage = new PracticePage();
        DashboardPage dashboardPage = new DashboardPage();

        driver.get(baseUri);
        assertThat(driver.findElement((homePage.heading)).getText())
                .isEqualTo("Master Test Automation");
        driver.findElement(homePage.loginButton).click();
        assertThat(driver.getCurrentUrl())
                .isEqualTo(baseUri + "#practice");
        driver.findElement(practicePage.loginEmail).sendKeys(practicePage.username);
        driver.findElement(practicePage.loginPassword).sendKeys(practicePage.password);
        driver.findElement(practicePage.loginButton).click();
        assertThat(webDriverWait
                .until(ExpectedConditions.visibilityOfElementLocated(dashboardPage.welcomeMessage)).getText())
                .isEqualTo("Welcome back, sintutu! \uD83D\uDC4B");
    }
}

/*
* https://ndosisimplifiedautomation.vercel.app is confusing.
* Is it using pages?
* I'm organising my locators by pages
* Yet things look like /#overview, /#practice, /#dashboard
* Are these pages? Does the page object model apply?
* After research, I see this is a Single Page App that uses hash routing
* Page objects may not be the best way to model the UI.
*
* Asserts are hard to understand.
* I was typing in assert and I didn't see a problem in the IDE.
* I had to google to find testNG asserts until I found I should have used Assert.
* Preferred fluent assertions from AssertJ. Worried about test runtime.
*
* Is the driver environment a whole browser window? Am I spinning up a whole new, RAM-hungry, Chrome window?
* Yes.
* */