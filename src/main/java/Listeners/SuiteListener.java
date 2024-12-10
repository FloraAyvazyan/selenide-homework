package Listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import java.time.Duration;
import java.time.LocalDateTime;

public class SuiteListener implements ISuiteListener {
    private LocalDateTime startTime; // ეს გლობალურად შევქმენი რომ ხილვადი იყოს მეორეში


    @Override
    public void onStart(ISuite suite) {
        //Logs start date of each suite.
        startTime = LocalDateTime.now();
        System.out.printf("Suite %s  Started On %s\n", suite.getName(), startTime);
    }

    @Override
    public void onFinish(ISuite suite) {
        //Logs end date of each suite.
        LocalDateTime endTime = LocalDateTime.now();
        System.out.printf("Suite %s Finished On %s\n", suite.getName(), endTime);
        //Logs total time needed to execute the suite.
        Duration duration = Duration.between(startTime, endTime);
        System.out.printf("Suite %s has taken %s time\n", suite.getName(), duration);
    }




}