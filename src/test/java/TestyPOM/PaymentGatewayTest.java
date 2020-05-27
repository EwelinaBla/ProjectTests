package TestyPOM;

import PageObjects.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaymentGatewayTest extends BaseTest {
    @Test
    public void incorrectNumberCartTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getCategoryUrl());
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
                .fillCartInformation(testData.getTestData().getIncorrectNumberCart(), testData.getTestData().getExpirationDate(), testData.getTestData().getCvc())
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String errorMessage = receivedOrderPage.getAlert();
        String expectedErrorMessage = "Numer karty nie jest prawidłowym numerem karty kredytowej.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void incompleteNumberCartTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getCategoryUrl());
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
                .fillCartInformation(testData.getTestData().getIncompleteNumberCart(), testData.getTestData().getExpirationDate(), testData.getTestData().getIncompleteCvc())
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String errorMessage = receivedOrderPage.getAlert();
        String expectedErrorMessage = "Numer karty jest niekompletny.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void incorrectExpirationDateTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getCategoryUrl());
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
                .fillCartInformation(testData.getTestData().getCorrectNumberCart(), testData.getTestData().getWrongExpirationDate(), testData.getTestData().getIncompleteCvc())
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String errorMessage = receivedOrderPage.getAlert();
        String expectedErrorMessage = "Rok ważności karty upłynął w przeszłości";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void incompleteExpirationDateTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getCategoryUrl());
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
                .fillCartInformation(testData.getTestData().getCorrectNumberCart(), testData.getTestData().getIncompleteExpirationDate(), testData.getTestData().getIncompleteCvc())
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String errorMessage = receivedOrderPage.getAlert();
        String expectedErrorMessage = "Data ważności karty jest niekompletna.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void incompleteCvcTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getCategoryUrl());
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
                .fillCartInformation(testData.getTestData().getCorrectNumberCart(), testData.getTestData().getExpirationDate(), testData.getTestData().getIncompleteCvc())
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String errorMessage = receivedOrderPage.getAlert();
        String expectedErrorMessage = "Kod bezpieczeństwa karty jest niekompletny.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void successfulPaymentTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getCategoryUrl());
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
                .fillCartInformation(testData.getTestData().getCorrectNumberCart(), testData.getTestData().getExpirationDate(), testData.getTestData().getCvc())
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String numberOrder = receivedOrderPage.getNumberOrder();

        Assertions.assertTrue(numberOrder != null,
                "Number order is null");
    }

    @Test
    public void cartDeclinedSecureTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getCategoryUrl());
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
                .fillCartInformation(testData.getTestData().getDeclinedNumberCart3DSecure(), testData.getTestData().getExpirationDate(), testData.getTestData().getCvc())
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        PaymentGatewayPage paymentGatewayPage = new PaymentGatewayPage(driver);
        paymentGatewayPage.goToSecure().paymentBySecure(true);

        String errorMessage = receivedOrderPage.getAlert();
        String expectedErrorMessage = "Karta została odrzucona.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not correct");
    }

    @Test
    public void unsuccessfulPaymentBySecureTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getCategoryUrl());
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
                .fillCartInformation(testData.getTestData().getNumberCart3DSecure(), testData.getTestData().getExpirationDate(), testData.getTestData().getCvc())
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        PaymentGatewayPage paymentGatewayPage = new PaymentGatewayPage(driver);
        paymentGatewayPage.goToSecure().paymentBySecure(false);

        String errorMessage = receivedOrderPage.getAlert();
        String expectedErrorMessage = "Nie można przetworzyć tej płatności, spróbuj ponownie lub użyj alternatywnej metody.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not correct");
    }

    @Test
    public void successfulPaymentBySecureTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getCategoryUrl());
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
                .fillCartInformation(testData.getTestData().getNumberCart3DSecure(), testData.getTestData().getExpirationDate(), testData.getTestData().getCvc())
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        PaymentGatewayPage paymentGatewayPage = new PaymentGatewayPage(driver);
        paymentGatewayPage.goToSecure().paymentBySecure(true);

        String numberOrder = receivedOrderPage.getNumberOrder();

        Assertions.assertTrue(numberOrder != null,
                "Number order is null");
    }
}
