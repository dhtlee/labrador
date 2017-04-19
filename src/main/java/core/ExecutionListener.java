package core;

import org.testng.IExecutionListener;

public class ExecutionListener implements IExecutionListener {

  @Override
  public void onExecutionFinish() {
    // clean up code after all tests have been executed
  }

  @Override
  public void onExecutionStart() {
    Configuration.INSTANCE.init();

    System.out.println("=============================================");
    System.out.println("========== Effective Configuration ==========");
    System.out.println("=============================================");
    System.out.println("===================== APP_BASE_URL = " + Configuration.APP_BASE_URL);
    System.out.println("=============== DRIVER_CHROME_PATH = " + Configuration.DRIVER_CHROME_PATH);
    System.out.println("=============== TEST_EXECUTION_ENV = " + Configuration.TEST_EXECUTION_ENV);
    System.out.println("========================= GRID_URL = " + Configuration.GRID_URL);
    System.out.println("========================== BROWSER = " + Configuration.BROWSER);
    System.out.println("============ BROWSERSTACK_USERNAME = " + Configuration.BROWSERSTACK_USERNAME);
    System.out.println("============== BROWSERSTACK_APIKEY = " + Configuration.BROWSERSTACK_APIKEY);
    System.out.println("================= BROWSERSTACK_URL = " + Configuration.BROWSERSTACK_URL);
    System.out.println("===== BROWSERSTACK_BROWSER_VERSION = " + Configuration.BROWSERSTACK_BROWSER_VERSION);
    System.out.println("================== BROWSERSTACK_OS = " + Configuration.BROWSERSTACK_OS);
    System.out.println("========== BROWSERSTACK_OS_VERSION = " + Configuration.BROWSERSTACK_OS_VERSION);
    System.out.println("== BROWSERSTACK_BROWSER_RESOLUTION = " + Configuration.BROWSERSTACK_BROWSER_RESOLUTION);
    System.out.println("=============== SAUCELABS_USERNAME = " + Configuration.SAUCELABS_USERNAME);
    System.out.println("================= SAUCELABS_APIKEY = " + Configuration.SAUCELABS_APIKEY);
    System.out.println("==================== SAUCELABS_URL = " + Configuration.SAUCELABS_URL);
    System.out.println("======== SAUCELABS_BROWSER_VERSION = " + Configuration.SAUCELABS_BROWSER_VERSION);
    System.out.println("===================== SAUCELABS_OS = " + Configuration.SAUCELABS_OS);
    System.out.println("============= SAUCELABS_OS_VERSION = " + Configuration.SAUCELABS_OS_VERSION);
  }
}