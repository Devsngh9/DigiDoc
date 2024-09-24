package page;

import ExtentRportBase.ExtentLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ORNGHRMADMIN {
    private WebDriver driver;

    private static final String LOGIN_INPUT_XPATH = "//input[@name=\"%s\"]";
    private static final String LOGIN_BUTTON_XPATH = "//button[@type=\"submit\"]";
    private static final String ADMIN_MENU_XPATH = "//span[text()='Admin']";
    private static final String ADMIN_MENURECORD_ROWS_CSSSELECTOR = ".oxd-table-row";
    private static final String ADMIN_MENURECORD_CELLS_CSSSELECTOR = ".oxd-table-cell";
    private static final String USERNAME_FIELD_XPATH = "//label[text()='Username']//following::input[@class=\"oxd-input oxd-input--active\"]";
    private static final String EMPLOYEE_NAME_FIELD_XPATH = "//input[@placeholder='Type for hints...']";
    private static final String DROPDOWN_XPATH = "(//div[text()='-- Select --'])[1]";
    private static final String LOGIN_WAIT = "3000";
    private static final String FILTER_WAIT = "2000";

    private static final String FIELD_VALUE= "(//tbody[@class='MuiTableBody-root css-1xnox0e']//tr)[1]//td";

    public ORNGHRMADMIN(WebDriver driver) {
        this.driver = driver;
    }
    private void waitFor(String milliseconds) throws InterruptedException {
        int waitTime = Integer.parseInt(milliseconds);
        Thread.sleep(waitTime);
    }
    public void SearchOrnghrm() {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        ExtentLogger.pass("Load OrangeHRM Login Page");
    }
    public void EnterUsername() {
        WebElement emailInput = driver.findElement(By.xpath(String.format(LOGIN_INPUT_XPATH, "username")));
        emailInput.sendKeys("Admin");
        ExtentLogger.pass("Enter Username = 'Admin'");
    }
    public void EnterPassword() {
        WebElement passwordInput = driver.findElement(By.xpath(String.format(LOGIN_INPUT_XPATH, "password")));
        passwordInput.sendKeys("admin123");
        ExtentLogger.pass("Enter Password = 'admin123'");
    }
    public void ClickLogin() throws InterruptedException {
        driver.findElement(By.xpath(LOGIN_BUTTON_XPATH)).click();
        waitFor(LOGIN_WAIT);
        ExtentLogger.pass("Click on 'Login' button");
    }
    public void ClickAdmin() throws InterruptedException {
        WebElement adminMenu = driver.findElement(By.xpath(ADMIN_MENU_XPATH));
        adminMenu.click();
        waitFor(FILTER_WAIT);
        ExtentLogger.pass("Click on 'Admin' button");
    }
    public void AdminMenuRecord() throws InterruptedException {
        List<WebElement> rows = driver.findElements(By.cssSelector(ADMIN_MENURECORD_ROWS_CSSSELECTOR));
        ArrayList<String> cellData = new ArrayList<>();
//        List<String> cellData = new ArrayList<>();
        if (rows.size() >= 3) {
            WebElement thirdRow = rows.get(2);
            List<WebElement> cells = thirdRow.findElements(By.cssSelector(ADMIN_MENURECORD_CELLS_CSSSELECTOR));
            for (int i = 0; i < 5; i++) {
                cellData.add(i, cells.get(i).findElement(By.cssSelector("div")).getText());
            }
        }
        ExtentLogger.pass("Here is the Admin Menu Record Fetch From Records :- ");
        ExtentLogger.pass("Data from cell : " + cellData);

//        WebElement Username = driver.findElement(By.xpath(USERNAME_FIELD_XPATH));
//        for (int i = 1; i < 5; i++) {
//            waitFor(FILTER_WAIT);
//            Username.clear();
//            Username.sendKeys(cellData.get(i));
//            ExtentLogger.pass("User Name Data from Records: " + cellData.get(i));
//        }
        WebElement Username = driver.findElement(By.xpath(USERNAME_FIELD_XPATH));
        Username.sendKeys(cellData.get(1));
        ExtentLogger.pass("User Name Data from Records: " + cellData.get(1));

        WebElement EmployeeName = driver.findElement(By.xpath(EMPLOYEE_NAME_FIELD_XPATH));
        EmployeeName.sendKeys(cellData.get(3));
        waitFor(FILTER_WAIT);
        ExtentLogger.pass("Employee Name Data from Records: " + cellData.get(3));

        WebElement UserRole = driver.findElement(By.xpath(DROPDOWN_XPATH));
        UserRole.click();
        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
        waitFor(FILTER_WAIT);
        ExtentLogger.pass("Select User Role: 'ESS' ");

        waitFor(FILTER_WAIT);
        WebElement Status = driver.findElement(By.xpath(DROPDOWN_XPATH));
        Status.click();
        waitFor(FILTER_WAIT);
        org.openqa.selenium.interactions.Actions aactions = new org.openqa.selenium.interactions.Actions(driver);
        aactions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
        ExtentLogger.pass("Select Status = 'Enabled' ");

        List<WebElement> searchResultItems = driver.findElements(By.xpath("(//label[@class=\"oxd-label\"])[3]"));
        Thread.sleep(3000);
        boolean allResultsContainName = true;
        for (WebElement result : searchResultItems) {
        String resultText = result.getText();
        if (resultText.contains(cellData.get(3))) {
        allResultsContainName = false;
        break;
        }
        }
        if (allResultsContainName) {
        System.out.println("All search results contain:-  " + cellData.get(3));
        } else {
        System.out.println("Not all search results contain:-  " + cellData.get(3));
        for (WebElement result : searchResultItems) {
        System.out.println(result.getText());
        }
        }
        driver.quit();
    }
}



