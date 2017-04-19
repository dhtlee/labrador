package core;

import exception.SetupException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * This class supports configuration injection from multiple location. The configuration values defined in the higher
 * location will take precedence over the ones defined in lower location. The priority order is:
 * 1) environment variables (defined in pom.xml)
 * 2) configuration.properties
 * 3) configuration-template.properties
 *
 * Example: If "app.url" environment is defined, say from Jenkins, then it will be used even if it is also defined
 * in configuration.properties
 */
public enum Configuration {

  INSTANCE;

  // environment-related
  public static String APP_BASE_URL;
  public static String DRIVER_CHROME_PATH;
  public static String DRIVER_GECKO_PATH;
  public static String TEST_EXECUTION_ENV;
  public static String GRID_URL;

  // general test configuration
  public static final int DEFAULT_WAIT_TIME_ELEMENT = 60;
  public static String BROWSER;

  // browserstack test configuration
  public static String BROWSERSTACK_USERNAME;
  public static String BROWSERSTACK_APIKEY;
  public static String BROWSERSTACK_URL;
  public static String BROWSERSTACK_BROWSER_VERSION;
  public static String BROWSERSTACK_BROWSER_RESOLUTION;
  public static String BROWSERSTACK_OS;
  public static String BROWSERSTACK_OS_VERSION;

  // saucelabs test configuration
  public static String SAUCELABS_USERNAME;
  public static String SAUCELABS_APIKEY;
  public static String SAUCELABS_URL;
  public static String SAUCELABS_BROWSER_VERSION;
  public static String SAUCELABS_OS;
  public static String SAUCELABS_OS_VERSION;

  public void init() {
    Properties prop = new Properties();
    URL configFile = getConfigFile();

    try {
      prop.load(configFile.openStream());
    } catch (IOException e) {
      throw new SetupException("Unexpected error while trying to read config file from " + configFile, e);
    }

    // configuration values that allows override
    APP_BASE_URL = isEnvVarSet("jAppUrl") ? System.getProperty("jAppUrl") : prop.getProperty("app.url");
    TEST_EXECUTION_ENV = isEnvVarSet("jTestExecutionEnv") ? System.getProperty("jTestExecutionEnv") : prop.getProperty("test.execution.env");
    GRID_URL = isEnvVarSet("jGridUrl") ? System.getProperty("jGridUrl") : prop.getProperty("grid.url");
    BROWSER = isEnvVarSet("jTestBrowser") ? System.getProperty("jTestBrowser") : prop.getProperty("test.browser");
    BROWSERSTACK_USERNAME = isEnvVarSet("jBsUsername") ? System.getProperty("jBsUsername") : prop.getProperty("browserstack.username");
    BROWSERSTACK_APIKEY = isEnvVarSet("jBsApiKey") ? System.getProperty("jBsApiKey") : prop.getProperty("browserstack.apikey");
    BROWSERSTACK_URL = "https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_APIKEY + "@hub-cloud.browserstack.com/wd/hub";
    BROWSERSTACK_BROWSER_VERSION = isEnvVarSet("jBsBrowserVersion") ? System.getProperty("jBsBrowserVersion") : prop.getProperty("browserstack.browser.version");
    BROWSERSTACK_BROWSER_RESOLUTION = isEnvVarSet("jBsBrowserResolution") ? System.getProperty("jBsBrowserResolution") : prop.getProperty("browserstack.browser.resolution");
    BROWSERSTACK_OS = isEnvVarSet("jBsOS") ? System.getProperty("jBsOS") : prop.getProperty("browserstack.os");
    BROWSERSTACK_OS_VERSION = isEnvVarSet("jBsOSVersion") ? System.getProperty("jBsOSVersion") : prop.getProperty("browserstack.os.version");
    SAUCELABS_USERNAME = isEnvVarSet("jSlUsername") ? System.getProperty("jSlUsername") : prop.getProperty("saucelabs.username");
    SAUCELABS_APIKEY = isEnvVarSet("jSlApiKey") ? System.getProperty("jSlApiKey") : prop.getProperty("saucelabs.apikey");
    SAUCELABS_URL = "https://" + SAUCELABS_USERNAME + ":" + SAUCELABS_APIKEY + "@ondemand.saucelabs.com:443/wd/hub";
    SAUCELABS_BROWSER_VERSION = isEnvVarSet("jSlBrowserVersion") ? System.getProperty("jSlBrowserVersion") : prop.getProperty("saucelabs.browser.version");
    SAUCELABS_OS = isEnvVarSet("jSlOS") ? System.getProperty("jSlOS") : prop.getProperty("saucelabs.os");
    SAUCELABS_OS_VERSION = isEnvVarSet("jSlOSVersion") ? System.getProperty("jSlOSVersion") : prop.getProperty("saucelabs.os.version");

    // configuration values that needs to be specified in config.properties or config-template.properties
    DRIVER_CHROME_PATH = System.getProperty("user.dir") + File.separator + prop.getProperty("driver.chrome.path");
    DRIVER_GECKO_PATH = System.getProperty("user.dir") + File.separator + prop.getProperty("driver.gecko.path");
  }

  /**
   * Get config.properties if exists, otherwise will get config-template.properties
   *
   * @return URL representing config file
   */
  private URL getConfigFile() {
    URL configFile = this.getClass().getClassLoader().getResource("config.properties");
    if (configFile == null) {
      configFile = this.getClass().getClassLoader().getResource("config-template.properties");
    }
    return configFile;
  }

  /**
   * Check if there is an environment variable for given property. This is to allow configuration
   * to be overwritten if it is provided from any CI tool (e.g. Jenkins)
   *
   * @param property property key
   * @return true if environment variable is set, false otherwise
   */
  private boolean isEnvVarSet(String property) {
    return System.getProperty(property) != null && !System.getProperty(property).isEmpty();
  }
}