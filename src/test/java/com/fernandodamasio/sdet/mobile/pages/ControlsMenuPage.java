package com.fernandodamasio.sdet.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public final class ControlsMenuPage extends BaseMobilePage {

    private static final By LIGHT_THEME =
            AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"1. Light Theme\")"
            );

    private static final By DARK_THEME =
            AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"2. Dark Theme\")"
            );

    public ControlsMenuPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(LIGHT_THEME)
                && isVisible(DARK_THEME);
    }

    public ControlsPage openLightTheme() {
        tap(LIGHT_THEME);
        return new ControlsPage(driver);
    }
}
