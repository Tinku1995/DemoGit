package PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import AbstractComponentTest.AbstractComponent;

public class CartPage extends AbstractComponent {

    @FindBy(css = "div.cart h3")
    private List<WebElement> cartProductNames;

    @FindBy(css = ".totalRow button.btn-primary")
    private WebElement checkoutButton;

    private final By cartProductNamesLocator = By.cssSelector("div.cart h3");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getCartProducts() {
        waitForElementsToBeVisible(cartProductNamesLocator);
        return cartProductNames;
    }

    public boolean verifyProductInCart(String productName) {
        return getCartProducts().stream()
                .anyMatch(item -> item.getText().equalsIgnoreCase(productName));
    }

    public CheckoutPage proceedToCheckout() {
        waitForElementToBeClickable(checkoutButton);
        checkoutButton.click();
        return new CheckoutPage(driver);
    }
}
