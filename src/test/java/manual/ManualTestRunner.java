package manual;

import infrastructure.Browser;
import ui.Harness;

/**
 * A manual test runner used to aid in performance benchmarking.
 * This class is the entry point to launching `Harness`
 * and performs a simple login and dashboard visibility verification.
 * This uses `Harness`'s implementation of AutoCloseable.
 *
 * Run this on Windows from project root with
 * mvn test-compile exec:java "-Dexec.mainClass=manual.ManualTestRunner" "-Dexec.classpathScope=test"
 * The double-quotes are needed since Windows PowerShell can be a pain.
 */
public class ManualTestRunner {
    /**
     * Test runner entry point.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args){
        long start = System.currentTimeMillis();

        try (Harness app = new Harness(Browser.CHROME, false)){
            app.loginAsOrdinaryUser();

            if (!app.isOnDashboard()){
                throw new RuntimeException("Dashboard not visible.");
            }
        }

        long end = System.currentTimeMillis();

        System.out.println("Total execution time: " + (end - start) + "ms.");
    }
}
