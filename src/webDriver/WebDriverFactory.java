package webDriver;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import utils.Config;
import utils.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class WebDriverFactory {

    private static Logger log = Logger.getLogger(WebDriverFactory.class.getCanonicalName());
    private static String PATH = Utils.getRelativePathToFileWithClass("resources/");

    @DataProvider(name = "driverProvider")
    public static Iterator<Object[]> webDriverProvider(ITestContext context) {
        List<Object[]> dataToReturn = new ArrayList<>();
        String[] browsers = Config.getBrowserType().split(",");
        for(String browser : browsers) {
            dataToReturn.add(new Object[] { browser });
        }
        return dataToReturn.iterator();
    }

    public static WebDriver getWebDriver(String browserType) {
        WebDriver webDriver = null;
        String[] driverProperty = new String[2];
        log.info("Starting webDriver " + browserType + " ...");
        try {
            switch (browserType) {
                case BrowserType.CHROME:
                    driverProperty[0] = "webdriver.chrome.driver";
                    driverProperty[1] = "chrome/chromedriver_";
                    setWebDriverProperty(driverProperty);
                    webDriver = new ChromeDriver(WebDriverCapability.getDesireCapabilitiesChrome());
                    break;
                case BrowserType.FIREFOX:
                    driverProperty[0] = "webdriver.gecko.driver";
                    driverProperty[1] = "firefox/geckodriver_";
                    setWebDriverProperty(driverProperty);
                    webDriver = new FirefoxDriver(WebDriverCapability.getDesireCapabilitiesFirefox());
                    break;

                default:
                    Assert.fail("Please set correct BROWSER_TYPE in config.properties file!\n" +
                            "Examples: chrome | firefox | chrome,firefox");
                    break;
            }
            log.info(browserType.toUpperCase() + " Remote WebDriver is running successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Cannot start remote webDriver! BrowserType: " + browserType);
        }
        return webDriver;
    }

    private static void setWebDriverProperty(String[] driverProperty) {
        String os = "lin64";
        if (Utils.isOS("mac")) {
            os = "mac64";
        } else if (Utils.isOS("win")) {
            os = "win32.exe";
        }
        System.setProperty(driverProperty[0], PATH + driverProperty[1] + os);
        log.info("Property: " + driverProperty[0] + ": " + PATH + driverProperty[1] + os);
    }

}
