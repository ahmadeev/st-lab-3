package ru.itmo.framework.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverFactory {
    public static WebDriver create() {
        String browserProp = System.getProperty("browser", "chrome");
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        Browser browser = Browser.from(browserProp);

        return switch (browser) {
            case FIREFOX -> createFirefox(headless);
            case CHROME -> createChrome(headless);
        };
    }

    private static WebDriver createChrome(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--start-maximized");
        options.addArguments("--disable-search-engine-choice-screen");

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefox(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("--headless");
        }

        WebDriver driver = new FirefoxDriver(options);

        if (!headless) {
            driver.manage().window().maximize();
        }

        return driver;
    }
}
