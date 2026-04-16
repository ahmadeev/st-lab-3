package ru.itmo.framework.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.itmo.framework.config.TestConfig;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    private static final Duration DEFAULT_TIMEOUT = TestConfig.getDurationSeconds("page.timeout.seconds", 10);

    protected final WebDriver driver;
    private final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this(driver, DEFAULT_TIMEOUT);
    }

    protected BasePage(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);

        PageFactory.initElements(driver, this);
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

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
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
        By acceptButton = By.xpath("//button[contains(., 'Принять') or contains(., 'Accept') or contains(., 'Соглас')]");

        List<WebElement> buttons = driver.findElements(acceptButton);

        if (buttons.isEmpty()) {
            return;
        }

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(acceptButton));

            button.click();

            wait.until(ExpectedConditions.invisibilityOf(button));
        } catch (TimeoutException | ElementClickInterceptedException ignored) { }
    }
}
