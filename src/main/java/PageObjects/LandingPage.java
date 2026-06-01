package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponentTest.AbstractComponent;

public class LandingPage extends AbstractComponent {

    @FindBy(id = "userEmail")
    private WebElement emailField;

    @FindBy(id = "userPassword")
    private WebElement passwordField;

    @FindBy(id = "login")
    private WebElement signInButton;

    @FindBy(css = "[class*='flyInOut']")
    private WebElement errorMessage;

    @FindBy(css = "[routerlink*='register']")
    private WebElement registerLink;

    public LandingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void goTo() {
        driver.get("https://rahulshettyacademy.com/client");
    }

    public ProductCataloguePage loginApplication(String email, String password) {
        waitForElementToBeVisible(emailField);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        signInButton.click();
        return new ProductCataloguePage(driver);
    }

    public String getErrorMessage() {
        waitForElementToBeVisible(errorMessage);
        return errorMessage.getText();
    }

    public RegistrationPage goToRegisterPage() {
        waitForElementToBeClickable(registerLink);
        registerLink.click();
        return new RegistrationPage(driver);
    }
}