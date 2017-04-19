package module;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.DriverUtils;

import java.util.List;

public class SearchResultSection extends BaseModule {

  @FindBy(css = "#search-summary")
  private WebElement summaryList;

  public SearchResultSection(WebDriver driver) {
    super(driver);
  }

  public List<SearchResult> getResults() {
    return DriverUtils.findModules(driver, By.cssSelector("div[class*='repo-list-item']"), SearchResult.class);
  }
}
