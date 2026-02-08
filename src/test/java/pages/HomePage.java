package pages;

import org.openqa.selenium.By;

public class HomePage {
    // Home page locators
    public final By heading = By.xpath("//h1[contains(.,'Master Test Automation')]");
    public final By loginButton = By.xpath("//button[contains(.,'Login')]");
}
