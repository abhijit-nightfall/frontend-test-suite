package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static factory.PlaywrightFactory.takeScreenshot;

public class ExtentReportListener implements ITestListener {

    private static final String OUTPUT_FOLDER = "./build/";
    private static final String FILE_NAME = "TestExecutionReport.html";

    private static ExtentReports extent = init();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    private static ExtentReports init() {
        Path path = Paths.get(OUTPUT_FOLDER);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
        extentSparkReporter.config().setReportName("Frontend Regression Test Suite");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("AUT", "Frontend Dashboard");

        return extentReports;
    }

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        String methodName = result.getMethod().getMethodName();
        String className = getClassName(result);

        System.out.println(methodName + " : Started");
        ExtentTest extentTest = extent.createTest(methodName, result.getMethod().getDescription());
        extentTest.assignCategory(className);
        test.set(extentTest);
        test.get().getModel().setStartTime(new Date(result.getStartMillis()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println( result.getMethod().getMethodName() + " : Passed");
        //test.get().pass("Test Passed");
        test.get().pass(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        test.get().getModel().setEndTime(new Date(result.getEndMillis()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println( result.getMethod().getMethodName() + " : Failed");
        test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        test.get().getModel().setEndTime(new Date(result.getEndMillis()));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println( result.getMethod().getMethodName() + " : Skipped");
        test.get().skip(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        test.get().getModel().setEndTime(new Date(result.getEndMillis()));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite is : Started");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite is : Terminated");
        extent.flush();
        test.remove();
    }

    private static String getClassName(ITestResult result) {
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid+1, last);
        return className;
    }
}
