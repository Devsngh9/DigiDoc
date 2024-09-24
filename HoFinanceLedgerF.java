package page;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import java.util.concurrent.TimeUnit;
public class HoFinanceLedgerF {
    private WebDriver driver;
    public HoFinanceLedgerF(WebDriver driver) {
        this.driver = driver;
    }
    public void SearchDigiDoc() {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://digidocbouat.stockholding.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    public void EnterEmail() {
        WebElement EnterEmail = driver.findElement(By.name("email"));
        EnterEmail.sendKeys("hofinancehead@yopmail.com");
    }
    public void EnterPassword() {
        WebElement EnterPassword = driver.findElement(By.name("password"));
        EnterPassword.sendKeys("Stockholding@123");
    }
    public void SelectUserType() throws InterruptedException {
        driver.findElement(By.id("mui-component-select-userType")).click();
        driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(3)")).click();
        Thread.sleep(2000);
    }
    public void ClickLogin() throws InterruptedException {
        WebElement ClickLogin = driver.findElement(By.xpath("//button[contains(text(),'Login')]"));
        ClickLogin.click();
        Thread.sleep(3000);
    }
    public void LedgerFilter() throws InterruptedException {
        driver.findElement(By.xpath("//div[contains(text(),'Reports')]")).click();
        driver.findElement(By.xpath("//div[contains(text(),'Ledger')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("combo-box-demo")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("combo-box-demo-option-20")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("rs-:rh:")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".rs-calendar-table-cell-is-today .rs-calendar-table-cell-day")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("rs-:rk:")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".rs-calendar-table-cell-is-today .rs-calendar-table-cell-day")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@id='combo-box-demo' and @placeholder='Select main code']")).click();
        driver.findElement(By.id("combo-box-demo-option-14")).click();
        driver.findElement(By.xpath("//button[contains(text(),'Run Report')]")).click();
    }
    public void OpeningBalance(){
        try {
            WebElement openingBalanceElement = driver.findElement(By.xpath("//span[contains(text(), 'Opening Balance As On')]"));
            String openingBalanceText = openingBalanceElement.getText();
            String todayOpeningBalance = extractBalance(openingBalanceText);

            driver.findElement(By.id("rs-:rh:")).click();
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(".rs-calendar-table-row:nth-child(2) > .rs-calendar-table-cell:nth-child(4) .rs-calendar-table-cell-day")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("rs-:rk:")).click();
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(".rs-calendar-table-row:nth-child(2) > .rs-calendar-table-cell:nth-child(4) .rs-calendar-table-cell-day")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//input[@id='combo-box-demo' and @placeholder='Select main code']")).click();
            driver.findElement(By.id("combo-box-demo-option-14")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//button[contains(text(),'Run Report')]")).click();
            Thread.sleep(5000);

            WebElement closingBalanceElement = driver.findElement(By.xpath("//span[contains(text(), 'Closing Balance As On')]"));
            String closingBalanceText = closingBalanceElement.getText();
            String yesterdayClosingBalance = extractBalance(closingBalanceText);

            if (todayOpeningBalance.equals(yesterdayClosingBalance)) {
                System.out.println("The opening balance for today matches yesterday's closing balance"  + openingBalanceText);
            } else {
                System.out.println("The opening balance for today does not match yesterday's closing balance." + closingBalanceText);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
        }
    }
    private static String extractBalance(String text) {
        String[] parts = text.split("Rs. ");
        if (parts.length > 1) {
            String balance = parts[1].trim();
            return balance;
        }
        return "";
    }
}
