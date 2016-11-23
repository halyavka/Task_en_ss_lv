package pageObjects;

import com.google.common.base.Function;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.ui.*;
import utils.Config;
import utils.ExtendedFieldDecorator;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class Page {

    protected static Logger log = Logger.getLogger(Page.class.getCanonicalName());

    protected WebDriver webDriver;
    protected WebElement webElement;

    protected static final int DEFAULT_PAGE_LOAD_TIMEOUT = Config.getDefaultPageLoadTimeout();
    protected static final int DEFAULT_WAIT_TIMEOUT = Config.getDefaultWaitTimeout();

    public Page(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(new ExtendedFieldDecorator(
                new DefaultElementLocatorFactory(webDriver), webDriver), this);
    }

    public void openPage(String url) {
        webDriver.get(url);
        waitForPageLoaded();
    }

    public void waitForPageLoaded() {
        new WebDriverWait(webDriver, DEFAULT_PAGE_LOAD_TIMEOUT).until((ExpectedCondition<Boolean>) wd ->
                    ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        Utils.sleep(1000);
    }

    public void waitForElement(WebElement element) {
        for (byte i = 0; i < DEFAULT_WAIT_TIMEOUT; i++) {
            try {
                if (element.isDisplayed()) {
                    element.getText();
                    break;
                }
            } catch (StaleElementReferenceException e) {
                e.getCause();
            }
        }
        setImplicitlyWait(DEFAULT_WAIT_TIMEOUT * 1000);
    }

    public List<WebElement> waitUntilElements(WebDriver webDriver, By by) {
        List<WebElement> elements = new ArrayList<>();

        for (int i=0; i<10; i++) {
            try {
                elements = webDriver.findElements(by);
                if (elements.size() != 0) {
                    for (WebElement element: elements) {
                        element.getText();
                    }
                }
            } catch (StaleElementReferenceException e) {
                Utils.sleep(1000);
                log.info("Waiting for elements ...");
            }
        }
        return elements;
    }

    public WebElement waitUntilElement(WebDriver webDriver, By by) {
        WebElement element = null;
        Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        for (int i=0; i<10; i++) {
            try {
                element = wait.until(new Function<WebDriver, WebElement>() {
                    @Override
                    public WebElement apply(WebDriver webDriver) {
                        return webDriver.findElement(by);
                    }
                });
                element.getText();

            } catch (StaleElementReferenceException e) {
                Utils.sleep(1000);
                log.info("Waiting for element ...");
            }
        }
        return element;
    }


    public void setImplicitlyWait(int milliseconds) {
        webDriver.manage().timeouts().implicitlyWait(milliseconds, TimeUnit.MILLISECONDS);
    }

    public Object executeJS(String script) {
        Object result = null;
        try {
            result = ((JavascriptExecutor) webDriver).executeScript(script);
        } catch (Exception e) {
            e.getMessage();
        }
        return result;
    }

}
