package com.fernandodamasio.sdet.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class ControlsPage extends BaseMobilePage {

    private static final By SAVE_BUTTON =
            AppiumBy.id(
                    "io.appium.android.apis:id/button"
            );

    private static final By DISABLED_SAVE_BUTTON =
            AppiumBy.id(
                    "io.appium.android.apis:id/button_disabled"
            );

    private static final By EDIT_TEXT =
            AppiumBy.id(
                    "io.appium.android.apis:id/edit"
            );

    private static final By CHECKBOX_1 =
            AppiumBy.id(
                    "io.appium.android.apis:id/check1"
            );

    private static final By RADIO_BUTTON_1 =
            AppiumBy.id(
                    "io.appium.android.apis:id/radio1"
            );

    private static final By RADIO_BUTTON_2 =
            AppiumBy.id(
                    "io.appium.android.apis:id/radio2"
            );

    private static final By TOGGLE_1 =
            AppiumBy.id(
                    "io.appium.android.apis:id/toggle1"
            );

    private static final By SPINNER =
            AppiumBy.id(
                    "io.appium.android.apis:id/spinner1"
            );

    private static final By EARTH_OPTION =
            AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Earth\")"
            );

    public ControlsPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(SAVE_BUTTON)
                && isVisible(EDIT_TEXT)
                && isVisible(CHECKBOX_1)
                && isVisible(RADIO_BUTTON_1)
                && isVisible(RADIO_BUTTON_2)
                && isVisible(TOGGLE_1)
                && isVisible(SPINNER);
    }

    public boolean isSaveButtonEnabled() {
        return waitUntilVisible(SAVE_BUTTON)
                .isEnabled();
    }

    public boolean isDisabledSaveButtonEnabled() {
        return waitUntilVisible(DISABLED_SAVE_BUTTON)
                .isEnabled();
    }

    public void selectCheckbox1() {
        tap(CHECKBOX_1);
    }

    public boolean isCheckbox1Checked() {
        return isChecked(CHECKBOX_1);
    }

    public void selectRadioButton2() {
        tap(RADIO_BUTTON_2);
    }

    public boolean isRadioButton1Checked() {
        return isChecked(RADIO_BUTTON_1);
    }

    public boolean isRadioButton2Checked() {
        return isChecked(RADIO_BUTTON_2);
    }

    public void activateToggle1() {
        tap(TOGGLE_1);
    }

    public boolean isToggle1Checked() {
        return isChecked(TOGGLE_1);
    }

    public String getToggle1Text() {
        return waitUntilVisible(TOGGLE_1)
                .getText()
                .trim();
    }

    public void selectEarth() {
        tap(SPINNER);
        tap(EARTH_OPTION);
    }

    public String getSelectedPlanet() {
        WebElement spinner =
                waitUntilVisible(SPINNER);

        WebElement selectedValue =
                spinner.findElement(
                        AppiumBy.id("android:id/text1")
                );

        return selectedValue
                .getText()
                .trim();
    }

    public void enterText(String value) {
        WebElement editText =
                waitUntilVisible(EDIT_TEXT);

        editText.clear();
        editText.sendKeys(value);
    }

    public String getEnteredText() {
        return waitUntilVisible(EDIT_TEXT)
                .getText()
                .trim();
    }

    private boolean isChecked(By locator) {
        String checked =
                waitUntilVisible(locator)
                        .getAttribute("checked");

        return Boolean.parseBoolean(checked);
    }
}
