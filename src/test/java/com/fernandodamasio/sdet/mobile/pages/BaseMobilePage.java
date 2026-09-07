package com.fernandodamasio.sdet.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    protected WebElement waitUntilVisible(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    protected void tap(By locator) {
        waitUntilVisible(locator).click();
    }

    protected boolean isVisible(By locator) {
        try {
            waitUntilVisible(locator);
            return true;

        } catch (TimeoutException exception) {
            return false;
        }
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
