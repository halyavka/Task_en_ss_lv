package elements;

import elements.typicalElements.GenericElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class InputField extends GenericElement {

    public InputField(WebDriver webDriver, WebElement webElement) {
        super(webDriver, webElement);
    }

    public void fillField(String value) {
        waitForElement();
        WebElement element = getWebElement();
        element.clear();
        element.sendKeys(value);
    }

}
