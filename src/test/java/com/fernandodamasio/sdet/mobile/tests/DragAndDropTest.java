package com.fernandodamasio.sdet.mobile.tests;

import com.fernandodamasio.sdet.mobile.pages.ApiDemosHomePage;
import com.fernandodamasio.sdet.mobile.pages.DragAndDropPage;
import com.fernandodamasio.sdet.mobile.pages.ViewsPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("regression")
class DragAndDropTest extends BaseMobileTest {

    @Test
    void shouldDragFirstDotAndDropItOnSecondDot() {
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

        DragAndDropPage dragAndDropPage =
                viewsPage.openDragAndDrop();

        assertTrue(
                dragAndDropPage.isLoaded(),
                "Drag and Drop page should be loaded."
        );

        dragAndDropPage.dragFirstDotToSecondDot();

        assertEquals(
                "Dropped!",
                dragAndDropPage.getResultText(),
                "Drag and Drop should complete successfully."
        );
    }
}
