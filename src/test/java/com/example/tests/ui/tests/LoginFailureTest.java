package com.example.tests.ui.tests;

import com.example.tests.ui.base.BaseTest;
import com.example.tests.ui.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginFailureTest extends BaseTest {

    @Test
    public void loginFailure() {
        LoginPage page = new LoginPage(driver);
        page.open("https://the-internet.herokuapp.com/login");
        page.login("wronguser", "wrongpass");

        boolean err = page.isErrorDisplayed();

        Assert.assertTrue(
                err,
                "Expected error message for invalid credentials but none found."
                        + "\nFlash text: " + page.getFlashText()
                        + "\nFlash HTML: " + page.debugFlashHtml()
        );
    }
}
