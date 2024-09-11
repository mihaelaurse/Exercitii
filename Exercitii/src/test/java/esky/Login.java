package esky;

import esky.TestBase;
import org.openqa.selenium.By;
import org.junit.Test;

public class Login extends TestBase {
    @Test
    public void login() {
        driver.findElement(By.xpath("//p[@class='user-zone-menu-section']")).click();
        driver.findElement(By.xpath("//li[@class='menu-item user-zone-email']")).click();

        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("michelle_ela88@yahoo.com");
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("1234");
        driver.findElement(By.xpath("//button[@class='submit-button initial']")).click();

    }
}
