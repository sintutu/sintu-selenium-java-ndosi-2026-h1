/*
 * PURPOSE:
 * Encapsulates DOM interactions specific to the Home page.
 *
 * RESPONSIBILITY:
 * - Locate elements.
 * - Perform clicks and input.
 * - Observe page-specific state.
 * - Handle waiting for its own elements.
 *
 * DOES NOT:
 * - Contain assertions.
 * - Manage WebDriver lifecycle.
 * - Coordinate flows across multiple pages.
 * - Represent business scenarios.
 *
 * Pages answer questions and perform actions.
 * They do not interpret business meaning.
 */

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;
    // Home page locators
    public final By heading = By.xpath("//h1[contains(.,'Master Test Automation')]");
    public final By loginButton = By.xpath("//button[contains(.,'Login')]");

    public HomePage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
}
