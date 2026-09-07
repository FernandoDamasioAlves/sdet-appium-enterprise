package com.fernandodamasio.sdet.mobile.evidence;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class EvidenceManager {

    private static final Path EVIDENCE_ROOT =
            Path.of("target", "evidence");

    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");

    private EvidenceManager() {
    }

    public static void capture(
            ExtensionContext context,
            AndroidDriver driver,
            Throwable failure
    ) {
        String className =
                sanitize(context.getRequiredTestClass().getSimpleName());

        String methodName =
                sanitize(context.getRequiredTestMethod().getName());

        String timestamp =
                LocalDateTime.now().format(TIMESTAMP_FORMAT);

        Path evidenceDirectory =
                EVIDENCE_ROOT
                        .resolve(className)
                        .resolve(methodName)
                        .resolve(timestamp);

        try {
            Files.createDirectories(evidenceDirectory);
        } catch (IOException exception) {
            System.err.println(
                    "Unable to create evidence directory: "
                            + exception.getMessage()
            );
            return;
        }

        captureScreenshot(
                driver,
                evidenceDirectory.resolve("screenshot.png")
        );

        capturePageSource(
                driver,
                evidenceDirectory.resolve("page-source.xml")
        );

        captureMetadata(
                context,
                driver,
                failure,
                evidenceDirectory.resolve("metadata.txt")
        );

        System.out.println(
                "Failure evidence saved at: "
                        + evidenceDirectory.toAbsolutePath()
        );
    }

    private static void captureScreenshot(
            AndroidDriver driver,
            Path destination
    ) {
        try {
            byte[] screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);

            Files.write(destination, screenshot);

        } catch (Exception exception) {
            System.err.println(
                    "Unable to capture screenshot: "
                            + exception.getMessage()
            );
        }
    }

    private static void capturePageSource(
            AndroidDriver driver,
            Path destination
    ) {
        try {
            Files.writeString(
                    destination,
                    driver.getPageSource(),
                    StandardCharsets.UTF_8
            );

        } catch (Exception exception) {
            System.err.println(
                    "Unable to capture page source: "
                            + exception.getMessage()
            );
        }
    }

    private static void captureMetadata(
            ExtensionContext context,
            AndroidDriver driver,
            Throwable failure,
            Path destination
    ) {
        String metadata =
                "testClass="
                        + context.getRequiredTestClass().getName()
                        + System.lineSeparator()
                        + "testMethod="
                        + context.getRequiredTestMethod().getName()
                        + System.lineSeparator()
                        + "displayName="
                        + context.getDisplayName()
                        + System.lineSeparator()
                        + "sessionId="
                        + driver.getSessionId()
                        + System.lineSeparator()
                        + "capabilities="
                        + driver.getCapabilities()
                        + System.lineSeparator()
                        + "failureType="
                        + failure.getClass().getName()
                        + System.lineSeparator()
                        + "failureMessage="
                        + String.valueOf(failure.getMessage())
                        + System.lineSeparator();

        try {
            Files.writeString(
                    destination,
                    metadata,
                    StandardCharsets.UTF_8
            );

        } catch (IOException exception) {
            System.err.println(
                    "Unable to capture metadata: "
                            + exception.getMessage()
            );
        }
    }

    private static String sanitize(String value) {
        return value.replaceAll(
                "[^a-zA-Z0-9._-]",
                "_"
        );
    }
}
