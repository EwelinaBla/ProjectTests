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
        wait = new WebDriverWait(driver, 10);
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
    private By frameNumberCartPath              = By.xpath(".//*[@id='stripe-card-element']/div/iframe");
    private By numberCartPath                   = By.xpath(".//*[@name='cardnumber' and contains(@class, 'Input')]");
    private By frameExpirationDataPath          = By.xpath("//*[@id='stripe-exp-element']/div/iframe");
    private By expirationDatePath               = By.xpath(".//span/input[@name='exp-date']");
    private By frameCvcPath                     = By.xpath("//*[@id='stripe-cvc-element']/div/iframe");
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
        sendKeys (driver, 15, ExpectedConditions.elementToBeClickable (firstNamePath), firstNamePath, firstName);
        sendKeys (driver, 10, ExpectedConditions.elementToBeClickable (lastNamePath), lastNamePath, lastName);
        click (driver, 10, ExpectedConditions.elementToBeClickable (countryContainerPath), countryContainerPath);
        By countryPath = By.xpath(countryLocator.replace("<countryCode>", countryCode));
        driver.findElement(countryPath).click();
        sendKeys (driver, 10, ExpectedConditions.elementToBeClickable (addressPath), addressPath, address);
        sendKeys (driver, 10, ExpectedConditions.elementToBeClickable (cityPath), cityPath, city);
        sendKeys (driver, 10, ExpectedConditions.elementToBeClickable (regionPath), regionPath, region);
        sendKeys (driver, 10, ExpectedConditions.elementToBeClickable (postCodePath), postCodePath, postCode);
        sendKeys (driver, 10, ExpectedConditions.elementToBeClickable (phonePath), phonePath, phoneNumber);
        sendKeys (driver, 10, ExpectedConditions.elementToBeClickable (emailPath), emailPath, email);
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
            click (driver, 10, ExpectedConditions.elementToBeClickable (checkboxAcceptRegulationsPath), checkboxAcceptRegulationsPath);
        }
        return this;
    }

    public ReceivedOrderPage buyAndPay() {
        driver.findElement(buttonBuyAndPayPath).click();
        return new ReceivedOrderPage(driver);
    }

    public String getPaymentMethodDetails() {
        return driver.findElement(paymentMethodPath).getText();
    }

    private void switchToDefaultContentAndFrame(By framePath) {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((framePath)));
    }

    public PaymentPage createAccount(String password) {
        driver.findElement(checkboxCreateAccountPath).click();
        sendKeys (driver, 10, ExpectedConditions.visibilityOfElementLocated (passwordNewUserPath), passwordNewUserPath,password );
        return this;
    }

    public PaymentPage selectCheckboxCreateAccount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkboxCreateAccountPath)).click();
        return this;
    }

    public PaymentPage loginAsUser(String existentEmail, String existentEmailPassword) {
        click (driver, 10, ExpectedConditions.elementToBeClickable (showLoginPath), showLoginPath);
        sendKeys (driver, 10, ExpectedConditions.visibilityOfElementLocated (usernamePath), usernamePath,existentEmail);
        sendKeys (driver, 10, ExpectedConditions.visibilityOfElementLocated (passwordPath), passwordPath,existentEmailPassword);
        click (driver, 10, ExpectedConditions.elementToBeClickable (buttonLoginPath), buttonLoginPath);
        return this;
    }
}
