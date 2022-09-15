package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPage  {
     private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    private  By loginSelector = By.id("UserLogin_username");
    private  By passwordSelector = By.id("UserLogin_password");
    private  By signInSelector = By.xpath("//div[@class=\"signin-body\"]" +
            "//input[@type=\"submit\"]");



    public void fillAdminCreds (String login, String password) {
        WebElement loginField = driver.findElement(loginSelector);
        WebElement passwordField = driver.findElement(passwordSelector);

        loginField.sendKeys(login);
        passwordField.sendKeys(password);

    }

    public void  pressSignInButton() {
        WebElement signInButton = driver.findElement(signInSelector);

        signInButton.submit();

    }

}
