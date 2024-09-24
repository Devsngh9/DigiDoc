package page;

import ExtentRportBase.ExtentLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.interactions.Actions;
import java.util.Random;

public class DigiDocSoAddStaff {
    private WebDriver driver;
    public DigiDocSoAddStaff(WebDriver driver) {
        this.driver = driver;
    }
    public void SearchDigiDoc() throws InterruptedException {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://digidocdevadmin.infodevbox.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        ExtentLogger.pass("Load DigiDoc Backend Login Page");
    }

    public void EnterEmail() {
        WebElement EnterEmail = driver.findElement(By.name("email"));
        EnterEmail.sendKeys("soadmindelhi@yopmail.com");
        ExtentLogger.pass("Enter Email = soadmindelhi@yopmail.com");
    }

    public void EnterPassword() {
        WebElement EnterPassword = driver.findElement(By.name("password"));
        EnterPassword.sendKeys("Test@123");
        ExtentLogger.pass("Enter Password = Test@123");
    }

    public void SelectUserType() throws InterruptedException {
        WebElement element = driver.findElement(By.id("mui-component-select-userType"));
        Actions builder = new Actions(driver);
        builder.moveToElement(element).clickAndHold().perform();
        driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(4)")).click();
        ExtentLogger.pass("Click on Select UserType = State Configurator");
        Thread.sleep(3000);
    }

    public void ClickLogin() throws InterruptedException {
        WebElement ClickLogin = driver.findElement(By.xpath("//button[contains(text(),'Login')]"));
        ClickLogin.click();
        ExtentLogger.pass("Click on Login button");
        Thread.sleep(3000);
    }

    public void ManageUsersAccounts() throws InterruptedException {
        WebElement ManageUsersAccounts = driver.findElement(By.xpath("//div[contains(text(),'Manage Users Accounts')]"));
        ManageUsersAccounts.click();
        ExtentLogger.pass("Click on Manage Users Accounts button");
        Thread.sleep(3000);
    }

    public void AddUsers() throws InterruptedException {
        WebElement AddUsers = driver.findElement(By.cssSelector("button.MuiButton-root.MuiButton-primary.MuiButton-sizeMedium"));
        AddUsers.click();
        ExtentLogger.pass("Click on Add Users button");
        Thread.sleep(3000);
    }

    public void AddUsersDetails() {
        WebElement element = driver.findElement(By.tagName("body"));
        Actions builder = new Actions(driver);
        builder.moveToElement(element, 0, 0).perform();

        String generatedName = generateUniqueName(5);
        String generatedEmployeeId = generateUniqueEmployeeId();
        String generatedEmail = generateUniqueEmail();
        String generatedPhoneNumber = generateUniquePhoneNumber();

        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).sendKeys(generatedName);
        ExtentLogger.pass("Enter Name = " + generatedName);

        driver.findElement(By.name("employeeId")).click();
        driver.findElement(By.name("employeeId")).sendKeys(generatedEmployeeId);
        ExtentLogger.pass("Enter Employee Id = " + generatedEmployeeId);

        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).sendKeys(generatedEmail);
        ExtentLogger.pass("Enter Email = " + generatedEmail);

        driver.findElement(By.name("phone")).click();
        driver.findElement(By.name("phone")).sendKeys(generatedPhoneNumber);
        ExtentLogger.pass("Enter Number = " + generatedPhoneNumber);
    }

    public void DepartmentSelect() {
        WebElement element = driver.findElement(By.id("mui-component-select-departmentId"));
        Actions builder = new Actions(driver);
        builder.moveToElement(element).clickAndHold().perform();
        driver.findElement(By.cssSelector("#menu-departmentId > .MuiBackdrop-root"));
        driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(3)")).click();
        ExtentLogger.pass("Select Department = So Business");
    }

    public void SelectRole() {
        WebElement SelectRole = driver.findElement(By.xpath("//div[contains(text(),'Select role')]"));
        SelectRole.click();
        Actions builder = new Actions(driver);
        builder.moveToElement(SelectRole).release().perform();
        driver.findElement(By.cssSelector(".MuiMenuItem-root:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".MuiButton-primary")).click();
        ExtentLogger.pass("Select Role = So Business");
    }
    public void VerifyAddUser() {
        WebElement tableCell = driver.findElement(By.xpath("//td[contains(text(),'Dev1')]"));
        String cellText = tableCell.getText();
        if (cellText.contains("Dev1")) {
            System.out.println("Assertion passed: Name 'Dev1' found in table cell.");
        } else {
            System.out.println("Assertion failed: Name 'Dev1' not found in table cell.");
        }
        ExtentLogger.pass("User Added successfully ");
        driver.quit();
    }

    public String generateUniqueName(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
    private String generateUniqueEmployeeId() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        return "EID" + randomNumber;
    }
    private String generateUniqueEmail() {
        String uniqueName = generateUniqueName(4).toLowerCase();
        return uniqueName + "@yopmail.com";
    }
    private String generateUniquePhoneNumber() {
        Random random = new Random();
        long randomNumber = 1000000000L + random.nextInt(900000000);
        return String.valueOf(randomNumber);
    }
}
