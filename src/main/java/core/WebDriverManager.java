package core;

import exception.SetupException;
import exception.TestException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.UnexpectedAlertBehaviour.IGNORE;
import static org.openqa.selenium.firefox.FirefoxDriver.PROFILE;
import static org.openqa.selenium.remote.CapabilityType.*;

public enum WebDriverManager {

  INSTANCE;

  public WebDriver initDriver() {

    WebDriver driver;

    switch(Configuration.TEST_EXECUTION_ENV) {
      case "local":
        driver = initLocalDriver();
        break;
      case "grid":
        driver = initGridDriver();
        break;
      case "browserstack":
        driver = initBrowserstackDriver();
        break;
      case "saucelabs":
        driver = initSauceLabsDriver();
        break;
      default:
        throw new SetupException("Unsupported test execution environment [" + Configuration.TEST_EXECUTION_ENV + "]");
    }
    driver.manage().timeouts().implicitlyWait(250, TimeUnit.MILLISECONDS);

    return driver;
  }

  public WebDriver openNewBrower(WebDriver previousDriver) {
    previousDriver.quit();
    return initDriver();
  }

  private WebDriver initSauceLabsDriver() {
    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability(BROWSER_NAME, Configuration.BROWSER);
    caps.setCapability(PLATFORM, Configuration.SAUCELABS_OS + " " + Configuration.SAUCELABS_OS_VERSION);
    caps.setCapability(VERSION, Configuration.SAUCELABS_BROWSER_VERSION);
    caps.setCapability(ACCEPT_SSL_CERTS, true);
    caps.setCapability(UNEXPECTED_ALERT_BEHAVIOUR, IGNORE);

    try {
      return new RemoteWebDriver(new URL(Configuration.SAUCELABS_URL), caps);
    } catch (MalformedURLException e) {
      throw new SetupException("Bad URL [" + Configuration.SAUCELABS_URL + "]", e);
    }
  }

  private WebDriver initBrowserstackDriver() {
    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability(BROWSER_NAME, Configuration.BROWSER);
    if (Configuration.BROWSER.equals("firefox")) {
      caps = DesiredCapabilities.firefox();
      FirefoxProfile ffProfile = new FirefoxProfile();
      caps.setCapability(PROFILE, ffProfile);
      caps.setCapability("marionette", false);
    }
    caps.setCapability("browser_version", Configuration.BROWSERSTACK_BROWSER_VERSION);
    caps.setCapability("os", Configuration.BROWSERSTACK_OS);
    caps.setCapability("os_version", Configuration.BROWSERSTACK_OS_VERSION);
    caps.setCapability("resolution", Configuration.BROWSERSTACK_BROWSER_RESOLUTION);
    caps.setCapability("browserstack.local", true);

    try {
      return new RemoteWebDriver(new URL(Configuration.BROWSERSTACK_URL), caps);
    } catch (MalformedURLException e) {
      throw new TestException("Bad URL [" + Configuration.BROWSERSTACK_URL + "]", e);
    }
  }

  private WebDriver initGridDriver() {
    DesiredCapabilities caps;
    switch(Configuration.BROWSER) {
      case "firefox":
        caps = DesiredCapabilities.firefox();
        break;
      case "chrome":
        caps = DesiredCapabilities.chrome();
        break;
      default:
        throw new SetupException("Browser [" + Configuration.BROWSER + "] is not supported");
    }
    caps.setPlatform(Platform.LINUX);
    try {
      return new RemoteWebDriver(new URL(Configuration.GRID_URL), caps);
    } catch (MalformedURLException e) {
      throw new SetupException("Error initializing remote webdriver on selenium grid with url: "
          + Configuration.GRID_URL, e);
    }
  }

  private WebDriver initLocalDriver() {
    switch(Configuration.BROWSER) {
      case "firefox":
        System.setProperty("webdriver.gecko.driver", Configuration.DRIVER_GECKO_PATH);
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        return new FirefoxDriver(capabilities);
      case "chrome":
        System.setProperty("webdriver.chrome.driver", Configuration.DRIVER_CHROME_PATH);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("test-type");
        return new ChromeDriver(chromeOptions);
      default:
        throw new SetupException(Configuration.BROWSER + " browser is not supported");
    }
  }
}