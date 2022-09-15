package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayersPage  {
    private WebDriver driver;
    public PlayersPage(WebDriver driver) {
        this.driver = driver;
    }
    private final By btnPlayersExpand = By.xpath("//a[@data-target='#s-menu-users']");
    private final By btnGoToListPlayers = By.xpath("(//a[@href='/user/player/admin'])[2]");
    private final By selectFilter = By.xpath("(//td[@class='hide-mobile']//select)[2]");
    private final By checkFirstResultAfterFilter = By.xpath("(//span[text()='Active'])[1]");



    public void checkingThatFilterWork(){
        WebElement checkFirstResultAfterFilterElement = driver.findElement(checkFirstResultAfterFilter);
        assertEquals("Active", checkFirstResultAfterFilterElement.getText());

    }

    public void clickAtUsersMenu (){
        WebElement btnPlayersExpandElement = driver.findElement(btnPlayersExpand);
        btnPlayersExpandElement.click();
    }

    public void clickAtUsers (){
        WebElement btnGoToListPlayersElement = driver.findElement(btnGoToListPlayers);
        btnGoToListPlayersElement.click();

    }

    public void selectFilter (){
        WebElement clickSelectElementFilter = driver.findElement(selectFilter);
        Select select = new Select(clickSelectElementFilter);
        select.selectByIndex(1);
    }

    public void checkTitleOfPage (){
        assertEquals("Dashboard - Casino", driver.getTitle());
    }

}
