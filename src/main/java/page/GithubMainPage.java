package page;

import module.SearchResultSection;
import org.openqa.selenium.Keys;
import utils.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GithubMainPage extends BasePage {

  @FindBy(css = "input[type='text']")
  private WebElement searchBox;

  public GithubMainPage(WebDriver driver) {
    super(driver);
  }

  public void search(String query) {
    searchBox.sendKeys(query + Keys.ENTER);
  }

  public SearchResultSection getSearchResultSection() {
    return DriverUtils.findModule(driver, By.cssSelector("div[class*='results']"), SearchResultSection.class);
  }
}
