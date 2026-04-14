package ru.itmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.itmo.BasePage;
import ru.itmo.Config;

public class SitesPage extends BasePage {
    private static final String PAGE_PATH = "/sites";

    @FindBy(xpath = "//div[contains(@class, 'dropdown')]//button")
    private WebElement dropdownButton;

    @FindBy(xpath = "//div[contains(@class, 'popover')]//a[contains(@href, 'ai-site-builder')]")
    private WebElement createSiteLink;

    @FindBy(xpath = "//div[contains(@class, 'chat-input')]//textarea")
    private WebElement chatTextArea;

    public SitesPage(WebDriver driver) {
        super(driver);
    }

    public SitesPage open() {
        openUrl(Config.get("base.url"), PAGE_PATH);

        return this;
    }

    public boolean isLoaded() {
        return driver.getCurrentUrl().contains(PAGE_PATH);
    }

    public SitesPage showDropdown() {
        waitUntilVisible(dropdownButton).click();

        return this;
    }

    public SitesPage clickCreateSiteLink() {
        waitUntilVisible(createSiteLink).click();

        return this;
    }

    public boolean isNavigatedToChat() {
        try {
            waitUntilVisible(chatTextArea);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
