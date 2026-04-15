package ru.itmo.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.itmo.framework.config.TestConfig;

import java.time.Duration;

final class AuthStateVerifier {
    private static final Duration AUTH_WAIT_TIMEOUT = TestConfig.getDurationSeconds("auth.timeout.seconds", 15);

    boolean isAuthenticated(WebDriver driver) {
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
}
