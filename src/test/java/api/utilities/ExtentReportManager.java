
//package api.utilities;
//
//import java.awt.Desktop;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
////Extent report 5.x...//version
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import com.aventstack.extentreports.reporter.configuration.Theme;
//
//public class ExtentReportManager implements ITestListener {
//	public ExtentSparkReporter sparkReporter;
//	public ExtentReports extent;
//	public ExtentTest test;
//
//	String repName;
//
//	public void onStart(ITestContext testContext) {
//
//		// Clean up jQuery file to avoid FileAlreadyExistsException
//	    try {
//	        Files.deleteIfExists(Paths.get("test-output/jquery-3.6.0.min.js"));
//	        System.out.println("✅ Deleted: test-output/jquery-3.6.0.min.js");
//	    } catch (IOException e) {
//	        System.out.println("⚠️ Unable to delete jquery-3.6.0.min.js");
//	        e.printStackTrace();
//	    }
//
////  SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
////  Date dt=new Date();
////  String currentdatetimestamp=df.format(dt);
//
//
//  String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
//  repName = "Test-Report-" + timeStamp + ".html";
//  sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);// specifylocation of the report
//
//  sparkReporter.config().setDocumentTitle("opencart Automation Report"); //Title of report
//  sparkReporter.config().setReportName("opencart Functional Testing"); // nameof the report
//  sparkReporter.config().setTheme(Theme.DARK);
//
//  extent = new ExtentReports(); extent.attachReporter(sparkReporter);
//  extent.setSystemInfo("Application", "DemoQA"); extent.setSystemInfo("Module",
//  "Practice Element"); extent.setSystemInfo("User Name",
//  System.getProperty("user.name")); extent.setSystemInfo("Environemnt", "QA");
//
//  String os = testContext.getCurrentXmlTest().getParameter("os");
//  extent.setSystemInfo("Operating System", os);
//
//  String browser = testContext.getCurrentXmlTest().getParameter("browser");
//  extent.setSystemInfo("Browser", browser);
//
//  List<String> includedGroups =
//  testContext.getCurrentXmlTest().getIncludedGroups();
//  if(!includedGroups.isEmpty()) { extent.setSystemInfo("Groups",
//  includedGroups.toString()); } }
//
//	public void onTestSuccess(ITestResult result) {
//
//  test = extent.createTest(result.getTestClass().getName());
//  test.assignCategory(result.getMethod().getGroups()); // to display groups in report
//  test.log(Status.PASS,result.getName()+" got successfully executed");
//
//  }
//
//	public void onTestFailure(ITestResult result) {
//		test = extent.createTest(result.getTestClass().getName());
//		test.assignCategory(result.getMethod().getGroups());
//
//		test.log(Status.FAIL, result.getName() + " got failed");
//		test.log(Status.INFO, result.getThrowable());
//
//	}
//
//	public void onTestSkipped(ITestResult result) {
//		test = extent.createTest(result.getTestClass().getName());
//		test.assignCategory(result.getMethod().getGroups());
//		test.log(Status.SKIP, result.getName() + " got skipped");
//		test.log(Status.INFO, result.getThrowable().getMessage());
//	}
//
//	public void onFinish(ITestContext testContext) {
//
//		extent.flush();
//
//		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
//		File extentReport = new File(pathOfExtentReport);
//
//		try {
//			Desktop.getDesktop().browse(extentReport.toURI());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//}

//--------------------------------------------------------------------------------------------------------------------------------------
package api.utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.restassured.response.Response;

public class ExtentReportManager implements ITestListener {

    private ExtentReports extReports;
    private static ThreadLocal <ExtentTest> test = new ThreadLocal<>();
    private String realtime;

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void setTest(ExtentTest Test) {
        test.set(Test);
    }

    @Override
    public void onStart(ITestContext context) {
    	try {
    	    File testOutputDir = new File(System.getProperty("user.dir") + "/test-output");
    	    if (testOutputDir.exists()) {
    	        FileUtils.deleteDirectory(testOutputDir);
    	        System.out.println("🧹 Deleted old test-output folder");
    	    }
    	} catch (IOException e) {
    	    System.out.println("⚠️ Could not delete test-output folder: " + e.getMessage());
    	}


        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH_mm_ss a");
        realtime = "Test-Report-" + time.format(customFormat) + ".html"; // Ensure .html extension

        extReports = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./reports/" + realtime);

        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Stan-Club API Automation");
        sparkReporter.config().setReportName("Manabi: Stan-Club API Automation");
        sparkReporter.config().setTimeStampFormat("dd/MM/yyyy hh:mm:ss a");

        extReports.attachReporter(sparkReporter);
        extReports.setSystemInfo("QA Engg.", "Manabi");
        extReports.setSystemInfo("Application", "Stan");
        extReports.setSystemInfo("Module", "Club");
        extReports.setSystemInfo("Environment", "Staging");
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extReports.createTest(
            result.getTestClass().getName() + " - " +
            result.getMethod().getPriority() + ". " +
            result.getMethod().getMethodName(),
            result.getMethod().getDescription()
        );
        setTest(extentTest);
        getTest().log(Status.INFO, result.getName() + " started executing.");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = getTest();
        if (test != null) {
            test.createNode(result.getName());
            test.assignCategory(result.getMethod().getGroups());
            test.log(Status.PASS, result.getName() + " got successfully executed.");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = getTest();
        if (test != null) {
            test.createNode(result.getName());
            test.assignCategory(result.getMethod().getGroups());
            test.log(Status.INFO, result.getThrowable());
            test.log(Status.FAIL, result.getName() + " got failed.");

        } else {
            System.out.println("ExtentTest is null in onTestFailure for: " + result.getName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = getTest();
        if (test != null) {
            test.createNode(result.getName());
            test.assignCategory(result.getMethod().getGroups());
            test.log(Status.INFO, result.getThrowable());
            test.log(Status.SKIP, result.getName() + " got skipped.");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extReports.flush();

        try {
            Thread.sleep(2000); // Optional delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String pathOfExtentReport = System.getProperty("user.dir") + "/reports/" + realtime;
        File extentReport = new File(pathOfExtentReport);

        if (extentReport.exists()) {
            try {
                Desktop.getDesktop().browse(extentReport.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Report file not found: " + pathOfExtentReport);
        }
    }
}