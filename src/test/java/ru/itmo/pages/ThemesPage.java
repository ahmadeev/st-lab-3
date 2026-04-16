package ru.itmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.itmo.framework.base.BasePage;

import java.time.Duration;

public class ThemesPage extends BasePage {
    private static final String PAGE_PATH = "/theme";
    private static final Duration PAGE_TIMEOUT = Duration.ofSeconds(20);
    private static final By SELECT_TRIGGER = By.xpath("//div[contains(@class, 'theme')]//button[contains(@role, 'combobox')]");
    private static final By SELECT_OPTION = By.xpath("//div[contains(@class, 'theme')]//div[contains(@role, 'option') and contains(., 'Бесплатно')]");
    private static final By FIRST_ITEM = By.xpath("//div[contains(@class, 'theme-card')]//a[contains(@href, '/theme/')]");
    private static final By PREVIEW_BUTTON = By.xpath("//button[contains(., 'Предпросмотр')]");
    private static final By PREVIEW_MODAL = By.xpath("//div[contains(@class, 'web-preview') and contains(@class, 'content')]");

    public ThemesPage(WebDriver driver) {
        super(driver, PAGE_TIMEOUT);
    }

    @Override
    protected void ensureLoaded() {
        visible(SELECT_TRIGGER);
    }

    public ThemesPage open() {
        open(PAGE_PATH);
        ensureLoaded();

        return this;
    }

    public void previewFirstFreeTheme() {
        clickable(SELECT_TRIGGER).click();
        clickable(SELECT_OPTION).click();
        clickable(FIRST_ITEM).click();
        clickable(PREVIEW_BUTTON).click();
    }

    public boolean isPreviewed() {
        return isVisible(PREVIEW_MODAL);
    }
}
