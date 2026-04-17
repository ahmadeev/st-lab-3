package ru.itmo.framework.base;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public abstract class BaseComponent extends BaseUiObject {
    protected final By rootLocator;

    protected BaseComponent(WebDriver driver, By rootLocator) {
        super(driver);
        this.rootLocator = rootLocator;

        ensureLoaded();
    }

    protected BaseComponent(WebDriver driver, By rootLocator, Duration timeout) {
        super(driver, timeout);
        this.rootLocator = rootLocator;

        ensureLoaded();
    }

    @Override
    protected SearchContext searchContext() {
        return root();
    }

    protected WebElement root() {
        return wait.until(driver -> driver.findElement(rootLocator));
    }
}
