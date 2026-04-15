package ru.itmo.session;

import org.openqa.selenium.WebDriver;
import ru.itmo.pages.AuthPage;

final class LoginFlow {
    void login(WebDriver driver) {
        driver.manage().deleteAllCookies();

        AuthPage loginPage = new AuthPage(driver).open();
        loginPage.logIn();

        if (!loginPage.waitUntilAuthorized()) {
            throw new IllegalStateException("UI login failed or additional verification is required");
        }
    }
}
