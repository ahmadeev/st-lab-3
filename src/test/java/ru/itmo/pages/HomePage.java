package ru.itmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.itmo.core.BasePage;

public class HomePage extends BasePage {
    private static final String PAGE_URL = "https://wordpress.com/";

    @FindBy(xpath = "//div/a[contains(@href, 'onboarding')]")
    private WebElement ctaButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(PAGE_URL);

        return this;
    }

    public boolean isLoaded() {
        return ctaButton.isDisplayed();
    }

    public void clickCta() {
        ctaButton.click();
    }

    public boolean isNavigated() {
        return waitUntilUrlContains("onboarding");
    }
}
