package com.fernandodamasio.sdet.mobile.tests;

import com.fernandodamasio.sdet.mobile.pages.ApiDemosHomePage;
import com.fernandodamasio.sdet.mobile.pages.ControlsMenuPage;
import com.fernandodamasio.sdet.mobile.pages.ControlsPage;
import com.fernandodamasio.sdet.mobile.pages.ViewsPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("regression")
class ControlsInteractionTest extends BaseMobileTest {

    @Test
    void shouldInteractWithNativeFormControls() {
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

        ControlsMenuPage controlsMenuPage =
                viewsPage.openControls();

        assertTrue(
                controlsMenuPage.isLoaded(),
                "Controls menu should be loaded."
        );

        ControlsPage controlsPage =
                controlsMenuPage.openLightTheme();

        assertTrue(
                controlsPage.isLoaded(),
                "Controls Light Theme page should be loaded."
        );

        assertTrue(
                controlsPage.isSaveButtonEnabled(),
                "Enabled Save button should be enabled."
        );

        assertFalse(
                controlsPage.isDisabledSaveButtonEnabled(),
                "Disabled Save button should remain disabled."
        );

        controlsPage.selectCheckbox1();

        assertTrue(
                controlsPage.isCheckbox1Checked(),
                "Checkbox 1 should be checked."
        );

        controlsPage.selectRadioButton2();

        assertFalse(
                controlsPage.isRadioButton1Checked(),
                "RadioButton 1 should not be checked."
        );

        assertTrue(
                controlsPage.isRadioButton2Checked(),
                "RadioButton 2 should be checked."
        );

        controlsPage.activateToggle1();

        assertTrue(
                controlsPage.isToggle1Checked(),
                "Toggle 1 should be checked."
        );

        assertEquals(
                "ON",
                controlsPage.getToggle1Text(),
                "Toggle 1 should display ON."
        );

        controlsPage.selectEarth();

        assertEquals(
                "Earth",
                controlsPage.getSelectedPlanet(),
                "Spinner should select Earth."
        );

        controlsPage.enterText("SDET");

        assertEquals(
                "SDET",
                controlsPage.getEnteredText(),
                "EditText should contain the entered value."
        );
    }
}
