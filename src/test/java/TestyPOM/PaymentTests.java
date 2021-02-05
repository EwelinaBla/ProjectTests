package TestyPOM;

import PageObjects.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PaymentTests extends BaseTest {

    @Test
    public void buyWithoutAccountTest() {
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.goTo(configuration.getBaseUrl()+ testData.getTestData().getCategoryUrl());
        categoryPage.footerAlertPage.close();
        CartPage cartPage = categoryPage.addToCart(testData.getTestData().getProductId()).viewCart();
        PaymentPage paymentPage = cartPage.goToCash();
        ReceivedOrderPage receivedOrderPage = paymentPage
                .fillPaymentDetails(
                        testData.getTestData().getFirstName(),
                        testData.getTestData().getLastName(),
                        testData.getTestData().getCountryCode(),
                        testData.getTestData().getAddress(),
                        testData.getTestData().getCity(),
                        testData.getTestData().getRegion(),
                        testData.getTestData().getPostCode(),
                        testData.getTestData().getPhoneNumber(),
                        paymentPage.generatedEmail())
                .fillCartInformation(testData.getTestData().getNumberCart(), testData.getTestData().getExpirationDate(), testData.getTestData().getCvc())
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String numberOrder = receivedOrderPage.getNumberOrder();

        Assertions.assertNotNull (numberOrder,
                "Number order is null");
    }

    @Test
    public void buyWithCreateAccountTest() {
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.goTo(configuration.getBaseUrl()+testData.getTestData().getCategoryUrl());
        categoryPage.footerAlertPage.close();
        CartPage cartPage = categoryPage.addToCart(testData.getTestData().getProductId()).viewCart();
        PaymentPage paymentPage = cartPage.goToCash();
        ReceivedOrderPage receivedOrderPage = paymentPage
                .fillPaymentDetails(
                        testData.getTestData().getFirstName(),
                        testData.getTestData().getLastName(),
                        testData.getTestData().getCountryCode(),
                        testData.getTestData().getAddress(),
                        testData.getTestData().getCity(),
                        testData.getTestData().getRegion(),
                        testData.getTestData().getPostCode(),
                        testData.getTestData().getPhoneNumber(),
                        paymentPage.generatedEmail())
                .createAccount(testData.getTestData().getPassword())
                .fillCartInformation(testData.getTestData().getNumberCart(), testData.getTestData().getExpirationDate(), testData.getTestData().getCvc())
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String numberOrder = receivedOrderPage.getNumberOrder();

        AccountPage accountPage = new AccountPage(driver).header.goToMyAccount();
        String numberOrderInMyAccount = accountPage.getNumberOrderInMyAccount();
        accountPage.goTo(configuration.getBaseUrl()+testData.getTestData().getMyAccountUrl());
        accountPage.removeAccount ();

        Assertions.assertEquals("#" + numberOrder, numberOrderInMyAccount,
                "Order number is not what expected");

    }

    @Test
    public void buyWithExistingAccountTest() {
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.goTo(configuration.getBaseUrl()+testData.getTestData().getCategoryUrl());
        categoryPage.footerAlertPage.close();
        CartPage cartPage = categoryPage.addToCart(testData.getTestData().getProductId()).viewCart();
        PaymentPage paymentPage = cartPage.goToCash();

        ReceivedOrderPage receivedOrderPage = paymentPage
                .loginAsUser(testData.getTestData().getExistentEmail(),testData.getTestData().getExistentEmailPassword())
                .fillCartInformation(testData.getTestData().getNumberCart(), testData.getTestData().getExpirationDate(), testData.getTestData().getCvc())
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
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.goTo(configuration.getBaseUrl()+testData.getTestData().getCategoryUrl());
        categoryPage.footerAlertPage.close();
        CartPage cartPage = categoryPage.addToCart(testData.getTestData().getProductId()).viewCart();
        PaymentPage paymentPage = cartPage.goToCash();

        ReceivedOrderPage receivedOrderPage = paymentPage.selectCheckboxCreateAccount()
                .fillCartInformation(testData.getTestData().getNumberCart(), testData.getTestData().getExpirationDate(), testData.getTestData().getCvc())
                .checkboxAcceptanceOfRegulations(false)
                .buyAndPay();

        int numberErrorMessages = receivedOrderPage.getSizeListAlertMessage();

        Assertions.assertEquals(9, numberErrorMessages,
                "Number of error messages is not what expected");
    }

    @Test
    public void summaryOrderTest() {
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.goTo(configuration.getBaseUrl()+testData.getTestData().getCategoryUrl());
        categoryPage.footerAlertPage.close();
        CartPage cartPage = categoryPage.addToCart(testData.getTestData().getProductId()).viewCart();

        String nameProduct = cartPage.getNameProduct();
        String priceProduct = cartPage.getPriceProduct();
        String quantity = cartPage.getQuantityInCart();

        PaymentPage paymentPage = cartPage.goToCash();
        paymentPage.fillPaymentDetails(
                testData.getTestData().getFirstName(),
                testData.getTestData().getLastName(),
                testData.getTestData().getCountryCode(),
                testData.getTestData().getAddress(),
                testData.getTestData().getCity(),
                testData.getTestData().getRegion(),
                testData.getTestData().getPostCode(),
                testData.getTestData().getPhoneNumber(),
                paymentPage.generatedEmail())
                .fillCartInformation(testData.getTestData().getNumberCart(), testData.getTestData().getExpirationDate(), testData.getTestData().getCvc())
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
                () ->
                        Assertions.assertNotNull (numberOrderInSummary,
                                "Number order is null"),
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
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.goTo(configuration.getBaseUrl()+testData.getTestData().getCategoryUrl());
        categoryPage.footerAlertPage.close();
        CartPage cartPage = categoryPage.addToCart(testData.getTestData().getProductId()).viewCart();
        PaymentPage paymentPage = cartPage.goToCash();
        ReceivedOrderPage receivedOrderPage = paymentPage
                .fillPaymentDetails(
                        testData.getTestData().getFirstName(),
                        testData.getTestData().getLastName(),
                        testData.getTestData().getCountryCode(),
                        testData.getTestData().getAddress(),
                        testData.getTestData().getCity(),
                        testData.getTestData().getRegion(),
                        testData.getTestData().getPostCode(),
                        testData.getTestData().getPhoneNumber(),
                        testData.getTestData().getWrongEmail())
                .fillCartInformation(testData.getTestData().getNumberCart(), testData.getTestData().getExpirationDate(), testData.getTestData().getCvc())
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String errorMessage = receivedOrderPage.getAlert();
        String expectedErrorMessage = "Invalid email address, please correct and try again.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not correct");
    }

    @Test
    public void wrongPhoneNumberTest() {
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.goTo(configuration.getBaseUrl()+testData.getTestData().getCategoryUrl());
        categoryPage.footerAlertPage.close();
        CartPage cartPage = categoryPage.addToCart(testData.getTestData().getProductId()).viewCart();
        PaymentPage paymentPage = cartPage.goToCash();
        ReceivedOrderPage receivedOrderPage = paymentPage
                .fillPaymentDetails(
                        testData.getTestData().getFirstName(),
                        testData.getTestData().getLastName(),
                        testData.getTestData().getCountryCode(),
                        testData.getTestData().getAddress(),
                        testData.getTestData().getCity(),
                        testData.getTestData().getRegion(),
                        testData.getTestData().getPostCode(),
                        testData.getTestData().getWrongPhoneNumber(),
                        paymentPage.generatedEmail())
                .fillCartInformation(testData.getTestData().getNumberCart(), testData.getTestData().getExpirationDate(), testData.getTestData().getCvc())
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        int numberErrorMessages = receivedOrderPage.getSizeListAlertMessage();

        Assertions.assertEquals(1, numberErrorMessages,
                "Number of error messages is not what expected");
    }
}