package page;

import ExtentRportBase.ExtentLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;

public class DigiDoc {
    private WebDriver driver;
    public DigiDoc(WebDriver driver) {
        this.driver = driver;
    }
    public void search() {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://digidocdevfront.infodevbox.com//");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    public void clickSignup() throws InterruptedException {
        Thread.sleep(2000);
        WebElement signupButton = driver.findElement(By.xpath("//a[contains(text(),'Signup')]"));
        signupButton.click();
        ExtentLogger.pass("Clicked on Signup button");
    }
    public void fillSignupForm(String email, String password) {
        WebElement emailField = driver.findElement(By.name("email"));
        emailField.sendKeys(email);
        ExtentLogger.pass("Entered email: " + email);

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(password);
        ExtentLogger.pass("Entered password");

        WebElement confirmPasswordField = driver.findElement(By.name("confirmPassword"));
        confirmPasswordField.sendKeys(password);
        ExtentLogger.pass("Confirmed password");

        WebElement termsAndConditions = driver.findElement(By.name("termsAndConditions"));
        termsAndConditions.click();
        ExtentLogger.pass("Accepted terms and conditions");

        WebElement privacyPolicy = driver.findElement(By.name("privacyPolicy"));
        privacyPolicy.click();
        ExtentLogger.pass("Accepted privacy policy");

        WebElement disclaimer = driver.findElement(By.name("disclaimer"));
        disclaimer.click();
        ExtentLogger.pass("Accepted disclaimer");
    }
    public void submitSignup() throws InterruptedException {
        Thread.sleep(1000);
        WebElement signupButton = driver.findElement(By.xpath("//button[contains(text(),'Signup')]"));
        signupButton.click();
        ExtentLogger.pass("Clicked on Signup");
        Thread.sleep(5000);
    }
    public void Businesslick() throws InterruptedException {
        Thread.sleep(1000);
        WebElement Businesslick = driver.findElement(By.xpath("//span[contains(text(),'Business associate')]"));
        Businesslick.click();
        ExtentLogger.pass("Click on Business Account button");
        Thread.sleep(3000);
    }
    public void Enteraddress() throws InterruptedException {
        WebElement Enteraddress = driver.findElement(By.name("address"));
        Enteraddress.sendKeys("Rajiv Nagar");
        ExtentLogger.pass("Enter address = Rajiv Nagar ");
        Thread.sleep(2000);

        WebElement SelectState = driver.findElement(By.xpath("//p[contains(text(),'Select State')]"));
        SelectState.click();
        ExtentLogger.pass("Click on Select State button");
        Thread.sleep(2000);

        WebElement Delhi = driver.findElement(By.xpath("(//li[@role=\"option\"])[10]"));
        Delhi.click();
        ExtentLogger.pass("Click on Delhi");
        Thread.sleep(2000);

        WebElement Selectcity = driver.findElement(By.xpath("//p[contains(text(),'Select City')]"));
        Selectcity.click();
        ExtentLogger.pass("Click on Select City button");
        Thread.sleep(2000);

        WebElement Delhicity = driver.findElement(By.xpath("(//li[@role=\"option\"])[2]"));
        Delhicity.click();
        ExtentLogger.pass("Click on Delhi");
        Thread.sleep(2000);

        WebElement Pincode = driver.findElement(By.name("pinCode"));
        Pincode.sendKeys("140301");
        ExtentLogger.pass("Enter Pincode = 140301 ");
        Thread.sleep(2000);

        WebElement Submit = driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));
        Submit.click();
        ExtentLogger.pass("Click on Submit");
        Thread.sleep(5000);
    }
    public void navigateToTickets() {
        WebElement helpMenu = driver.findElement(By.xpath("//div[contains(text(),'Help')]"));
        helpMenu.click();
        ExtentLogger.pass("Clicked on Help");

        WebElement ticketsButton = driver.findElement(By.xpath("//button[contains(text(),'Tickets')]"));
        ticketsButton.click();
        ExtentLogger.pass("Clicked on Tickets");
    }
    public void addTicket(String issueDescription) throws InterruptedException {
        WebElement addTicketButton = driver.findElement(By.xpath("//button[contains(text(),'Add ticket')]"));
        addTicketButton.click();
        ExtentLogger.pass("Clicked on Add Ticket");

        WebElement categoryIssue = driver.findElement(By.xpath("//p[contains(text(),'List of category issues')]"));
        categoryIssue.click();
        ExtentLogger.pass("Clicked on List of category issues");

        WebElement selectIssue = driver.findElement(By.xpath("//li[@data-value='4'][1]"));
        selectIssue.click();
        ExtentLogger.pass("Selected issue: E-Sign");

        WebElement descriptionField = driver.findElement(By.id("description"));
        descriptionField.sendKeys(issueDescription);
        ExtentLogger.pass("Entered description: " + issueDescription);

        WebElement submitTicketButton = driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));
        submitTicketButton.click();
        ExtentLogger.pass("Clicked on Submit Ticket");
        Thread.sleep(7000);
    }
    public void  verifyTicket() throws InterruptedException {
        Thread.sleep(8000);
        WebElement ticketNumberCell = driver.findElement(By.xpath("//tbody/tr/td[1]"));
        Thread.sleep(7000);
        String actualTicketNumber = ticketNumberCell.getText();
        Thread.sleep(6000);
        ExtentLogger.pass("Ticket generated successfully: " + actualTicketNumber);
        driver.quit();
    }
}

