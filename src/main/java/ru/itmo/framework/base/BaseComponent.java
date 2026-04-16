package ru.itmo.framework.base;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.itmo.framework.config.TestConfig;

import java.time.Duration;

public abstract class BaseComponent {
    private static final Duration DEFAULT_TIMEOUT = TestConfig.getDurationSeconds("page.timeout.seconds", 10);

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final By rootLocator;

    protected BaseComponent(WebDriver driver, By rootLocator) {
        this(driver, rootLocator, DEFAULT_TIMEOUT);
    }

    protected BaseComponent(WebDriver driver, By rootLocator, Duration timeout) {
        this.driver = driver;
        this.rootLocator = rootLocator;
        this.wait = new WebDriverWait(driver, timeout);

        ensureLoaded();
    }

    protected abstract void ensureLoaded();

    protected WebElement root() {
        return driver.findElement(rootLocator);
    }

    protected WebElement find(By locator) {
        return wait.until(driver -> root().findElement(locator));
    }

    protected boolean isVisible(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
