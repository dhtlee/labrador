package module;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchResult extends BaseModule {

  @FindBy(css = "a")
  private WebElement link;

  public SearchResult(WebDriver driver) {
    super(driver);
  }

  public void clickLink() {
    link.click();
  }
}
