package base;

import com.microsoft.playwright.Page;
import factory.PlaywrightFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pages.LoginPage;

import java.util.Properties;

public class BaseTest {

    protected PlaywrightFactory playwrightFactory;
    protected Page page;
    protected Properties properties;

    protected LoginPage loginPage;

    @BeforeTest
    public void setup() {
        playwrightFactory = new PlaywrightFactory();
        properties = playwrightFactory.initProperty();
        page = playwrightFactory.initBrowser(properties);
        loginPage = new LoginPage(page);
    }

    @AfterTest
    public void tearDown() {
        page.context().browser().close();
    }

}
