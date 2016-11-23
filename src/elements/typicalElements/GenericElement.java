package elements.typicalElements;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.testng.Assert;
import utils.Config;
import utils.Utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by vanle on 08.01.2016.
 */
public class GenericElement {

    protected static Logger log = Logger.getLogger(GenericElement.class.getCanonicalName());
    private static final int DEFAULT_WAIT_TIMEOUT = Config.getDefaultWaitTimeout();

    private WebDriver webDriver;
    private WebElement webElement;

    public GenericElement(WebDriver webDriver, WebElement webElement) {
        this.webDriver = webDriver;
        this.webElement = webElement;
    }

    public WebElement getWebElement() {
        return webElement;
    }

    public void click() {
        click(DEFAULT_WAIT_TIMEOUT);
    }

    public void click(int timeOut) {
        waitForElement(timeOut);
        try {
            webElement.click();

        } catch (Exception e) {
            if (e.getMessage().contains("at point")) {
                jQueryClick(webElement);
            } else {
                e.printStackTrace();
            }
        }
    }

    public void waitForElement() {
        waitForElement(DEFAULT_WAIT_TIMEOUT);
    }

    public void waitForElement(int timeOut) {
        if(isElementDisplayed(timeOut)) {
            return;
        }
    }

    public void jQueryClick(WebElement element) {
        try {
            String script = "arguments[0].click()";
            ((JavascriptExecutor) webDriver).executeScript(script, element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isElementDisplayed(int timeOutSeconds) {
        boolean isDisplayed = false;
        setImplicitlyWait(1000);
        byte i = 1;
        for(; i <= timeOutSeconds; i++) {
            try {
                if(getWebElement().isDisplayed()) {
                    isDisplayed = true;
                    getWebElement().getText();
                    break;
                }
                Utils.sleep(1000);
            } catch (Exception e) {
                log.info("Waiting for element: " + getWebElementSelector());
                Utils.sleep(1000);
            }
        }
        if(!isDisplayed && i == timeOutSeconds) {
            Assert.fail("Element not found after waiting " + timeOutSeconds + " seconds!");
        }
        setImplicitlyWait(DEFAULT_WAIT_TIMEOUT * 1000);
        return isDisplayed;
    }

    public String getWebElementSelector() {
        String el = getWebElement().toString();
        String selector = el;
        try {
            selector = el.substring(el.lastIndexOf("-> ") + 3, el.lastIndexOf("]"));
        } catch (Exception e) {
        }
        return selector;
    }

    public void setImplicitlyWait(int milliseconds) {
        webDriver.manage().timeouts().implicitlyWait(milliseconds, TimeUnit.MILLISECONDS);
    }

}

