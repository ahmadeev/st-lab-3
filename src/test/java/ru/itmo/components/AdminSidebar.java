package ru.itmo.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.itmo.framework.base.BaseComponent;

public class AdminSidebar extends BaseComponent {

    public AdminSidebar(WebDriver driver, By rootLocator) {
        super(driver, rootLocator);
    }

    @Override
    public void ensureLoaded() {
        for (Links link : Links.values()) {
            find(getMenuElementLocator(link, false));
        }
    }

    public void clickLinkTo(Links link) {
        By linkLocator = getMenuElementLocator(link, false);
        By activeLinkLocator = getMenuElementLocator(link, true);

        click(linkLocator);
        visible(activeLinkLocator);
    }

    public boolean isNavigatedTo(Links link) {
        By activeLinkLocator = getMenuElementLocator(link, true);

        return isVisible(activeLinkLocator);
    }

    protected By getMenuElementLocator(Links link, boolean isActive) {
        String result = String.format(
                isActive ? "//li[contains(@class, 'selected')]//a[contains(@href, '%s')]" : "//a[contains(@href, '%s')]",
                link.getHrefPart()
        );

        return By.xpath(result);
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
