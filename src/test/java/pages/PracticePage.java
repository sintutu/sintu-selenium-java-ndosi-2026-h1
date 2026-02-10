package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PracticePage {
    // Declare driver and wait
    WebDriver driver;
    WebDriverWait wait;

    // Practice page locators
    public final By loginEmail = By.id("login-email");
    public final String username = "sintutu@dev.com";
    public final By loginPassword = By.id("login-password");
    public final String password = "@987654321";
    public final By loginButton = By.id("login-submit");

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
