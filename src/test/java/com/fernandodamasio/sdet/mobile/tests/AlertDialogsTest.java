package com.fernandodamasio.sdet.mobile.tests;

import com.fernandodamasio.sdet.mobile.pages.AlertDialogsPage;
import com.fernandodamasio.sdet.mobile.pages.ApiDemosHomePage;
import com.fernandodamasio.sdet.mobile.pages.AppMenuPage;
import com.fernandodamasio.sdet.mobile.pages.OkCancelDialog;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("regression")
class AlertDialogsTest extends BaseMobileTest {

    private static final String EXPECTED_ALERT_TITLE =
            "Lorem ipsum dolor sit aie consectetur adipiscing\n"
                    + "Plloaso mako nuto siwuf cakso dodtos anr koop.";

    @Test
    void shouldCancelNativeOkCancelDialog() {
        OkCancelDialog dialog =
                openOkCancelDialog();

        assertDialogContent(dialog);

        AlertDialogsPage alertDialogsPage =
                dialog.cancel();

        assertTrue(
                alertDialogsPage.isLoaded(),
                "Alert Dialogs page should be restored after Cancel."
        );
    }

    @Test
    void shouldConfirmNativeOkCancelDialog() {
        OkCancelDialog dialog =
                openOkCancelDialog();

        assertDialogContent(dialog);

        AlertDialogsPage alertDialogsPage =
                dialog.confirm();

        assertTrue(
                alertDialogsPage.isLoaded(),
                "Alert Dialogs page should be restored after OK."
        );
    }

    private OkCancelDialog openOkCancelDialog() {
        ApiDemosHomePage homePage =
                new ApiDemosHomePage(driver);

        assertTrue(
                homePage.isLoaded(),
                "ApiDemos home page should be loaded."
        );

        AppMenuPage appMenuPage =
                homePage.openApp();

        assertTrue(
                appMenuPage.isLoaded(),
                "App menu should be loaded."
        );

        AlertDialogsPage alertDialogsPage =
                appMenuPage.openAlertDialogs();

        assertTrue(
                alertDialogsPage.isLoaded(),
                "Alert Dialogs page should be loaded."
        );

        OkCancelDialog dialog =
                alertDialogsPage.openOkCancelDialog();

        assertTrue(
                dialog.isLoaded(),
                "OK Cancel native dialog should be loaded."
        );

        return dialog;
    }

    private void assertDialogContent(
            OkCancelDialog dialog
    ) {
        assertEquals(
                EXPECTED_ALERT_TITLE,
                dialog.getAlertTitle(),
                "Alert title should match the expected text."
        );

        assertEquals(
                "OK",
                dialog.getOkButtonText(),
                "Positive button should display OK."
        );

        assertEquals(
                "Cancel",
                dialog.getCancelButtonText(),
                "Negative button should display Cancel."
        );
    }
}
