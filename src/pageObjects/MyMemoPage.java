package pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class MyMemoPage extends Page {

    protected static Logger log = Logger.getLogger(MyMemoPage.class.getCanonicalName());

    public MyMemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    private String tableXpath = "//table[@id='page_main']//form[@id='filter_frm']/table";

    private List<WebElement> getTableList() {
        return webDriver.findElements(By.xpath(tableXpath));
    }

    private List<WebElement> getMyItems() {
        List<WebElement> myItems = new ArrayList<>();
        List<WebElement> tables = getTableList();
        for (WebElement element : tables) {
            List<WebElement> tableItems = waitUntilElements(webDriver, By.xpath(".//tr[contains(@id,'tr_')]"));
            for (WebElement tableItem : tableItems) {
                myItems.add(tableItem);
            }
        }
        return myItems;
    }

    public List<String[]> getItemsData() {
        List<String[]> itemsData = new ArrayList<>();
        for (WebElement item : getMyItems()) {
            String[] itemData = new String[2];
            itemData[0] = item.getAttribute("id");
            itemData[1] = item.getText();
            itemsData.add(itemData);
        }
        return itemsData;
    }


}
