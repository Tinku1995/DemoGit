package resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter {

    private static ExtentReports extent;

    public static ExtentReports getExtentReport() {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(
                    System.getProperty("user.dir") + "/reports/ExtentReport.html");

            sparkReporter.config().setDocumentTitle("Automation Test Report");
            sparkReporter.config().setReportName("E-Commerce Test Results");
            sparkReporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Application", "https://rahulshettyacademy.com/client");
            extent.setSystemInfo("Author", "QA Team");
            extent.setSystemInfo("Environment", "Staging");
        }

        return extent;
    }
}
