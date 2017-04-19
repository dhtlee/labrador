package utils;

import exception.TestException;
import module.BaseModule;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DriverUtils {

  public static void scrollToTop(WebDriver driver) {
    ((JavascriptExecutor) driver).executeScript("scrollTo(0,0);");
  }

  public static void scrollBy(WebDriver driver, int pixels) {
    ((JavascriptExecutor) driver).executeScript("scrollBy(0, " + pixels + ")");
  }

  public static void scrollToElement(WebDriver driver, WebElement element) {
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
  }

  public static void switchBrowserTab(WebDriver driver, String tab) {
    driver.switchTo().window(tab);
  }

  public static String captureScreenshot(WebDriver driver, String testName) {

    SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
    try {
      File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      String fileName = testName + "_" + formatter.format(Calendar.getInstance().getTime()) + ".jpg";
      FileUtils.copyFile(f, new File("target/surefire-reports/" + fileName));
      return fileName;
    } catch (Exception e) {
      e.printStackTrace();
      // continue
    }
    return "";
  }

  public static <T extends BaseModule> T findModule(WebDriver driver, By by, Class<T> clazz) {
    return findModule(driver, null, by, clazz);
  }

  private static <T extends BaseModule> T findModule(WebDriver driver, SearchContext searchContext, By by,
                                                     Class<T> clazz) {

    WebElement moduleElement;

    if (searchContext == null) {
      moduleElement = driver.findElement(by);
    } else {
      moduleElement = searchContext.findElement(by);
    }
    return initModule(driver, clazz, moduleElement);
  }

  public static <T extends BaseModule> List<T> findModules(WebDriver driver, By by, Class<T> clazz) {
    return findModules(driver, null, by, clazz);
  }

  private static <T extends BaseModule> List<T> findModules(WebDriver driver, SearchContext searchContext, By by,
                                                            Class<T> clazz) {

    ArrayList<T> list = new ArrayList<>();
    List<WebElement> moduleElements;

    if (searchContext == null) {
      moduleElements = driver.findElements(by);
    } else {
      moduleElements = searchContext.findElements(by);
    }

    for (WebElement moduleElement : moduleElements) {
      list.add(initModule(driver, clazz, moduleElement));
    }
    return list;
  }

  private static <T extends BaseModule> T initModule(WebDriver driver, Class<T> clazz, WebElement moduleElement) {
    T module;
    try {
      DefaultElementLocatorFactory elf = new DefaultElementLocatorFactory(moduleElement);
      Constructor<?> ctor = clazz.getConstructor(WebDriver.class);
      module = (T) ctor.newInstance(driver);
      PageFactory.initElements(elf, module);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException
        | InstantiationException e) {
      throw new TestException("Unexpected error initiating elements for " + clazz.getName(), e);
    }
    return module;
  }
}
