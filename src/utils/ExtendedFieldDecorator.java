package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class ExtendedFieldDecorator extends DefaultFieldDecorator {

    private WebDriver webDriver;

    public ExtendedFieldDecorator(ElementLocatorFactory factory) {
        super(factory);
    }

    public ExtendedFieldDecorator(ElementLocatorFactory factory, WebDriver webDriver) {
        super(factory);
        this.webDriver = webDriver;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        Class<?> decoratableClass = decoratableClass(field);
        if (decoratableClass != null) {
            ElementLocator locator = factory.createLocator(field);
            if (locator == null) {
                return null;
            }
            return createElement(loader, locator, decoratableClass);
        }
        return null;
    }

    private Class<?> decoratableClass(Field field) {
        Class<?> clazz = field.getType();
        try {
            clazz.getConstructor(WebDriver.class, WebElement.class);
        } catch (Exception e) {
            //e.printStackTrace();  log4j.Logger
            return null;
        }
        return clazz;
    }

    protected <T> T createElement(ClassLoader loader, ElementLocator locator, Class<T> clazz) {
        WebElement proxy = proxyForLocator(loader, locator);
        return createInstance(clazz, proxy);
    }

    private <T> T createInstance(Class<T> clazz, WebElement element) {
        try {
            return (T) clazz.getConstructor(WebDriver.class, WebElement.class).newInstance(webDriver, element);
        } catch (Exception e) {
            throw new AssertionError("WebElement can't be represented as " + clazz);
        }
    }

}
