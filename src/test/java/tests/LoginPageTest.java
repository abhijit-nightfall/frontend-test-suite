package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test
    public void loginSuccessTest() {
        Assert.assertTrue(loginPage.login(properties.getProperty("username"), properties.getProperty("password")));
    }
}
