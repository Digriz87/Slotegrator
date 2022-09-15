package factory;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class DriverFactory {

   public static WebDriver getWebDriver(String browserName){
        switch (browserName){
            case "chrome":
                File file = new File("src/main/resources/chromedriver.exe");
                System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

                return new ChromeDriver();
            case "firefox":
                return new FirefoxDriver();
            default:
                throw new RuntimeException("Incorrect BrowserName");
        }

    }
}