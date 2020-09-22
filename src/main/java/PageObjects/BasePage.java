package PageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public abstract class BasePage {
    protected WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public Object goTo(String url) {
        driver.navigate ().to (url);
        return this;
    }

    public String generatedEmail() {
        return "test" + new Random ().nextInt () + "@wp.pl";
    }

    public static void click(WebDriver driver, long time, ExpectedCondition<WebElement> condition, By by) {
        try {
            (new WebDriverWait (driver, time)).until (condition);
            driver.findElement (by).click ();
        } catch (StaleElementReferenceException sere) {
            driver.findElement (by).click ();
        } catch (TimeoutException toe) {
            toe.printStackTrace ();
        }
    }

    public static void sendKeys(WebDriver driver, long time, ExpectedCondition<WebElement> condition, By by, String keys) {
        try {
            (new WebDriverWait (driver, time)).until (condition);
            driver.findElement (by).sendKeys (keys);
        } catch (StaleElementReferenceException sere) {
            driver.findElement (by).click ();
        } catch (TimeoutException toe) {
            toe.printStackTrace ();
        }
    }
}
