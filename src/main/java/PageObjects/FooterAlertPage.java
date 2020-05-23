package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FooterAlertPage extends BasePage{
    protected FooterAlertPage(WebDriver driver){
        super(driver);
    }
    private By alertCookiesPath=By.xpath(".//a[@class='woocommerce-store-notice__dismiss-link']");

    public void close(){
        driver.findElement(alertCookiesPath).click();
    }
}
