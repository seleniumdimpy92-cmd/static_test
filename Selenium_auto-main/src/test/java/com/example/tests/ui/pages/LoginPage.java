package com.example.tests.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginBtn = By.cssSelector("button[type='submit']");
    private final By flash = By.id("flash"); // demo site uses id "flash"

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        // 10s default wait; adjust if your app is slower in CI
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open(String url) {
        driver.get(url);
    }

    /**
     * Robust login: waits for fields, types, clicks, and waits either for redirect or flash message.
     */
    public void login(String user, String pass) {
        WebElement u = wait.until(ExpectedConditions.elementToBeClickable(username));
        u.clear();
        u.sendKeys(user);

        WebElement p = wait.until(ExpectedConditions.elementToBeClickable(password));
        p.clear();
        p.sendKeys(pass);

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
        btn.click();

        // Wait for either success redirect or flash message (whichever appears first)
        try {
            wait.withTimeout(Duration.ofSeconds(8)).until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/secure"),
                ExpectedConditions.visibilityOfElementLocated(flash)
            ));
        } catch (Exception ignored) {
            // we'll let callers assert the final condition
        } finally {
            // restore default wait timeout for subsequent calls
            wait.withTimeout(Duration.ofSeconds(10));
        }
    }

    /**
     * Returns visible flash text (if present), or empty string.
     */
    public String getFlashText() {
        try {
            WebElement f = wait.until(ExpectedConditions.visibilityOfElementLocated(flash));
            return f.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * True if we are on the secure page (successful login).
     */
    public boolean isOnSecurePage() {
        try {
            // small tolerance for redirect
            return wait.until(driver -> driver.getCurrentUrl().contains("/secure"));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * True if an error flash is visible (invalid credentials).
     */
    public boolean isErrorDisplayed() {
        try {
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));

            WebElement f = longWait.until(ExpectedConditions.presenceOfElementLocated(flash));
            String txt = f.getText().trim().toLowerCase();

            return txt.contains("invalid")
                    || txt.contains("is invalid")
                    || txt.contains("your password")
                    || txt.contains("your username");
        } catch (Exception ex) {
            return false;
        }
    }
    public String debugFlashHtml() {
        try {
            return driver.findElement(flash).getAttribute("outerHTML");
        } catch (Exception e) {
            return "<flash not found>";
        }
}


}
