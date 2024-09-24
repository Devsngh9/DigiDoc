package page;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.util.*;
import java.util.concurrent.TimeUnit;
import Enum.PASSVALUE;
import Enum.XPATHVALUES;
import Enum.XPATH;
import static org.junit.Assert.assertEquals;

public class Soadmin {
    private WebDriver driver;
    private static final String INPUT_NAME_XPATH = XPATH.NAME_XPATH.getText();
    private static final String SELECT_TYPE_ID = XPATH.SELECT_TYPE.getText();
    private static final String BUTTON_XPATH = XPATH.BUTTON_PATH.getText();
    private static final String ROW_LOCATOR = XPATH.ROW_GET.getText();
    private static final String EDIT_User = XPATH.EDIT_USER_VALUE.getText();

    private static final String LOGIN_WAIT = "3000";
    private static final String FILTER_WAIT = "1500";

    String generatedName = generateUniqueName(5);
    String generatedEmployeeId = generateUniqueEmployeeId();
    String generatedEmail = generateUniqueEmail();
    String generatedPhoneNumber = generateUniquePhoneNumber();

    String generatedNameEditUser = generateUniqueName(3);
    String generatedEmployeeIdEditUser = generateUniqueEmployeeId();
    String generatedPhoneNumberEditUser = generateUniquePhoneNumber();

