package infrastructure;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverFactory {
  public static WebDriver create(Browser browser, boolean isHeadless) {
    return switch (browser) {
      case CHROME -> makeChromeDriver(isHeadless);
      case FIREFOX -> makeFirefoxDriver(isHeadless);
      case EDGE -> makeEdgeDriver(isHeadless);
    };
  }

  private static WebDriver makeChromeDriver(boolean isHeadless) {
    ChromeOptions options = new ChromeOptions();
    if (isHeadless) {
      options.addArguments("--headless=new");
    }
    return new ChromeDriver(options);
  }

  private static WebDriver makeFirefoxDriver(boolean isHeadless) {
    FirefoxOptions options = new FirefoxOptions();
    if (isHeadless) {
      options.addArguments("-headless");
    }
    return new FirefoxDriver(options);
  }

  private static WebDriver makeEdgeDriver(boolean isHeadless) {
    EdgeOptions options = new EdgeOptions();
    if (isHeadless) {
      options.addArguments("--headless=new");
    }
    return new EdgeDriver(options);
  }
}
