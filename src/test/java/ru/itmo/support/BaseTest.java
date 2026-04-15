package ru.itmo.support;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import ru.itmo.framework.driver.DriverFactory;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    protected void setUp() {
        driver = DriverFactory.createChromeDriver();
    }

    @AfterEach
    protected void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
