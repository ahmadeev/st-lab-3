package ru.itmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.itmo.framework.page.BasePage;

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
        open(PAGE_PATH);

        return this;
    }

    public boolean isLoaded() {
        return visible(dropdownButton).isDisplayed();
    }

    public SitesPage startSiteCreation() {
        clickable(dropdownButton).click();
        clickable(createSiteLink).click();

        return this;
    }

    public boolean isNavigatedToChat() {
        try {
            visible(chatTextArea);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
