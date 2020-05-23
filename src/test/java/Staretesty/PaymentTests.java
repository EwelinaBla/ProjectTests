package Staretesty;

import Helpers.StatusTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PaymentTests {
    WebDriver driver;
    WebDriverWait wait;
    //region PATHS
    By alertCookiesPath = By.xpath(".//a[@class='woocommerce-store-notice__dismiss-link'] ");
    By shopPath = By.xpath(".//*[@id='menu-item-198']/a");
    By firstNamePath = By.xpath(".//*[@id='billing_first_name']");
    By lastNamePath = By.xpath(".//*[@id='billing_last_name']");
    By countryContainerPath = By.xpath(".//*[@id='select2-billing_country-container']");
    By countryAndoraPath = By.xpath(".//select[@id='billing_country']//*[@value='AD']");
    By addressPath = By.xpath(".//*[@id='billing_address_1']");
    By cityPath = By.xpath(".//*[@id='billing_city']");
    By regionPath = By.xpath(".//*[@id='billing_state']");
    By postCodePath = By.xpath(".//*[@id='billing_postcode']");
    By phonePath = By.xpath(".//*[@id='billing_phone']");
    By emailPath = By.xpath(".//*[@id='billing_email']");
    By createAccountPath = By.xpath(".//*[@id='createaccount']");
    By passwordNewUserPath = By.xpath(".//*[@id='account_password']");
    By numberCartPath = By.xpath(".//*[@name='cardnumber']");
    By expirationDatePath = By.xpath(".//span/input[@name='exp-date']");
    By cvcPath = By.xpath(".//span/input[@name='cvc']");
    By nameCategoryPath = By.xpath(".//*[@class='products columns-3']/li[2]//a//img");
    By buttonByCategoryPage = By.xpath(".//*[@id='main']/ul/li[2]/a[2]");
    By lookCartFromCategoryPagePath = By.xpath(".//*[contains(@class, 'added_to_cart')]");
    By cartTitlePath = By.xpath(".//*[@id='post-6']/header/h1");
    By buttonGoToCashPath = By.xpath(".//a[contains(@class,'checkout-button')]");
    By frameNumberCartPath = By.xpath(".//iframe[@name='__privateStripeFrame8']");
    By frameExpirationDataPath = By.xpath(".//iframe[@name='__privateStripeFrame9']");
    By frameCvcPath = By.xpath(".//iframe[@name='__privateStripeFrame10']");
    By buttonBuyAnDPayPath = By.xpath(".//button[@id='place_order']");
    By listAlertMessagePath = By.xpath(".//*[@class='woocommerce-error']");
    By checkboxAcceptRegulations = By.xpath(".//*[@id='terms']");
    By orderReceivedPath = By.xpath("//*[@id='post-7']/div/div/div/p");
    By stripeErrorPath = By.xpath(".//div[@class='stripe-source-errors']//*[contains(@class, 'woocommerce_error')]");
    By numberOrderPath = By.xpath(".//*[contains(@class, 'order order')]//strong");
    By dataOfOrderPath = By.xpath(".//*[contains(@class, 'date date')]//strong");
    By totalOrderPath = By.xpath(".//*[contains(@class, 'total total')]//strong");
    By paymentMethodOrderPath = By.xpath(".//*[contains(@class, 'payment-method method')]//strong");
    By productNameOrderPath = By.xpath("//*[contains(@class,' product-name')]//a");
    By productQuantityOrderPath = By.xpath("//*[@class='product-quantity']");
    By quantityInCartPath = By.xpath(".//div[@class='quantity']//input");
    By priceProductInCartPath = By.xpath(".//*[@class='product-price']//*[contains(@class, 'Price-amount')]");
    By nameProductInCartPath = By.xpath(".//*[@class='product-name']/a");
    By paymentMethodPath = By.xpath("//*[contains(@class,'payment_method_stripe')]/label");
    By myAccountPath = By.xpath(".//*[@id='menu-item-201']/a");
    By deleteAccountPath = By.xpath(".//div[@class='woocommerce-MyAccount-content']//p[3]/a");
    By usernameFromMyAccountPath = By.xpath(".//div[@class='woocommerce-MyAccount-content']//p//strong[1]");
    By showLoginPath = By.xpath(".//div[@class='woocommerce-info']//a[1]");
    By usernamePath = By.xpath(".//*[@id='username']");
    By passwordPath = By.xpath("//*[@id='password']");
    By buttonLoginPath = By.xpath(".//button[@name='login']");
    By myOrderPath = By.xpath(".//*[@class='entry-content']//li[2]/a");
    By numberOrderFromMyAccountPath = By.xpath(".//table/tbody/tr[1]/td[1]/a");
    //endregion
    String numberCart = "4242424242424242";
    String expirationDate = "12/22";
    String cvc = "111";
    String phoneNumber = "123123123";
    String existentEmail = "ewebla18.06@wp.pl";
    String existentEmailPassword = "CSSXPATH123";
    String password = "1XYE3WQsdk!";
    String firstName = "Jan";
    String lastName = "Kowalski";

    @RegisterExtension
    StatusTest statusTest = new StatusTest();

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();

        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 15);

        driver.manage().window().maximize();
        driver.navigate().to("https://fakestore.testelka.pl/");

        driver.findElement(alertCookiesPath).click();
        driver.findElement(shopPath).click();
    }

    @Test
    public void fieldValidationOnTheOrderFormTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(createAccountPath)).click();
        fillCartInformation(numberCart, expirationDate, cvc);
        checkboxAcceptanceOfRegulations(false);

        driver.findElement(buttonBuyAnDPayPath).click();
        String alertMessage = wait.until(ExpectedConditions.presenceOfElementLocated(listAlertMessagePath)).getText();

        Assertions.assertAll(
                () -> Assertions.assertTrue(alertMessage.contains("Billing Imię jest wymaganym polem."),
                        "Error message doesn't contain first name error"),
                () -> Assertions.assertTrue(alertMessage.contains("Billing Nazwisko jest wymaganym polem."),
                        "Error message doesn't contain last name error"),
                () -> Assertions.assertTrue(alertMessage.contains("Billing Ulica jest wymaganym polem."),
                        "Error message doesn't contain address error"),
                () -> Assertions.assertTrue(alertMessage.contains("Billing Miasto jest wymaganym polem."),
                        "Error message doesn't contain city error"),
                () -> Assertions.assertTrue(alertMessage.contains("Billing Telefon jest wymaganym polem."),
                        "Error message doesn't contain number of phone error"),
                () -> Assertions.assertTrue(alertMessage.contains("Billing Adres email jest wymaganym polem."),
                        "Error message doesn't contain address email error"),
                () -> Assertions.assertTrue(alertMessage.contains("Utwórz hasło do konta jest wymaganym polem."),
                        "Error message doesn't contain crating an account password error"),
                () -> Assertions.assertTrue(alertMessage.contains("Billing Kod pocztowy nie jest prawidłowym kodem pocztowym"),
                        "Error message doesn't contain post code error"),
                () -> Assertions.assertTrue(alertMessage.contains("Proszę przeczytać i zaakceptować regulamin sklepu aby móc sfinalizować zamówienie."),
                        "Error message doesn't contain acceptance of the regulations error")
        );
    }

    @Test
    public void buyWithoutAccountTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();

        fillPaymentDetails(phoneNumber, generatedEmail());
        fillCartInformation(numberCart, expirationDate, cvc);
        checkboxAcceptanceOfRegulations(true);

        driver.findElement(buttonBuyAnDPayPath).click();
        wait.until(ExpectedConditions.urlContains("zamowienie-otrzymane"));

        String message = wait.until(ExpectedConditions.presenceOfElementLocated(orderReceivedPath)).getText();
        String expectedMessage = "Dziękujemy. Otrzymaliśmy Twoje zamówienie.";

        Assertions.assertEquals(expectedMessage, message,
                "The order was not approved");
    }

    @Test
    public void buyWithCreateAccountTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();
        fillPaymentDetails(phoneNumber, generatedEmail());

        driver.findElement(createAccountPath).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordNewUserPath)).sendKeys(password);

        fillCartInformation(numberCart, expirationDate, cvc);
        checkboxAcceptanceOfRegulations(true);

        driver.findElement(buttonBuyAnDPayPath).click();
        wait.until(ExpectedConditions.urlContains("zamowienie-otrzymane"));
        String numberOrder = driver.findElement(numberOrderPath).getText();

        goToMyAccount();
        String userName = getUsernameFromMyAccount();
        wait.until(ExpectedConditions.elementToBeClickable(myOrderPath)).click();
        String numberOrderFromMyAccount = driver.findElement(numberOrderFromMyAccountPath).getText();
        driver.navigate().back();
        removeAccount();

        Assertions.assertAll(
                () -> Assertions.assertTrue(userName.contains(lastName),
                        "My account does not contain correct name."),
                () -> Assertions.assertEquals("#" + numberOrder, numberOrderFromMyAccount,
                        "Order number is not what expected")
        );
    }

    @Test
    public void buyWithExistingAccountTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();

        loginAsUser();

        fillCartInformation(numberCart, expirationDate, cvc);
        checkboxAcceptanceOfRegulations(true);

        driver.findElement(buttonBuyAnDPayPath).click();
        wait.until(ExpectedConditions.urlContains("zamowienie-otrzymane"));
        String numberOrder = driver.findElement(numberOrderPath).getText();

        goToMyAccount();
        wait.until(ExpectedConditions.elementToBeClickable(myOrderPath)).click();
        String numberOrderFromMyAccount = driver.findElement(numberOrderFromMyAccountPath).getText();
        Assertions.assertEquals("#" + numberOrder, numberOrderFromMyAccount,
                "Order number is not what expected");
    }

    @Test
    public void summaryOrderTest() {
        addProductFromCategoryPage();
        String nameProduct = getNameProduct();
        String priceProduct = getPriceProduct();
        String quantity = getQuantityInCart();

        driver.findElement(buttonGoToCashPath).click();
        fillPaymentDetails(phoneNumber, generatedEmail());
        fillCartInformation(numberCart, expirationDate, cvc);
        checkboxAcceptanceOfRegulations(true);

        driver.findElement(buttonBuyAnDPayPath).click();
        String paymentMethod = getPaymentMethodDetails();
        waitForVisibilityOfOrderDetails();

        String numberOrderInSummary = driver.findElement(numberOrderPath).getText();
        String dataOfOrderInSummary = driver.findElement(dataOfOrderPath).getText();
        String productNameInSummary = driver.findElement(productNameOrderPath).getText();
        String priceProductInSummary = driver.findElement(totalOrderPath).getText();
        String productQuantityInSummary = driver.findElement(productQuantityOrderPath).getText();
        String paymentMethodInSummary = driver.findElement(paymentMethodOrderPath).getText();

        Assertions.assertAll(
                () -> Assertions.assertTrue(numberOrderInSummary != null,
                        "Order number is null"),
                () -> Assertions.assertEquals(currentDate(), dataOfOrderInSummary,
                        "Date on the summary is not correct"),
                () -> Assertions.assertEquals(nameProduct, productNameInSummary,
                        "Product name in summary is not correct"),
                () -> Assertions.assertEquals(priceProduct, priceProductInSummary,
                        "Price in summary is not correct. "),
                () -> Assertions.assertEquals("× " + quantity, productQuantityInSummary,
                        "product quantity in summary is not correct"),
                () -> Assertions.assertEquals(paymentMethod, paymentMethodInSummary,
                        "Payment method in summary is not correct")
        );
    }

    @Test
    public void wrongEmailTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();

        fillPaymentDetails(phoneNumber, "11onet");
        fillCartInformation(numberCart, expirationDate, cvc);
        checkboxAcceptanceOfRegulations(true);

        driver.findElement(buttonBuyAnDPayPath).click();
        String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(stripeErrorPath)).getText();
        String expectedErrorMessage = "Invalid email address, please correct and try again.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not correct");
    }

    @Test
    public void wrongPhoneNumberTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();

        fillPaymentDetails("aaabbbccc", generatedEmail());
        fillCartInformation(numberCart, expirationDate, cvc);
        checkboxAcceptanceOfRegulations(true);

        driver.findElement(buttonBuyAnDPayPath).click();

        String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(listAlertMessagePath)).getText();
        String expectedErrorMessage = "Billing Telefon nie jest poprawnym numerem telefonu.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not correct");
    }

    @AfterEach
    public void quit(TestInfo info) throws IOException {
        if (statusTest.isField) {
            System.out.println(" The screenshot is available in: " + screenshot(info));
            driver.quit();
        } else driver.quit();
    }

    private void fillPaymentDetails(String phoneNumber, String email) {
        wait.until(ExpectedConditions.elementToBeClickable(firstNamePath)).sendKeys(firstName);
        wait.until(ExpectedConditions.elementToBeClickable(lastNamePath)).sendKeys(lastName);
        wait.until(ExpectedConditions.elementToBeClickable(countryContainerPath)).click();
        driver.findElement(countryAndoraPath).click();
        wait.until(ExpectedConditions.elementToBeClickable(addressPath)).sendKeys("Misia 77");
        wait.until(ExpectedConditions.elementToBeClickable(cityPath)).sendKeys("Warszawa");
        wait.until(ExpectedConditions.elementToBeClickable(regionPath)).sendKeys("Mazowieckie");
        wait.until(ExpectedConditions.elementToBeClickable(postCodePath)).sendKeys("00-121");
        wait.until(ExpectedConditions.elementToBeClickable(phonePath)).sendKeys(phoneNumber);
        wait.until(ExpectedConditions.elementToBeClickable(emailPath)).sendKeys(email);
    }

    private void fillCartInformation(String numberCart, String expirationDate, String cvc) {
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
    }


    private void switchToDefaultContentAndFrame(By framePath) {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((framePath)));
    }

    private void addProductFromCategoryPage() {
        driver.findElement(nameCategoryPath).click();
        driver.findElement(buttonByCategoryPage).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(lookCartFromCategoryPagePath)).click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(cartTitlePath));
    }

    private String getNameProduct() {
        String nameProduct = driver.findElement(nameProductInCartPath).getText();
        return nameProduct;
    }

    private String getPriceProduct() {
        String priceProduct = driver.findElement(priceProductInCartPath).getText();
        return priceProduct;
    }

    private String getQuantityInCart() {
        String quantityInCart = driver.findElement(quantityInCartPath).getAttribute("value");
        return quantityInCart;
    }

    private String getPaymentMethodDetails() {
        String paymentMethod = driver.findElement(paymentMethodPath).getText();
        return paymentMethod;
    }

    private String getUsernameFromMyAccount() {
        String username = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameFromMyAccountPath)).getText();
        return username;
    }

    private void waitForVisibilityOfOrderDetails() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productNameOrderPath));
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalOrderPath));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productQuantityOrderPath));
        wait.until(ExpectedConditions.visibilityOfElementLocated(paymentMethodOrderPath));
    }

    private String currentDate() {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d MMMM, yyyy");
        return formatterDate.format(LocalDateTime.now());
    }

    private void checkboxAcceptanceOfRegulations(Boolean isSelected) {
        if (isSelected) {
            wait.until(ExpectedConditions.elementToBeClickable(checkboxAcceptRegulations)).click();
        }
    }

    private String generatedEmail() {
        Random r = new Random();
        int number = r.nextInt();
        return "test" + number + "@wp.pl";
    }

    private void goToMyAccount() {
        driver.findElement(myAccountPath).click();
        wait.until(ExpectedConditions.urlContains("moje-konto"));
    }

    private void removeAccount() {
        driver.findElement(deleteAccountPath).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    private void loginAsUser() {
        wait.until(ExpectedConditions.elementToBeClickable(showLoginPath)).click();
        wait.until(ExpectedConditions.elementToBeClickable(usernamePath)).sendKeys(existentEmail);
        wait.until(ExpectedConditions.elementToBeClickable(passwordPath)).sendKeys(existentEmailPassword);
        wait.until(ExpectedConditions.elementToBeClickable(buttonLoginPath)).click();
    }

    private String screenshot(TestInfo info) throws IOException {
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String path = "C:\\Users\\Paweł\\Downloads\\ScreenShots\\" + info.getDisplayName() + formatter.format(LocalDateTime.now()) + ".jpg";
        FileUtils.copyFile(screen, new File(path));
        return path;
    }
}
