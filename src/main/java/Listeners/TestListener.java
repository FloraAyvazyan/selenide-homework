package Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//Change
public class TestListener implements ITestListener {
    private Map<String, LocalDateTime> caseStartTimes = new HashMap<>();
    private LocalDateTime testStartTime;


    @Override
    public void onTestStart(ITestResult result) {
        LocalDateTime startTime = LocalDateTime.now();
        caseStartTimes.put(result.getMethod().getMethodName(), startTime);
        System.out.printf("Test Case %s has started on %s\n", result.getMethod().getMethodName(), startTime);

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        LocalDateTime startTime = caseStartTimes.get(methodName);
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        // System.out.printf("Test Case %s Successfully executed on %s\n", methodName, new Date(result.getEndMillis()));
        System.out.printf("Test Case %s Finished On %s\n", result.getName(), endTime);
        System.out.printf("Test Case %s took %s\n", methodName, duration);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // screenshot("error.png");
        String methodName = result.getMethod().getMethodName();
        LocalDateTime startTime = caseStartTimes.get(methodName);
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        System.out.printf("Test Case %s Failed on %s\n", methodName, new Date(result.getEndMillis()));
        System.out.printf("Test Case %s Finished On %s\n", result.getName(), endTime);
        System.out.printf("Test Case %s took %s\n", methodName, duration);
    }


    @Override
    public void onStart(ITestContext context) {
        testStartTime = LocalDateTime.now();
        System.out.printf("Test %s Started on %s\n", context.getName(), context.getStartDate());
    }

    @Override
    public void onFinish(ITestContext context) {
        LocalDateTime endTime = LocalDateTime.now();
        System.out.printf("Test %s Finished on %s\n", context.getName(), context.getEndDate());
        Duration duration = Duration.between(testStartTime, endTime);
        System.out.printf("%s Test Took %s\n", context.getName(), duration);
    }

}
