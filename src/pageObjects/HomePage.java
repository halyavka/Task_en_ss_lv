package pageObjects;

import elements.LinkText;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class HomePage extends Page {

    protected static Logger log = Logger.getLogger(HomePage.class.getCanonicalName());

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    private By getSection(String section) {
        return By.xpath("//a[@class='a1' and text()='" + section + "']");
    }

    public void goToSection(String section) {
        webElement = webDriver.findElement(getSection(section));
        new LinkText(webDriver, webElement).click();
        waitForPageLoaded();
    }


}
