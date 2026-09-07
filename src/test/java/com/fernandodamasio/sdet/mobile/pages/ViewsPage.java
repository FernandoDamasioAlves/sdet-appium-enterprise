package com.fernandodamasio.sdet.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public final class ViewsPage extends BaseMobilePage {

    private static final By ANIMATION =
            AppiumBy.accessibilityId("Animation");

    private static final By BUTTONS =
            AppiumBy.accessibilityId("Buttons");

    private static final By CONTROLS =
            AppiumBy.accessibilityId("Controls");

    private static final By DRAG_AND_DROP =
            AppiumBy.accessibilityId("Drag and Drop");

    public ViewsPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(ANIMATION)
                && isVisible(BUTTONS)
                && isVisible(CONTROLS)
                && isVisible(DRAG_AND_DROP);
    }

    public boolean isDragAndDropVisible() {
        return isVisible(DRAG_AND_DROP);
    }

    public ControlsMenuPage openControls() {
        tap(CONTROLS);
        return new ControlsMenuPage(driver);
    }

    public DragAndDropPage openDragAndDrop() {
        tap(DRAG_AND_DROP);
        return new DragAndDropPage(driver);
    }
}
