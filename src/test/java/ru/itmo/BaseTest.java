package ru.itmo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import ru.itmo.core.DriverFactory;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = DriverFactory.createChromeDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
