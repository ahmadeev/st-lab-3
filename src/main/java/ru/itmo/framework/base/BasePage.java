package ru.itmo.framework.base;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.itmo.framework.config.TestConfig;

import java.time.Duration;

public abstract class BasePage extends BaseUiObject {
    protected BasePage(WebDriver driver) {
        super(driver);
    }

    protected BasePage(WebDriver driver, Duration timeout) {
        super(driver, timeout);
    }

    @Override
    protected SearchContext searchContext() {
        return driver;
    }

    protected void open(String path) {
        driver.get(TestConfig.get("base.url") + path);
        closeCookieBannerIfPresent();
    }

    protected void openAbsolute(String url) {
        driver.get(url);
    }

    protected String currentUrl() {
        return driver.getCurrentUrl();
    }

    protected boolean titleContains(String value) {
        return wait.until(ExpectedConditions.titleContains(value));
    }

    protected boolean titleDoesNotContain(String value) {
        return wait.until(ExpectedConditions.not(ExpectedConditions.titleContains(value)));
    }

    protected boolean urlContains(String value) {
        return wait.until(ExpectedConditions.urlContains(value));
    }
}
