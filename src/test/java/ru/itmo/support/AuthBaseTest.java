package ru.itmo.support;

import org.junit.jupiter.api.BeforeEach;
import ru.itmo.session.SessionManager;

public abstract class AuthBaseTest extends BaseTest {
    @BeforeEach
    protected void prepareAuthorizedSession() {
        if (forceRelogin()) {
            SessionManager.relogin(driver);

            return;
        }

        SessionManager.ensureLoggedIn(driver);
    }

    protected boolean forceRelogin() {
        return false;
    }
}
