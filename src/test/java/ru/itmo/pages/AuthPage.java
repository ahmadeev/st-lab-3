package ru.itmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import ru.itmo.framework.config.TestConfig;
import ru.itmo.framework.base.BasePage;

public class AuthPage extends BasePage {
    private static final String PAGE_PATH = "/log-in";

    @FindBy(xpath = "//input[contains(@autocomplete, 'username')]")
    private WebElement usernameInput;

    @FindBy(xpath = "//input[contains(@autocomplete, 'password')]")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//header//a[contains(@href, '/me')]")
    private WebElement profileLink;

    @FindBy(xpath = "//header//button[contains(., 'Выйти')]")
    private WebElement logoutButton;

    @FindBy(xpath = "//a[contains(@href, '/log-in')]")
    private WebElement loginLink;

    public AuthPage(WebDriver driver) {
        super(driver);
    }

    public AuthPage open() {
        open(PAGE_PATH);

        return this;
    }

    public boolean isLoaded() {
        return visible(usernameInput).isDisplayed();
    }

    public void logIn() {
        enterEmail(TestConfig.get("email"))
                .clickSubmit()
                .enterPassword(TestConfig.get("password"))
                .clickSubmit();
    }

    public AuthPage enterEmail(String email) {
        visible(usernameInput).clear();
        usernameInput.sendKeys(email);

        return this;
    }

    public AuthPage enterPassword(String password) {
        visible(passwordInput).clear();
        passwordInput.sendKeys(password);

        return this;
    }

    public AuthPage clickSubmit() {
        clickable(submitButton).click();

        return this;
    }

    public boolean waitUntilAuthorized() {
        return titleDoesNotContain("Log In");
    }

    public AuthPage logout() {
        visible(profileLink);
        new Actions(driver).moveToElement(profileLink).perform();
        clickable(logoutButton).click();

        return this;
    }

    public boolean isLoggedOut() {
        try {
            visible(loginLink);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
