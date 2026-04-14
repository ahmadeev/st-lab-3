package ru.itmo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(1000);

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

    protected boolean isLogInPage() {
        String currentUrl = driver.getCurrentUrl();

        if (currentUrl == null) {
            throw new IllegalStateException("url turned to null");
        }

        return currentUrl.contains("log-in");
    }

    protected boolean isCookieBannerVisible() {
        final By cookieBanner = By.xpath("//form[contains(@class, 'cookie-banner')]");

        return driver.findElement(cookieBanner).isDisplayed();
    }

    public void handleCookieBanner() {
        final By cookieBanner = By.xpath("//form[contains(@class, 'cookie-banner')]");
        final By acceptCookiesButton = By.xpath("//a[contains(., 'Принять')]");

        List<WebElement> banners = driver.findElements(cookieBanner);

        if (!banners.isEmpty() && banners.get(0).isDisplayed()) {
            waitUntilClickable(acceptCookiesButton).click();
            waitUntilInvisible(cookieBanner);
        }
    }

    protected boolean isModalWindowVisible() {
        final By modalWindow = By.xpath("//div[contains(@class, 'modal')]");

        return driver.findElement(modalWindow).isDisplayed();
    }

    public void handleModalWindow() {
        final By modalWindow = By.xpath("//div[contains(@class, 'modal')]");
        final By closeModalButton = By.xpath("//button[contains(aria-label, 'Закрыть')]");

        List<WebElement> modals = driver.findElements(modalWindow);

        if (!modals.isEmpty() && modals.get(0).isDisplayed()) {
            waitUntilClickable(closeModalButton).click();
            waitUntilInvisible(modalWindow);
        }
    }

    protected boolean isAuthErrorVisible() {
        final By errorBox = By.xpath("//p[contains(@class, 'error') and contains(text(), 'authorization_required')]");

        return driver.findElement(errorBox).isDisplayed();
    }

    public void handleAuthError() {
        final By errorBox = By.xpath("//p[contains(@class, 'error') and contains(text(), 'authorization_required')]");

        List<WebElement> errors = driver.findElements(errorBox);

        if (!errors.isEmpty() && errors.get(0).isDisplayed()) {
            SessionManager.clearSavedSession();
            SessionManager.ensureLoggedIn(driver);
        }
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
