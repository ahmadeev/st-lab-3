package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.pages.SitesPage;
import ru.itmo.support.AuthBaseTest;

public class SitesPageTest extends AuthBaseTest {
    @Test
    void shouldNavigate() {
        SitesPage page = new SitesPage(driver);

        page.open();

        page.startSiteCreation();

        Assertions.assertTrue(page.isNavigatedToChat());
    }
}
