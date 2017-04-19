package module;

import core.Configuration;
import core.Wait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class BaseModule {

  protected final WebDriver driver;

  protected final Wait wait;

  @FindBy(xpath = ".")
  protected WebElement thisElement;

  BaseModule(WebDriver driver) {
    this.driver = driver;
    this.wait = new Wait(driver, Configuration.DEFAULT_WAIT_TIME_ELEMENT);
    PageFactory.initElements(driver, this);
  }
}