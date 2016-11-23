package pageObjects;

import elements.Button;
import elements.CheckBox;
import elements.LinkText;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class ResultPage extends Page {

    protected static Logger log = Logger.getLogger(ResultPage.class.getCanonicalName());

    public ResultPage(WebDriver webDriver) {
        super(webDriver);
    }

    private String getXpathSortBy(String by) {
        return "//a[@rel='nofollow' and text()='" + by + "']";
    }

    @FindBy(id = "alert_ok")
    private Button alertOk;

    public void clickSortBy(String by, boolean ascending) {
        webElement = waitUntilElement(webDriver, By.xpath(getXpathSortBy(by)));
        new LinkText(webDriver, webElement).click();
        waitForPageLoaded();
        if (sortedByAsc() != ascending) {
            clickSortBy(by, ascending);
            return;
        }
    }

    public void clickAddToMemo(String byText) {
        webElement = waitUntilElement(webDriver, By.xpath("//*[text()='" + byText + "']"));
        new LinkText(webDriver, webElement).click();
        waitForElement(alertOk.getWebElement());
        alertOk.click();
    }

    public void selectTypeDeal(String value) {
        new Select(waitUntilElement(webDriver, By.xpath("//select[@class='filter_sel']"))).selectByVisibleText(value);
        waitForPageLoaded();
    }

    public List<WebElement> getResultList() {
        List<WebElement> list = waitUntilElements(webDriver, By.xpath("//table/tbody/tr[contains(@id,'tr_')]"));
        log.info("Result: " + list.size() + " items were found.");
        return list;
    }

    public String[] chooseItem(int byIndex) {
        String[] itemData = new String[2];
        List<WebElement> list = getResultList();
        if (list.size() < byIndex) {
            log.info("Cannot choose item by index " + byIndex + ". \n" +
                    "Full amount of items: " + list.size()) ;
            return null;
        }
        itemData[0] = list.get(byIndex).getAttribute("id");
        itemData[1] = list.get(byIndex).getText();
        webElement = list.get(byIndex).findElement(By.xpath(".//input[@type='checkbox']"));
        new CheckBox(webDriver, webElement).check();
        log.info("Choose item " + byIndex);
        return itemData;
    }

    private boolean sortedByAsc() {
        List<Float> prices = new ArrayList<>();
        for (WebElement element : getResultList()) {
            String text = element.findElement(By.xpath(".//a[@class='amopt']")).getText();
            text = text.split(" ")[0].replace(",", ".");
            prices.add(Float.valueOf(text));
        }
        int compareRes = 0;
        for (int i = 0; i < prices.size(); i++) {
            if (i + 1 < prices.size()) {
                compareRes += compare(prices.get(i), prices.get(i + 1));
            }
        }
        if (compareRes >= prices.size() - 2) {
            return false;
        } else {
            return true;
        }
    }

    private int compare(Float change1, Float change2) {
        if (change1 < change2) return -1;
        if (change1 > change2) return 1;
        return 0;
    }

}
