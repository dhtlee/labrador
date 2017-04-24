package page;

import core.Configuration;
import core.Wait;
import exception.TestException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

public abstract class BasePage {

  protected final WebDriver driver;

  protected final Wait wait;

  protected BasePage(WebDriver driver) {
    this.driver = driver;
    this.wait = new Wait(driver, Configuration.DEFAULT_WAIT_TIME_ELEMENT);
    PageFactory.initElements(driver, this);

    // No log implementation for Geckodriver - https://github.com/mozilla/geckodriver/issues/330
    if (!Configuration.BROWSER.equals("firefox")) {
      checkJavascriptErrors();
    }
  }

  /**
   * Code to accept alert if present (Note: Firefox and Safari has security alert during login)
   * http://stackoverflow.com/questions/9577711/disabling-firefox-security-warning
   */
  protected void handleAlertIfPresent() {
    try {
      wait.sleep(2000); // wait for alert to pop up and finish animating
      driver.switchTo().alert().accept();
    } catch (NoAlertPresentException Ex) {
      // no alert, let's continue
    }
  }

  /**
   * Get driver logs and validate whether there are any javascript errors
   *
   * @throws TestException if an unexpected javascript error is found
   */
  private void checkJavascriptErrors() {
    LogEntries logs = driver.manage().logs().get(LogType.BROWSER);

    if (logs == null) {
      return;
    }

    for (LogEntry logEntry : logs) {
      // fail test if javascript errors are not of ignorable type
      if (!ignorableLogLevel(logEntry) && !ignorableLogMessage(logEntry)) {
        throw new TestException(logEntry.getMessage());
      }
    }
  }

  private boolean ignorableLogLevel(LogEntry logEntry) {
    return logEntry.getLevel().equals(Level.WARNING) || logEntry.getLevel().equals(Level.INFO);
  }

  private boolean ignorableLogMessage(LogEntry logEntry) {
    return logEntry.getMessage().contains("javascript errors that can be ignored")
        || logEntry.getMessage().contains("javascript errors that can be ignored");
  }
}
