package demo;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoFailTest extends BaseTest {

  @Test
  public void failedTest() {
    driver.get("https://github.com/dhtlee/labrador/");
    Assert.fail("This test should fail. You can check for screenshots in the target/surefire-reports directory");
  }
}
