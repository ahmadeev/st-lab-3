package ru.itmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.itmo.components.AdminSidebar;
import ru.itmo.framework.base.BasePage;

public class AdminPage extends BasePage {
    private static final String PAGE_PATH = "/sites/";

    private final AdminSidebar sidebar;

    public AdminPage(WebDriver driver) {
        super(driver);

        this.sidebar = new AdminSidebar(driver, By.xpath("//div[contains(@class, 'sidebar')]"));
    }

    public AdminPage open() {
        open(PAGE_PATH);
        ensureLoaded();

        return this;
    }

    public void ensureLoaded() {
        sidebar.ensureLoaded();
    }

    public void clickLinkTo(AdminSidebar.Links link) {
        sidebar.clickLinkTo(link);
    }

    public boolean isNavigatedTo(AdminSidebar.Links link) {
        return sidebar.isNavigatedTo(link);
    }
}
