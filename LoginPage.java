package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterSearchTerm(String searchTerm) {
        driver.findElement(By.name("q")).sendKeys(searchTerm);
    }

    public void clickSearchButton() {
        driver.findElement(By.name("btnK")).submit();
    }

    public void verifySearchResults() {
    }

}
