package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.AuthBaseTest;
import ru.itmo.pages.SitesPage;

public class SitesPageTest extends AuthBaseTest {
    @Test
    void shouldNavigate() {
        SitesPage page = new SitesPage(driver);

        if (!page.isLoaded()) {
            page.open();
        }

        page.handleCookieBanner();

        page.navigateToChatFromCreateSite();

        Assertions.assertTrue(page.isNavigatedToChat());
    }
}
