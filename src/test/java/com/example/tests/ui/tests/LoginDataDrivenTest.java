package com.example.tests.ui.tests;

import com.example.tests.ui.base.BaseTest;
import com.example.tests.ui.pages.LoginPage;
import com.example.utils.CsvDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginDataDrivenTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = CsvDataProvider.class)
    public void loginDataDriven(String user, String pass, String expected) {
        LoginPage page = new LoginPage(driver);
        page.open("https://the-internet.herokuapp.com/login");
        page.login(user, pass);

        if ("success".equalsIgnoreCase(expected)) {
            boolean ok = page.isOnSecurePage();
            String flash = page.getFlashText();
            Assert.assertTrue(ok || flash.toLowerCase().contains("you logged into a secure area"),
                    "Expected successful login for user=" + user + ". URL: " + driver.getCurrentUrl() + " | flash: " + flash);
        } else {
            // expected error case
            boolean err = page.isErrorDisplayed();
            String flash = page.getFlashText();
            Assert.assertTrue(err, "Expected error for user=" + user + " but no error shown. Flash: " + flash);
        }
    }
}
