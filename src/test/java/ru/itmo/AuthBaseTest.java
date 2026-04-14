package ru.itmo;

import org.junit.jupiter.api.BeforeEach;

public abstract class AuthBaseTest extends BaseTest {
    @BeforeEach
    void logIn() {
        SessionManager.ensureLoggedIn(driver);
    }
}
