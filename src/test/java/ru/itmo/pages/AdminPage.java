package ru.itmo.pages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.itmo.Config;
import ru.itmo.BasePage;

import java.time.Duration;

public class AdminPage extends BasePage {
    private static final String PAGE_PATH = "/sites/";

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public AdminPage open() {
        openUrl(Config.get("base.url"), PAGE_PATH);

        return this;
    }

    public boolean isLoaded() {
        String currentUrl = driver.getCurrentUrl();

        if (currentUrl == null) {
            return false;
        }

        String url = Config.get("base.url") + PAGE_PATH;

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

        waitUntilClickable(By.xpath(navLink)).click();
        waitUntilVisible(By.xpath(activeNavLink));
    }

    public boolean isNavigatedTo(Links link) {
        return waitUntilUrlContains(link.getHrefPart());
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
