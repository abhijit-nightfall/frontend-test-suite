package base;

import com.microsoft.playwright.Page;
import factory.PlaywrightFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.DashboardPage;
import pages.LoginPage;
import pages.SlackPolicyPage;

import java.util.Properties;

public class BaseTest {

    protected PlaywrightFactory playwrightFactory;
    protected Page page;
    protected Properties properties;

    protected LoginPage loginPage;
    protected DashboardPage dashboardPage;
    protected SlackPolicyPage slackPolicyPage;

    @Parameters({"browser"})
    @BeforeClass
    public void setup(@Optional("chrome")String browserName ) {
        playwrightFactory = new PlaywrightFactory();
        properties = playwrightFactory.initProperty();

        if (browserName!=null) {
            properties.setProperty("browser", browserName);
        }
        page = playwrightFactory.initBrowser(properties);
        loginPage = new LoginPage(page);
    }

    @AfterClass
    public void tearDown() {
        playwrightFactory.closeTracing();
        page.context().browser().close();
    }

}
