package TestyPOM;

import PageObjects.CategoryPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartTests extends BaseTest {

    String productUrl = baseUrl + "/product/wspinaczka-via-ferraty/";
    String firstCategoryUrl = baseUrl + "/product-category/wspinaczka/";
    String secondCategoryUrl = baseUrl + "/product-category/windsurfing/";
    String thirdCategoryUrl = baseUrl + "/product-category/yoga-i-pilates/";
    String productId = "40";
    int quantity = 10;


    @Test
    public void addOneProductToCartFromProductPageTest() {
        ProductPage productPage = new ProductPage(driver).goTo(productUrl);
        productPage.footerAlertPage.close();
        boolean isProductInCart = productPage.addToCart().viewCart().isProductInCart(productId);

        assertTrue(isProductInCart,
                "Product with id=" + productId + " (Wspinaczka Via Ferraty) was not found in cart");
    }

    @Test
    public void addOneProductToCartFromCategoryPageTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(firstCategoryUrl);
        categoryPage.footerAlertPage.close();
        boolean isProductInCart = categoryPage.addToCart(productId).viewCart().isProductInCart(productId);

        assertTrue(isProductInCart,
                "Product with id=" + productId + " (Wspinaczka Via Ferraty) was not found in cart");
    }

    @Test
    public void addFewProductsToCartFromProductPageTest() {
        ProductPage productPage = new ProductPage(driver).goTo(productUrl);
        productPage.footerAlertPage.close();
        int quantityOfProduct = productPage.addToCart(quantity).viewCart().getProductQuantity();

        Assertions.assertEquals(10, quantityOfProduct,
                "Quantity of the product is not what expected");
    }

    @Test
    public void addFewProductToCartFromCategoryPageTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(secondCategoryUrl);
        categoryPage.footerAlertPage.close();
        categoryPage.addAllProductsToCart();
        categoryPage.goTo(thirdCategoryUrl).addAllProductsToCart();
        int productAmount = categoryPage.header.goToCart().getProductAmount();

        Assertions.assertEquals(10, productAmount,
                "Quantity of the product is not what expected");
    }

    @Test
    public void changeNumberOfProductTest() {
        ProductPage productPage = new ProductPage(driver).goTo(productUrl);
        productPage.footerAlertPage.close();
        int quantityOfProduct = productPage.addToCart().viewCart().changeProductAmount(quantity).getProductQuantity();

        Assertions.assertEquals(10, quantityOfProduct,
                "Quantity of the product is not what expected");
    }

    @Test
    public void removeProductFromCartTest() {
        ProductPage productPage = new ProductPage(driver).goTo(productUrl);
        productPage.footerAlertPage.close();
        Boolean cartIsEmpty = productPage.addToCart().viewCart().removeProduct(productId).noExistsProductInCart(productId);

        Assertions.assertTrue(cartIsEmpty,
                "Product id=" + productId + " is in the cart");
    }


}
