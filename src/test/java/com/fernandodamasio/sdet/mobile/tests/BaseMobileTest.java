package com.fernandodamasio.sdet.mobile.tests;

import com.fernandodamasio.sdet.mobile.driver.MobileDriverFactory;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseMobileTest {

    protected AndroidDriver driver;

    @BeforeEach
    void setUpMobileDriver() {
        MobileDriverFactory.createDriver();
        driver = MobileDriverFactory.getDriver();
    }

    @AfterEach
    void tearDownMobileDriver() {
        MobileDriverFactory.quitDriver();
    }
}
