package ru.itmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.pages.ThemesPage;
import ru.itmo.support.AuthBaseTest;

public class ThemesPageTest extends AuthBaseTest {
    @Test
    void shouldPreview() {
        ThemesPage page = new ThemesPage(driver);

        if (!page.isLoaded()) {
            page.open();
        }

        page.previewFirstFreeTheme();

        Assertions.assertTrue(page.isPreviewed());
    }
}
