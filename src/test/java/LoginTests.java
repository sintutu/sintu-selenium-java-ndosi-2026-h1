import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests {
    // Declare private driver instance
    private WebDriver driver;

    private final String baseUri = "https://ndosisimplifiedautomation.vercel.app/";

    // Home page locators
    private final By homePageHeading = By.xpath("//h1[contains(.,'Master Test Automation')]");
    private final By homeLoginButton = By.xpath("//button[contains(.,'Login')]");

    // Practice page locators
    private final By loginEmail = By.id("login-email");
    private final String username = "sintutu@dev.com";
    private final By loginPassword = By.id("login-password");
    private final String password = "@987654321";
    private final By loginButton = By.id("login-submit");

    // Dashboard page locators
    private final By dashboardWelcomeMessage = By.xpath("//h2[contains(.,'Welcome back, sintutu! \uD83D\uDC4B')]");
    // Emojis are UTF-8 compliant


    @Test
    public void logIntoNdosiDevSite() throws InterruptedException {
        // Instantiate driver. But why in the test?
        driver = new ChromeDriver();
        driver.navigate().to(baseUri); // driver.get(baseUri) would work. What's navigate() for?
        driver.manage().window().maximize(); // what's the manage interface for?
        Assert.assertEquals(
                driver.findElement((homePageHeading)).getText(),
                "Master Test Automation");
        driver.findElement(homeLoginButton).click();
        Assert.assertEquals(driver.getCurrentUrl(), baseUri + "#practice");
        driver.findElement(loginEmail).sendKeys(username);
        driver.findElement(loginPassword).sendKeys(password);
        driver.findElement(loginButton).click();
        Thread.sleep(1000); // Added a wait. Didn't feel like adding a Wait.
        Assert.assertEquals(
                driver.findElement(dashboardWelcomeMessage).getText(),
                "Welcome back, sintutu! \uD83D\uDC4B");
    }
}

/*
* https://nlosisimplifiedautomation.vercel.app is confusing.
* Is it using pages?
* I'm organising my locators by pages
* Yet things look like /#overview, /#practice, /#dashboard
* Are these pages? Does the page object model apply?
*
* Asserts are hard to understand.
* I was typing in assert and I didn't see a problem in the IDE.
* I had to google to find testNG asserts until I found I should have used Assert.
* 
* */