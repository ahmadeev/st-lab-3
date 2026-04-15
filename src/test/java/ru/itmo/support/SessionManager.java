package ru.itmo.support;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.itmo.framework.config.TestConfig;
import ru.itmo.pages.AuthPage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class SessionManager {
    private static final Path COOKIES_FILE = Path.of("target", "session-cookies.bin");
    private static final Duration AUTH_WAIT_TIMEOUT = TestConfig.getDurationSeconds("auth.timeout.seconds", 15);

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
        Set<Cookie> cookies = loadCookies();

        if (cookies.isEmpty()) {
            return false;
        }

        driver.get(TestConfig.get("base.url"));
        driver.manage().deleteAllCookies();

        for (Cookie cookie : filterValidCookies(cookies)) {
            try {
                driver.manage().addCookie(cookie);
            } catch (Exception ignored) {
            }
        }

        driver.navigate().refresh();

        return isAuthenticated(driver);
    }

    public static void clearSavedSession() {
        try {
            Files.deleteIfExists(COOKIES_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete " + COOKIES_FILE, e);
        }
    }

    private static void loginAndPersist(WebDriver driver) {
        loginViaUi(driver);
        saveCookies(driver);
    }

    private static void loginViaUi(WebDriver driver) {
        driver.manage().deleteAllCookies();

        AuthPage loginPage = new AuthPage(driver)
                .open()
                .enterEmail(TestConfig.get("email"))
                .clickSubmit()
                .enterPassword(TestConfig.get("password"))
                .clickSubmit();

        if (!loginPage.waitUntilAuthorized()) {
            throw new IllegalStateException("UI login failed or additional verification is required");
        }
    }

    private static void saveCookies(WebDriver driver) {
        Set<SerializableCookie> cookies = driver.manage().getCookies().stream()
                .map(SerializableCookie::from)
                .collect(Collectors.toCollection(HashSet::new));

        try {
            Files.createDirectories(COOKIES_FILE.getParent());

            try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(COOKIES_FILE))) {
                out.writeObject(cookies);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save cookies to " + COOKIES_FILE, e);
        }
    }

    private static Set<Cookie> loadCookies() {
        if (!Files.exists(COOKIES_FILE)) {
            return Set.of();
        }

        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(COOKIES_FILE))) {
            Object raw = in.readObject();

            @SuppressWarnings("unchecked")
            Set<SerializableCookie> serialized = (Set<SerializableCookie>) raw;

            return serialized.stream()
                    .map(SerializableCookie::toSeleniumCookie)
                    .collect(Collectors.toCollection(HashSet::new));
        } catch (Exception e) {
            return Set.of();
        }
    }

    private static Set<Cookie> filterValidCookies(Set<Cookie> cookies) {
        Date now = new Date();

        return cookies.stream()
                .filter(cookie -> cookie.getName() != null && !cookie.getName().isBlank())
                .filter(cookie -> cookie.getValue() != null)
                .filter(cookie -> cookie.getExpiry() == null || cookie.getExpiry().after(now))
                .collect(Collectors.toCollection(HashSet::new));
    }

    private static boolean isAuthenticated(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, AUTH_WAIT_TIMEOUT);

            return wait.until(d -> {
                String url = d.getCurrentUrl();

                return url != null
                        && !url.contains("/log-in")
                        && !url.contains("/signup")
                        && !url.contains("/start");
            });
        } catch (Exception e) {
            return false;
        }
    }

    private record SerializableCookie(
            String name,
            String value,
            String domain,
            String path,
            Long expiryEpochMillis,
            boolean secure,
            boolean httpOnly,
            String sameSite
    ) implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private static SerializableCookie from(Cookie cookie) {
            return new SerializableCookie(
                    cookie.getName(),
                    cookie.getValue(),
                    cookie.getDomain(),
                    cookie.getPath(),
                    cookie.getExpiry() == null ? null : cookie.getExpiry().getTime(),
                    cookie.isSecure(),
                    cookie.isHttpOnly(),
                    cookie.getSameSite()
            );
        }

        private Cookie toSeleniumCookie() {
            Date expiry = expiryEpochMillis == null ? null : new Date(expiryEpochMillis);

            return new Cookie(
                    name,
                    value,
                    domain,
                    path,
                    expiry,
                    secure,
                    httpOnly,
                    sameSite
            );
        }
    }
}
