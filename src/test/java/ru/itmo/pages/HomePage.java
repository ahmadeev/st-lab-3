package ru.itmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.itmo.framework.base.BasePage;

public class HomePage extends BasePage {
    private static final String PAGE_PATH = "";
    private static final By CTA_BUTTON = By.xpath("//div/a[contains(@href, 'onboarding')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void ensureLoaded() {
        visible(CTA_BUTTON);
    }

    public HomePage open() {
        open(PAGE_PATH);
        ensureLoaded();

        return this;
    }

    public void clickCta() {
        clickable(CTA_BUTTON).click();
    }

    public boolean isNavigated() {
        return urlContains("onboarding");
    }
}
