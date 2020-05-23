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

public class PaymentMethodTests {
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
    By numberCartPath = By.xpath(".//*[@name='cardnumber']");
    By expirationDatePath = By.xpath(".//span/input[@name='exp-date']");
    By cvcPath = By.xpath(".//span/input[@name='cvc']");
    By nameCategoryPath = By.xpath(".//*[@class='products columns-3']/li[2]//a//img");
    By buttonByCategoryPagePath = By.xpath(".//*[@id='main']/ul/li[2]/a[2]");
    By lookCartFromCategoryPagePath = By.xpath(".//*[contains(@class, 'added_to_cart')]");
    By cartTitlePath = By.xpath(".//*[@id='post-6']/header/h1");
    By buttonGoToCashPath = By.xpath(".//a[contains(@class,'checkout-button')]");
    By frameNumberCartPath = By.xpath(".//iframe[@name='__privateStripeFrame8']");
    By frameExpirationDataPath = By.xpath(".//iframe[@name='__privateStripeFrame9']");
    By frameCvcPath = By.xpath(".//iframe[@name='__privateStripeFrame10']");
    By buttonBuyAnDPayPath = By.xpath(".//button[@id='place_order']");
    By checkboxAcceptRegulations = By.xpath(".//*[@id='terms']");
    By orderReceivedPath = By.xpath(".//*[@id='post-7']/header/h1");
    By alertPaymentPath = By.xpath(".//*[@role='alert']//li");
    By transactionCompletePath = By.xpath(".//*[@id='test-source-authorize-3ds']");
    By transactionFailPath = By.xpath(".//*[@id='test-source-fail-3ds']");
    By firstSecureFramePath = By.xpath("/html/body/div[1]/iframe");
    By secondSecureFramePath = By.xpath(".//*[@id='challengeFrame']");
    //endregion

