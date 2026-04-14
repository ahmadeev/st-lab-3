package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.AuthBaseTest;
import ru.itmo.pages.AdminPage;

public class AdminPageTest extends AuthBaseTest {
    @Test
    void shouldNavigate() {
        AdminPage page = new AdminPage(driver);

        if (!page.isCurrentPage()) {
            page.open();
        }

        page.clickDomainsLink();

        Assertions.assertTrue(page.isNavigated());
    }
}
