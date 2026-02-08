package pages;

import org.openqa.selenium.By;

public class DashboardPage {
    // Dashboard page locators
    public final By welcomeMessage = By.xpath("//h2[contains(.,'Welcome back, sintutu! \uD83D\uDC4B')]");
    // Emojis are UTF-8 compliant
}
