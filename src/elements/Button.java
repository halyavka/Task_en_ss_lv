package elements;

import elements.typicalElements.GenericElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class Button extends GenericElement {

    public Button(WebDriver webDriver, WebElement webElement) {
        super(webDriver, webElement);
    }

    public void click() {
        super.click();
    }

}
