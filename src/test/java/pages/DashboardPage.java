package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DashboardPage {
    WebDriver driver;
    WebDriverWait wait;

    // Dashboard page locators
    public final By welcomeMessage = By.xpath("//h2[contains(.,'Welcome back, sintutu! \uD83D\uDC4B')]");

    public DashboardPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void assertDashboardIsVisible(){
        assertThat(driver.findElement(welcomeMessage).getText())
                .isEqualTo("Welcome back, sintutu! \uD83D\uDC4B");
    }
}
