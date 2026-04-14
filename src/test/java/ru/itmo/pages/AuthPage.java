package ru.itmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.itmo.Config;
import ru.itmo.core.BasePage;

public class AuthPage extends BasePage {
    private static final String PAGE_URL = "https://wordpress.com/log-in/";

    @FindBy(xpath = "//input[contains(@autocomplete, 'username')]")
    private WebElement usernameInput;

    @FindBy(xpath = "//input[contains(@autocomplete, 'password')]")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    public AuthPage(WebDriver driver) {
        super(driver);
    }

    public AuthPage open() {
        openUrl(PAGE_URL);

        return this;
    }

    public boolean isLoaded() {
        return usernameInput.isDisplayed();
    }

    public void logIn() {
        this.enterEmail(Config.get("email"))
                .clickSubmit()
                .enterPassword(Config.get("password"))
                .clickSubmit();
    }

    public AuthPage enterEmail(String email) {
        waitUntilVisible(usernameInput).clear();
        usernameInput.sendKeys(email);

        return this;
    }

    public AuthPage enterPassword(String password) {
        waitUntilVisible(passwordInput).clear();
        passwordInput.sendKeys(password);

        return this;
    }

    public AuthPage clickSubmit() {
        waitUntilClickable(submitButton).click();

        return this;
    }

    public boolean waitUntilAuthorized() {
        return waitUntilTitleDoesNotContain("Log In");
    }

    public boolean isNavigated() {
        return waitUntilUrlContains("sites");
    }
}
