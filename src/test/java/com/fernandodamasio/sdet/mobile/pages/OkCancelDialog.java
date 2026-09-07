package com.fernandodamasio.sdet.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public final class OkCancelDialog extends BaseMobilePage {

    private static final By ALERT_TITLE =
            AppiumBy.id(
                    "android:id/alertTitle"
            );

    private static final By OK_BUTTON =
            AppiumBy.id(
                    "android:id/button1"
            );

    private static final By CANCEL_BUTTON =
            AppiumBy.id(
                    "android:id/button2"
            );

    public OkCancelDialog(AndroidDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(ALERT_TITLE)
                && isVisible(OK_BUTTON)
                && isVisible(CANCEL_BUTTON);
    }

    public String getAlertTitle() {
        return waitUntilVisible(ALERT_TITLE)
                .getText()
                .trim();
    }

    public String getOkButtonText() {
        return waitUntilVisible(OK_BUTTON)
                .getText()
                .trim();
    }

    public String getCancelButtonText() {
        return waitUntilVisible(CANCEL_BUTTON)
                .getText()
                .trim();
    }

    public AlertDialogsPage confirm() {
        tap(OK_BUTTON);
        return new AlertDialogsPage(driver);
    }

    public AlertDialogsPage cancel() {
        tap(CANCEL_BUTTON);
        return new AlertDialogsPage(driver);
    }
}
