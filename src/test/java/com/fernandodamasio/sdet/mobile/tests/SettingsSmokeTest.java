package com.fernandodamasio.sdet.mobile.tests;

import com.fernandodamasio.sdet.mobile.pages.SettingsPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SettingsSmokeTest extends BaseMobileTest {

    @Test
    void shouldOpenAndroidSettingsWithAppium() {
        SettingsPage settingsPage =
                new SettingsPage(driver);

        assertTrue(
                settingsPage.isLoaded(),
                "Android Settings should be loaded."
        );

        String activity =
                settingsPage.getCurrentActivity();

        assertNotNull(
                activity,
                "Current Android activity should not be null."
        );

        assertTrue(
                activity.contains("Settings"),
                () -> "Unexpected Settings activity: "
                        + activity
        );
    }
}
