package TestyPOM;

import Helpers.StatusTest;
import Utils.ConfigurationReader;
import Utils.TestDataReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    protected WebDriver driver;
    protected ConfigurationReader configuration;
    protected TestDataReader testData;

    private final String configurationLocation = "src/Configs/Configurations.properties";
    private final String testDataLocation = "src/test/java/TestData.properties";

    @RegisterExtension
    StatusTest statusTest = new StatusTest();

    @BeforeAll
    public void getConfiguration() {
        configuration = new ConfigurationReader(configurationLocation);
        testData = new TestDataReader(testDataLocation);
    }

    @BeforeEach
    public void setUp() {
//        local tests
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();

//          Grid
//        WebDriverFactory driverFactory=new WebDriverFactory();
//        driver=driverFactory.create(BrowserType.valueOf(configuration.getBrowser()), configuration.getHubUrl());

        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void quit(TestInfo info) throws IOException {
        if (statusTest.isField) {
            System.out.println(" The screenshot is available in: " + makeScreenshot(info));
        }
        driver.quit();
    }

    private String makeScreenshot(TestInfo info) throws IOException {
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String path = configuration.getPathScreenshot() + info.getDisplayName() + formatter.format(LocalDateTime.now()) + ".jpg";
        FileUtils.copyFile(screen, new File(path));
        return path;
    }
}
