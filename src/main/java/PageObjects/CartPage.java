package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage {
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 7);
    }

    private By shopTablePath = By.xpath(".//form//*[contains(@class,'shop_table')]");
    private By productQuantityFieldPath = By.xpath(".//div[@class='quantity']//input");
    private By removeProductButtonPath = By.xpath(".//*[contains(@class,'cart_item')]");
    private By quantityPath = By.xpath(".//input[contains(@class,'qty')]");
    private By buttonUpdatePath = By.xpath(".//*[@id='post-6']/div/div/form/table/tbody/tr[2]/td/button");
    private By buttonGoToCashPath = By.xpath(".//a[contains(@class,'checkout-button')]");
    private By nameProductInCartPath = By.xpath(".//*[@class='product-name']/a");
    private By priceProductInCartPath = By.xpath(".//*[@class='product-price']//*[contains(@class, 'Price-amount')]");
    private By quantityInCartPath = By.xpath(".//div[@class='quantity']//input");
    private By communiquePath=By.xpath(".//div[@class='woocommerce-message' and @role='alert']");
    private String removeProductButtonLocator = ".//a[@data-product_id='<productId>']";

    public int getProductQuantity() {
        String quantityInCart = wait.until(ExpectedConditions.visibilityOfElementLocated(productQuantityFieldPath)).getAttribute("value");
        int quantity = Integer.parseInt(quantityInCart);
        return quantity;
    }

    public int getProductAmount() {
        waitForShopTable();
        return driver.findElements(removeProductButtonPath).size();
    }

    public CartPage changeProductAmount(int quantity) {
        WebElement quantityField = driver.findElement(quantityPath);
        quantityField.clear();
        quantityField.sendKeys(String.valueOf(quantity));
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(buttonUpdatePath))).click();
        return this;
    }

    public CartPage removeProduct(String productId) {
        waitForShopTable();
        By removeProductButtonPath = By.xpath(removeProductButtonLocator.replace("<productId>", productId));
        driver.findElement(removeProductButtonPath).click();
        return this;
    }

    public Boolean noExistsProductInCart(String productId) {
        driver.navigate().refresh();
        boolean present;
        try {
            By removeProductButtonPath = By.xpath(removeProductButtonLocator.replace("<productId>", productId));
            driver.findElement(removeProductButtonPath);
            present = false;
        } catch (NoSuchElementException e) {
            present = true;
        }
        return present;
    }

    public boolean isProductInCart(String productId) {
        waitForShopTable();
        By removeProductButtonPath = By.xpath(removeProductButtonLocator.replace("<productId>", productId));
        int productRecords = driver.findElements(removeProductButtonPath).size();
        boolean presenceOfProduct = false;
        if (productRecords == 1) {
            presenceOfProduct = true;
        } else if (productRecords > 1) {
            throw new IllegalArgumentException("Number of records is greater than 1");
        }
        return presenceOfProduct;
    }

    public PaymentPage goToCash() {
        driver.findElement(buttonGoToCashPath).click();
        return new PaymentPage(driver);
    }

    public void waitForShopTable() {
        wait.until(ExpectedConditions.presenceOfElementLocated(shopTablePath));

    }

    public String getNameProduct() {
        String nameProduct = driver.findElement(nameProductInCartPath).getText();
        return nameProduct;
    }

    public String getPriceProduct() {
        String priceProduct = driver.findElement(priceProductInCartPath).getText();
        return priceProduct;
    }

    public String getQuantityInCart() {
        String quantityInCart = driver.findElement(quantityInCartPath).getAttribute("value");
        return quantityInCart;
    }
}


