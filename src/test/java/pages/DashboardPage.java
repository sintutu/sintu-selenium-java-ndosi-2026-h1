/*
 * PURPOSE:
 * Encapsulates DOM interactions specific to the Dashboard page.
 *
 * RESPONSIBILITY:
 * - Observe dashboard-specific signals (e.g. welcome message).
 * - Wait for elements that indicate the page is loaded.
 *
 * DOES NOT:
 * - Perform assertions.
 * - Decide whether login succeeded.
 * - Know about user roles or scenarios.
 *
 * This class reports facts about the UI.
 * The facade interprets those facts.
 */

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {
    WebDriver driver;
    WebDriverWait wait;

    // Dashboard page locators
    public final By welcomeMessage = By.xpath("//h2[contains(.,'Welcome back, sintutu! \uD83D\uDC4B')]");

    public DashboardPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isVisible(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage)).isDisplayed();
    }
}
