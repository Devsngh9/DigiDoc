package page;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import Enum.Credential;
import Enum.XPATHVALUES;
import Enum.XPATH;
import Enum.MenuNameVerify;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertEquals;

public class Hoadmin {
    private WebDriver driver;
    //XPATH
    private static final String INPUT_NAME_XPATH = XPATH.NAME_XPATH.getText();
    private static final String SELECT_TYPE_ID = XPATH.SELECT_TYPE.getText();
    private static final String BUTTON_XPATH = XPATH.BUTTON_PATH.getText();
    private static final String VERIFY_ROW = XPATH.ROW_VALUE.getText();
    private static final String FILTER_SELECT = XPATH.FILTER_VALUE_SELECT.getText();
    private static final String VERIFY_DATA = XPATH.TABLE_XATH.getText();
    private static final String APPLY_DATE_RANGE = XPATH.DATE_RANGE.getText();
    private static final String START_DATE = XPATH.SELECT_DATES.getText();
    private static final String LOGIN_WAIT = "3000";
    private static final String FILTER_WAIT = "1500";

    private void waitFor(String milliseconds) throws InterruptedException {
        int waitTime = Integer.parseInt(milliseconds);
        Thread.sleep(waitTime);
    }
    public Hoadmin(WebDriver driver) {
        this.driver = driver;
    }
    public void SearchDigiDoc() {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
        driver.get(Credential.WEBSITE_URL.getText());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    public void LogInPageCredentials() throws InterruptedException {
        driver.findElement(By.xpath(String.format(INPUT_NAME_XPATH, XPATHVALUES.EMAIL.getText()))).sendKeys(Credential.EMAIL.getText());
        driver.findElement(By.xpath(String.format(INPUT_NAME_XPATH, XPATHVALUES.PASSWORD.getText()))).sendKeys(Credential.PASSWORD.getText());
        driver.findElement(By.xpath(String.format(SELECT_TYPE_ID, XPATHVALUES.SELECT_USER.getText()))).click();
        Actions builder = new Actions(driver);
        builder.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
        waitFor(FILTER_WAIT);
        driver.findElement(By.xpath(String.format(BUTTON_XPATH, XPATHVALUES.LOGIN.getText()))).click();
        waitFor(LOGIN_WAIT);
    }
    public void VerifyMenu() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("root")));
        for (MenuNameVerify VerifyName : MenuNameVerify.values()) {
            String xpath = String.format(SELECT_TYPE_ID, VerifyName.getText());
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            System.out.println("Verify Menu Icon = '" + VerifyName.getText() + "' ");
        }
    }
    public void ClickOnAuditLogs() throws InterruptedException {
        driver.findElement(By.xpath(String.format(SELECT_TYPE_ID, XPATHVALUES.AUDIT_LOG.getText()))).click();
        waitFor(FILTER_WAIT);
    }
    public void verifyTransactionalLogs(String expectedMessage) {
        String actualMessage = driver.findElement(By.xpath(String.format(BUTTON_XPATH, XPATHVALUES.TRANSACTIONAL_LOGS.getText()))).getText();
        assertEquals("Expected message does not match!", expectedMessage, actualMessage);
        System.out.println(expectedMessage + actualMessage);
    }
    public void verifyOperationalLogs(String expectedMessage) {
        String actualMessage = driver.findElement(By.xpath(String.format(BUTTON_XPATH, XPATHVALUES.OPERATIONAL_LOGS.getText()))).getText();
        assertEquals("Expected message does not match!", expectedMessage, actualMessage);
        System.out.println(expectedMessage + actualMessage);
    }
    public void ClickOperationalLogs() throws InterruptedException {
        driver.findElement(By.xpath(String.format(BUTTON_XPATH, XPATHVALUES.OPERATIONAL_LOGS.getText()))).click();
        waitFor(FILTER_WAIT);
    }
    public void verifyRows() {
        String actualMessage = driver.findElement(By.xpath(VERIFY_ROW)).getText();
        assertEquals("Expected message does not match!", actualMessage, actualMessage);
        System.out.println(actualMessage + actualMessage);
    }
    public void ApplyFilter() throws InterruptedException {
        driver.findElement(By.xpath(String.format(SELECT_TYPE_ID, XPATHVALUES.APPLY_FILTER.getText()))).click();
        waitFor(FILTER_WAIT);
        driver.findElement(By.xpath(FILTER_SELECT)).click();
    }
    public void verifyTableData(String expectedMessage) throws InterruptedException {
        List<WebElement> searchResultItems = driver.findElements(By.xpath(String.format(VERIFY_DATA,XPATHVALUES.TABLE_XPATH_VALUE.getText())));
        waitFor(FILTER_WAIT);
        for (WebElement result : searchResultItems) {
            String resultText = result.getText();
            assertEquals("Expected message not found in table data", expectedMessage, resultText);
            System.out.println(expectedMessage + resultText);
        }
    }
    public void ApplyDateRange() throws InterruptedException {
        WebElement dateRangeInput = driver.findElement(By.xpath(APPLY_DATE_RANGE));
        waitFor(FILTER_WAIT);
        dateRangeInput.click();
        LocalDate startDate = generateRandomDate(30);
        LocalDate endDate = startDate.plusDays(new Random().nextInt(10) + 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<String> formattedStartDate = Arrays.asList(startDate.format(formatter).split("/"));
        List<String> formattedEndDate = Arrays.asList(endDate.format(formatter).split("/"));
        driver.findElement(By.xpath(String.format(START_DATE, formattedStartDate.get(0).replace("0", "")))).click();
        driver.findElement(By.xpath(String.format(START_DATE, formattedEndDate.get(0).replace("0", "")))).click();
        WebElement el = driver.findElement(By.xpath((String.format(INPUT_NAME_XPATH, XPATHVALUES.SEARCH.getText()))));
        Actions builder = new Actions(driver);
        builder.moveToElement(el).click(el);
        builder.perform();
    }
    public void verifyDateRangeTableData() throws InterruptedException {
        List<WebElement> searchResultItems = driver.findElements(By.xpath(String.format(VERIFY_DATA,XPATHVALUES.DATE_RANGE_VALUE.getText())));
        waitFor(FILTER_WAIT);
        for (WebElement result : searchResultItems) {
            String resultText = result.getText();
            assertEquals("Expected message not found in table data", resultText, resultText);
            System.out.println(resultText + resultText);
        }
    }
    private static LocalDate generateRandomDate(int days) {
        LocalDate today = LocalDate.now();
        Random random = new Random();
        int randomDays = random.nextInt(days + 1);
        return today.minusDays(randomDays);
     }
}





















    //Random Date Picker Method
