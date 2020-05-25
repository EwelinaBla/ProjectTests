package TestyPOM;

import PageObjects.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaymentGatewayTest extends BaseTest {
    //region VARIABLES
    String categoryUrl = "/product-category/wspinaczka/";
    String productId = "40";
    String phoneNumber = "123123123";
    String correctNumberCart = "378282246310005";
    String declinedNumberCart3DSecure = "4000008400001629";
    String incorrectNumberCart = "1000000000001111";
    String numberCart3DSecure = "4000000000003220";
    String incompleteNumberCart = "1000";
    String expirationDate = "12/22";
    String wrongExpirationDate = "1111";
    String incompleteExpirationDate = "11";
    String cvc = "111";
    String incompleteCvc = "1";
    String firstName = "Jan";
    String lastName = "Kowalski";
    String address = "Misia 77";
    String city = "Warszawa";
    String region = "Mazowieckie";
    String postCode = "00-121";
    String countryCode = "AD";

    //endregion
    @Test
    public void incorrectNumberCartTest() {
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
                .fillCartInformation(incorrectNumberCart, expirationDate, cvc)
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String errorMessage = receivedOrderPage.getAlert();
        String expectedErrorMessage = "Numer karty nie jest prawidłowym numerem karty kredytowej.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void incompleteNumberCartTest() {
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
                .fillCartInformation(incompleteNumberCart, expirationDate, cvc)
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String errorMessage = receivedOrderPage.getAlert();
        String expectedErrorMessage = "Numer karty jest niekompletny.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void incorrectExpirationDateTest() {
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
                .fillCartInformation(correctNumberCart, wrongExpirationDate, cvc)
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String errorMessage = receivedOrderPage.getAlert();
        String expectedErrorMessage = "Rok ważności karty upłynął w przeszłości";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void incompleteExpirationDateTest() {
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
                .fillCartInformation(correctNumberCart, incompleteExpirationDate, cvc)
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String errorMessage = receivedOrderPage.getAlert();
        String expectedErrorMessage = "Data ważności karty jest niekompletna.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void incompleteCvcTest() {
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
                .fillCartInformation(correctNumberCart, expirationDate, incompleteCvc)
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String errorMessage = receivedOrderPage.getAlert();
        String expectedErrorMessage = "Kod bezpieczeństwa karty jest niekompletny.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage,
                "Error message is not what expected");
    }

    @Test
    public void successfulPaymentTest() {
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
                .fillCartInformation(correctNumberCart, expirationDate, cvc)
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        String numberOrder = receivedOrderPage.getNumberOrder();

        Assertions.assertTrue(numberOrder != null,
                "Number order is null");
    }

    @Test
    public void cartDeclinedSecureTest() {
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
                .fillCartInformation(declinedNumberCart3DSecure, expirationDate, cvc)
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
                .fillCartInformation(numberCart3DSecure, expirationDate, cvc)
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
                .fillCartInformation(numberCart3DSecure, expirationDate, cvc)
                .checkboxAcceptanceOfRegulations(true)
                .buyAndPay();

        PaymentGatewayPage paymentGatewayPage = new PaymentGatewayPage(driver);
        paymentGatewayPage.goToSecure().paymentBySecure(true);

        String numberOrder = receivedOrderPage.getNumberOrder();

        Assertions.assertTrue(numberOrder != null,
                "Number order is null");
    }
}
