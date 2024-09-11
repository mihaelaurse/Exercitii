package esky;

import esky.TestBase;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.lang.String.valueOf;
import static java.time.LocalDate.now;

public class BookFlight extends TestBase {

    public static final int MAX_PRICE = 300;

    @Test
    public void selectDestination() {

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

//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        checkPriceRange();
    }

    private void selectDate(String day, WebElement dateTable) {
        String month;
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

    private void checkPriceRange() {
        List<WebElement> priceElements = driver.findElements(By.xpath("//*[@id=\"undefined amount\"]"));
//        String priceText = priceElement.getText();
        OptionalInt minOptional = priceElements.stream()
                .map(WebElement::getText)
                .map(el -> el = el.split(" ")[0].replace(".", ""))
                .mapToInt(Integer::parseInt)
                .min();
        int actualPrice = minOptional.orElseThrow(() -> new InvalidArgumentException("No min value was found, the stream is empty!"));

//        String price = priceText.split(" ")[0].replace(".", "");

//        int actualPrice = Integer.parseInt(price);

        if (actualPrice >= MAX_PRICE) {
            //        Open alternative flights
            driver.findElement(By.xpath("//div[@class='ng-tns-c258-2 ng-star-inserted']")).click();
        }
    }
}