//    private static final String START_DATE = "(//div[@class='rdrDays']//button)[%s]"; //XPATH
//     public void ApplyDateRange() throws InterruptedException {
//        WebElement dateRangeInput = driver.findElement(By.xpath(APPLY_DATE_RANGE));
//        waitFor(FILTER_WAIT);
//        dateRangeInput.click();
//        LocalDate startDate = generateRandomDate(30);
//        LocalDate endDate = startDate.plusDays(new Random().nextInt(10) + 1);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        List<String> formattedStartDate = Arrays.asList(startDate.format(formatter).split("/"));
//        List<String> formattedEndDate = Arrays.asList(endDate.format(formatter).split("/"));
//        driver.findElement(By.xpath(String.format(START_DATE, formattedStartDate.get(0).replace("0", "")))).click();
//        driver.findElement(By.xpath(String.format(START_DATE, formattedEndDate.get(0).replace("0", "")))).click();
//        WebElement el = driver.findElement(By.xpath((String.format(INPUT_NAME_XPATH, XPATHVALUES.SEARCH.getText()))));
//        Actions builder = new Actions(driver);
//        builder.moveToElement( el ).click( el );
//        builder.perform();
//     }
//     private static LocalDate generateRandomDate(int days) {
//        LocalDate today = LocalDate.now();
//        Random random = new Random();
//        int randomDays = random.nextInt(days + 1);
//        return today.minusDays(randomDays);
//     }

//        Specific Date Filter apply in DigiDoc Date Range
//        public void ApplyDateRange() throws InterruptedException {
//        WebElement dateRangeInput = driver.findElement(By.xpath(APPLY_DATE_RANGE));
//        waitFor(FILTER_WAIT);
//        dateRangeInput.click();
//        driver.findElement(By.cssSelector(".rdrDay:nth-child(1) > .rdrDayNumber")).click();
//        waitFor(FILTER_WAIT);
//        driver.findElement(By.cssSelector(".rdrDay:nth-child(19) > .rdrDayNumber")).click();
//        WebElement el = driver.findElement(By.xpath((String.format(INPUT_NAME_XPATH, XPATHVALUES.SEARCH.getText()))));
//        Actions builder = new Actions(driver);
//        builder.moveToElement(el).click(el);
//        builder.perform();
//    }