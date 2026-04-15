package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import ru.itmo.pages.SitesPage;
import ru.itmo.support.AuthBaseTest;

public class SitesPageTest extends AuthBaseTest {
    @Test
    @Order(1)
    void shouldCreate() {
        SitesPage page = new SitesPage(driver);

        page.open();

        page.startSiteCreation();

        Assertions.assertTrue(page.isNavigatedToChat());
    }

    @Test
    @Order(2)
    void shouldDelete() {
        SitesPage page = new SitesPage(driver);

        page.open();

        String url = page.startSiteDeletion();

        Assertions.assertTrue(page.isSiteDeleted(url));
    }
}