    String phoneNumber = "123123123";
    String firstName = "Jan";
    String lastName = "Kowalski";
    String cvc = "111";
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
    }

    @Test
    public void incorrectNumberCartTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();

        fillPaymentDetails(phoneNumber, generatedEmail());
        fillCartInformation("1000000000001111", "111", "111");
        checkboxAcceptanceOfRegulations(true);
        driver.findElement(buttonBuyAnDPayPath).click();

        String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(alertPaymentPath)).getText();
        String expectedErrorMessage = "Numer karty nie jest prawidłowym numerem karty kredytowej.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void incompleteNumberCartTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();

        fillPaymentDetails(phoneNumber, generatedEmail());
        fillCartInformation("1000", "111", "111");
        checkboxAcceptanceOfRegulations(true);
        driver.findElement(buttonBuyAnDPayPath).click();

        String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(alertPaymentPath)).getText();
        String expectedErrorMessage = "Numer karty jest niekompletny.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void incorrectExpirationDateTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();

        fillPaymentDetails(phoneNumber, generatedEmail());
        fillCartInformation("378282246310005", "1111", "111");
        checkboxAcceptanceOfRegulations(true);
        driver.findElement(buttonBuyAnDPayPath).click();

        String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(alertPaymentPath)).getText();
        String expectedErrorMessage = "Rok ważności karty upłynął w przeszłości";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void incompleteExpirationDateTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();

        fillPaymentDetails(phoneNumber, generatedEmail());
        fillCartInformation("378282246310005", "11", "111");
        checkboxAcceptanceOfRegulations(true);
        driver.findElement(buttonBuyAnDPayPath).click();

        String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(alertPaymentPath)).getText();
        String expectedErrorMessage = "Data ważności karty jest niekompletna.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void incompleteCvcTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();

        fillPaymentDetails(phoneNumber, generatedEmail());
        fillCartInformation("378282246310005", "1122", "1");
        checkboxAcceptanceOfRegulations(true);
        driver.findElement(buttonBuyAnDPayPath).click();

        String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(alertPaymentPath)).getText();
        String expectedErrorMessage = "Kod bezpieczeństwa karty jest niekompletny.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void successfulPaymentTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();

        fillPaymentDetails(phoneNumber, generatedEmail());
        fillCartInformation("378282246310005", "1223", "111");
        checkboxAcceptanceOfRegulations(true);
        driver.findElement(buttonBuyAnDPayPath).click();
        wait.until(ExpectedConditions.urlContains("zamowienie-otrzymane"));

        String message = wait.until(ExpectedConditions.visibilityOfElementLocated(orderReceivedPath)).getText();
        String expectedMessage = "Zamówienie otrzymane";

        Assertions.assertEquals(expectedMessage, message,
                "The order was not approved");
    }

    @Test
    public void successfulPaymentBySecureTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();

        fillPaymentDetails(phoneNumber, generatedEmail());
        fillCartInformation("4000000000003220", "1122", "111");
        checkboxAcceptanceOfRegulations(true);

        driver.findElement(buttonBuyAnDPayPath).click();
        wait.until(ExpectedConditions.urlContains("zamowienie/#"));

        switchToFrameSecure(firstSecureFramePath);
        switchToFrameSecure(secondSecureFramePath);

        paymentBySecure(true);
        driver.switchTo().defaultContent();

        wait.until(ExpectedConditions.urlContains("zamowienie-otrzymane"));

        String message = wait.until(ExpectedConditions.visibilityOfElementLocated(orderReceivedPath)).getText();
        String expectedMessage = "Zamówienie otrzymane";

        Assertions.assertEquals(expectedMessage, message,
                "The order was not approved");
    }

    @Test
    public void cartDeclinedSecureTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();

        fillPaymentDetails(phoneNumber, generatedEmail());
        fillCartInformation("4000008400001629", "1122", "111");
        checkboxAcceptanceOfRegulations(true);

        driver.findElement(buttonBuyAnDPayPath).click();
        wait.until(ExpectedConditions.urlContains("zamowienie/#"));

        switchToFrameSecure(firstSecureFramePath);
        switchToFrameSecure(secondSecureFramePath);

        paymentBySecure(true);
        driver.switchTo().defaultContent();

        String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(alertPaymentPath)).getText();
        String expectedErrorMessage = "Karta została odrzucona.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void unsuccessfulPaymentBySecureTest() {
        addProductFromCategoryPage();
        driver.findElement(buttonGoToCashPath).click();

        fillPaymentDetails(phoneNumber, generatedEmail());
        fillCartInformation("4000000000003220", "1122", "111");
        checkboxAcceptanceOfRegulations(true);

        driver.findElement(buttonBuyAnDPayPath).click();
        wait.until(ExpectedConditions.urlContains("zamowienie/#"));

        switchToFrameSecure(firstSecureFramePath);
        switchToFrameSecure(secondSecureFramePath);

        paymentBySecure(false);
        driver.switchTo().defaultContent();

        String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(alertPaymentPath)).getText();
        String expectedErrorMessage = "Nie można przetworzyć tej płatności, spróbuj ponownie lub użyj alternatywnej metody.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "The order was not approved");
    }

    @AfterEach
    public void quit(TestInfo info) throws IOException {
        if (statusTest.isField) {
            System.out.println(" The screenshot is available in: " + screenshot(info));
            driver.quit();
        } else driver.quit();
    }

    private void addProductFromCategoryPage() {
        driver.findElement(shopPath).click();
        driver.findElement(nameCategoryPath).click();
        driver.findElement(buttonByCategoryPagePath).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(lookCartFromCategoryPagePath)).click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(cartTitlePath));
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

    private void switchToFrameSecure(By locatorFrame) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((locatorFrame)));
        wait.until(d -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    private void paymentBySecure(Boolean isComplete) {
        if (isComplete) {
            wait.until(ExpectedConditions.elementToBeClickable(transactionCompletePath)).submit();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(transactionFailPath)).submit();
        }
        driver.switchTo().defaultContent();
    }

    private String screenshot(TestInfo info) throws IOException {
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String path = "C:\\Users\\Paweł\\Downloads\\ScreenShots\\" + info.getDisplayName() + formatter.format(LocalDateTime.now()) + ".jpg";
        FileUtils.copyFile(screen, new File(path));
        return path;
    }
}
