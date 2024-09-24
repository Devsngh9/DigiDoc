package Enum;

public enum DropdownOption {
    WEBSITE_URL("https://digidocdevadmin.infodevbox.com/"),
    EMAIL("soadmindelhi@yopmail.com"),
    PASSWORD("Stockholding@123"),
    VERIFY_FIRST_MESSAGE("User created successfully!"),
    VERIFY_SECOND_MESSAGE("User updated successfully!"),
    VERIFY_THIRD_MESSAGE("Status updated successfully!"),
    VERIFY_USER_STATUS("INACTIVE");

    private String optionText;
    DropdownOption(String optionText) {
        this.optionText = optionText;
    }
    public String getText() {
        return optionText;
    }
}









































//package Enum;
//public enum DropdownOption {
////    PIM("PIM"),
////    ADMIN("Admin"),
////    LEAVE("Leave"),
////    TIME("Time"),
////    RECRUITMENT( "Recruitment"),
////    MY_INFO("My Info"),
////    PERFORMANCE("Performance"),
////    DASHBOARD("Dashboard"),
////    MAINTENANCE("Maintenance"),
//
//    //    DASHBOARD("Dashboard"),
////   MANAGE_USERS_ACCOUNTS ("Manage Users Accounts"),
////    GENERATE_INVOICE("Generate Invoice"),
////   SETTINGS ("Settings"),
//    EMAIL("soadmindelhi@yopmail.com"),
//    PASSWORD("Stockholding@123");
//
//    private String optionText;
//    DropdownOption(String optionText) {
//        this.optionText = optionText;
//    }
//    public String getText() {
//        return optionText;
//    }
//}
