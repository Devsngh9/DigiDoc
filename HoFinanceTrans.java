package page;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class HoFinanceTrans {
    private WebDriver driver;
// %s - string
// %d - int
    private static final String EMAIL_INPUT_XPATH = "//input[@name='%s']";
    private static final String USER_TYPE_DROPDOWN_XPATH = "//div[@id='mui-component-select-userType']";
    private static final String USER_TYPE_OPTION_XPATH = "//ul[@role='listbox']//li[@data-value='2']";
    private static final String LOGIN_BUTTON_XPATH = "//button[normalize-space(text())='Login']";
    private static final String REPORTS_MENU_XPATH = "//div[contains(text(),'%s')]";
    private static final String STATE_DROPDOWN_XPATH = "//div[@aria-label='Select State']";
    private static final String STATE_OPTION_XPATH = "//ul[@role='listbox']//li[@data-value='3']";
    private static final String USER_TYPE_FILTER_DROPDOWN_XPATH = "//div[@aria-label='%s']";
    private static final String TRANSACTION_TYPE_OPTION_XPATH = "//ul[@role='listbox']//li[@data-value='1']";
    private static final String SEARCH_RESULTS_XPATH = "//tbody/tr/td[8]";
    private static final String TRANSACTION_TYPE_OPTION_XPATH_CREDIT = "//ul[@role='listbox']//li[@data-value='2']";
    private static final String SEARCH_RESULTS_XPATH_CREDIT = "//tbody/tr/td[8]";

    private static final String SELECT_USER_TYPE_WAIT = "2000";
    private static final String LOGIN_WAIT = "3000";
    private static final String FILTER_WAIT = "1000";
    private static final String VERIFY_SEARCH_RESULTS_WAIT = "3000";

    public HoFinanceTrans(WebDriver driver) {
        this.driver = driver;
    }
    private void waitFor(String milliseconds) throws InterruptedException {
        int waitTime = Integer.parseInt(milliseconds);
        Thread.sleep(waitTime);
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
        WebElement emailInput = driver.findElement(By.xpath(String.format(EMAIL_INPUT_XPATH, "email")));
       emailInput.sendKeys("hofinanceheadone@yopmail.com");
    }
    public void EnterPassword() {
        WebElement passwordInput = driver.findElement(By.xpath(String.format(EMAIL_INPUT_XPATH, "password")));
        passwordInput.sendKeys("Stockholding@123");
    }
    public void SelectUserType() throws InterruptedException {
        driver.findElement(By.xpath(USER_TYPE_DROPDOWN_XPATH)).click();
        driver.findElement(By.xpath(USER_TYPE_OPTION_XPATH)).click();
        waitFor(SELECT_USER_TYPE_WAIT);
    }
    public void ClickLogin() throws InterruptedException {
        driver.findElement(By.xpath(LOGIN_BUTTON_XPATH)).click();
        waitFor(LOGIN_WAIT);
    }
    public void Filter() throws InterruptedException {
        driver.findElement(By.xpath(String.format(REPORTS_MENU_XPATH, "Reports"))).click();
        driver.findElement(By.xpath(String.format(REPORTS_MENU_XPATH, "Transaction"))).click();
        waitFor(FILTER_WAIT);
    }
    public void TransactionFilter() throws InterruptedException {
        driver.findElement(By.xpath(STATE_DROPDOWN_XPATH)).click();
        waitFor(FILTER_WAIT);
        driver.findElement(By.xpath(STATE_OPTION_XPATH)).click();
        waitFor(FILTER_WAIT);
        driver.findElement(By.xpath(String.format(USER_TYPE_FILTER_DROPDOWN_XPATH, "Select user type"))).click();
        waitFor(FILTER_WAIT);
        driver.findElement(By.xpath(USER_TYPE_OPTION_XPATH)).click();
        waitFor(FILTER_WAIT);
        driver.findElement(By.xpath(String.format(USER_TYPE_FILTER_DROPDOWN_XPATH, "Select type of transaction"))).click();
        waitFor(FILTER_WAIT);
    }
    public void verifyDebitSearchResults() throws InterruptedException {
        driver.findElement(By.xpath(TRANSACTION_TYPE_OPTION_XPATH)).click();
        waitFor(VERIFY_SEARCH_RESULTS_WAIT);
        List<WebElement> searchResultItems = driver.findElements(By.xpath(SEARCH_RESULTS_XPATH));
        waitFor(VERIFY_SEARCH_RESULTS_WAIT);
        boolean allResultsContainName = true;
        for (WebElement result : searchResultItems) {
            String resultText = result.getText();
            if (resultText.contains("DEBIT")) {
                allResultsContainName = true;
                break;
            }
        }
        if (allResultsContainName) {
            System.out.println("All search results contain 'DEBIT'.");
        } else {
            System.out.println("Not all search results contain 'DEBIT'.");
        }
        for (WebElement result : searchResultItems) {
            System.out.println(result.getText());
        }
    }

    public void verifyCreditSearchResults() throws InterruptedException {
        driver.findElement(By.xpath(String.format(USER_TYPE_FILTER_DROPDOWN_XPATH, "Select type of transaction"))).click();
        driver.findElement(By.xpath(TRANSACTION_TYPE_OPTION_XPATH_CREDIT)).click();
        waitFor(VERIFY_SEARCH_RESULTS_WAIT);
        List<WebElement> searchResultItems = driver.findElements(By.xpath(SEARCH_RESULTS_XPATH_CREDIT));
        waitFor(VERIFY_SEARCH_RESULTS_WAIT);
        boolean allResultsContainName = true;
        for (WebElement result : searchResultItems) {
            String resultText = result.getText();
            if (resultText.contains("CREDIT")) {
                allResultsContainName = true;
                break;
            }
        }
        if (allResultsContainName) {
            System.out.println("All search results contain 'CREDIT'.");
        } else {
            System.out.println("Not all search results contain 'CREDIT'.");
        }
        for (WebElement result : searchResultItems) {
            System.out.println(result.getText());
        }
    }
}
