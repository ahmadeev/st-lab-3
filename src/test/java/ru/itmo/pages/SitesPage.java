package ru.itmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.itmo.framework.base.BasePage;

import java.time.Duration;
import java.util.Arrays;

public class SitesPage extends BasePage {
    private static final String PAGE_PATH = "/sites";
    private static final Duration PAGE_TIMEOUT = Duration.ofSeconds(20);
    private static final String WORDPRESS_DOMAIN = "wordpress.com";
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
        super(driver, PAGE_TIMEOUT);
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

    public SitesPage openCreationMenu() {
        click(DROPDOWN_BUTTON);

        return this;
    }

    public SitesPage chooseAiSiteBuilder() {
        click(CREATE_SITE_LINK);

        return this;
    }

    public SitesPage startSiteCreation() {
        return openCreationMenu().chooseAiSiteBuilder();
    }

    public boolean isNavigatedToChat() {
        return isVisible(CHAT_TEXT_AREA);
    }

    public SitesPage openSiteActionsMenu() {
        click(SHOW_MENU_BUTTON);

        return this;
    }

    public SitesPage openSettings() {
        click(SETTINGS_MENU_OPTION);

        return this;
    }

    public String getCurrentSiteName() {
        String siteName = Arrays.stream(currentUrl().split("/"))
                .filter((s) -> s.endsWith(WORDPRESS_DOMAIN) && !s.startsWith(WORDPRESS_DOMAIN))
                .toList()
                .get(0);

        return siteName;
    }

    public SitesPage waitForSettingsPage() {
        visible(SETTINGS_ANCHOR);

        return this;
    }

    public SitesPage requestSiteDeletion() {
        click(DELETE_SITE_BUTTON);

        return this;
    }

    public SitesPage confirmSiteDeletion(String siteName) {
        visible(DELETE_MODAL_INPUT).sendKeys(siteName);
        click(DELETE_MODAL_BUTTON);

        return this;
    }

    public String startSiteDeletion() {
        openSiteActionsMenu()
                .openSettings()
                .waitForSettingsPage();

        String siteName = getCurrentSiteName();

        requestSiteDeletion().confirmSiteDeletion(siteName);

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
