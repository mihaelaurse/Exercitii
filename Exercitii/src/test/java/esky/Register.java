package esky;

import esky.TestBase;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class Register extends TestBase {
    @Test
    public void register() {

        driver.findElement(By.xpath("//span[@class='account-title']")).click();
        driver.findElement(By.xpath("//li[@class='menu-item user-zone-email']")).click();

//        Create account
//        Scroll to element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement inregistrareButton = driver.findElement(By.xpath("//button[@tabindex='0']"));
        js.executeScript("arguments[0].scrollIntoView();", inregistrareButton);
        inregistrareButton.click();

//        Create account information
        driver.findElement(By.id("email")).sendKeys("michelle_ela88@yahoo.com");
        driver.findElement(By.xpath("//i[@class='checked-icon icon-eui_ok-line']")).click();
        driver.findElement(By.xpath("//button[@class='submit-button initial']")).click();

    }
}
