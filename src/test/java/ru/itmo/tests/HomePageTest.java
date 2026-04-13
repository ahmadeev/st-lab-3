package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.BaseTest;
import ru.itmo.pages.HomePage;

public class HomePageTest extends BaseTest {
    @Test
    void shouldOpen() {
        HomePage homePage = new HomePage(driver).open();

        Assertions.assertTrue(homePage.isLoaded());
        Assertions.assertTrue(homePage.getTitle().contains("WordPress"));
    }

    @Test
    void shouldNavigate() {
        HomePage homePage = new HomePage(driver).open();

        Assertions.assertTrue(homePage.isLoaded());

        homePage.clickCta();

        Assertions.assertTrue(homePage.isOnboardingPageOpened());
    }
}
