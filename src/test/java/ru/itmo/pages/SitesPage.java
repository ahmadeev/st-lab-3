package ru.itmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import ru.itmo.framework.base.BasePage;

import java.util.Arrays;

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

    public String startSiteDeletion() {
        final String wpUrl = "wordpress.com";

        String showMenuButton = "//div[contains(@class, 'dashboard')]//td//button[contains(@aria-haspopup, 'menu')]";
        String settingMenuOption = "//div[contains(@role, 'menuitem')][.//span[contains(., 'Настройки')]]";
        String anchor = "//h1[contains(., 'Настройки')]";
        String deleteSiteButton = "//div[contains(@class, 'dashboard')]//button[contains(., 'Удалить')]";
        String modalInput = "//div[contains(@role, 'dialog')]//input";
        String modalDeleteButton = "//div[contains(@role, 'dialog')]//button[contains(., 'Удалить')]";

        clickable(By.xpath(showMenuButton)).click();
        clickable(By.xpath(settingMenuOption)).click();

        // notes: ?
        String siteName = Arrays.stream(currentUrl().split("/"))
                .filter((s) -> s.endsWith(wpUrl) && !s.startsWith(wpUrl))
                .toList()
                .get(0);

        visible(By.xpath(anchor));

        new Actions(driver).scrollToElement(driver.findElement(By.xpath(deleteSiteButton))).perform();

        visible(By.xpath(deleteSiteButton));

        clickable(By.xpath(deleteSiteButton)).click();
        visible(By.xpath(modalInput)).sendKeys(siteName);
        clickable(By.xpath(modalDeleteButton)).click();

        return siteName;
    }

    public boolean isSiteDeleted(String siteName) {
        String snackbar = String.format(
                "//*[(contains(., 'удален') or contains(., 'удалён')) and contains(., '%s')]",
                siteName
        );

        try {
            visible(By.xpath(snackbar));

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
