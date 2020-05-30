package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class PaymentPage extends BasePage {
    private WebDriverWait wait;

    public PaymentPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 5);
    }

    //region PATH
    private By firstNamePath                    = By.xpath(".//*[@id='billing_first_name']");
    private By lastNamePath                     = By.xpath(".//*[@id='billing_last_name']");
    private By countryContainerPath             = By.xpath(".//*[@id='select2-billing_country-container']");
    private By addressPath                      = By.xpath(".//*[@id='billing_address_1']");
    private By cityPath                         = By.xpath(".//*[@id='billing_city']");
    private By regionPath                       = By.xpath(".//*[@id='billing_state']");
    private By postCodePath                     = By.xpath(".//*[@id='billing_postcode']");
    private By phonePath                        = By.xpath(".//*[@id='billing_phone']");
    private By emailPath                        = By.xpath(".//*[@id='billing_email']");
    private By frameNumberCartPath              = By.xpath(".//iframe[@name='__privateStripeFrame8']");
    private By numberCartPath                   = By.xpath(".//*[@name='cardnumber']");
    private By frameExpirationDataPath          = By.xpath(".//iframe[@name='__privateStripeFrame9']");
    private By expirationDatePath               = By.xpath(".//span/input[@name='exp-date']");
    private By frameCvcPath                     = By.xpath(".//iframe[@name='__privateStripeFrame10']");
    private By cvcPath                          = By.xpath(".//span/input[@name='cvc']");
    private By checkboxAcceptRegulationsPath    = By.xpath(".//*[@id='terms']");
    private By buttonBuyAndPayPath              = By.xpath(".//button[@id='place_order']");
    private By checkboxCreateAccountPath        = By.xpath(".//*[@id='createaccount']");
    private By passwordNewUserPath              = By.xpath(".//*[@id='account_password']");
    private By showLoginPath                    = By.xpath(".//div[@class='woocommerce-info']//a[1]");
    private By usernamePath                     = By.xpath(".//*[@id='username']");
    private By passwordPath                     = By.xpath("//*[@id='password']");
    private By buttonLoginPath                  = By.xpath(".//button[@name='login']");
    private By paymentMethodPath                = By.xpath("//*[contains(@class,'payment_method_stripe')]/label");
    //endregion
    private String countryLocator = ".//*[contains(@id,'<countryCode>')]";

    public PaymentPage fillPaymentDetails(String firstName, String lastName, String countryCode, String address,
                                          String city, String region, String postCode, String phoneNumber, String email) {
        wait.until(ExpectedConditions.elementToBeClickable(firstNamePath)).sendKeys(firstName);
        wait.until(ExpectedConditions.elementToBeClickable(lastNamePath)).sendKeys(lastName);
        wait.until(ExpectedConditions.elementToBeClickable(countryContainerPath)).click();
        By countryPath = By.xpath(countryLocator.replace("<countryCode>", countryCode));
        driver.findElement(countryPath).click();
        wait.until(ExpectedConditions.elementToBeClickable(addressPath)).sendKeys(address);
        wait.until(ExpectedConditions.elementToBeClickable(cityPath)).sendKeys(city);
        wait.until(ExpectedConditions.elementToBeClickable(regionPath)).sendKeys(region);
        wait.until(ExpectedConditions.elementToBeClickable(postCodePath)).sendKeys(postCode);
        wait.until(ExpectedConditions.elementToBeClickable(phonePath)).sendKeys(phoneNumber);
        wait.until(ExpectedConditions.elementToBeClickable(emailPath)).sendKeys(email);
        return this;
    }

    public PaymentPage fillCartInformation(String numberCart, String expirationDate, String cvc) {
        switchToDefaultContentAndFrame(frameNumberCartPath);

        WebElement elementNumberCart = wait.until(ExpectedConditions.elementToBeClickable(numberCartPath));
        for (int i = 0; i < numberCart.length(); i++) {
            String indeksOfNumberCart = Character.toString(numberCart.charAt(i));
            elementNumberCart.sendKeys(indeksOfNumberCart);
        }

        switchToDefaultContentAndFrame(frameExpirationDataPath);
        driver.findElement(expirationDatePath).sendKeys(expirationDate);

        switchToDefaultContentAndFrame(frameCvcPath);
        driver.findElement(cvcPath).sendKeys(cvc);

        driver.switchTo().defaultContent();
        return this;
    }

    public PaymentPage checkboxAcceptanceOfRegulations(Boolean isSelected) {
        if (isSelected) {
            wait.until(ExpectedConditions.elementToBeClickable(checkboxAcceptRegulationsPath)).click();
        }
        return this;
    }

    public ReceivedOrderPage buyAndPay() {
        driver.findElement(buttonBuyAndPayPath).click();
        return new ReceivedOrderPage(driver);
    }

    public String getPaymentMethodDetails() {
        String paymentMethod = driver.findElement(paymentMethodPath).getText();
        return paymentMethod;
    }

    public String generatedEmail() {
        Random r = new Random();
        int number = r.nextInt();
        return "test" + number + "@wp.pl";
    }

    private void switchToDefaultContentAndFrame(By framePath) {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((framePath)));
    }

    public PaymentPage createAccount(String password) {
        driver.findElement(checkboxCreateAccountPath).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordNewUserPath)).sendKeys(password);
        return this;
    }

    public PaymentPage selectCheckboxCreateAccount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkboxCreateAccountPath)).click();
        return this;
    }

    public PaymentPage loginAsUser(String existentEmail, String existentEmailPassword) {
        wait.until(ExpectedConditions.elementToBeClickable(showLoginPath)).click();
        wait.until(ExpectedConditions.elementToBeClickable(usernamePath)).sendKeys(existentEmail);
        wait.until(ExpectedConditions.elementToBeClickable(passwordPath)).sendKeys(existentEmailPassword);
        wait.until(ExpectedConditions.elementToBeClickable(buttonLoginPath)).click();
        return this;
    }
}
