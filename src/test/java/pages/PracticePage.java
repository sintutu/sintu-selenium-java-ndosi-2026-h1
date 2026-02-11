package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

class PracticePage {
    // Declare these as private final so that the passed in driver and waits
    //  are not mutated inside this class
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Practice page locators
    private final By loginEmail = By.id("login-email");
    private final String username = "sintutu@dev.com";
    private final By loginPassword = By.id("login-password");
    private final String password = "@987654321";
    private final By loginButton = By.id("login-submit");

    public PracticePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void enterUsername(){
        driver.findElement(loginEmail).sendKeys(username);
    }

    public void enterPassword(){
        driver.findElement(loginPassword).sendKeys(password);
    }

    public void clickLogin(){
        driver.findElement(loginButton).click();
    }
}
