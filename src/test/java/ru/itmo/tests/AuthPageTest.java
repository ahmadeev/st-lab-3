package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import ru.itmo.pages.AuthPage;
import ru.itmo.support.BaseTest;

public class AuthPageTest extends BaseTest {
    @Test
    @Order(0)
    void shouldOpen() {
        AuthPage page = new AuthPage(driver);

        page.open();

        Assertions.assertTrue(page.isLoaded());
        Assertions.assertTrue(page.getTitle().contains("Log In"));
    }

    @Test
    @Order(1)
    void shouldLogIn() {
        AuthPage page = new AuthPage(driver);

        page.open();

        Assertions.assertTrue(page.isLoaded()); // notes: ?

        page.logIn();

        Assertions.assertTrue(page.waitUntilAuthorized());
    }

    @Test
    @Order(2)
    void shouldLogOut() {
        AuthPage page = new AuthPage(driver);

        page.open();

        page.logIn();

        Assertions.assertTrue(page.waitUntilAuthorized());

        page.logout();

        Assertions.assertTrue(page.isLoggedOut());
    }
}
