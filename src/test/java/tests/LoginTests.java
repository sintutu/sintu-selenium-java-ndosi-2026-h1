/*
 * PURPOSE:
 * This class expresses user-visible behaviour as executable specifications.
 *
 * RESPONSIBILITY:
 * - Describe scenarios in business language.
 * - Call high-level behaviours on NdosiDev (the application facade).
 * - Perform assertions explicitly.
 *
 * DOES NOT:
 * - Create or manage WebDriver.
 * - Interact with Selenium APIs.
 * - Instantiate page objects.
 * - Perform waits.
 * - Know about routing, locators, or DOM structure.
 *
 * If you need Selenium mechanics, go to:
 * - NdosiDev (for behaviour orchestration)
 * - Page objects (for DOM interaction)
 *
 * Tests assert outcomes.
 * Tests do not implement mechanics.
 */

package tests;

import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.Test;

import pages.NdosiDev;

public class LoginTests {

    @Test
    public void logIntoNdosiDevSite() {
        try (NdosiDev app = new NdosiDev()) {
            app.loginAsOrdinaryUser();
            assertThat(app.isOnDashboard()).isTrue();
        }
    }
}