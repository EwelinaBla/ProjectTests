package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HeaderPage extends BasePage{
    private WebDriverWait wait;
    protected HeaderPage(WebDriver driver) {
        super(driver);
        wait=new WebDriverWait(driver,5);
    }
    private By cartPath = By.xpath("//*[@id='menu-item-200']/a");
    private By myAccountPath = By.xpath(".//*[@id='menu-item-201']/a");

    public CartPage goToCart() {
        driver.findElement(cartPath).click();
        return new CartPage(driver);
    }
    public AccountPage goToMyAccount(){
        driver.findElement(myAccountPath).click();
        wait.until(ExpectedConditions.urlContains("moje-konto"));
        return new AccountPage(driver);
    }
}
