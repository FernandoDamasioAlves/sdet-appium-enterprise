package com.fernandodamasio.sdet.mobile.pages;

import io.appium.java_client.android.AndroidDriver;

public final class SettingsPage extends BaseMobilePage {

    private static final String SETTINGS_PACKAGE =
            "com.android.settings";

    public SettingsPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return wait.until(ignored ->
                SETTINGS_PACKAGE.equals(
                        currentPackage()
                )
        );
    }

    public String getCurrentActivity() {
        return currentActivity();
    }
}
