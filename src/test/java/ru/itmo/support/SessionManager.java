package ru.itmo.support;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import ru.itmo.framework.config.TestConfig;

import java.util.Objects;
import java.util.Set;

public final class SessionManager {
    private static final CookieStorage COOKIE_STORAGE = new CookieStorage();
    private static final LoginFlow LOGIN_FLOW = new LoginFlow();
    private static final AuthStateVerifier AUTH_STATE_VERIFIER = new AuthStateVerifier();

    private SessionManager() {
    }

    public static void ensureLoggedIn(WebDriver driver) {
        Objects.requireNonNull(driver, "driver must not be null");

        if (tryRestoreSession(driver)) {
            return;
        }

        loginAndPersist(driver);
    }

    public static void relogin(WebDriver driver) {
        Objects.requireNonNull(driver, "driver must not be null");

        clearSavedSession();
        loginAndPersist(driver);
    }

    public static boolean tryRestoreSession(WebDriver driver) {
        Set<Cookie> cookies = COOKIE_STORAGE.load();

        if (cookies.isEmpty()) {
            return false;
        }

        COOKIE_STORAGE.restore(driver, COOKIE_STORAGE.filterValid(cookies));

        driver.get(TestConfig.get("base.url"));
        driver.navigate().refresh();

        return AUTH_STATE_VERIFIER.isAuthenticated(driver);
    }

    public static void clearSavedSession() {
        COOKIE_STORAGE.clear();
    }

    private static void loginAndPersist(WebDriver driver) {
        LOGIN_FLOW.login(driver);
        COOKIE_STORAGE.save(driver);
    }
}
