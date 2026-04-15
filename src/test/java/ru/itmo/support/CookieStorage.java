package ru.itmo.support;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import ru.itmo.framework.config.TestConfig;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

final class CookieStorage {
    private static final String PUBLIC_API_URL = "https://public-api.wordpress.com/wp-admin/rest-proxy/?v=2.0";
    private static final String SITES_URL_SUFFIX = "/sites";

    private volatile Set<Cookie> cachedCookies = Set.of();

    Set<Cookie> load() {
        return new HashSet<>(cachedCookies);
    }

    void save(WebDriver driver) {
        String previousUrl = driver.getCurrentUrl();
        Set<Cookie> cookies = new LinkedHashSet<>();

        cookies.addAll(readCookiesFor(driver, TestConfig.get("base.url") + SITES_URL_SUFFIX));
        cookies.addAll(readCookiesFor(driver, PUBLIC_API_URL));

        cachedCookies = cookies;

        if (previousUrl != null && !previousUrl.isBlank()) {
            driver.get(previousUrl);
        }
    }

    void clear() {
        cachedCookies = Set.of();
    }

    void restore(WebDriver driver, Set<Cookie> cookies) {
        driver.get(TestConfig.get("base.url"));
        driver.manage().deleteAllCookies();

        restoreFor(driver, TestConfig.get("base.url") + SITES_URL_SUFFIX, cookies);
        restoreFor(driver, PUBLIC_API_URL, cookies);
    }

    Set<Cookie> filterValid(Set<Cookie> cookies) {
        Date now = new Date();

        return cookies.stream()
                .filter(cookie -> cookie.getName() != null && !cookie.getName().isBlank())
                .filter(cookie -> cookie.getValue() != null)
                .filter(cookie -> cookie.getExpiry() == null || cookie.getExpiry().after(now))
                .collect(Collectors.toCollection(HashSet::new));
    }

    private Set<Cookie> readCookiesFor(WebDriver driver, String url) {
        driver.get(url);

        return new HashSet<>(driver.manage().getCookies());
    }

    private void restoreFor(WebDriver driver, String url, Set<Cookie> cookies) {
        driver.get(url);

        for (Cookie cookie : cookies) {
            try {
                driver.manage().addCookie(cookie);
            } catch (Exception ignored) {
            }
        }
    }
}
