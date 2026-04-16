package ru.itmo.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.itmo.framework.base.BasePage;

public class GoogleHomePage extends BasePage {
    private static final String PAGE_PATH = "/ncr";

    @FindBy(xpath = "//textarea[@name='q']")
    private WebElement searchField;

    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }

    public GoogleHomePage open() {
        openAbsolute("https://www.google.com" + PAGE_PATH);

        return this;
    }

    public boolean isLoaded() {
        return visible(searchField).isDisplayed();
    }

    public boolean isSearchFieldReady() {
        return clickable(searchField).isEnabled();
    }

    public void search(String query) {
        WebElement field = visible(searchField);

        field.clear();
        field.sendKeys(query);
        field.sendKeys(Keys.ENTER);
    }

    public boolean areResultsOpenedFor(String query) {
        return urlContains("/search") && titleContains(query);
    }
}
