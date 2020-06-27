package TestyPOM;

import PageObjects.CategoryPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartTests extends BaseTest {

    @Test
    public void addOneProductToCartFromProductPageTest() {
        ProductPage productPage = new ProductPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getProductUrl());
        productPage.footerAlertPage.close();
        boolean isProductInCart = productPage.addToCart().viewCart().isProductInCart(testData.getTestData().getProductId());

        assertTrue(isProductInCart,
                "Product with id=" + testData.getTestData().getProductId() + " (Wspinaczka Via Ferraty) was not found in cart");
    }

    @Test
    public void addOneProductToCartFromCategoryPageTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getFirstCategoryUrl());
        categoryPage.footerAlertPage.close();
        boolean isProductInCart = categoryPage.addToCart(testData.getTestData().getProductId()).viewCart().isProductInCart(testData.getTestData().getProductId());

        assertTrue(isProductInCart,
                "Product with id=" + testData.getTestData().getProductId() + " (Wspinaczka Via Ferraty) was not found in cart");
    }

    @Test
    public void addFewProductsToCartFromProductPageTest() {
        ProductPage productPage = new ProductPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getProductUrl());
        productPage.footerAlertPage.close();
        int quantityOfProduct = productPage.addToCart(testData.getTestData().getQuantity()).viewCart().getProductQuantity();

        Assertions.assertEquals(10, quantityOfProduct,
                "Quantity of the product is not what expected");
    }

    @Test
    public void addFewProductToCartFromCategoryPageTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getSecondCategoryUrl());
        categoryPage.footerAlertPage.close();
        categoryPage.addAllProductsToCart();
        categoryPage.goTo(configuration.getBaseUrl() + testData.getTestData().getThirdCategoryUrl());
        categoryPage.addAllProductsToCart();
        int productAmount = categoryPage.header.goToCart().getProductAmount();

        Assertions.assertEquals(10, productAmount,
                "Quantity of the product is not what expected");
    }

    @Test
    public void changeNumberOfProductTest() {
        ProductPage productPage = new ProductPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getProductUrl());
        productPage.footerAlertPage.close();
        int quantityOfProduct = productPage.addToCart().viewCart().changeProductAmount(testData.getTestData().getQuantity()).getProductQuantity();

        Assertions.assertEquals(10, quantityOfProduct,
                "Quantity of the product is not what expected");
    }

    @Test
    public void removeProductFromCartTest() {
        ProductPage productPage = new ProductPage(driver).goTo(configuration.getBaseUrl() + testData.getTestData().getProductUrl());
        productPage.footerAlertPage.close();
        Boolean cartIsEmpty = productPage.addToCart()
                .viewCart()
                .removeProduct(testData.getTestData().getProductId())
                .isNoExistsProductInCart(testData.getTestData().getProductId());

        Assertions.assertTrue(cartIsEmpty,
                "Product id=" + testData.getTestData().getProductId() + " is in the cart");
    }
}
