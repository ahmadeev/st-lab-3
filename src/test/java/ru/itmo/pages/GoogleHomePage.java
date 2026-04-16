package ru.itmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.itmo.framework.base.BasePage;

public class GoogleHomePage extends BasePage {
    private static final String PAGE_PATH = "/ncr";
    private static final By SEARCH_FIELD = By.xpath("//textarea[@name='q']");

    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void ensureLoaded() {
        visible(SEARCH_FIELD);
    }

    public GoogleHomePage open() {
        openAbsolute("https://www.google.com" + PAGE_PATH);
        ensureLoaded();

        return this;
    }

    public boolean isSearchFieldReady() {
        return clickable(SEARCH_FIELD).isEnabled();
    }

    public void search(String query) {
        WebElement field = visible(SEARCH_FIELD);

        field.clear();
        field.sendKeys(query);
        field.sendKeys(Keys.ENTER);
    }

    public boolean areResultsOpenedFor(String query) {
        return urlContains("/search") && titleContains(query);
    }
}
