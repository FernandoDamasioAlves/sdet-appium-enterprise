package com.fernandodamasio.sdet.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseMobilePage {

    protected final AndroidDriver driver;
    protected final WebDriverWait wait;

    protected BaseMobilePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    protected String currentPackage() {
        Object value = driver.executeScript(
                "mobile: getCurrentPackage"
        );

        return value == null
                ? null
                : value.toString();
    }

    protected String currentActivity() {
        Object value = driver.executeScript(
                "mobile: getCurrentActivity"
        );

        return value == null
                ? null
                : value.toString();
    }
}
