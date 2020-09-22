package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentGatewayPage extends BasePage {
    private WebDriverWait wait;

    public PaymentGatewayPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 10);
    }

    private By firstSecureFramePath         = By.xpath(".//iframe[@name= '__privateStripeFrame35']");
    private By secondSecureFramePath        = By.xpath(".//*[@id='challengeFrame']");
    private By thirdSecureFramePath         = By.xpath(".//iframe[@class='FullscreenFrame']");
    private By transactionCompletePath      = By.xpath(".//button[@id='test-source-authorize-3ds']");
    private By transactionFailPath          = By.xpath(".//button[@id='test-source-fail-3ds']");

    public PaymentGatewayPage goToSecure() {
        wait.until(ExpectedConditions.urlContains("zamowienie/#"));
        switchToFrameSecure(firstSecureFramePath);

        if( driver.findElement (secondSecureFramePath).getAttribute ("class")=="AuthorizeWithUrlApp-content"){
            switchToFrameSecure(secondSecureFramePath);
            switchToFrameSecure(thirdSecureFramePath);
        } else
            switchToFrameSecure(secondSecureFramePath);
        return this;
    }

    public void paymentBySecure(Boolean isComplete) {
        wait.until(ExpectedConditions.elementToBeClickable(isComplete ? transactionCompletePath : transactionFailPath)).submit();
        driver.switchTo().defaultContent();
    }
    private void switchToFrameSecure(By locatorFrame) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((locatorFrame)));
        wait.until(d -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }
}
