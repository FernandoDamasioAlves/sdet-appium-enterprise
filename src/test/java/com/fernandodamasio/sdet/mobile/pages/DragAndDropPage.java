package com.fernandodamasio.sdet.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

public final class DragAndDropPage extends BaseMobilePage {

    private static final By DRAG_DOT_1 =
            AppiumBy.id("io.appium.android.apis:id/drag_dot_1");

    private static final By DRAG_DOT_2 =
            AppiumBy.id("io.appium.android.apis:id/drag_dot_2");

    private static final By RESULT_TEXT =
            AppiumBy.id("io.appium.android.apis:id/drag_result_text");

    public DragAndDropPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isVisible(DRAG_DOT_1)
                && isVisible(DRAG_DOT_2)
                && isVisible(RESULT_TEXT);
    }

    public void dragFirstDotToSecondDot() {
        WebElement source = waitUntilVisible(DRAG_DOT_1);
        WebElement target = waitUntilVisible(DRAG_DOT_2);

        Point sourceCenter = centerOf(source.getRect());
        Point targetCenter = centerOf(target.getRect());

        PointerInput finger =
                new PointerInput(
                        PointerInput.Kind.TOUCH,
                        "finger"
                );

        Sequence gesture =
                new Sequence(finger, 1);

        gesture.addAction(
                finger.createPointerMove(
                        Duration.ZERO,
                        PointerInput.Origin.viewport(),
                        sourceCenter.getX(),
                        sourceCenter.getY()
                )
        );

        gesture.addAction(
                finger.createPointerDown(
                        PointerInput.MouseButton.LEFT.asArg()
                )
        );

        gesture.addAction(
                new Pause(
                        finger,
                        Duration.ofMillis(1200)
                )
        );

        gesture.addAction(
                finger.createPointerMove(
                        Duration.ofMillis(900),
                        PointerInput.Origin.viewport(),
                        targetCenter.getX(),
                        targetCenter.getY()
                )
        );

        gesture.addAction(
                finger.createPointerUp(
                        PointerInput.MouseButton.LEFT.asArg()
                )
        );

        driver.perform(List.of(gesture));
    }

    public String getResultText() {
        return waitUntilVisible(RESULT_TEXT)
                .getText()
                .trim();
    }

    private Point centerOf(Rectangle rectangle) {
        return new Point(
                rectangle.getX()
                        + rectangle.getWidth() / 2,
                rectangle.getY()
                        + rectangle.getHeight() / 2
        );
    }
}
