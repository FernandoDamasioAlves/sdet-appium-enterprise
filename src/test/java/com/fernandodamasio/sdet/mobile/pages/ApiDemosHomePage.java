package com.fernandodamasio.sdet.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public final class ApiDemosHomePage extends BaseMobilePage {

    private static final String APP_PACKAGE =
            "io.appium.android.apis";

    private static final By VIEWS =
            AppiumBy.accessibilityId("Views");

    private static final By APP =
            AppiumBy.accessibilityId("App");

    private static final By ACCESSIBILITY =
            AppiumBy.accessibilityId("Accessibility");

    public ApiDemosHomePage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return APP_PACKAGE.equals(currentPackage())
                && isVisible(ACCESSIBILITY)
                && isVisible(VIEWS);
    }

    public AppMenuPage openApp() {
        tap(APP);
        return new AppMenuPage(driver);
    }

    public ViewsPage openViews() {
        tap(VIEWS);
        return new ViewsPage(driver);
    }
}
