package com.example.tests.ui.tests;

import com.example.tests.ui.base.BaseTest;
import com.example.tests.ui.pages.LoginPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginSuccessTest extends BaseTest {

    @Test
    public void loginSuccess() {
        LoginPage page = new LoginPage(driver);
        page.open("https://the-internet.herokuapp.com/login");
        page.login("tomsmith", "SuperSecretPassword!");

        boolean redirected = page.isOnSecurePage();
        String flash = page.getFlashText();

        Assert.assertTrue(
            redirected || flash.toLowerCase().contains("you logged into a secure area"),
            "Login did not succeed. URL: " + driver.getCurrentUrl() + " | flash: " + flash
        );
    }
}
