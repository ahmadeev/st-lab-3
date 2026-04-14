package ru.itmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.itmo.Config;
import ru.itmo.BasePage;

public class HomePage extends BasePage {
    private static final String PAGE_PATH = "";

    @FindBy(xpath = "//div/a[contains(@href, 'onboarding')]")
    private WebElement ctaButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        openUrl(Config.get("base.url"), PAGE_PATH);

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
