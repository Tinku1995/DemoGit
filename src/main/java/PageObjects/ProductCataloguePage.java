package PageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import AbstractComponentTest.AbstractComponent;

public class ProductCataloguePage extends AbstractComponent {

    @FindBy(css = ".mb-3")
    private List<WebElement> productList;

    @FindBy(css = ".ng-animating")
    private WebElement spinner;

    private final By productCards = By.cssSelector(".mb-3");
    private final By addToCartBtn = By.cssSelector(".card-body button:last-of-type");
    private final By toastMessage = By.cssSelector("#toast-container");

    public ProductCataloguePage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getProductList() {
        waitForElementsToBeVisible(productCards);
        return productList;
    }

    public WebElement getProductByName(String productName) {
        return getProductList().stream()
                .filter(product -> product.findElement(By.cssSelector("b"))
                        .getText().equalsIgnoreCase(productName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found: " + productName));
    }

    public void addProductToCart(String productName) {
        WebElement product    = getProductByName(productName);
        WebElement addToCart  = product.findElement(By.cssSelector(".card-body button:last-of-type"));
        waitForElementToBeClickable(addToCart);
        addToCart.click();
        waitForElementToBeVisible(toastMessage);
        waitForSpinnerToDisappear();
        waitForToastToDisappear();
    }

    private void waitForSpinnerToDisappear() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector(".ng-animating")));
        } catch (Exception ignored) {}
    }

    private void waitForToastToDisappear() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("#toast-container")));
        } catch (Exception ignored) {}
    }
}