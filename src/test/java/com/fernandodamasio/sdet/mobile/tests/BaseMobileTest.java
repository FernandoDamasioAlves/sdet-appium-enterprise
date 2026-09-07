package com.fernandodamasio.sdet.mobile.tests;

import com.fernandodamasio.sdet.mobile.driver.MobileDriverFactory;
import com.fernandodamasio.sdet.mobile.extensions.MobileFailureEvidenceExtension;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;

public abstract class BaseMobileTest {

    @RegisterExtension
    final MobileFailureEvidenceExtension failureEvidence =
            new MobileFailureEvidenceExtension();

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
