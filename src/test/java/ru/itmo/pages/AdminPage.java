package ru.itmo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.itmo.BaseTest;
import ru.itmo.core.BasePage;

public class AdminPage extends BasePage {
    private static final String PAGE_URL = "https://wordpress.com/sites/";

    @FindBy(xpath = "//div[contains(@class, 'sidebar')]//a[contains(@href, 'domains')]")
    private WebElement domainsLink;

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public AdminPage open() {
        openUrl(PAGE_URL);

        return this;
    }

    public boolean isCurrentPage() {
        try {
            return driver.getCurrentUrl().startsWith(PAGE_URL);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoaded() {
        return domainsLink.isDisplayed();
    }

    public void clickDomainsLink() {
        waitUntilVisible(domainsLink).click();
    }

    public boolean isNavigated() {
        return waitUntilUrlContains("domains");
    }
}
