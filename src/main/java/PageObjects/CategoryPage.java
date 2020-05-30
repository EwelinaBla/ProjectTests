package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CategoryPage extends BasePage {

    public HeaderPage header;
    public FooterAlertPage footerAlertPage;
    private WebDriverWait wait;

    public CategoryPage(WebDriver driver) {
        super(driver);
        header = new HeaderPage(driver);
        footerAlertPage = new FooterAlertPage(driver);
    }

    private By buttonsAddToCartPath          = By.xpath(".//*[contains(@class,'add_to_cart_button')]");
    private By viewCartButtonPath            = By.xpath(".//*[contains(@class,'added_to_cart')]");

    private String buttonAddToCartLocator = ".//*[contains(@class,'add_to_cart_button') and @data-product_id='<productId>']";

    public CategoryPage goTo(String categoryUrl) {
        driver.navigate().to(categoryUrl);
        return this;
    }

    public CategoryPage addToCart(String productId) {
        By buttonAddToCartPath = By.xpath(buttonAddToCartLocator.replace("<productId>", productId));
        driver.findElement(buttonAddToCartPath).click();
        return this;
    }

    public CartPage viewCart() {
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(viewCartButtonPath)).click();
//        wait.until(ExpectedConditions.elementToBeClickable(viewCartButtonPath)).click();
        return new CartPage(driver);
    }

    public CategoryPage addAllProductsToCart() {
        wait = new WebDriverWait(driver, 10);
        List<WebElement> buttonsAddToCart = driver.findElements(buttonsAddToCartPath);
        for (WebElement buttonAddToCart : buttonsAddToCart) {
            buttonAddToCart.click();
            wait.until(ExpectedConditions.attributeContains(buttonAddToCart, "class", "add_to_cart added"));
        }
        return this;
    }

}
