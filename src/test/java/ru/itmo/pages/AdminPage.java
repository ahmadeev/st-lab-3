package ru.itmo.pages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.itmo.Config;
import ru.itmo.core.BasePage;

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
        String xpath = String.format(
                "//div[contains(@class, 'sidebar')]//a[contains(@href, '%s')]",
                link.getHrefPart()
        );

        waitUntilVisible(By.xpath(xpath)).click();
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
