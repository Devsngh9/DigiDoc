package page;

import ExtentRportBase.ExtentLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import Enum.PASSVALUE;

public class ORNGHRMLOGIN {
    private WebDriver driver;

    private static final String LOGIN_INPUT_XPATH = "//input[@name=\"%s\"]";
    private static final String LOGIN_BUTTON_XPATH = "//button[@type=\"submit\"]";
    private static final String PIM_XPATH = "//span[text()='%s']";
    private static final String CLICKLEAVE_XPATH = "//span[text()='Leave']";
    private static final String SEARCH_BUTTON_XPATH = "//button[@type='submit']";

    private static final String LOGIN_WAIT = "3000";
    private static final String FILTER_WAIT = "5000";

    public ORNGHRMLOGIN(WebDriver driver) {
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
        ExtentLogger.pass("Enter Username = Admin" );
    }
    public void EnterPassword() {
        WebElement passwordInput = driver.findElement(By.xpath(String.format(LOGIN_INPUT_XPATH, "password")));
        passwordInput.sendKeys("admin123");
        ExtentLogger.pass("Enter Password = admin123");
    }
    public void ClickLogin() throws InterruptedException {
        driver.findElement(By.xpath(LOGIN_BUTTON_XPATH)).click();
        waitFor(LOGIN_WAIT);
        ExtentLogger.pass("Click on Login button");
    }
    public void VerifyMenu()  {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("app")));
        for(PASSVALUE dropdownOption: PASSVALUE.values()) {
//            verifyMenuItemVisibility(wait, PIM_XPATH, dropdownOption.getText());
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(PIM_XPATH, dropdownOption.getText()))));
            ExtentLogger.pass("Verify Menu Icon = '" + dropdownOption.getText() + "' ");
        }
    }
    private static void verifyMenuItemVisibility(WebDriverWait wait, String xpath, String menuName) {
        WebElement menuElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        System.out.println(menuName + " menu displayed: " + menuElement.isDisplayed());
    }
    public void LeaveMenu() throws InterruptedException {
        driver.findElement(By.xpath(CLICKLEAVE_XPATH)).click();
        ExtentLogger.pass("Click on Leave Menu button");
        waitFor(FILTER_WAIT);
        driver.findElement(By.cssSelector(".oxd-grid-item:nth-child(1) .oxd-icon")).click();
        ExtentLogger.pass("Click on From Date ");
        driver.findElement(By.cssSelector(".oxd-calendar-date-wrapper:nth-child(10) > .oxd-calendar-date")).click();
        ExtentLogger.pass("Pass The Date '2024-10-01' In From Date ");
        driver.findElement(By.cssSelector(".oxd-grid-item:nth-child(2) .oxd-date-wrapper .oxd-icon")).click();
        ExtentLogger.pass("Click on To Date ");
        driver.findElement(By.cssSelector(".oxd-calendar-date-wrapper:nth-child(18) > .oxd-calendar-date")).click();
        ExtentLogger.pass("Pass The Date '2024-31-12' In To Date ");
    }
    public void ClickSearchButton() throws InterruptedException {
        driver.findElement(By.xpath(SEARCH_BUTTON_XPATH)).click();
        ExtentLogger.pass("Click on Search button");
        waitFor(FILTER_WAIT);
        driver.quit();
    }
}











