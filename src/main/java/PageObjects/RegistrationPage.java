package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import AbstractComponentTest.AbstractComponent;

public class RegistrationPage extends AbstractComponent {

    @FindBy(css = "input[placeholder='First Name']")
    private WebElement firstNameField;

    @FindBy(css = "input[placeholder='Last Name']")
    private WebElement lastNameField;

    @FindBy(css = "input[placeholder='email@example.com']")
    private WebElement emailField;

    @FindBy(css = "input[placeholder='enter your number']")
    private WebElement phoneNumberField;

    @FindBy(css = "select")
    private WebElement occupationDropdown;

    @FindBy(css = "input[value='Male']")
    private WebElement genderMale;

    @FindBy(css = "input[value='Female']")
    private WebElement genderFemale;

    @FindBy(css = "input[placeholder='Passsword']")
    private WebElement passwordField;

    @FindBy(css = "input[placeholder='Confirm Passsword']")
    private WebElement confirmPasswordField;

    @FindBy(css = "input[type='checkbox']")
    private WebElement ageCheckbox;

    @FindBy(id = "login")
    private WebElement registerButton;

    @FindBy(css = "#toast-container")
    private WebElement successToast;

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void registerUser(String firstName, String lastName, String email,
            String phoneNumber, String occupation, String gender,
            String password, String confirmPassword) {

        waitForElementToBeVisible(firstNameField);
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        emailField.sendKeys(email);
        phoneNumberField.sendKeys(phoneNumber);

        waitForElementToBeVisible(occupationDropdown);
        Select select = new Select(occupationDropdown);
        select.selectByVisibleText(occupation);

        if (gender.equalsIgnoreCase("Male")) {
            genderMale.click();
        } else {
            genderFemale.click();
        }

        passwordField.sendKeys(password);
        confirmPasswordField.sendKeys(confirmPassword);

        if (!ageCheckbox.isSelected()) {
            ageCheckbox.click();
        }

        waitForElementToBeClickable(registerButton);
        registerButton.click();
    }

    public String getSuccessMessage() {
        waitForElementToBeVisible(successToast);
        return successToast.getText().trim();
    }
}
