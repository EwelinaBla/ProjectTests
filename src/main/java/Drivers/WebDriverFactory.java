package Drivers;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory {

    private RemoteWebDriver driver;

    public WebDriver create(Browser browserType, String hubUrl) {
        switch (browserType) {
            case CHROME:
                return getChromeDriver(hubUrl);
            case FIREFOX:
                return getFirefoxDriver(hubUrl);
            default:
                throw new IllegalArgumentException("Not supported browser " + browserType);
        }
    }

    private WebDriver getFirefoxDriver(String hubUrl) {
        FirefoxOptions options = new FirefoxOptions();
        return getDriver(hubUrl, options);
    }

    private WebDriver getChromeDriver(String hubUrl) {
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.VERSION, "81");
        return getDriver(hubUrl, options);
    }

    private WebDriver getDriver(String hubUrl, MutableCapabilities options) {
        try {
            driver = new RemoteWebDriver(new URL(hubUrl), options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("HubUrl in the configurations file is incorrect or missing");
        }
        return driver;
    }
}
