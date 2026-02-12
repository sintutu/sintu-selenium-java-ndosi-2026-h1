/*
* TestBase is dangerous in many automation projects because:
* - People put logic in it.
* - People start inheriting behaviour.
* - It becomes a dumping ground.
* - It becomes a framework.
* This violates the architecture principles.
*
* TestBase should only:
* - Create Harness
* - Destroy Harness
*
* Nothing else.
*
* No helper methods.
* No login methods.
* No browser logic.
* No test data.
 */

package tests;

import infrastructure.Browser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ui.Harness;

public abstract class TestBase {
    protected Harness app;
    private Browser browser;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("CHROME") String browserName){
        browser = Browser.valueOf(browserName);
        System.out.println(browserName);
        app = new Harness(browser, false);
    }

    @AfterMethod
    public void tearDown(){
        if (app != null){
            app.close();
        }
    }
}
