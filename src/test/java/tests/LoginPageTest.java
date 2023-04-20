package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test
    public void loginSuccessTest() {
        dashboardPage = loginPage.login(properties.getProperty("nightfall_username").trim(), properties.getProperty("nightfall_password").trim());
        Assert.assertTrue(dashboardPage.isPageLoaded());
    }
}
