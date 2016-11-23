package elements;

import elements.typicalElements.GenericElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class LinkText extends GenericElement {


    public LinkText(WebDriver webDriver, WebElement webElement) {
        super(webDriver, webElement);
    }

    public void click() {
        super.click();
    }
}
