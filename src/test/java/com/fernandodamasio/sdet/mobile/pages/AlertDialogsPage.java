package com.fernandodamasio.sdet.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public final class AlertDialogsPage extends BaseMobilePage {

    private static final By OK_CANCEL_DIALOG =
            AppiumBy.id(
                    "io.appium.android.apis:id/two_buttons"
            );

    private static final By LIST_DIALOG =
            AppiumBy.id(
                    "io.appium.android.apis:id/select_button"
            );

    private static final By TEXT_ENTRY_DIALOG =
            AppiumBy.id(
                    "io.appium.android.apis:id/text_entry_button"
            );

    public AlertDialogsPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(OK_CANCEL_DIALOG)
                && isVisible(LIST_DIALOG)
                && isVisible(TEXT_ENTRY_DIALOG);
    }

    public OkCancelDialog openOkCancelDialog() {
        tap(OK_CANCEL_DIALOG);
        return new OkCancelDialog(driver);
    }
}
