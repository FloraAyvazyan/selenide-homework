import com.codeborne.selenide.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

//კარგი imagination არ მაქვს დდ
public class ConfigTest {
    @BeforeSuite
    public void initialSetup(){
        Configuration.timeout = 12000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.screenshots = true;
        Configuration.fileDownload = FileDownloadMode.HTTPGET;
        Configuration.pageLoadTimeout = 12000;
    }
    @BeforeTest
    @Parameters({"browserType"})
    public void setUp(@Optional("chrome") String browserType) {
        switch (browserType.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                WebDriverRunner.setWebDriver(new ChromeDriver());
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                WebDriverRunner.setWebDriver(new FirefoxDriver());
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                WebDriverRunner.setWebDriver(new EdgeDriver());
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
        Configuration.timeout = 10000;
        WebDriverRunner.getWebDriver().manage().window().maximize();
        Configuration.assertionMode = AssertionMode.STRICT;
        Configuration.holdBrowserOpen = true;
    }

//    @AfterClass
//    public void tearDown(){
//        Selenide.closeWindow();
//    }
}