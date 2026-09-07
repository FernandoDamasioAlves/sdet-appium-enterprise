package com.fernandodamasio.sdet.mobile.driver;

import com.fernandodamasio.sdet.mobile.config.ConfigManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public final class MobileDriverFactory {

    private static final ThreadLocal<AndroidDriver> DRIVER =
            new ThreadLocal<>();

    private MobileDriverFactory() {
    }

    public static void createDriver() {
        if (DRIVER.get() != null) {
            throw new IllegalStateException(
                    "AndroidDriver already exists for this thread."
            );
        }

        UiAutomator2Options options =
                new UiAutomator2Options()
                        .setDeviceName(
                                ConfigManager.get(
                                        "android.device-name"
                                )
                        )
                        .setUdid(
                                ConfigManager.get(
                                        "android.udid"
                                )
                        )
                        .setPlatformVersion(
                                ConfigManager.get(
                                        "android.platform-version"
                                )
                        )
                        .setAppPackage(
                                ConfigManager.get(
                                        "android.app-package"
                                )
                        )
                        .setAppActivity(
                                ConfigManager.get(
                                        "android.app-activity"
                                )
                        )
                        .setNoReset(
                                ConfigManager.getBoolean(
                                        "android.no-reset"
                                )
                        )
                        .setNewCommandTimeout(
                                Duration.ofSeconds(
                                        ConfigManager.getLong(
                                                "android.new-command-timeout"
                                        )
                                )
                        );

        options.setCapability(
                "appium:forceAppLaunch",
                true
        );

        try {
            AndroidDriver driver =
                    new AndroidDriver(
                            URI.create(
                                    ConfigManager.get(
                                            "appium.server-url"
                                    )
                            ).toURL(),
                            options
                    );

            DRIVER.set(driver);

        } catch (MalformedURLException exception) {
            throw new IllegalStateException(
                    "Invalid Appium server URL.",
                    exception
            );
        }
    }

    public static AndroidDriver getDriver() {
        AndroidDriver driver = DRIVER.get();

        if (driver == null) {
            throw new IllegalStateException(
                    "AndroidDriver has not been created."
            );
        }

        return driver;
    }

    public static void quitDriver() {
        AndroidDriver driver = DRIVER.get();

        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            DRIVER.remove();
        }
    }
}
