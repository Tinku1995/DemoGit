package AbstractComponentTest;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import PageObjects.CartPage;
import PageObjects.OrdersPage;

public class AbstractComponent {

    protected WebDriver driver;
    private WebDriverWait wait;

    // ── Header Navigation Elements ───────────────────────────────────────────

    @FindBy(css = "[routerlink*='cart']")
    private WebElement cartHeader;

    @FindBy(css = "[routerlink*='myorders']")
    private WebElement ordersHeader;

    @FindBy(css = ".toast-container")
    private WebElement toastMessage;

    // ── Constructor ──────────────────────────────────────────────────────────

    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // ── Explicit Wait Utilities ──────────────────────────────────────────────

    public void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public List<WebElement> waitForElementsToBeVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    public WebElement waitForElementToBeVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitForUrlToContain(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    // ── Toast Message ────────────────────────────────────────────────────────

    public String waitAndGetToastMessage() {
        waitForElementToBeVisible(toastMessage);
        return toastMessage.getText();
    }

    // ── Navigation ───────────────────────────────────────────────────────────

    public CartPage goToCartPage() {
        waitForElementToBeClickable(cartHeader);
        cartHeader.click();
        waitForUrlToContain("cart");
        return new CartPage(driver);
    }

    public OrdersPage goToOrdersPage() {
        waitForElementToBeClickable(ordersHeader);
        ordersHeader.click();
        waitForUrlToContain("myorders");
        return new OrdersPage(driver);
    }
}
