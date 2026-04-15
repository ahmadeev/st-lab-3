package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.support.BaseTest;
import ru.itmo.pages.AuthPage;

public class AuthPageTest extends BaseTest {
    @Test
    void shouldOpen() {
        AuthPage page = new AuthPage(driver).open();

        Assertions.assertTrue(page.isLoaded());
        Assertions.assertTrue(page.getTitle().contains("Log In"));
    }

    @Test
    void shouldLogIn() {
        AuthPage page = new AuthPage(driver).open();

        Assertions.assertTrue(page.isLoaded()); // notes: ?

        page.logIn();

        Assertions.assertTrue(page.waitUntilAuthorized());
    }
}
