package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.pages.HomePage;
import ru.itmo.support.BaseTest;

public class HomePageTest extends BaseTest {
    @Test
    void shouldNavigate() {
        HomePage page = new HomePage(driver);

        page.open();

        page.clickCta();

        Assertions.assertTrue(page.isNavigated());
    }
}
