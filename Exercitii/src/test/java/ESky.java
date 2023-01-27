import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.valueOf;
import static java.time.LocalDate.now;

public class ESky {

    private ChromeDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
//        Disable notifications
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

//    @After
//    public void tearDown() {
//        driver.quit();
//    }

    @Test
    public void selectDestination() {

        driver.get("https://www.esky.ro/");

//        Wait for all elements to be visible
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

//        Accept cookies
        driver.findElement(By.xpath("//button[@mode='primary']")).click();

//        Search destination
        driver.findElement(By.id("departureRoundtrip0")).click();
        driver.findElement(By.id("departureRoundtrip0")).sendKeys("Iasi");
        driver.findElement(By.id("arrivalRoundtrip0")).click();
        driver.findElement(By.id("arrivalRoundtrip0")).sendKeys("Larnaca");

//        Pick today's date
        driver.findElement(By.id("departureDateRoundtrip0")).click();

        // Get today's date + 1
        String departureDay = valueOf(now().plusDays(1).getDayOfMonth());

        // Identify the date table
        WebElement departureDateTable = driver.findElement(By.id("departureDateRoundtrip0"));

        selectDate(departureDay, departureDateTable);

//        Return date
        driver.findElement(By.id("departureDateRoundtrip1")).click();

        String returnDay = valueOf(now().plusDays(4).getDayOfMonth());

        WebElement returnDateTable = driver.findElement(By.id("departureDateRoundtrip1"));

        selectDate(returnDay, returnDateTable);

//        Choose number of passengers
        driver.findElement(By.xpath("//div[@class='wrap pax-widget custom-dropdown  ']")).click();
        driver.findElement(By.xpath("//i[@class='icon-plus']")).click();

//        Search
        driver.findElement(By.xpath("//button[@class='btn transaction qsf-search']")).click();

    }

    private void selectDate(String day, WebElement dateTable) {
        String month;
        // Get all cell in a list
        List<WebElement> columns = dateTable.findElements(By.xpath("//td/a"));

        for (WebElement cell : columns) {
            if (cell.getText().equals(day)) {
                month = cell.findElement(By.xpath("//table[@class='ui-datepicker-calendar']")).getAttribute("class");
                //TODO Adjust the test to work for next month selection
                if (!month.contains("OtherMonth")) {
                    cell.click();
                    break;
                }
            }
        }
    }

//    Select another date if first is not available
//    public void chooseAnotherDate() {
//
//        driver.findElement(By.xpath("//span[@class='title ng-tns-c257-2']")).click();
//
////        is date found or not
//        boolean dateFound = false;
//
//        do {
//            List<WebElement> availableDates = driver.findElements(By.xpath("//esky-flex-table[@class='table ng-tns-c266-3 ng-star-inserted ng-tns-c257-2']"));
//            if (availableDates.size()>0) {
//                for(WebElement date:availableDates) {
//                    date.click();
//                    break;
//                }
//            }
//            else {
//                WebElement next = driver.findElement(By.xpath("//i[@class='icon icon-eui_arrow-right ng-star-inserted']"));
//                dateFound = true;
//            }
//        }while (dateFound);
//    }


    @Test
    public void logIn() {

        driver.get("https://www.esky.ro/");
        driver.findElement(By.xpath("//button[@mode='primary']")).click();

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

//        Log in
        driver.findElement(By.xpath("//input[@class='input ng-pristine ng-valid ng-star-inserted ng-touched']")).sendKeys("michelle_ela88@yahoo.com");
        driver.findElement(By.xpath("//input[@class='input ng-pristine ng-valid ng-touched']")).sendKeys("1234");

    }

}
