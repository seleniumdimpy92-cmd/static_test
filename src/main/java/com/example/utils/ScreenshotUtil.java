package com.example.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class ScreenshotUtil {
    public static String capture(WebDriver driver, String name) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destDir = Paths.get("reports", "screenshots");
            Files.createDirectories(destDir);
            Path dest = destDir.resolve(name + ".png");
            Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
            return dest.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
