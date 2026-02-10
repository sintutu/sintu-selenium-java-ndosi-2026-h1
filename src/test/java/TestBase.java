import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.time.Duration;

public class TestBase {
    // Declare WebDriver instance
    protected WebDriver driver;

    // Declare WebDriverWait instance
    protected WebDriverWait webDriverWait;

    protected final String baseUri = "https://ndosisimplifiedautomation.vercel.app/";

    @BeforeTest
    public void Setup(){
        // Instantiate driver
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        // I get why the manage interface exists. It manages the driver environment.
        driver.manage().window().maximize();
    }

    @AfterTest
    public void Teardown(){
        driver.quit();
    }
}