    private void waitFor(String milliseconds) throws InterruptedException {
        int waitTime = Integer.parseInt(milliseconds);
        Thread.sleep(waitTime);
    }
    public Soadmin(WebDriver driver) {
        this.driver = driver;
    }
    public void SearchDigiDoc() {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
        driver.get(PASSVALUE.WEBSITE_URL.getText());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    public void LogInPageCredentials() throws InterruptedException {
        driver.findElement(By.xpath(String.format(INPUT_NAME_XPATH, XPATHVALUES.EMAIL.getText()))).sendKeys(PASSVALUE.EMAIL.getText());
        driver.findElement(By.xpath(String.format(INPUT_NAME_XPATH, XPATHVALUES.PASSWORD.getText()))).sendKeys(PASSVALUE.PASSWORD.getText());
        driver.findElement(By.xpath(String.format(SELECT_TYPE_ID,XPATHVALUES.SELECT_USER.getText()))).click();
        Actions builder = new Actions(driver);
        builder.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
        waitFor(FILTER_WAIT);
        driver.findElement(By.xpath(String.format(BUTTON_XPATH,XPATHVALUES.LOGIN.getText()))).click();
        waitFor(LOGIN_WAIT);
    }
    public void AddUserDetails() throws InterruptedException {
        driver.findElement(By.xpath(String.format(SELECT_TYPE_ID, XPATHVALUES.CLICK_MANAGE_USER.getText()))).click();
        waitFor(FILTER_WAIT);
        driver.findElement(By.xpath(String.format(BUTTON_XPATH, XPATHVALUES.CLICK_ADD_USER.getText()))).click();
        waitFor(FILTER_WAIT);
        String[] fields = {XPATHVALUES.NAME.getText(),XPATHVALUES.EMPLOYEE_ID.getText(),XPATHVALUES.EMAIL.getText(),XPATHVALUES.PHONE.getText()};
        String[] values = {generatedName, generatedEmployeeId, generatedEmail, generatedPhoneNumber};
        for (int i = 0; i < fields.length; i++) {
            driver.findElement(By.xpath(String.format(INPUT_NAME_XPATH, fields[i]))).sendKeys(values[i]);
        }
    }
    public void SelectDepartmentDropdown() throws InterruptedException {
        driver.findElement(By.xpath(String.format(SELECT_TYPE_ID, XPATHVALUES.SELECT_DEPARTMENT.getText()))).click();
        Actions builder = new Actions(driver);
        builder.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
        waitFor(FILTER_WAIT);
    }
    public void SelectRoleDropdown() throws InterruptedException {
        driver.findElement(By.xpath(String.format(SELECT_TYPE_ID,XPATHVALUES.SELECT_ROLE.getText()))).click();
        Actions builder = new Actions(driver);
        builder.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
        driver.findElement(By.xpath(String.format(BUTTON_XPATH,XPATHVALUES.ADD_BUTTON.getText()))).click();
        waitFor(LOGIN_WAIT);
    }
    public void verifySuccessMessage( String expectedMessage) {
        String actualMessage = driver.findElement(By.xpath(String.format(SELECT_TYPE_ID,XPATHVALUES.VERIFY_USER_CREATED_MESSAGE.getText()))).getText();
        assertEquals("Expected message does not match!", expectedMessage, actualMessage);
    }
    public void VerifyAddUser() throws InterruptedException {
        driver.findElement(By.xpath(String.format(INPUT_NAME_XPATH,XPATHVALUES.VERIFY_USER.getText()))).sendKeys(generatedName);
        waitFor(FILTER_WAIT);
        List<String> tableData = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            tableData.add(i, (driver.findElement(By.xpath(String.format(ROW_LOCATOR,i+1))).getText()));
        }
        assertEquals("Username not match !", generatedName, tableData.get(0));
        assertEquals("EmployeeID not match !", generatedEmployeeId, tableData.get(1));
        assertEquals("Phone Number not match !", generatedPhoneNumber, tableData.get(5));
    }
    public void EditUser() throws InterruptedException {
        driver.findElement(By.xpath(EDIT_User)).click();
        String[] fields = {XPATHVALUES.NAME.getText(),XPATHVALUES.EMPLOYEE_ID.getText(),XPATHVALUES.PHONE.getText()};
        String[] values = {generatedNameEditUser, generatedEmployeeIdEditUser, generatedPhoneNumberEditUser};
        for (int i = 0; i < fields.length; i++) {
            WebElement element = driver.findElement(By.xpath(String.format(INPUT_NAME_XPATH, fields[i])));
            element.clear();
            waitFor(FILTER_WAIT);
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"), values[i]);
        }
        driver.findElement(By.xpath(String.format(BUTTON_XPATH,XPATHVALUES.EDIT_USER.getText()))).click();
        waitFor(LOGIN_WAIT);
    }
    public void verifyUpdatedMessage( String expectedMessage) {
        String actualMessage = driver.findElement(By.xpath(String.format(SELECT_TYPE_ID,XPATHVALUES.VERIFY_UPDATE_MESSAGE.getText()))).getText();
        assertEquals("Expected message does not match!", expectedMessage, actualMessage);
    }
    public void VerifyEditUser() throws InterruptedException {
        driver.findElement(By.xpath(String.format(INPUT_NAME_XPATH,XPATHVALUES.VERIFY_USER.getText()))).sendKeys(generatedNameEditUser);
        waitFor(FILTER_WAIT);
        List<String> tableData = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            tableData.add(i, (driver.findElement(By.xpath(String.format(ROW_LOCATOR,i+1))).getText()));
        }
        assertEquals("Username not match !", generatedNameEditUser, tableData.get(0));
        assertEquals("EmployeeID not match !", generatedEmployeeIdEditUser, tableData.get(1));
        assertEquals("Phone Number not match !", generatedPhoneNumberEditUser, tableData.get(5));
    }
    public void UserStatus( String expectedMessage) throws InterruptedException {
        driver.findElement(By.xpath(String.format(SELECT_TYPE_ID,XPATHVALUES.CLICK_ACTIVE_BUTTON.getText()))).click();
        waitFor(FILTER_WAIT);
        driver.findElement(By.xpath(String.format(BUTTON_XPATH,XPATHVALUES.CLICK_AGREE_BUTTON.getText()))).click();
        waitFor(LOGIN_WAIT);
        String actualMessage = driver.findElement(By.xpath(String.format(SELECT_TYPE_ID,XPATHVALUES.VERIFY_STATUS_MESSAGE.getText()))).getText();
        assertEquals("Expected message does not match!", expectedMessage, actualMessage);
    }
    public void VerifyUserStatus( String expectedMessage) throws InterruptedException {
        waitFor(FILTER_WAIT);
        List<String> tableData = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            tableData.add(i, (driver.findElement(By.xpath(String.format(ROW_LOCATOR,i+1))).getText()));
        }
        assertEquals("Status not match !", expectedMessage, tableData.get(7));
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



























