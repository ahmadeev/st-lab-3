package ru.itmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.itmo.BasePage;
import ru.itmo.Config;

import java.time.Duration;

public class ThemesPage extends BasePage {
    private static final String PAGE_PATH = "/theme";

    @FindBy(xpath = "//div[contains(@class, 'theme')]//button[contains(@role, 'combobox')]")
    private WebElement selectTrigger;

    @FindBy(xpath = "//div[contains(@class, 'theme')]//div[contains(@role, 'option') and contains(., 'Бесплатно')]")
    private WebElement selectOption;

    @FindBy(xpath = "//div[contains(@class, 'theme-card')]//a[contains(@href, '/theme/')]")
    private WebElement firstItem;

    @FindBy(xpath = "//button[contains(., 'Предпросмотр')]")
    private WebElement previewButton;

    @FindBy(xpath = "//div[contains(@class, 'web-preview') and contains(@class, 'content')]")
    private WebElement previewModal;

    public ThemesPage(WebDriver driver, Duration timeout) {
        super(driver, timeout);
    }

    public ThemesPage open() {
        openUrl(Config.get("base.url"), PAGE_PATH);

        return this;
    }

    public boolean isLoaded() {
        return driver.getCurrentUrl().contains(PAGE_PATH);
    }

    public void previewTheme() {
        showPopover().clickSelectOption().clickFirstItem().clickPreviewButton();
    }

    public ThemesPage showPopover() {
        waitUntilClickable(selectTrigger).click();

        return this;
    }

    public ThemesPage clickSelectOption() {
        waitUntilClickable(selectOption).click();

        return this;
    }

    public ThemesPage clickFirstItem() {
        waitUntilClickable(firstItem).click();

        return this;
    }

    public ThemesPage clickPreviewButton() {
        waitUntilClickable(previewButton).click();

        return this;
    }

    public boolean isPreviewed() {
        try {
            waitUntilVisible(previewModal);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
