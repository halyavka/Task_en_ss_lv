import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import pageObjects.*;
import utils.Config;
import webDriver.WebDriverFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class BaseTest {

    protected static Logger log = Logger.getLogger(BaseTest.class.getCanonicalName());
    protected WebDriver webDriver;
    protected String browserType;

    protected Page page;
    protected HeaderPage headerPage;
    protected HomePage homePage;
    protected SearchPage searchPage;
    protected ResultPage resultPage;
    protected MyMemoPage myMemoPage;

    @BeforeClass
    public void beforeClass() {
        webDriver = WebDriverFactory.getWebDriver(browserType);
    }

    @BeforeMethod
    public void beforeMethod() {
        page.setImplicitlyWait(Config.getDefaultWaitTimeout() * 1000);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(Config.getDefaultPageLoadTimeout(), TimeUnit.SECONDS);
    }


    @AfterMethod
    public void afterMethod(ITestResult testResult) {
        String testName = testResult.getName() + "-" + browserType;
        if(webDriver != null) {
            takeScreenshotWithPageSource("target/surefire-reports/html/screenshots" + "/failed/" + testName);
        }
        configuringReport(testName, testResult);
    }

    @AfterClass
    public void afterClass() {
        if (webDriver != null) {
            webDriver.quit();
        }
        log.info("WebDriver quit.");
    }

    public void takeScreenshotWithPageSource(String pathToFile) {
        try {
            File dirForScreenshots = new File(pathToFile);
            dirForScreenshots.mkdir();
            File screenFile;
            screenFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(screenFile, new File(dirForScreenshots, "screenshot.png"));
            try (FileOutputStream fileOutputStream = new FileOutputStream(new File(dirForScreenshots, "page_source.html"))) {
                fileOutputStream.write(webDriver.getPageSource().getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception ignored) {
            ignored.getMessage();
        }
    }

    protected void configuringReport(String testName, ITestResult testResult) {
        Reporter.setCurrentTestResult(testResult);
        Reporter.log("<br>");
        if (!testResult.isSuccess()) {
            Reporter.log("<<<<<<<<< " + browserType.toUpperCase() + " >>>>>>>>>>");
            Reporter.log("<br>");
            Reporter.log("<a href='screenshots/failed/" + testName + "/screenshot.png'><img src='screenshots/failed/" +
                    testName + "/screenshot.png' width='200' height='150' alt='Click on image to enlarge'/></a>");
            Reporter.log("<br>");
        }
    }

}
