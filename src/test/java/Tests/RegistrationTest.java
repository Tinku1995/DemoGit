package Tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageObjects.LandingPage;
import PageObjects.RegistrationPage;
import TestComponent.BaseTest;

public class RegistrationTest extends BaseTest {

    @Test(dataProvider = "getRegisterData")
    public void registerUserTest(HashMap<String, String> input) {
        LandingPage landingPage = new LandingPage(getDriver());

        // Navigate to registration page
        RegistrationPage registrationPage = landingPage.goToRegisterPage();

        // Fill and submit registration form
        registrationPage.registerUser(
                input.get("firstName"),
                input.get("lastName"),
                input.get("email"),
                input.get("phoneNumber"),
                input.get("occupation"),
                input.get("gender"),
                input.get("password"),
                input.get("confirmPassword")
        );

        // Verify success message
        String successMsg = registrationPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Account Created Successfully"),
                "Registration failed. Actual message: " + successMsg);
    }
    
    public void invalidLogin()
    {
    	System.out.println("You have done invalid login");
    	System.out.println("You can try to login again");
    }
    
    public void finalLogin()
    {
    	System.out.println("Make sure this will be last login");
    }

    @DataProvider(name = "getRegisterData")
    public Object[][] getRegisterData() throws IOException {
        List<Map<String, String>> jsonData = getJsonTestData("registrationData.json");
        Object[][] data = new Object[jsonData.size()][1];
        for (int i = 0; i < jsonData.size(); i++) {
            data[i][0] = jsonData.get(i);
        }
        return data;
    }
}
