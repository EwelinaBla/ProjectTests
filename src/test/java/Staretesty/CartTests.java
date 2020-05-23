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
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CartTests {
    WebDriver driver;
    WebDriverWait wait;
    //region PATHS
    By alertCookiesPath = By.xpath(".//a[@class='woocommerce-store-notice__dismiss-link'] ");
    By shopPath = By.xpath(".//*[@id='menu-item-198']/a");
    By nameCategoryPath = By.xpath(".//*[@class='products columns-3']/li[2]//a//img");
    By nameSecondCategoryPath = By.xpath(".//div/div[2]/main/ul/li[1]/a/h2");
    By nameThirdCategoryPath = By.xpath(".//div/div[2]/main/ul/li[3]/a/h2");
    By titleCategoryPath = By.xpath(".//*[@id='main']/ul/li[2]/a[1]/h2");
    By buttonAddToCartFromProductPagePath = By.xpath(".//button[@name='add-to-cart']");
    By textNameProductPath = By.xpath(".//*[@id='product-40']/div[2]/h1");
    By lookCartPath = By.xpath(".//div[@class='woocommerce-message']//a[@class='button wc-forward']");
    By productToCartPath = By.xpath(".//*[@id='post-6']/div/div/form/table/tbody/tr[1]/td[3]/a");
    By buttonByCategoryPagePath = By.xpath(".//*[@id='main']/ul/li[2]/a[2]");
    By lookCartFromCategoryPagePath = By.xpath(".//*[contains(@class, 'added_to_cart')]");
    By cartTitlePath = By.xpath(".//*[@id='post-6']/header/h1");
    By nameProductPath = By.xpath(".//div[@class='summary entry-summary']//h1");
    By quantityInCartPath = By.xpath(".//tbody/tr[1]/td[5]/div/input");
    By buttonUpdateCartPath = By.xpath(".//*[@id='post-6']/div/div/form/table/tbody/tr[2]/td/button");
    By removeProductPath = By.xpath("//tbody/tr[1]/td[1]/a[@class='remove']");
    By alertPath = By.xpath(".//div[@role='alert']");
    By quantityOnProductPagePath = By.xpath(".//form/div[1]/input");
    By buttonsAddToCartPath = By.xpath(".//*[contains(@class,'add_to_cart_button')]");
    By cartTagPath = By.xpath(".//header/div[2]/div/nav/div[1]/ul/li[4]/a");
    By allProductsInCartPath = By.xpath(".//*[@id='post-6']/div/div/form/table/tbody/tr[contains(@class,'cart_item')]");
    String nameProduct = "Wspinaczka Via Ferraty";
    int numberOfProduct = 10;
    //endregion

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
        driver.findElement(shopPath).click();
    }

    @Test
    public void addOneProductToCartFromProductPageTest() {
        addProductFromProductPage();
        driver.findElement(buttonAddToCartFromProductPagePath).click();

        String message = String.format("Zobacz koszyk\n“%s” został dodany do koszyka.",
                wait.until(ExpectedConditions.visibilityOfElementLocated(textNameProductPath)).getText());

        String alertMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(alertPath)).getText();
        driver.findElement(lookCartPath).click();
        String nameProductToCart = wait.until(ExpectedConditions.visibilityOfElementLocated(productToCartPath)).getText();

        Assertions.assertAll(
                () -> Assertions.assertEquals(message, alertMessage,
                        "Alert doesn't contain information that the product " + nameProduct + " was added to cart"),
                () -> Assertions.assertEquals(nameProduct, nameProductToCart,
                        "The product name is not what expected")
        );
    }

    @Test
    public void addFewProductsToCartFromProductPageTest() {
        addProductFromProductPage();
        WebElement quantityOnProductPage = driver.findElement(quantityOnProductPagePath);
        quantityOnProductPage.clear();
        quantityOnProductPage.sendKeys("" + numberOfProduct + "");

        driver.findElement(buttonAddToCartFromProductPagePath).click();
        String message = String.format("Zobacz koszyk\n%s × “%s” zostało dodanych do koszyka.", numberOfProduct,
                wait.until(ExpectedConditions.visibilityOfElementLocated(textNameProductPath)).getText());
        String alertMessage = driver.findElement(alertPath).getText();

        driver.findElement(lookCartPath).click();
        String quantityInCart = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInCartPath)).getAttribute("value");
        int quantity = Integer.parseInt(quantityInCart);

        Assertions.assertAll(
                () -> Assertions.assertEquals(message, alertMessage,
                        "Alert doesn't contain information that the product " + nameProduct + " added to cart"),
                () -> Assertions.assertEquals(nameProduct, driver.findElement(productToCartPath).getText(),
                        "The product name is not what expected"),
                () -> Assertions.assertEquals(numberOfProduct, quantity,
                        "The product name is not what expected")
        );
    }

    @Test
    public void addOneProductToCartFromCategoryPageTest() {
        addProductFromCategoryPage();
        String nameProductToCart = driver.findElement(productToCartPath).getText();
        String quantityInCart = driver.findElement(quantityInCartPath).getAttribute("value");

        Assertions.assertAll(
                () -> Assertions.assertEquals(nameProduct, nameProductToCart,
                        "The product name is not what expected"),
                () -> Assertions.assertEquals("1", quantityInCart,
                        "Quantity of the product is not what expected")
        );
    }

    @Test
    public void addFewProductToCartFromCategoryPageTest() {
        driver.findElement(nameSecondCategoryPath).click();
        addAllProductsToCart();

        driver.navigate().back();
        driver.findElement(nameThirdCategoryPath).click();
        addAllProductsToCart();

        driver.findElement(cartTagPath).click();
        int numberOfItem = driver.findElements(allProductsInCartPath).size();

        Assertions.assertEquals(10, numberOfItem,
                "Quantity of the product is not what expected");
    }

    @Test
    public void changeNumberOfProductTest() {
        addProductFromCategoryPage();
        WebElement quantityOnCategoryPage = driver.findElement(quantityInCartPath);
        quantityOnCategoryPage.clear();
        quantityOnCategoryPage.sendKeys("" + numberOfProduct + "");

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(buttonUpdateCartPath))).click();
        String quantityInCart = wait.until(ExpectedConditions.visibilityOf(quantityOnCategoryPage)).getAttribute("value");
        int quantity = Integer.parseInt(quantityInCart);
        Assertions.assertEquals(numberOfProduct, quantity,
                "Quantity of the product is not what expected");
    }

    @Test
    public void removeProductFromCartTest() {
        addProductFromCategoryPage();
        driver.findElement(removeProductPath).click();
        String alert = wait.until(ExpectedConditions.visibilityOfElementLocated(alertPath)).getText();
        String expectedAlert = "Usunięto: “" + nameProduct + "”. Cofnij?";

        Assertions.assertEquals(expectedAlert, alert,
                "Alert doesn't contain information that the product " + nameProduct + " was removed");
    }

    @AfterEach
    public void quit(TestInfo info) throws IOException {
        if (statusTest.isField) {
            System.out.println(" The screenshot is available in: " + screenshot(info));
        }
        driver.quit();
    }

    private void addProductFromCategoryPage() {
        driver.findElement(nameCategoryPath).click();
        driver.findElement(buttonByCategoryPagePath).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(lookCartFromCategoryPagePath)).click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(cartTitlePath));
    }

    private void addProductFromProductPage() {
        wait.until(ExpectedConditions.elementToBeClickable(nameCategoryPath)).click();
        wait.until(ExpectedConditions.elementToBeClickable(titleCategoryPath)).click();

        WebElement nameProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(nameProductPath));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", nameProduct);
    }

    private void addAllProductsToCart() {
        List<WebElement> buttonsAddToCart = driver.findElements(buttonsAddToCartPath);
        for (WebElement buttonAddToCart : buttonsAddToCart) {
            buttonAddToCart.click();
            wait.until(ExpectedConditions.attributeContains(buttonAddToCart, "class", "ajax_add_to_cart added"));
        }
    }

    private String screenshot(TestInfo info) throws IOException {
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String path = "C:\\Users\\Paweł\\Downloads\\ScreenShots\\" + info.getDisplayName() + formatter.format(LocalDateTime.now()) + ".jpg";
        FileUtils.copyFile(screen, new File(path));
        return path;
    }
}
