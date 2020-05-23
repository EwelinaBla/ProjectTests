package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountPage extends BasePage {
    private WebDriverWait wait;
    public HeaderPage header;

    public AccountPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 5);
        header = new HeaderPage(driver);
    }

    private By myOrderPath = By.xpath(".//*[@class='entry-content']//li[2]/a");
    private By numberOrderPath = By.xpath(".//table/tbody/tr[1]/td[1]/a");
    private By deleteAccountPath = By.xpath(".//div[@class='woocommerce-MyAccount-content']//p[3]/a");


    public String getNumberOrderInMyAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(myOrderPath)).click();
        String numberOrderInMyAccount = driver.findElement(numberOrderPath).getText();
        return numberOrderInMyAccount;
    }

    public AccountPage goTo(String myAccountUrl){
        driver.navigate().to(myAccountUrl);
        return this;
    }
    public AccountPage removeAccount() {
        driver.findElement(deleteAccountPath).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        return this;
    }
}