//    private static final String INPUT_NAME_XPATH = "%s";

//    private static final String SEARCH_INPUT_ID = "search";
//    private static final String SEARCH_RESULT_XPATH = "//tbody/tr/td[1]";
//    private static final String MENU_XPATH = "//div[text()='%s']";
//    private static final String MENURECORD_ROWS = "//tr[@class=\"MuiTableRow-root css-1gqug66\"]";
//    private static final String FIELD_VALUE = "(//tbody[@class='MuiTableBody-root css-1xnox0e']//tr)[1]//td";
//    private static final String FIELD_VALUE1 = "(//tr[@class=\"MuiTableRow-root css-1gqug66\"])[%s]";
//    private static final String FIELD_VALUE2 = FIELD_VALUE1 + "//td[%s]";
//    private static final String HEADER_LOCATOR = "//tr[@class=\"MuiTableRow-root MuiTableRow-head css-1gqug66\"]//th[%s]";
//    private static final String ROW_LOCATOR = "//tr[@class=\"MuiTableRow-root css-1gqug66\"]";
//    private static final String SPECIFIC_ROW_LOCATORs = "(//tr[@class=\"MuiTableRow-root css-1gqug66\"]//td)";
//    private static final String SPECIFIC_ROW_LOCATOR = "(//tr[@class=\"MuiTableRow-root css-1gqug66\"]//td)[%s]";
//    private static final String SPECIFIC_ROW_LOCATOR1 = "//tr[@class=\"MuiTableRow-root css-1gqug66\"]//td[1]";




//    multiple row verify with random row and random json
//    public List<User> getUsernamesFromJson() throws IOException {
//        String jsonFilePath = "/home/devender/IdeaProjects/SeleniumDemo/src/test/java/PojoJson.json";
//        ObjectMapper mapper = new ObjectMapper();
//        Responcerapper response = mapper.readValue(new File(jsonFilePath), Responcerapper.class);
//        return response.getData();
//    }
//    public void MenuRecord() throws InterruptedException, IOException {
//        List<WebElement> rows = driver.findElements(By.xpath(MENURECORD_ROWS));
//        int randomIndex = new Random().nextInt(rows.size());
//        System.out.println("Random row index: " + randomIndex);
//        List<String> tableData = new ArrayList<>();
//        for (int i = 1, j = 0; i < 10; i++, j++) {
//            tableData.add(j, (driver.findElement(By.xpath(String.format(FIELD_VALUE2, randomIndex + 1, i))).getText()));
//            System.out.println(tableData.get(j));
//        }
//        List<User> usersFromJson = getUsernamesFromJson();
//        if (randomIndex >= 0 && randomIndex < usersFromJson.size()) {
//        User userToVerify = usersFromJson.get(randomIndex);
//        userToVerify.getUsername().equals(tableData.get(0));
//        System.out.println("Match found: " + userToVerify.getUsername());
//        userToVerify.getEmployeeId().equals(tableData.get(1));
//        System.out.println("Match found: " + userToVerify.getEmployeeId());
//        userToVerify.getEmail().equals(tableData.get(4));
//        System.out.println("Match found: " + userToVerify.getEmail());
//        userToVerify.getPhoneNo().equals(tableData.get(5));
//        System.out.println("Match found: " + userToVerify.getPhoneNo());
//        } else {
//        System.out.println("Invalid user index.");
//        }
////        driver.quit();
//    }
//}







    //method for verify multiple json table data with array
