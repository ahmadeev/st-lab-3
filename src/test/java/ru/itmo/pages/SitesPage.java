package ru.itmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.itmo.Config;
import ru.itmo.BasePage;

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
        return waitUntilUrlContains(PAGE_PATH);
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

    protected boolean isLoginPageWithContinueVisible() {
        By xpath = By.xpath("//div[contains(@class, 'login')]//a[contains(text(), 'Продолжить')]");

        return driver.findElement(xpath).isDisplayed();
    }

    public void handleLoginPageWithContinue() {
        By xpath = By.xpath("//div[contains(@class, 'login')]//a[contains(text(), 'Продолжить')]");

        waitUntilClickable(xpath).click();
    }

    public void navigateToChatFromCreateSite() {
        showDropdown().clickCreateSiteLink();

        for (int i = 0; i < 5; i++) {
            if (isNavigatedToChat()) {
                return;
            }

            if (isAuthErrorVisible()) {
                handleAuthError();
                continue;
            }

            if (isCookieBannerVisible()) {
                handleCookieBanner();
                continue;
            }

            if (isModalWindowVisible()) {
                handleModalWindow();
                continue;
            }

            if (isLoginPageWithContinueVisible()) {
                handleLoginPageWithContinue();
            }
        }

        throw new IllegalStateException("Failed to reach chat page: unknown state after create site flow");
    }
}
