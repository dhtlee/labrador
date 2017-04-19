package base;

import core.ExecutionListener;
import core.WebDriverManager;
import utils.DriverUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.net.URL;

import static core.Configuration.GRID_URL;
import static core.Configuration.TEST_EXECUTION_ENV;

@Listeners({ExecutionListener.class})
public abstract class BaseTest {

  protected WebDriver driver;

  @BeforeMethod
  public void setup() {
    driver = WebDriverManager.INSTANCE.initDriver();
  }

  @AfterMethod(alwaysRun = true)
  public void teardown(ITestResult result) {
    if (driver == null)
      return;

    if (!result.isSuccess()) {
      String screenshotFile = DriverUtils.captureScreenshot(driver, result.getName());
      logFailureReport(result, screenshotFile);
    }
    driver.quit();
  }

  private void logFailureReport(ITestResult result, String screenshotFile) {
    Reporter.setCurrentTestResult(result);
    Reporter.log("[" + getTestMachineIp() + "] " + result.getTestClass().getName() + "."
        + result.getMethod().getMethodName() + "<br>");
    if (null != result.getThrowable()) {
      Reporter.log(StringEscapeUtils.escapeHtml4(result.getThrowable().toString()) + "<br>");
      StackTraceElement[] stackTrace = result.getThrowable().getStackTrace();
      for (StackTraceElement s : stackTrace) {
        Reporter.log("&nbsp;&nbsp;&nbsp;&nbsp;at " + s.toString() + "<br>");
      }
    } else {
      Reporter.log("**No stacktrace available!" + "<br>");
    }
    Reporter.log("<br> <img src=" + screenshotFile + " /> <br><br>");
    Reporter.setCurrentTestResult(null);
  }

  private String getTestMachineIp() {
    switch (TEST_EXECUTION_ENV) {
      case "local":
        return "localhost";

      case "grid":
        try {
          String gridHubIP = new URL(GRID_URL).getHost();
          int gridHubPort = new URL(GRID_URL).getPort();
          HttpHost host = new HttpHost(gridHubIP, gridHubPort);
          HttpClient client = HttpClientBuilder.create().build();
          URL testSessionApi = new URL("http://" + gridHubIP + ":" + gridHubPort
              + "/grid/api/testsession?session=" + ((RemoteWebDriver) driver).getSessionId());
          BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST",
              testSessionApi.toExternalForm());
          HttpResponse response = client.execute(host, r);
          JSONObject object = new JSONObject(EntityUtils.toString(response.getEntity()));
          return (String) object.get("proxyId");
        } catch (Exception e) {
          // continue test without grid node ip
          return "";
        }

      default:
        return "others";
    }
  }
}
