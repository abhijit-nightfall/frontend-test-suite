package base;

import com.microsoft.playwright.Page;
import factory.PlaywrightFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import pages.DashboardPage;
import pages.LoginPage;

import java.util.Properties;

public class BaseTest {

    protected PlaywrightFactory playwrightFactory;
    protected Page page;
    protected Properties properties;

    protected LoginPage loginPage;
    protected DashboardPage dashboardPage;

    @Parameters({"browser"})
    @BeforeTest
    public void setup(String browserName ) {
        playwrightFactory = new PlaywrightFactory();
        properties = playwrightFactory.initProperty();

        if (browserName!=null) {
            properties.setProperty("browser", browserName);
        }
        page = playwrightFactory.initBrowser(properties);
        loginPage = new LoginPage(page);
    }

    @AfterTest
    public void tearDown() {
        page.context().browser().close();
    }

}
