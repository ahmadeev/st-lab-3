package ru.itmo.pages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.itmo.framework.config.TestConfig;
import ru.itmo.framework.page.BasePage;

public class AdminPage extends BasePage {
    private static final String PAGE_PATH = "/sites/";

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public AdminPage open() {
        open(PAGE_PATH);

        return this;
    }

    public boolean isLoaded() {
        String currentUrl = currentUrl();

        if (currentUrl == null) {
            return false;
        }

        String url = TestConfig.get("base.url") + PAGE_PATH;

        return currentUrl.startsWith(url);
    }

    public void clickLinkTo(Links link) {
        String navLink = String.format(
                "//div[contains(@class, 'sidebar')]//a[contains(@href, '%s')]",
                link.getHrefPart()
        );
        String activeNavLink = String.format(
                "//div[contains(@class, 'sidebar')]//li[contains(@class, 'selected')]//a[contains(@href, '%s')]",
                link.getHrefPart()
        );

        clickable(By.xpath(navLink)).click();
        visible(By.xpath(activeNavLink));
    }

    public boolean isNavigatedTo(Links link) {
        return urlContains(link.getHrefPart());
    }

    @RequiredArgsConstructor
    @Getter
    public enum Links {
        SITES("sites"),
        DOMAINS("domains"),
        THEMES("themes"),
        PLUGINS("plugins");

        private final String hrefPart;
    }
}
