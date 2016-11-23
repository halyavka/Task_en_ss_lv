package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by ivankhaliavka on 11/23/16.
 */
public abstract class Config {

    private static final String PROJECT_URL;
    private static final String BROWSER_TYPE;
    private static final String DEFAULT_WAIT_TIMEOUT;
    private static final String DEFAULT_PAGE_LOAD_TIMEOUT;


    static {
        Properties properties = loadPropertiesFromClassPath("/config.properties");

        PROJECT_URL = properties.getProperty("PROJECT_URL");
        BROWSER_TYPE = properties.getProperty("BROWSER_TYPE");
        DEFAULT_WAIT_TIMEOUT = properties.getProperty("DEFAULT_WAIT_TIMEOUT");
        DEFAULT_PAGE_LOAD_TIMEOUT = properties.getProperty("DEFAULT_PAGE_LOAD_TIMEOUT");
    }

    public static String getProjectUrl() {
        return "http://" + PROJECT_URL + "/";
    }

    public static String getBrowserType() {
        return BROWSER_TYPE.toLowerCase();
    }

    public static int getDefaultPageLoadTimeout() {
        return Integer.valueOf(DEFAULT_PAGE_LOAD_TIMEOUT);
    }

    public static int getDefaultWaitTimeout() {
        return Integer.valueOf(DEFAULT_WAIT_TIMEOUT);
    }

    private static Properties loadPropertiesFromClassPath(String path) {
        Properties properties = new Properties();
        URL resource = Config.class.getResource(path);
        try (InputStream inputStream = new FileInputStream(resource.getPath())) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


}
