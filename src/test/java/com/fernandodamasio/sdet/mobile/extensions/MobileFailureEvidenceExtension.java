package com.fernandodamasio.sdet.mobile.extensions;

import com.fernandodamasio.sdet.mobile.driver.MobileDriverFactory;
import com.fernandodamasio.sdet.mobile.evidence.EvidenceManager;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public final class MobileFailureEvidenceExtension
        implements TestExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(
            ExtensionContext context,
            Throwable throwable
    ) throws Throwable {

        if (MobileDriverFactory.hasDriver()) {
            EvidenceManager.capture(
                    context,
                    MobileDriverFactory.getDriver(),
                    throwable
            );
        }

        throw throwable;
    }
}
