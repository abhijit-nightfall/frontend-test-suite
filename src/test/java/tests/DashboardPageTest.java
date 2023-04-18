package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardPageTest extends BaseTest {

    @Test
    public void testNavigationToSlackPolicyPage() {
        dashboardPage = loginPage.login(properties.getProperty("username").trim(), properties.getProperty("password").trim());
        Assert.assertTrue(dashboardPage.isPageLoaded());
        slackPolicyPage = dashboardPage.navigateToSlackPolicyPage();
        Assert.assertTrue(slackPolicyPage.isPageLoaded());
    }
}
