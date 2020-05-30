package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceivedOrderPage extends BasePage {
    private WebDriverWait wait;

    public ReceivedOrderPage(WebDriver driver) {
        super(driver);
    }

    private By numberOrderPath                  = By.xpath(".//*[contains(@class, 'order order')]//strong");
    private By listAlertMessagePath             = By.xpath(".//*[@class='woocommerce-error']");
    private By alertMessagePath                 = By.xpath(".//*[@class='woocommerce-error']//li");
    private By productNameOrderPath             = By.xpath("//*[contains(@class,' product-name')]//a");
    private By totalOrderPath                   = By.xpath(".//*[contains(@class, 'total total')]//strong");
    private By productQuantityOrderPath         = By.xpath("//*[@class='product-quantity']");
    private By paymentMethodOrderPath           = By.xpath(".//*[contains(@class, 'payment-method method')]//strong");
    private By dataOfOrderPath                  = By.xpath(".//*[contains(@class, 'date date')]//strong");
    private By stripeErrorPath                  = By.xpath(".//div[@class='stripe-source-errors']//*[contains(@class, 'woocommerce_error')]");

    public String getNumberOrder() {
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.urlContains("zamowienie-otrzymane"));
        return driver.findElement(numberOrderPath).getText();
    }

    public int getSizeListAlertMessage() {
        wait = new WebDriverWait(driver, 7);
        wait.until(ExpectedConditions.presenceOfElementLocated(listAlertMessagePath));
        return driver.findElements(alertMessagePath).size();
    }

    public String getProductName() {
        return driver.findElement(productNameOrderPath).getText();
    }

    public String getPriceProduct() {
        return driver.findElement(totalOrderPath).getText();
    }

    public String getProductQuantity() {
        return driver.findElement(productQuantityOrderPath).getText();
    }

    public String getDateOfOrder() {
        return driver.findElement(dataOfOrderPath).getText();
    }

    public String getPaymentMethod() {
        return driver.findElement(paymentMethodOrderPath).getText();
    }

    public String currentDate() {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d MMMM, yyyy");
        return formatterDate.format(LocalDateTime.now());
    }

    public String getAlert() {
        wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(stripeErrorPath)).getText();
    }
}
