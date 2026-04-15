package ru.itmo.support;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

final class CookieStorage {
    private volatile Set<Cookie> cachedCookies = Set.of();

    Set<Cookie> load() {
        return new HashSet<>(cachedCookies);
    }

    void save(WebDriver driver) {
        cachedCookies = new HashSet<>(driver.manage().getCookies());
    }

    void clear() {
        cachedCookies = Set.of();
    }

    Set<Cookie> filterValid(Set<Cookie> cookies) {
        Date now = new Date();

        return cookies.stream()
                .filter(cookie -> cookie.getName() != null && !cookie.getName().isBlank())
                .filter(cookie -> cookie.getValue() != null)
                .filter(cookie -> cookie.getExpiry() == null || cookie.getExpiry().after(now))
                .collect(Collectors.toCollection(HashSet::new));
    }
}
