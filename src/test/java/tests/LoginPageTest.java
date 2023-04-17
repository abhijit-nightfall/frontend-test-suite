package tests;

import com.microsoft.playwright.Page;
import factory.PlaywrightFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginPageTest {

    PlaywrightFactory playwrightFactory;
    Page page;
    LoginPage loginPage;

    @BeforeTest
    public void setup() {
        playwrightFactory = new PlaywrightFactory();
        page = playwrightFactory.initBrowser("chrome");
        loginPage = new LoginPage(page);
    }

    @Test
    public void loginSuccessTest() {
        Assert.assertTrue(loginPage.login("abhijit@nightfall.ai", "Automation@123"));
    }

    @AfterTest
    public void tearDown() {
        page.context().browser().close();
    }
}
