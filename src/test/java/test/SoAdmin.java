package test;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import Enum.DropdownOption;
import java.io.IOException;

@Test
public class SoAdmin  extends BaseTest{
    @Test
    public void TestSoAdmin() throws InterruptedException, IOException {
        pageFactory.getsoadminPage().SearchDigiDoc();
        pageFactory.getsoadminPage().LogInPage();
        pageFactory.getsoadminPage().AddUsersDetails();
        pageFactory.getsoadminPage().DepartmentSelect();
        pageFactory.getsoadminPage().SelectRole();
        pageFactory.getsoadminPage().verifySuccessMessage(DropdownOption.VERIFY_FIRST_MESSAGE.getText());
        pageFactory.getsoadminPage().VerifyUser();
        pageFactory.getsoadminPage().EditUser();
        pageFactory.getsoadminPage().verifyUpdatedMessage(DropdownOption.VERIFY_SECOND_MESSAGE.getText());
        pageFactory.getsoadminPage().VerifyEditUser();
        pageFactory.getsoadminPage().UserStatus(DropdownOption.VERIFY_THIRD_MESSAGE.getText());
        pageFactory.getsoadminPage().VerifyUserStatus(DropdownOption.VERIFY_USER_STATUS.getText());

    }
}

























//        pageFactory.getsoadminPage().verifySuccessMessage("User created successfully!" );
//        pageFactory.getsoadminPage().getUsernamesFromJson();
//        pageFactory.getsoadminPage().MenuRecord();
//        pageFactory.getsoadminPage().verifySearchResults();
//        pageFactory.getsoadminPage().getUsernamesFromJson2();
//        pageFactory.getsoadminPage().Record();
