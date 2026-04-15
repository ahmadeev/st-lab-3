package ru.itmo.support;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

final class CookieStorage {
    private static final Path COOKIES_FILE = Path.of("target", "session-cookies.bin");

    Set<Cookie> load() {
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

    void save(WebDriver driver) {
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

    void clear() {
        try {
            Files.deleteIfExists(COOKIES_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete " + COOKIES_FILE, e);
        }
    }

    Set<Cookie> filterValid(Set<Cookie> cookies) {
        Date now = new Date();

        return cookies.stream()
                .filter(cookie -> cookie.getName() != null && !cookie.getName().isBlank())
                .filter(cookie -> cookie.getValue() != null)
                .filter(cookie -> cookie.getExpiry() == null || cookie.getExpiry().after(now))
                .collect(Collectors.toCollection(HashSet::new));
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
