package core;

import exception.TestException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait extends WebDriverWait {

  public Wait(WebDriver driver, long timeOutInSeconds) {
    super(driver, timeOutInSeconds);
  }

  /**
   * Convenience method for Thread.sleep()
   * Note: This method should be avoided as much as possible
   *
   * @param ms milliseconds
   */
  public void sleep(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      throw new TestException("Unexpected error has occurred while waiting", e);
    }
  }
}