//    public List<User> getUsernamesFromJson() throws IOException {
//        String jsonFilePath = "/home/devender/IdeaProjects/SeleniumDemo/src/test/java/PojoJson.json";
//        ObjectMapper mapper = new ObjectMapper();
//        Responcerapper response = mapper.readValue(new File(jsonFilePath), Responcerapper.class);
//        return response.getData();
//    }
//    public List<User2> getUsernamesFromJson2() throws IOException {
//        String jsonFilePath = "/home/devender/IdeaProjects/SeleniumDemo/src/test/java/PojoJson.json";
//        ObjectMapper mapper = new ObjectMapper();
//        Responcerapper response = mapper.readValue(new File(jsonFilePath), Responcerapper.class);
//        return response.getDepartment();
//    }
//    public void Record() throws InterruptedException, IOException {
//        List<String> tableData = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            tableData.add(i, (driver.findElements(By.xpath(SPECIFIC_ROW_LOCATOR1)).get(i).getText()));
//            System.out.println(tableData.get(i));
//        }
//        if (!tableData.isEmpty()) {
////            // Get users from JSON
//            List<User> usersFromJson = getUsernamesFromJson();
////            // Validate each user's details
//            for (User user : usersFromJson) {
//                Assert.assertEquals("Username", user.getUsername(), tableData.get(0));
//                System.out.println("Username: " + user.getUsername() + tableData.get(0));
//            }
//            List<User2> users2FromJson = getUsernamesFromJson2();
//            for (User2 user2 : users2FromJson) {
//                Assert.assertEquals("Username", user2.getUsername(), tableData.get(1));
//                System.out.println("Username: " + user2.getUsername() + tableData.get(1));
//            }
//        }
//    }




    // Mehtod for verify all table data from json
//    public List<User> getUsernamesFromJson() throws IOException {
//        String jsonFilePath = "/home/devender/IdeaProjects/SeleniumDemo/src/test/java/PojoJson.json";
//        ObjectMapper mapper = new ObjectMapper();
//        Responcerapper response = mapper.readValue(new File(jsonFilePath), Responcerapper.class);
//        return response.getData();
//    }
//    public void Record() throws InterruptedException, IOException {
//        List<Map<String, String>> allTableData = new ArrayList<>();
//        HashMap<String, String> capitalCities = new HashMap<>();
//        for (int j = 1; j < 7; j++) {
//            String headerName = driver.findElement(By.xpath(String.format(HEADER_LOCATOR, j))).getText();
//            String cellValue = driver.findElement(By.xpath(String.format(SPECIFIC_ROW_LOCATOR, j))).getText();
//            capitalCities.put(headerName, cellValue);
//        }
//        allTableData.add(capitalCities);
//        if (!allTableData.isEmpty()) {
//            // Get users from JSON
//            List<User> usersFromJson = getUsernamesFromJson();
//            // Validate each user's details
//            for (User user : usersFromJson) {
//                Assert.assertEquals("Email", user.getEmail(), capitalCities.get("Email"));
//                System.out.println("Email: " +  user.getEmail() + capitalCities.get("Email"));
//                Assert.assertEquals("Username!", user.getUsername(), capitalCities.get("Name"));
//                System.out.println("Username: " +  user.getUsername() + capitalCities.get("Name"));
//                Assert.assertEquals("Phone number!", user.getPhoneNo(), capitalCities.get("Phone"));
//                System.out.println("Phone number: " + user.getPhoneNo()+ capitalCities.get("Phone"));
//                Assert.assertEquals("Employee Id!", user.getEmployeeId(), capitalCities.get("Employee Id"));
//                System.out.println("Employee Id: " +user.getEmployeeId() + capitalCities.get("Employee Id"));
//            }
//        }
//    }
//}



