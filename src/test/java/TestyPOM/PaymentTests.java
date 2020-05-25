package TestyPOM;

import PageObjects.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PaymentTests extends BaseTest {
    //region VARIABLES
    String categoryUrl = "/product-category/wspinaczka/";
    String myAccountUrl = "/moje-konto/";
    String productId = "40";
    String phoneNumber = "123123123";
    String numberCart = "4242424242424242";
    String expirationDate = "12/22";
    String cvc = "111";
    String firstName = "Jan";
    String lastName = "Kowalski";
    String address = "Misia 77";
    String city = "Warszawa";
    String region = "Mazowieckie";
    String postCode = "00-121";
    String countryCode = "AD";
    String password = "1XYE3WQsdk!";
    String existentEmail = "test262729388@wp.pl";
    String existentEmailPassword = "CSSXPATH123";
    String wrongEmail = "11onet";
    String wrongPhoneNumber = "aaabbbccc";

    //endregion
    @Test
    public void buyWithoutAccountTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl()+categoryUrl);
        categoryPage.footerAlertPage.close();
        CartPage cartPage = categoryPage.addToCart(productId).viewCart();
        PaymentPage paymentPage = cartPage.goToCash();
        ReceivedOrderPage receivedOrderPage = paymentPage
                .fillPaymentDetails(
                        firstName,
                        lastName,
                        countryCode,
                        address,
                        city,
                        region,
                        postCode,
                        phoneNumber,
                        paymentPage.generatedEmail())
                .fillCartInformation(numberCart, expirationDate, cvc)
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String numberOrder = receivedOrderPage.getNumberOrder();

        Assertions.assertTrue(numberOrder != null,
                "Number order is null");
    }

    @Test
    public void buyWithCreateAccountTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl()+categoryUrl);
        categoryPage.footerAlertPage.close();
        CartPage cartPage = categoryPage.addToCart(productId).viewCart();
        PaymentPage paymentPage = cartPage.goToCash();
        ReceivedOrderPage receivedOrderPage = paymentPage
                .fillPaymentDetails(
                        firstName,
                        lastName,
                        countryCode,
                        address,
                        city,
                        region,
                        postCode,
                        phoneNumber,
                        paymentPage.generatedEmail())
                .createAccount(password)
                .fillCartInformation(numberCart, expirationDate, cvc)
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String numberOrder = receivedOrderPage.getNumberOrder();

        AccountPage accountPage = new AccountPage(driver).header.goToMyAccount();
        String numberOrderInMyAccount = accountPage.getNumberOrderInMyAccount();
        accountPage.goTo(configuration.getBaseUrl()+myAccountUrl).removeAccount();

        Assertions.assertEquals("#" + numberOrder, numberOrderInMyAccount,
                "Order number is not what expected");

    }

    @Test
    public void buyWithExistingAccountTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl()+categoryUrl);
        categoryPage.footerAlertPage.close();
        CartPage cartPage = categoryPage.addToCart(productId).viewCart();
        PaymentPage paymentPage = cartPage.goToCash();

        ReceivedOrderPage receivedOrderPage = paymentPage
                .loginAsUser(existentEmail, existentEmailPassword)
                .fillCartInformation(numberCart, expirationDate, cvc)
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String numberOrder = receivedOrderPage.getNumberOrder();

        AccountPage accountPage = new AccountPage(driver).header.goToMyAccount();
        String numberOrderInMyAccount = accountPage.getNumberOrderInMyAccount();

        Assertions.assertEquals("#" + numberOrder, numberOrderInMyAccount,
                "Order number is not what expected");
    }

    @Test
    public void fieldValidationOnTheOrderFormTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl()+categoryUrl);
        categoryPage.footerAlertPage.close();
        CartPage cartPage = categoryPage.addToCart(productId).viewCart();
        PaymentPage paymentPage = cartPage.goToCash();

        ReceivedOrderPage receivedOrderPage = paymentPage.selectCheckboxCreateAccount()
                .fillCartInformation(numberCart, expirationDate, cvc)
                .checkboxAcceptanceOfRegulations(false)
                .buyAndPay();

        int numberErrorMessages = receivedOrderPage.getSizeListAlertMessage();

        Assertions.assertEquals(9, numberErrorMessages,
                "Number of error messages is not what expected");
    }

    @Test
    public void summaryOrderTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl()+categoryUrl);
        categoryPage.footerAlertPage.close();
        CartPage cartPage = categoryPage.addToCart(productId).viewCart();

        String nameProduct = cartPage.getNameProduct();
        String priceProduct = cartPage.getPriceProduct();
        String quantity = cartPage.getQuantityInCart();

        PaymentPage paymentPage = cartPage.goToCash();
        paymentPage.fillPaymentDetails(
                firstName,
                lastName,
                countryCode,
                address,
                city,
                region,
                postCode,
                phoneNumber,
                paymentPage.generatedEmail())
                .fillCartInformation(numberCart, expirationDate, cvc)
                .checkboxAcceptanceOfRegulations(true);

        String paymentMethod = paymentPage.getPaymentMethodDetails();

        ReceivedOrderPage receivedOrderPage = paymentPage.buyAndPay();

        String numberOrderInSummary = receivedOrderPage.getNumberOrder();
        String dataOfOrderInSummary = receivedOrderPage.getDateOfOrder();
        String currentDate = receivedOrderPage.currentDate();
        String productNameInSummary = receivedOrderPage.getProductName();
        String priceProductInSummary = receivedOrderPage.getPriceProduct();
        String productQuantityInSummary = receivedOrderPage.getProductQuantity();
        String paymentMethodInSummary = receivedOrderPage.getPaymentMethod();

        Assertions.assertAll(
                () -> Assertions.assertTrue(numberOrderInSummary != null,
                        "Order number is null"),
                () -> Assertions.assertEquals(currentDate, dataOfOrderInSummary,
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
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl()+categoryUrl);
        categoryPage.footerAlertPage.close();
        CartPage cartPage = categoryPage.addToCart(productId).viewCart();
        PaymentPage paymentPage = cartPage.goToCash();
        ReceivedOrderPage receivedOrderPage = paymentPage
                .fillPaymentDetails(
                        firstName,
                        lastName,
                        countryCode,
                        address,
                        city,
                        region,
                        postCode,
                        phoneNumber,
                        wrongEmail)
                .fillCartInformation(numberCart, expirationDate, cvc)
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String errorMessage = receivedOrderPage.getAlert();
        String expectedErrorMessage = "Invalid email address, please correct and try again.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not correct");
    }

    @Test
    public void wrongPhoneNumberTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl()+categoryUrl);
        categoryPage.footerAlertPage.close();
        CartPage cartPage = categoryPage.addToCart(productId).viewCart();
        PaymentPage paymentPage = cartPage.goToCash();
        ReceivedOrderPage receivedOrderPage = paymentPage
                .fillPaymentDetails(
                        firstName,
                        lastName,
                        countryCode,
                        address,
                        city,
                        region,
                        postCode,
                        wrongPhoneNumber,
                        paymentPage.generatedEmail())
                .fillCartInformation(numberCart, expirationDate, cvc)
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        int numberErrorMessages = receivedOrderPage.getSizeListAlertMessage();

        Assertions.assertEquals(1, numberErrorMessages,
                "Number of error messages is not what expected");
    }
}