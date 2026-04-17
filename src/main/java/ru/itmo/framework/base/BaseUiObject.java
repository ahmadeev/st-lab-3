package ru.itmo.framework.base;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.itmo.framework.config.TestConfig;

import java.time.Duration;
import java.util.List;

abstract class BaseUiObject {
    private static final Duration DEFAULT_TIMEOUT = TestConfig.getDurationSeconds("page.timeout.seconds", 10);
    private static final Duration COOKIE_BANNER_TIMEOUT = Duration.ofSeconds(3);
    private static final By COOKIE_ACCEPT_BUTTON = By.cssSelector(
            ".cookie-banner__accept-all-button, .a8c-cookie-banner__button"
    );

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BaseUiObject(WebDriver driver) {
        this(driver, DEFAULT_TIMEOUT);
    }

    protected BaseUiObject(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
    }

    protected abstract void ensureLoaded();

    protected abstract SearchContext searchContext();

    protected WebElement find(By locator) {
        return wait.until(driver -> locate(locator));
    }

    protected WebElement visible(By locator) {
        return wait.until(driver -> {
            WebElement element = locate(locator);

            return element != null && element.isDisplayed() ? element : null;
        });
    }

    protected WebElement clickable(By locator) {
        return wait.until(driver -> {
            WebElement element = locate(locator);

            return isReadyToClick(element) ? element : null;
        });
    }

    protected boolean isVisible(By locator) {
        try {
            return visible(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void click(By locator) {
        wait.until(driver -> {
            WebElement element = locate(locator);

            if (!isReadyToClick(element)) {
                return false;
            }

            scrollToCenter(element);

            try {
                element.click();

                return true;
            } catch (ElementClickInterceptedException e) {
                closeCookieBannerIfPresent();

                return false;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
    }

    protected void closeCookieBannerIfPresent() {
        List<WebElement> buttons = driver.findElements(COOKIE_ACCEPT_BUTTON);

        if (buttons.isEmpty()) {
            return;
        }

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, COOKIE_BANNER_TIMEOUT);
            WebElement button = shortWait.until(ExpectedConditions.elementToBeClickable(COOKIE_ACCEPT_BUTTON));

            button.click();
            shortWait.until(ExpectedConditions.invisibilityOf(button));
        } catch (TimeoutException | ElementClickInterceptedException | StaleElementReferenceException ignored) { }
    }

    private WebElement locate(By locator) {
        try {
            return searchContext().findElement(locator);
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return null;
        }
    }

    private boolean isReadyToClick(WebElement element) {
        return element != null && element.isDisplayed() && element.isEnabled();
    }

    private void scrollToCenter(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                """
                arguments[0].scrollIntoView({
                    block: 'center',
                    inline: 'nearest'
                });
                """,
                element
        );
    }
}