//       Mehtod for verify one data from json
//    public List<String> getUsernamesFromJson() throws IOException {
//        String jsonFilePath = "/home/devender/IdeaProjects/SeleniumDemo/src/test/java/PojoJson.json";
//        ObjectMapper mapper = new ObjectMapper();
//        Responcerapper response = mapper.readValue(new File(jsonFilePath), Responcerapper.class);
//        List<User> users = response.getData();
//        List<String> usernames = new ArrayList<>();
//        for (User user : users) {
//            usernames.add(user.getUsername());
//        }
//        return usernames;
//    }
//    public void Record() throws InterruptedException, IOException {
//        List<Map<String, String>> allTableData = new ArrayList<>();
//        HashMap<String, String> capitalCities = new HashMap<>();
//        for (int j = 1; j < 7; j++) {
//            String headerName = driver.findElement(By.xpath(String.format(HEADER_LOCATOR, j))).getText();
//            String cellValue = driver.findElement(By.xpath(String.format(SPECIFIC_ROW_LOCATOR, j))).getText();
//            capitalCities.put(headerName, cellValue);
//        }
//        allTableData.add(capitalCities);
//        if (!allTableData.isEmpty()) {
//            String usernameFromTable = capitalCities.get("Name");
//            System.out.println(usernameFromTable);
//            List<String> usernamesFromJson = getUsernamesFromJson();
//            Assert.assertTrue("Username does not match!", usernamesFromJson.contains(usernameFromTable));
//            System.out.println("Username match: " + usernamesFromJson.contains(usernameFromTable));
//        }
//        WebElement searchInput = driver.findElement(By.id(SEARCH_INPUT_ID));
//        searchInput.sendKeys(capitalCities.get("Name"));
//        Thread.sleep(3000);
//    }
//}
















