package PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import AbstractComponentTest.AbstractComponent;

public class CheckoutPage extends AbstractComponent {

    @FindBy(css = "[placeholder='Select Country']")
    private WebElement countryInput;

    @FindBy(css = ".ta-results")
    private WebElement countryDropdown;

    @FindBy(css = ".action__submit")
    private WebElement placeOrderButton;

    private final By countryOptions = By.cssSelector(".ta-item span");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void selectCountry(String countryName) {
        waitForElementToBeVisible(countryInput);
        countryInput.sendKeys(countryName.substring(0, 3));
        waitForElementToBeVisible(countryDropdown);
        List<WebElement> options = waitForElementsToBeVisible(countryOptions);
        options.stream()
                .filter(option -> option.getText().trim().equalsIgnoreCase(countryName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Country not found: " + countryName))
                .click();
    }

    public ConfirmationPage placeOrder() {
        waitForElementToBeClickable(placeOrderButton);
        placeOrderButton.click();
        return new ConfirmationPage(driver);
    }
}
