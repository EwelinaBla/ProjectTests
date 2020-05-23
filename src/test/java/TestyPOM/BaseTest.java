package TestyPOM;

import Drivers.DriverFactory;
import Helpers.StatusTest;
import Drivers.Browser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected static String baseUrl;
    private static String hubUrl;
    private static String browser;

    @RegisterExtension
    StatusTest statusTest = new StatusTest();

    @BeforeAll
    public static void loadConfig() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("C:\\Users\\Paweł\\IdeaProjects\\Project\\src\\Configs\\Configurations.properties"));
        hubUrl = properties.getProperty("hubUrl");
        baseUrl = properties.getProperty("baseUrl");
        browser = properties.getProperty("browser");
    }

    @BeforeEach
    public void setUp() throws MalformedURLException {
//        local tests
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();

//        DriverFactory driverFactory=new DriverFactory();
//        driver=driverFactory.create(Browser.valueOf(browser), hubUrl);

        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.navigate().to("https://fakestore.testelka.pl/");
    }

    @AfterEach
    public void quit(TestInfo info) throws IOException {
        if (statusTest.isField) {
            System.out.println(" The screenshot is available in: " + screenshot(info));
        }
        driver.quit();
    }

    private String screenshot(TestInfo info) throws IOException {
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String path = "C:\\Users\\Paweł\\Downloads\\ScreenShots\\" + info.getDisplayName() + formatter.format(LocalDateTime.now()) + ".jpg";
        FileUtils.copyFile(screen, new File(path));
        return path;
    }
}
