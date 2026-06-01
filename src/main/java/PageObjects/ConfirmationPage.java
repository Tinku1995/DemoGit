package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import AbstractComponentTest.AbstractComponent;

public class ConfirmationPage extends AbstractComponent {

    @FindBy(css = ".hero-primary")
    private WebElement confirmationMessage;

    @FindBy(css = ".em-spacer-1 .ng-star-inserted")
    private WebElement orderId;

    private final By confirmationMsgLocator = By.cssSelector(".hero-primary");

    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public String getConfirmationMessage() {
        waitForElementToBeVisible(confirmationMsgLocator);
        return confirmationMessage.getText().trim();
    }

    public String getOrderId() {
        waitForElementToBeVisible(orderId);
        return orderId.getText().trim();
    }
}
