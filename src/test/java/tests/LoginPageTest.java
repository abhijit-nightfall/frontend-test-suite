package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;

public class LoginPageTest extends BaseTest {

    @Test
    public void loginSuccessTest() {
        DashboardPage dashboardPage = loginPage.login(properties.getProperty("username"), properties.getProperty("password"));
        Assert.assertTrue(dashboardPage.isViolationsGraphVisible());
    }
}
