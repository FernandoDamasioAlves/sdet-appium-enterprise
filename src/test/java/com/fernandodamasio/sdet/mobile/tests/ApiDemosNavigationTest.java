package com.fernandodamasio.sdet.mobile.tests;

import com.fernandodamasio.sdet.mobile.pages.ApiDemosHomePage;
import com.fernandodamasio.sdet.mobile.pages.ViewsPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("smoke")
class ApiDemosNavigationTest extends BaseMobileTest {

    @Test
    void shouldNavigateFromHomeToViewsMenu() {
        ApiDemosHomePage homePage =
                new ApiDemosHomePage(driver);

        assertTrue(
                homePage.isLoaded(),
                "ApiDemos home page should be loaded."
        );

        ViewsPage viewsPage =
                homePage.openViews();

        assertTrue(
                viewsPage.isLoaded(),
                "Views menu should be loaded."
        );

        assertTrue(
                viewsPage.isDragAndDropVisible(),
                "Drag and Drop option should be visible."
        );
    }
}
