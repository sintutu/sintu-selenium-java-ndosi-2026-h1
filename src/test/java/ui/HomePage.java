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

package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

class HomePage {
    // Declare these as private final so that the passed in driver and waits
    //  are not mutated inside this class
    private final WebDriver driver;
    private final WebDriverWait wait;
    // Home page locators
    private final By heading = By.xpath("//h1[contains(.,'Master Test Automation')]");
    private final By loginButton = By.xpath("//button[contains(.,'Login')]");

    public HomePage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
}
