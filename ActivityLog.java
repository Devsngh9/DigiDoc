package page;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class ActivityLog {
    private WebDriver driver;
    public ActivityLog(WebDriver driver) {
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
        EnterEmail.sendKeys("hoopeartionshead@yopmail.com");
    }
    public void EnterPassword() {
        WebElement EnterPassword = driver.findElement(By.name("password"));
        EnterPassword.sendKeys("Stockholding@123");
    }
    public void SelectUserType() throws InterruptedException {
        driver.findElement(By.id("mui-component-select-userType")).click();
//        driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(3)")).click();
        driver.findElement(By.xpath("//li[contains(text(),'HO Configurator')]")).click();
        Thread.sleep(2000);
    }
    public void ClickLogin() throws InterruptedException {
        WebElement ClickLogin = driver.findElement(By.xpath("//button[contains(text(),'Login')]"));
        ClickLogin.click();
        Thread.sleep(3000);
    }
    public void ClickReport() throws InterruptedException {
        driver.findElement(By.xpath("//div[contains(text(),'Reports')]")).click();
        driver.findElement(By.xpath("//div[contains(text(),'Activity Log')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[contains(text(),'Clients')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.id(":rd:")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".rdrStaticRangeSelected > .rdrStaticRangeLabel"));
        System.out.println("1");
        Thread.sleep(1000);
        driver.findElement(By.tagName("body"));
        System.out.println("2");
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".rdrDay:nth-child(10)"));
        System.out.println("3");
        driver.findElement(By.tagName("body"));
        System.out.println("4");
        driver.findElement(By.cssSelector(".rdrDayNumber:nth-child(2) > span")).click();
        System.out.println("5");
        driver.findElement(By.xpath("//button[contains(text(),'Clients')]")).click();
        driver.findElement(By.cssSelector("#simple-popover > .MuiBackdrop-root")).click();
        System.out.println("6");

//        driver.findElement(By.id("mui-component-select-stateId"));
//
//        driver.findElement(By.cssSelector("#menu-stateId > .MuiBackdrop-root"));
//
//
//        driver.findElement(By.cssSelector("body")).click();
//
//        driver.findElement(By.cssSelector("body"));
//
//        driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(22)")).click();
//
//        driver.findElement(By.id("mui-component-select-userType"));
//
//        driver.findElement(By.cssSelector("#menu-userType > .MuiBackdrop-root"));
//
//        driver.findElement(By.cssSelector("body")).click();
//        driver.findElement(By.cssSelector(".MuiMenuItem-root:nth-child(2)")).click();
//        driver.findElement(By.cssSelector(".MuiButton-primary")).click();


    }

}



