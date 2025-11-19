package com.example.tests.ui.base;

import com.example.framework.WebDriverFactory;
import com.example.reporting.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.ITestResult;
import com.example.utils.ScreenshotUtil;

import java.lang.reflect.Method;

@Listeners({com.example.tests.ui.base.TestListener.class})
public class BaseTest {
    protected WebDriver driver;
    protected ExtentReports extent = ExtentManager.getInstance();
    protected ExtentTest test;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browserMode"})
    public void setUp(@Optional("headless") String browserMode) {
        driver = WebDriverFactory.createDriver(browserMode);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        test = extent.createTest(method.getName());
    }

    @AfterMethod
    public void tearDownMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String path = ScreenshotUtil.capture(driver, result.getName());
            if (path != null) {
                test.addScreenCaptureFromPath(path);
            }
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) driver.quit();
        extent.flush();
    }
}
