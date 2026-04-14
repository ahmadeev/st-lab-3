package ru.itmo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    protected final WebDriver driver;
    private final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);

        PageFactory.initElements(driver, this);
    }

    protected void openUrl(String url, String path) {
        driver.get(url + path);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    protected WebElement waitUntilVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitUntilVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected boolean waitUntilInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected WebElement waitUntilClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitUntilClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected boolean waitUntilTitleContains(String value) {
        return wait.until(ExpectedConditions.titleContains(value));
    }

    protected boolean waitUntilTitleDoesNotContain(String value) {
        return wait.until(ExpectedConditions.not(ExpectedConditions.titleContains(value)));
    }

    protected boolean waitUntilUrlContains(String value) {
        return wait.until(ExpectedConditions.urlContains(value));
    }
}
