package ru.itmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ru.itmo.framework.base.BasePage;

import java.util.Arrays;

public class SitesPage extends BasePage {
    private static final String PAGE_PATH = "/sites";
    private static final By DROPDOWN_BUTTON = By.xpath("//div[contains(@class, 'dropdown')]//button");
    private static final By CREATE_SITE_LINK = By.xpath("//div[contains(@class, 'popover')]//a[contains(@href, 'ai-site-builder')]");
    private static final By CHAT_TEXT_AREA = By.xpath("//div[contains(@class, 'chat-input')]//textarea");
    private static final By SHOW_MENU_BUTTON = By.xpath("//div[contains(@class, 'dashboard')]//td//button[contains(@aria-haspopup, 'menu')]");
    private static final By SETTINGS_MENU_OPTION = By.xpath("//div[contains(@role, 'menuitem')][.//span[contains(., 'Настройки')]]");
    private static final By SETTINGS_ANCHOR = By.xpath("//h1[contains(., 'Настройки')]");
    private static final By DELETE_SITE_BUTTON = By.xpath("//div[contains(@class, 'dashboard')]//button[contains(., 'Удалить')]");
    private static final By DELETE_MODAL_INPUT = By.xpath("//div[contains(@role, 'dialog')]//input");
    private static final By DELETE_MODAL_BUTTON = By.xpath("//div[contains(@role, 'dialog')]//button[contains(., 'Удалить')]");

    public SitesPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void ensureLoaded() {
        visible(DROPDOWN_BUTTON);
    }

    public SitesPage open() {
        open(PAGE_PATH);
        ensureLoaded();

        return this;
    }

    public SitesPage startSiteCreation() {
        clickable(DROPDOWN_BUTTON).click();
        clickable(CREATE_SITE_LINK).click();

        return this;
    }

    public boolean isNavigatedToChat() {
        return isVisible(CHAT_TEXT_AREA);
    }

    public String startSiteDeletion() {
        final String wpUrl = "wordpress.com";

        clickable(SHOW_MENU_BUTTON).click();
        clickable(SETTINGS_MENU_OPTION).click();

        // todo
        String siteName = Arrays.stream(currentUrl().split("/"))
                .filter((s) -> s.endsWith(wpUrl) && !s.startsWith(wpUrl))
                .toList()
                .get(0);

        visible(SETTINGS_ANCHOR);

        WebElement deleteSiteButton = visible(DELETE_SITE_BUTTON);
        new Actions(driver).scrollToElement(deleteSiteButton).perform();

        deleteSiteButton.click();
        visible(DELETE_MODAL_INPUT).sendKeys(siteName);
        clickable(DELETE_MODAL_BUTTON).click();

        return siteName;
    }

    public boolean isSiteDeleted(String siteName) {
        By snackbar = By.xpath(String.format(
                "//*[(contains(., 'удален') or contains(., 'удалён')) and contains(., '%s')]",
                siteName
        ));

        return isVisible(snackbar);
    }
}
