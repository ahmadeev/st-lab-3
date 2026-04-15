package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.AuthBaseTest;
import ru.itmo.pages.ThemesPage;

import java.time.Duration;

public class ThemesPageTest extends AuthBaseTest {
    @Test
    void shouldPreview() {
        ThemesPage page = new ThemesPage(driver, Duration.ofSeconds(20));

        if (!page.isLoaded()) {
            page.open();
        }

        page.previewTheme();

        Assertions.assertTrue(page.isPreviewed());
    }
}
