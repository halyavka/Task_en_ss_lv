package webDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.Utils;


/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class WebDriverCapability {

    public static DesiredCapabilities getDesireCapabilitiesFirefox() {
        FirefoxProfile profile = new FirefoxProfile();

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability(FirefoxDriver.PROFILE, profile);
        capabilities.setJavascriptEnabled(true);

        return capabilities;
    }

    public static DesiredCapabilities getDesireCapabilitiesChrome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        return capabilities;
    }


}
