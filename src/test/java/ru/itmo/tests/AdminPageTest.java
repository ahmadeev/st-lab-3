package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.components.AdminSidebar;
import ru.itmo.pages.AdminPage;
import ru.itmo.support.AuthBaseTest;

public class AdminPageTest extends AuthBaseTest {
    @Test
    void shouldNavigate() {
        AdminPage page = new AdminPage(driver);

        page.open();

        for (AdminSidebar.Links link : AdminSidebar.Links.values()) {
            page.clickLinkTo(link);
            Assertions.assertTrue(page.isNavigatedTo(link));
        }
    }
}
