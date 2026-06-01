package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.LandingPage;
import TestComponent.BaseTest;

public class LoginTest extends BaseTest {

    @Test(groups = { "Login" })
    public void invalidCredentialsTest() {
        LandingPage landingPage = new LandingPage(getDriver());
        landingPage.loginApplication("invalid@gmail.com", "wrongpassword");
        Assert.assertEquals(landingPage.getErrorMessage(),
                "Incorrect email or password.");
    }

    @Test(groups = { "Login" })
    public void blankEmailTest() {
        LandingPage landingPage = new LandingPage(getDriver());
        landingPage.loginApplication("", "Krushak@12345");
        Assert.assertEquals(landingPage.getErrorMessage(),
                "Incorrect email or password.");
    }
}
