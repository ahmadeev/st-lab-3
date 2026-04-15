package ru.itmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.itmo.framework.page.BasePage;

public class HomePage extends BasePage {
    private static final String PAGE_PATH = "";

    @FindBy(xpath = "//div/a[contains(@href, 'onboarding')]")
    private WebElement ctaButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        open(PAGE_PATH);

        return this;
    }

    public boolean isLoaded() {
        return visible(ctaButton).isDisplayed();
    }

    public void clickCta() {
        clickable(ctaButton).click();
    }

    public boolean isNavigated() {
        return urlContains("onboarding");
    }
}
