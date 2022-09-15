package steps;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;
import pages.PlayersPage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CommonSteps  {



    private static WebDriver driver;
     PlayersPage playersPage = new PlayersPage(driver);

     LoginPage loginPage = new LoginPage(driver);

    @Before
    public static void startUp()  {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en-us");

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }
    @Given("I am at the login page")
    public void iAmAtTheLoginPage() throws Throwable {
        driver.navigate().to("http://test-app.d6.dev.devcaz.com/admin/login");

    }

    @When("I logging in as user {string} with password {string}")
    public void iLoggingInAsUserWithPassword(String login, String password) {
        loginPage.fillAdminCreds(login, password);
    }

    @And("I click the Sign in button")
    public void iClickTheSignInButton() {
        loginPage.pressSignInButton();
    }

    @Then("I should be in Dashboard page")
    public void iShouldBeInDashboardPage() {
        playersPage.checkTitleOfPage();
    }

    @When("I click on the Users tab in menus")
    public void iClickOnTheUsersTabInMenus() {
        playersPage.clickAtUsersMenu();
    }

    @When("I click on the Players sub-tab")
    public void iClickOnThePlayersSubTab() {
        playersPage.clickAtUsers();
    }

    @And("I select filter by Active of Players list")
    public void iSelectFilterByActiveOfPlayersList() {
        playersPage.selectFilter();
    }

    @When("I checking that filter by Active is working")
    public void iCheckingThatFilterByActiveIsWorking() {
        playersPage.checkingThatFilterWork();
    }


    @After
    public void tearDown(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File("./target/screenshots/" + scenario.getName() + " " + System.nanoTime() + ".png"));
            System.out.println("the Screenshot is taken");
        }

            driver.quit();

    }


}