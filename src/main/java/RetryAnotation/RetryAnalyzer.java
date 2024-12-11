package RetryAnotation;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    int count = 0;
    @Override
    public boolean retry(ITestResult iTestResult) {
        RetryCount annotation = iTestResult.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(RetryCount.class);

        if ((annotation != null) && count < annotation.count()){
            count++;
            return true;
        }
        return false;
    }
}
