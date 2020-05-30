package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentGatewayPage extends BasePage {
    private WebDriverWait wait;

    public PaymentGatewayPage(WebDriver driver) {
        super(driver);
    }

    private By firstSecureFramePath         = By.xpath("/html/body/div[1]/iframe");
    private By secondSecureFramePath        = By.xpath(".//*[@id='challengeFrame']");
    private By transactionCompletePath      = By.xpath(".//*[@id='test-source-authorize-3ds']");
    private By transactionFailPath          = By.xpath(".//*[@id='test-source-fail-3ds']");

    public PaymentGatewayPage goToSecure() {
        wait = new WebDriverWait(driver, 7);
        wait.until(ExpectedConditions.urlContains("zamowienie/#"));
        switchToFrameSecure(firstSecureFramePath);
        switchToFrameSecure(secondSecureFramePath);
        return this;
    }

    public void paymentBySecure(Boolean isComplete) {
        if (isComplete) {
            wait.until(ExpectedConditions.elementToBeClickable(transactionCompletePath)).submit();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(transactionFailPath)).submit();
        }
        driver.switchTo().defaultContent();
    }

    private void switchToFrameSecure(By locatorFrame) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((locatorFrame)));
        wait.until(d -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }
}
