package ru.itmo.framework.base;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.itmo.framework.config.TestConfig;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    private static final Duration DEFAULT_TIMEOUT = TestConfig.getDurationSeconds("page.timeout.seconds", 10);
    private static final Duration COOKIE_BANNER_TIMEOUT = Duration.ofSeconds(3);
    private static final By COOKIE_ACCEPT_BUTTON = By.xpath(
            "//button[contains(., 'Принять') or contains(., 'Accept') or contains(., 'Соглас')]"
    );

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this(driver, DEFAULT_TIMEOUT);
    }

    protected BasePage(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);

        PageFactory.initElements(driver, this);
    }

    protected abstract void ensureLoaded();

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

    public String getTitle() {
        return driver.getTitle();
    }

    protected WebElement find(By locator) {
        return wait.until(driver -> searchContext().findElement(locator));
    }

    protected WebElement visible(By locator) {
        return wait.until(driver -> {
            WebElement element = searchContext().findElement(locator);

            return element.isDisplayed() ? element : null;
        });
    }

    protected WebElement visible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement clickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected boolean isVisible(By locator) {
        try {
            return visible(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
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

    public void closeCookieBannerIfPresent() {
        List<WebElement> buttons = driver.findElements(COOKIE_ACCEPT_BUTTON);

        if (buttons.isEmpty()) {
            return;
        }

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, COOKIE_BANNER_TIMEOUT);

            WebElement button = shortWait.until(ExpectedConditions.elementToBeClickable(COOKIE_ACCEPT_BUTTON));

            button.click();

            shortWait.until(ExpectedConditions.invisibilityOf(button));
        } catch (TimeoutException | ElementClickInterceptedException ignored) { }
    }
}
