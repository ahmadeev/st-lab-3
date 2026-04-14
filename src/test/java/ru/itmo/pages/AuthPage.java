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
        driver.get(PAGE_URL);

        return this;
    }

    public boolean isLoaded() {
        return usernameInput.isDisplayed();
    }

    public void logIn() {
        waitUntilVisible(usernameInput).sendKeys(Config.get("EMAIL"));

        submitButton.click();

        waitUntilVisible(passwordInput).sendKeys(Config.get("PASSWORD"));

        submitButton.click();
    }

    public boolean isNavigated() {
        return waitUntilUrlContains("sites");
    }
}