//package page;
//
//import ExtentRportBase.ExtentLogger;
//import POJO.Pojo;
//import POJO.TableRow;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import java.time.Duration;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//import Enum.DropdownOption;
//
//
//public class Soadmin {
//    private WebDriver driver;
//
//    private static final String LOGIN_INPUT_NAME = "%s";
//    private static final String USER_TYPE_SELECT_ID = "mui-component-select-userType";
//    private static final String USER_TYPE_BUTTON_CSS = ".MuiButtonBase-root:nth-child(4)";
//    private static final String LOGIN_BUTTON_XPATH = "//button[contains(text(),'Login')]";
//    private static final String MANAGE_USERS_ACCOUNTS_XPATH = "//div[contains(text(),'Manage Users Accounts')]";
//    private static final String SEARCH_INPUT_ID = "search";
//    private static final String SEARCH_RESULT_XPATH = "//tbody/tr/td[1]";
//    private static final String MENU_XPATH = "//div[text()='%s']";
//    private static final String MENURECORD_ROWS = "//tr[@class=\"MuiTableRow-root css-1gqug66\"]";
//    private static final String FIELD_VALUE = "(//tbody[@class='MuiTableBody-root css-1xnox0e']//tr)[1]//td";
//    private static final String HEADER_LOCATOR = "//tr[@class=\"MuiTableRow-root MuiTableRow-head css-1gqug66\"]//th[%s]";
//    private static final String ROW_LOCATOR = "//tr[@class=\"MuiTableRow-root css-1gqug66\"]";
//    private static final String SPECIFIC_ROW_LOCATORs = "(//tr[@class=\"MuiTableRow-root css-1gqug66\"]//td)";
//    private static final String SPECIFIC_ROW_LOCATOR = "(//tr[@class=\"MuiTableRow-root css-1gqug66\"]//td)[%s]";
//
//    public Soadmin(WebDriver driver) {
//        this.driver = driver;
//    }
//
//    public void SearchDigiDoc() {
//        ChromeOptions chromeOptions = new ChromeOptions();
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver(chromeOptions);
//        driver.get("https://digidocbouat.stockholding.com/");
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        ExtentLogger.pass("Load DigiDoc Backend Login Page");
//    }
//    //    public void UserCredentials() {
////        HashMap<String, String> credentials = new HashMap<>();
////        credentials.put("email", "soadmindelhi@yopmail.com");
////        credentials.put("password", "Stockholding@123");
////
////        WebElement emailInput = driver.findElement(By.name(String.format(LOGIN_INPUT_NAME, "email")));
////        emailInput.sendKeys(credentials.get("email"));
////        ExtentLogger.pass("Enter Email:- " + credentials.get("email"));
////
////        WebElement passwordInput = driver.findElement(By.name(String.format(LOGIN_INPUT_NAME, "password")));
////        passwordInput.sendKeys(credentials.get("password"));
////        ExtentLogger.pass("Enter Password:- " + credentials.get("password"));
////    }
//    public void UserCredentials() {
//        Pojo User = new Pojo();
//        User.setEmail("soadmindelhi@yopmail.com");
//        User.setPassword("Stockholding@123");
//
//        WebElement emailInput = driver.findElement(By.name(String.format(LOGIN_INPUT_NAME, "email")));
//        emailInput.sendKeys(User.getEmail());
//        ExtentLogger.pass("Enter Email:- " + User.getEmail());
//
//        WebElement passwordInput = driver.findElement(By.name(String.format(LOGIN_INPUT_NAME, "password")));
//        passwordInput.sendKeys(User.getPassword());
//        ExtentLogger.pass("Enter Password:- " + User.getPassword());
//    }
//    public void SelectUserType() throws InterruptedException {
//        WebElement userTypeElement = driver.findElement(By.id(USER_TYPE_SELECT_ID));
//        Actions builder = new Actions(driver);
//        builder.moveToElement(userTypeElement).clickAndHold().perform();
//        driver.findElement(By.cssSelector(USER_TYPE_BUTTON_CSS)).click();
//        ExtentLogger.pass("Click on Select UserType = State Configurator");
//        Thread.sleep(3000);
//    }
//    public void ClickLogin() throws InterruptedException {
//        WebElement loginButton = driver.findElement(By.xpath(LOGIN_BUTTON_XPATH));
//        loginButton.click();
//        ExtentLogger.pass("Click on Login button");
//        Thread.sleep(3000);
//    }
//    public void VerifyMenu() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("root")));
//        for (DropdownOption dropdownOption : DropdownOption.values()) {
//            String xpath = String.format(MENU_XPATH, dropdownOption.getText());
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
//            ExtentLogger.pass("Verify Menu Icon = '" + dropdownOption.getText() + "' ");
//        }
//    }
//    private static void verifyMenuItemVisibility(WebDriverWait wait, String xpath, String menuName) {
//        WebElement menuElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
//        System.out.println(menuName + " menu displayed: " + menuElement.isDisplayed());
//    }
//    public void ManageUsersAccounts() throws InterruptedException {
//        WebElement manageUsersAccountsButton = driver.findElement(By.xpath(MANAGE_USERS_ACCOUNTS_XPATH));
//        manageUsersAccountsButton.click();
//        ExtentLogger.pass("Click on Manage Users Accounts button");
//        Thread.sleep(3000);
//    }
//    public void Record() throws InterruptedException {
//        List allTableData = new ArrayList();
//        HashMap<String, String> capitalCities = new HashMap<>();
//        for (int j = 1; j < 7; j++) {
//            String headerName = driver.findElement(By.xpath(String.format(HEADER_LOCATOR, j))).getText();
//            String cellValue = driver.findElement(By.xpath(String.format(SPECIFIC_ROW_LOCATOR, j))).getText();
//            System.out.println(cellValue);
//            capitalCities.put(headerName, cellValue);
//        }
//        allTableData.add(capitalCities);
//
//        if (!allTableData.isEmpty()) {
//            System.out.println(capitalCities.get("Name"));
//        }
//        WebElement searchInput = driver.findElement(By.id(SEARCH_INPUT_ID));
//        searchInput.sendKeys(capitalCities.get("Name"));
//        Thread.sleep(3000);
//    }
//
////    public void Record() throws InterruptedException {
////        List<TableRow> allTableData = new ArrayList<>();
////        TableRow tableRow = new TableRow();
////        for (int j = 1; j < 7; j++) {
////            String headerName = driver.findElement(By.xpath(String.format(HEADER_LOCATOR, j))).getText();
////            String cellValue = driver.findElement(By.xpath(String.format(SPECIFIC_ROW_LOCATOR, j))).getText();
////            System.out.println(cellValue);
////            switch (headerName) {
////                case "Name":
////                    tableRow.setName(cellValue);
////                    break;
////                case "Employee Id":
////                    tableRow.setEmployeeId(cellValue);
////                    break;
////            }
////        }
////        allTableData.add(tableRow);
////
////        if (!allTableData.isEmpty()) {
////            TableRow firstRow = allTableData.get(0);
////            System.out.println(firstRow.getName());
////            WebElement searchInput = driver.findElement(By.id(SEARCH_INPUT_ID));
////            searchInput.sendKeys(firstRow.getName());
////            Thread.sleep(3000);
////        }
////    }
//}
//
//
//
//
////        public void MenuRecord() throws InterruptedException {
////        List<WebElement> rows = driver.findElements(By.xpath(MENURECORD_ROWS));
////        if (rows.isEmpty()) {
////            throw new RuntimeException("No rows found.");
////        }
////        Random random = new Random();
////        int randomIndex = random.nextInt(rows.size());
////        WebElement randomRow = rows.get(randomIndex);
////        String rowData = randomRow.getText();
////        System.out.println("Selected Row Data: " + rowData);
////        ExtentLogger.pass("Here is the Random Record Fetch From Records :- " + rowData);
////        List<String> tableData = new ArrayList<>();
////        for (int i = 0; i < 9; i++) {
////            tableData.add(i, (driver.findElements(By.xpath(FIELD_VALUE)).get(i).getText()));
////            System.out.println(tableData.get(i));
////        }
////        WebElement searchInput = driver.findElement(By.id(SEARCH_INPUT_ID));
////        searchInput.sendKeys(tableData.get(0));
////        Thread.sleep(3000);
////        List<WebElement> searchResultItems = driver.findElements(By.xpath(SEARCH_RESULT_XPATH));
////        Thread.sleep(3000);
////        boolean allResultsContainName = true;
////        for (WebElement result : searchResultItems) {
////            String resultText = result.getText();
////            if (!resultText.contains(tableData.get(0))) {
////                allResultsContainName = false;
////                break;
////            }
////        }
////        if (allResultsContainName) {
////            System.out.println("All search results contain :-" + tableData.get(0));
////            ExtentLogger.pass("All search results contain :-" + tableData.get(0));
////        } else {
////            System.out.println("Not all search results contain :- " + tableData.get(0));
////            for (WebElement result : searchResultItems) {
////                System.out.println(result.getText());
////            }
////        }
////    }
////            public void verifySearchResults() throws InterruptedException {
////                HashSet<String> tableData = new HashSet<>();
////                List<WebElement> elements = driver.findElements(By.xpath(FIELD_VALUE));
////                for (WebElement element : elements) {
////                    String data = element.getText();
////                    tableData.add(data);
////                    System.out.println(data);
////                }
////                List<String> tableDataList = new ArrayList<>(tableData);
////                String valueToSearch = tableDataList.get(0);
////                WebElement searchInput = driver.findElement(By.id(SEARCH_INPUT_ID));
////                searchInput.sendKeys(valueToSearch);
////                Thread.sleep(3000);
////                System.out.println("No data found in the HashSet.");
////                }


