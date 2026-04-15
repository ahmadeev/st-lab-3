package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.AuthBaseTest;
import ru.itmo.BaseTest;
import ru.itmo.SessionManager;
import ru.itmo.pages.AdminPage;

public class AdminPageTest extends BaseTest {
    @Test
    void shouldNavigate() {
        AdminPage page = new AdminPage(driver);

        SessionManager.clearSavedSession();
        SessionManager.ensureLoggedIn(driver);

        if (!page.isLoaded()) {
            page.open();
        }

        for (AdminPage.Links link : AdminPage.Links.values()) {
            page.clickLinkTo(link);
            Assertions.assertTrue(page.isNavigatedTo(link));
        }
    }
}
