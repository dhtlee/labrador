package demo;

import base.BaseTest;
import module.SearchResult;
import org.testng.annotations.Test;
import page.GithubMainPage;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DemoPassTest extends BaseTest {

  @Test
  public void passedTest() {
    String queryText = "labrador user:dhtlee";

    driver.get("http://www.github.com");
    GithubMainPage mainPage = new GithubMainPage(driver);
    mainPage.search(queryText);

    List<SearchResult> results = mainPage.getSearchResultSection().getResults();
    assertThat(results.size(), equalTo(1));

    results.get(0).clickLink();
  }
}
