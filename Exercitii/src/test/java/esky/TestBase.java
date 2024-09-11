package esky;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class TestBase {
    protected ChromeDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver1.exe");
//        Disable notifications
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        driver.get("https://www.esky.ro/");

        //        Wait for all elements to be visible
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

//        Accept cookies
        driver.findElement(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();
    }

//    @After
//    public void tearDown() {
//        driver.quit();
//    }
}
