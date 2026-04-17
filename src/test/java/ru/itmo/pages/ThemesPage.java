package ru.itmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import ru.itmo.framework.base.BasePage;

import java.time.Duration;

public class ThemesPage extends BasePage {
    private static final String PAGE_PATH = "/theme";
    private static final Duration PAGE_TIMEOUT = Duration.ofSeconds(20);
    private static final By SELECT_TRIGGER = By.xpath("//div[contains(@class, 'theme')]//button[contains(@role, 'combobox')]");
    private static final By SELECT_OPTION = By.xpath("//div[contains(@class, 'theme')]//div[contains(@role, 'option') and contains(., 'Бесплатно')]");
    private static final By POPOVER_OPTION = By.xpath("//div[contains(@class, 'components-popover')]//button[contains(@role, 'menuitem') and contains(., 'демо')]");
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

    public ThemesPage openThemeFilter() {
        click(SELECT_TRIGGER);

        return this;
    }

    public ThemesPage selectFreeThemes() {
        click(SELECT_OPTION);

        return this;
    }

    public ThemesPage openFirstTheme() {
        click(FIRST_ITEM);

        return this;
    }

    public ThemesPage openPreview() {
        click(PREVIEW_BUTTON);

        return this;
    }

    public ThemesPage openPreviewDemo() {
        click(POPOVER_OPTION);

        return this;
    }

    public void previewFirstFreeTheme() {
        openThemeFilter()
                .selectFreeThemes()
                .openFirstTheme()
                .openPreview();

        try {
            if (!isPreviewed()) {
                throw new TimeoutException();
            }
        } catch (TimeoutException e) {
            // fallback на случай отличной верстки (если есть сайт)
            openPreviewDemo();
        }
    }

    public boolean isPreviewed() {
        return isVisible(PREVIEW_MODAL);
    }
}
