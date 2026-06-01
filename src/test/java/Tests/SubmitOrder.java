package Tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageObjects.CartPage;
import PageObjects.CheckoutPage;
import PageObjects.ConfirmationPage;
import PageObjects.LandingPage;
import PageObjects.ProductCataloguePage;
import TestComponent.BaseTest;

public class SubmitOrder extends BaseTest {

    @Test(dataProvider = "getMyData")
    public void submitOrderTest(HashMap<String, String> input) {
        LandingPage landingPage = new LandingPage(getDriver());

        // Login
        ProductCataloguePage productCataloguePage = landingPage
                .loginApplication(input.get("email"), input.get("password"));

        // Add product to cart
        productCataloguePage.addProductToCart(input.get("productName"));

        // Go to cart and verify product
        CartPage cartPage = productCataloguePage.goToCartPage();
        Assert.assertTrue(cartPage.verifyProductInCart(input.get("productName")));

        // Proceed to checkout and select country
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        checkoutPage.selectCountry(input.get("country"));

        // Place order and verify confirmation
        ConfirmationPage confirmationPage = checkoutPage.placeOrder();
        Assert.assertEquals(confirmationPage.getConfirmationMessage(),
                "THANKYOU FOR THE ORDER.");
    }

    @DataProvider(name = "getMyData")
    public Object[][] getData() throws IOException {
        List<Map<String, String>> jsonData = getJsonTestData("loginData.json");
        Object[][] data = new Object[jsonData.size()][1];
        for (int i = 0; i < jsonData.size(); i++) {
            data[i][0] = jsonData.get(i);
        }
        return data;
    }
}
