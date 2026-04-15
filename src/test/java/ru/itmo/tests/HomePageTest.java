package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.pages.HomePage;
import ru.itmo.support.BaseTest;

public class HomePageTest extends BaseTest {
    @Test
    void shouldOpen() {
        HomePage page = new HomePage(driver);

        page.open();

        Assertions.assertTrue(page.isLoaded());
        Assertions.assertTrue(page.getTitle().contains("WordPress"));
    }

    @Test
    void shouldNavigate() {
        HomePage homePage = new HomePage(driver).open();

        Assertions.assertTrue(homePage.isLoaded()); // notes: ?

        homePage.clickCta();

        Assertions.assertTrue(homePage.isNavigated());
    }
}
