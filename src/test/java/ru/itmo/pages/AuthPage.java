package ru.itmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ru.itmo.framework.base.BasePage;
import ru.itmo.framework.config.TestConfig;

public class AuthPage extends BasePage {
    private static final String PAGE_PATH = "/log-in";
    private static final By USERNAME_INPUT = By.xpath("//input[contains(@autocomplete, 'username')]");
    private static final By PASSWORD_INPUT = By.xpath("//input[contains(@autocomplete, 'password')]");
    private static final By SUBMIT_BUTTON = By.xpath("//button[@type='submit']");
    private static final By PROFILE_LINK = By.xpath("//header//a[contains(@href, '/me')]");
    private static final By LOGOUT_BUTTON = By.xpath("//header//button[contains(., 'Выйти')]");
    private static final By LOGIN_LINK = By.xpath("//a[contains(@href, '/log-in')]");

    public AuthPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void ensureLoaded() {
        visible(USERNAME_INPUT);
    }

    public AuthPage open() {
        open(PAGE_PATH);
        ensureLoaded();

        return this;
    }

    public void logIn() {
        enterEmail(TestConfig.get("email"))
                .clickSubmit()
                .enterPassword(TestConfig.get("password"))
                .clickSubmit();
    }

    public AuthPage enterEmail(String email) {
        WebElement field = visible(USERNAME_INPUT);

        field.clear();
        field.sendKeys(email);

        return this;
    }

    public AuthPage enterPassword(String password) {
        WebElement field = visible(PASSWORD_INPUT);

        field.clear();
        field.sendKeys(password);

        return this;
    }

    public AuthPage clickSubmit() {
        click(SUBMIT_BUTTON);

        return this;
    }

    public boolean waitUntilAuthorized() {
        return titleDoesNotContain("Log In");
    }

    public void logout() {
        driver.get(TestConfig.get("base.url") + "/sites");

        WebElement profileLink;

        try {
            profileLink = clickable(By.xpath("//header//a[contains(@href, '/me')]"));
        } catch (TimeoutException e) {
            // fallback на случай отличной верстки (если есть сайт)
            profileLink = clickable(By.xpath("//header//div[contains(@class, 'item-wrapper')][.//a[@href='/me']]"));
        }

        new Actions(driver).moveToElement(profileLink).perform();

        click(LOGOUT_BUTTON);
    }

    public boolean isLoggedOut() {
        return isVisible(LOGIN_LINK);
    }
}
