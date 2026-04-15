package ru.itmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.itmo.framework.page.BasePage;

import java.time.Duration;

public class ThemesPage extends BasePage {
    private static final String PAGE_PATH = "/theme";
    private static final Duration PAGE_TIMEOUT = Duration.ofSeconds(20);

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

    public ThemesPage(WebDriver driver) {
        super(driver, PAGE_TIMEOUT);
    }

    public ThemesPage open() {
        open(PAGE_PATH);

        return this;
    }

    public boolean isLoaded() {
        return visible(selectTrigger).isDisplayed();
    }

    public void previewFirstFreeTheme() {
        clickable(selectTrigger).click();
        clickable(selectOption).click();
        clickable(firstItem).click();
        clickable(previewButton).click();
    }

    public boolean isPreviewed() {
        try {
            visible(previewModal);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
