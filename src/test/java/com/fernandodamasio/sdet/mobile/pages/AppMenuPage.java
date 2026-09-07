package com.fernandodamasio.sdet.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public final class AppMenuPage extends BaseMobilePage {

    private static final By ACTION_BAR =
            AppiumBy.accessibilityId("Action Bar");

    private static final By ALERT_DIALOGS =
            AppiumBy.accessibilityId("Alert Dialogs");

    private static final By ACTIVITY =
            AppiumBy.accessibilityId("Activity");

    public AppMenuPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(ACTION_BAR)
                && isVisible(ACTIVITY)
                && isVisible(ALERT_DIALOGS);
    }

    public AlertDialogsPage openAlertDialogs() {
        tap(ALERT_DIALOGS);
        return new AlertDialogsPage(driver);
    }
}
