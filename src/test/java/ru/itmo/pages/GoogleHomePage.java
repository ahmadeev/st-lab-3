package ru.itmo.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.itmo.core.BasePage;

public class GoogleHomePage extends BasePage {
    private static final String PAGE_PATH = "/ncr";

    @FindBy(xpath = "//textarea[@name='q']")
    private WebElement searchField;

    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }

    public GoogleHomePage open() {
        openUrl("https://www.google.com", PAGE_PATH);

        return this;
    }

    public boolean isLoaded() {
        return waitUntilVisible(searchField).isDisplayed();
    }

    public boolean isSearchFieldReady() {
        return waitUntilClickable(searchField).isEnabled();
    }

    public void search(String query) {
        WebElement field = waitUntilVisible(searchField);

        field.clear();
        field.sendKeys(query);
        field.sendKeys(Keys.ENTER);
    }

    public boolean areResultsOpenedFor(String query) {
        return waitUntilUrlContains("/search") && waitUntilTitleContains(query);
    }
}
