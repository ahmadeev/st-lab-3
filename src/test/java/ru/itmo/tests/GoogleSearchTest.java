package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.itmo.support.BaseTest;
import ru.itmo.pages.GoogleHomePage;

@Disabled
public class GoogleSearchTest extends BaseTest {
    @Test
    void shouldOpenSearchResultsAfterSubmittingQuery() {
        String query = "Selenium WebDriver";
        GoogleHomePage googleHomePage = new GoogleHomePage(driver).open();

        Assertions.assertTrue(googleHomePage.isSearchFieldReady());
        googleHomePage.search(query);

        Assertions.assertTrue(googleHomePage.areResultsOpenedFor(query));
    }
}
