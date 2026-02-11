/*
 * PURPOSE:
 * Represents a running instance of the Ndosi application as experienced by a user.
 *
 * This class owns the browser session lifecycle.
 *
 * RESPONSIBILITY:
 * - Create and destroy WebDriver.
 * - Orchestrate user-level behaviours (e.g. login).
 * - Interpret page observations into domain-level answers.
 *
 * DOES NOT:
 * - Expose WebDriver.
 * - Expose page objects.
 * - Contain raw locators.
 * - Perform low-level Selenium interactions directly.
 *
 * Pages handle mechanics.
 * The facade handles behaviour sequencing.
 * Tests handle assertions.
 *
 * One instance = one user story execution.
 */

package facade;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.DashboardPage;
import pages.HomePage;
import pages.PracticePage;

import java.time.Duration;

public class NdosiDev implements AutoCloseable{
    WebDriver driver;
    WebDriverWait wait;

    private final String baseUri = "https://ndosisimplifiedautomation.vercel.app/";

    public NdosiDev(){
        // Initialise driver and wait
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Get the application running
        driver.manage().window().maximize();
        driver.get(baseUri);

    }

    public void loginAsOrdinaryUser(){
        HomePage homePage = new HomePage(driver, wait);
        homePage.clickLogin();
        PracticePage practicePage = new PracticePage(driver, wait);
        practicePage.enterUsername();
        practicePage.enterPassword();
        practicePage.clickLogin();
    }

    public boolean isOnDashboard(){
        DashboardPage dashboardPage = new DashboardPage(driver, wait);
        return dashboardPage.isVisible();
    }

    @Override
    public void close() {
        driver.quit();
    }

}
