package pageObjects;

import elements.LinkText;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class HeaderPage extends Page {

    protected static Logger log = Logger.getLogger(HeaderPage.class.getCanonicalName());

    public HeaderPage(WebDriver webDriver) {
        super(webDriver);
    }

    public By getByHeaderMenuButton(String title) {
        return By.xpath("//*[@class='page_header_menu']//*[contains(@class,'a_menu') and text()='" + title + "']");
    }

    public By getByChangeLanguage(String lang) {
        return By.xpath("//*[@id='main_table']//*[contains(@class,'a_menu') and text()='" + lang + "']");
    }

    public void clickInHeaderMenu(String value) {
        executeJS("scroll(0,0);");
        webElement = waitUntilElement(webDriver, getByHeaderMenuButton(value));
        new LinkText(webDriver, webElement).click();
        waitForPageLoaded();
    }

    public void changeLanguage(String lang) {
        webElement = waitUntilElement(webDriver, getByChangeLanguage(lang));
        new LinkText(webDriver, webElement).click();
        waitForPageLoaded();
    }


}
