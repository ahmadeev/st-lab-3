package ru.itmo.session;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import ru.itmo.framework.config.TestConfig;

import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

final class CookieStorage {
    private volatile Set<Cookie> cachedCookies = Set.of();

    Set<Cookie> load() {
        return new HashSet<>(cachedCookies);
    }

    void save(WebDriver driver, Collection<String> contexts) {
        Set<Cookie> cookies = new LinkedHashSet<>();

        for (String contextUrl : contexts) {
            Set<Cookie> contextCookies = readCookiesFor(driver, contextUrl);
            cookies.addAll(contextCookies);
        }

        cachedCookies = cookies;

        driver.get(TestConfig.get("base.url") + "/sites");
    }

    void clear() {
        cachedCookies = Set.of();
    }

    void restore(WebDriver driver, Set<Cookie> cookies, Collection<String> contexts) {
        clearBrowserCookies(driver, contexts);

        for (String contextUrl : contexts) {
            restoreFor(driver, contextUrl, cookies);
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

    private Set<Cookie> readCookiesFor(WebDriver driver, String url) {
        driver.get(url);

        return new HashSet<>(driver.manage().getCookies());
    }

    private void clearBrowserCookies(WebDriver driver, Collection<String> contexts) {
        for (String contextUrl : contexts) {
            driver.get(contextUrl);
            driver.manage().deleteAllCookies();
        }
    }

    private void restoreFor(WebDriver driver, String contextUrl, Set<Cookie> cookies) {
        String host = URI.create(contextUrl).getHost();

        driver.get(contextUrl);

        for (Cookie cookie : cookies) {
            if (!matchesContext(cookie, host)) {
                continue;
            }

            try {
                driver.manage().addCookie(cookie);
            } catch (Exception ignored) {
            }
        }
    }

    private boolean matchesContext(Cookie cookie, String host) {
        String domain = cookie.getDomain();

        if (domain == null || domain.isBlank()) {
            return false;
        }

        String normalizedDomain = domain.startsWith(".") ? domain.substring(1) : domain;

        return host.equals(normalizedDomain) || host.endsWith("." + normalizedDomain);
    }
}
