package pageObjects;

import elements.Button;
import elements.InputField;
import elements.LinkText;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class SearchPage extends Page {

    protected static Logger log = Logger.getLogger(SearchPage.class.getCanonicalName());

    public SearchPage(WebDriver webDriver) {
        super(webDriver);
    }

    @FindBy(xpath = "//input[@id='sbtn' and @type='submit']")
    private Button searchButton;

    @FindBy(id = "ptxt")
    private InputField searchPhraseField;

    @FindBy(xpath = "//input[contains(@name,'topt') and contains(@name,'[min]')]")
    private InputField minPrice;

    @FindBy(xpath = "//input[contains(@name,'topt') and contains(@name,'[max]')]")
    private InputField maxPrice;

    private void typeSearchPhrase(String phrase) {
        searchPhraseField.fillField(phrase);
    }

    public void typeMinPrice(String value) {
        minPrice.fillField(value);
    }

    public void typeMaxPrice(String value) {
        maxPrice.fillField(value);
    }

    public void clickSearch(String value) {
        webElement = waitUntilElement(webDriver, By.xpath("//a[text()='" + value + "']"));
        new LinkText(webDriver, webElement).click();
        waitForPageLoaded();
    }

    private List<WebElement> getPreloadListSearch() {
        List<WebElement> list = waitUntilElements(webDriver,
                By.xpath("//div[@id='preload_txt']/div[contains(@id,'cmp_')]"));
        return list;
    }

    private void clickOnItemInListSearch(String value) {
        List<WebElement> list = getPreloadListSearch();
        for (WebElement element : list) {
            if (element.getText().equals(value)) {
                String id = element.getAttribute("id").replace("cmp_", "");
                executeJS("cmp_over(" + id + ");");
                break;
            }
        }
    }

    public ResultPage clickSearchButton() {
        searchButton.click();
        waitForPageLoaded();
        return new ResultPage(webDriver);
    }

    public ResultPage search(String searchValue, String selectedSearch) {
        typeSearchPhrase(searchValue);
        clickOnItemInListSearch(selectedSearch);
        clickSearchButton();
        log.info("Searching by value '" + searchValue + "'...");
        return new ResultPage(webDriver);
    }

}
