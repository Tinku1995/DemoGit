package TestComponent;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.JsonDataReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BaseTest {

    protected static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public Properties prop;

    private static final String SCREENSHOT_DIR =
            System.getProperty("user.dir") + "/reports/screenshots/";

    // ── Browser Initialisation ───────────────────────────────────────────────

    public WebDriver initializeBrowser() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream("src/main/java/resources/globalData.properties");
        prop.load(fis);

        String browserName = prop.getProperty("browser");

        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");

            // Suppress Chrome password manager and breach warning popups
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.password_manager_leak_detection", false);
            options.setExperimentalOption("prefs", prefs);

            tlDriver.set(new ChromeDriver(options));
        }

        return getDriver();
    }

    // ── Launch Application ───────────────────────────────────────────────────

    @BeforeMethod(alwaysRun = true)
    public void launchApplication() throws IOException {
        WebDriver driver = initializeBrowser();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.get(prop.getProperty("appUrl"));
    }

    // ── Tear Down ────────────────────────────────────────────────────────────

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            tlDriver.remove();
        }
    }

    // ── Driver Accessor ──────────────────────────────────────────────────────

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    // ── Screenshot Utility ───────────────────────────────────────────────────

    public static String captureScreenshot(String testName) throws IOException {
        String timestamp   = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName    = testName + "_" + timestamp + ".png";
        String absolutePath = SCREENSHOT_DIR + fileName;

        byte[] screenshotBytes = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);

        Path path = Paths.get(absolutePath);
        Files.createDirectories(path.getParent());
        Files.write(path, screenshotBytes);

        return absolutePath;
    }

    // ── JSON Test Data Reader ─────────────────────────────────────────────────

    public List<Map<String, String>> getJsonTestData(String fileName) throws IOException {
        return JsonDataReader.readTestData("src/test/java/TestResources/" + fileName);
    }
}
