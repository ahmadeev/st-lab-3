package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.BaseTest;
import ru.itmo.SessionManager;
import ru.itmo.pages.SitesPage;

public class SitesPageTest extends BaseTest {
    @Test
    void shouldNavigate() {
        SitesPage page = new SitesPage(driver);

        SessionManager.clearSavedSession();
        SessionManager.ensureLoggedIn(driver);

        if (!page.isLoaded()) {
            page.open();
        }

        page.showDropdown().clickCreateSiteLink();

        Assertions.assertTrue(page.isNavigatedToChat());
    }
}
