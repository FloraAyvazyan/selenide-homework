package Listeners;

import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReportListener implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> results = suite.getResults();
            for (ISuiteResult suiteResult : results.values()) {
                Set<ITestResult> failedTests = suiteResult.getTestContext().getFailedTests().getAllResults();
                for (ITestResult failedTest : failedTests) {
                    System.out.println("Failed Test: " + failedTest.getName());
                    System.out.println("Description: " + failedTest.getMethod().getDescription());
                }
            }
        }
    }
}