package elements;

import elements.typicalElements.GenericElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class CheckBox extends GenericElement {

    public CheckBox(WebDriver webDriver, WebElement webElement) {
        super(webDriver, webElement);
    }

    public void check() {
        if (!isSelected()) {
            click();
        }
    }

    public void unCheck() {
        if (isSelected()) {
            click();
        }
    }

    public boolean isSelected() {
        return getWebElement().isSelected();
    }

}
