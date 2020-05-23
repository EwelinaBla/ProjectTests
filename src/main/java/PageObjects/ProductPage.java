package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends BasePage {

    public FooterAlertPage footerAlertPage;
    private WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        super(driver);
        footerAlertPage =new FooterAlertPage(driver);
        wait = new WebDriverWait(driver, 7);
    }

    private By buttonAddToCartButtonPath = By.xpath(".//button[@name='add-to-cart']");
    private By buttonViewCartPath = By.xpath(".//div[@role='alert']//a");
    private By productQuantityPath = By.xpath(".//form/div[1]/input");

    public ProductPage goTo(String productUrl) {
        driver.navigate().to(productUrl);
        return this;
    }

    public ProductPage addToCart() {
        driver.findElement(buttonAddToCartButtonPath).click();
        return this;
    }

    public CartPage viewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonViewCartPath)).click();
        return new CartPage(driver);
    }

    public ProductPage addToCart(int quantity) {
        WebElement productQuantity = driver.findElement(productQuantityPath);
        productQuantity.clear();
        productQuantity.sendKeys(String.valueOf(quantity));
        driver.findElement(buttonAddToCartButtonPath).click();
        return this;
    }
}
