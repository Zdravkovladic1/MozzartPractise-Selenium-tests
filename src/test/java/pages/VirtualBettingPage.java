package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class VirtualBettingPage extends BasePage {

    public VirtualBettingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".nav-group>a[href='/sr#/inspired-virtuals']")
    WebElement VirtualsElement;

    @FindBy(css = ".nav-bottom>.nav-group:nth-child(2) >a:nth-child(3)")
    WebElement VirtualFootballElement;

    @FindBy(css = "#matchdayPickerLeagueView>.nextMatchday")
    WebElement NextDayElement;

    @FindBy(css = ".subgames>.title")
    WebElement title;

    @FindBy(css = "#focus .matches .vl-match-row_right>.odds-holder:nth-child(2) span:nth-child(1)")
    List<WebElement> FirstHalfMatches;

    List<WebElement> FirstHalfMatches2 = driver.findElements(By.id("#focus .matches .vl-match-row_right>.odds-holder:nth-child(2) span:nth-child(1)"));

    @FindBy(css = ".matches .vl-match-row_right>.more-odds")
    List<WebElement> MoreOdds;

    @FindBy(xpath = "//div[text()='2:0']")
    WebElement ResultElement;

    @FindBy(css = ".top-payment >.amount")
    WebElement amountElement;

    @FindBy(css = ".payin-button-holder >.payin-button")
    WebElement PayingButton;

    @FindBy(xpath = "//*[@class='summary-header']/*[@class='amount-error'][2]")
    WebElement ErrorMessageSmall;

    @FindBy(xpath = "//*[@class='summary-header']/*[@class='amount-error'][1]")
    WebElement ErrorMessageOver;

    @FindBy(css = ".confirmation-footer .has-help-button>.message")
    WebElement ErrorMessageNoMoney;

    @FindBy(css = ".actions>.button")
    WebElement ConfirmationButton;

    @FindBy(css = ".header-left >.virtual-ticket-count")
    WebElement VirtualTicketCountElement;



    public void OpenVirtualPage() {
        clickElement(VirtualsElement, "Virtual page link from nav bar.");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.mozzartbet.com/sr#/inspired-virtuals");
    }

    public void PickVirtualFootbalLink() throws InterruptedException {
        Boolean staleElement = true;
        while (staleElement) {
            try {
                clickElementJS(VirtualFootballElement, "Virtuelni fudbal.");
                staleElement = false;
            } catch (StaleElementReferenceException e) {
                staleElement = true;
            }
        }
        WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
        webDriverWait.until(ExpectedConditions.urlToBe("https://www.mozzartbet.com/sr#/vfl"));
    }

    public void PickNextDay() throws InterruptedException {
        Thread.sleep(1000);
        driver.switchTo().frame(0);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", NextDayElement);
        clickElementJS(NextDayElement, "Next day.");
        driver.switchTo().defaultContent();
    }

    public void SelectGames(){
        SelectMatches(3, FirstHalfMatches);
        Assert.assertEquals(VirtualTicketCountElement.getText(), "3");
    }

    public void MoreOddsFourthGame() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", MoreOdds.get(4));
        clickElement(MoreOdds.get(4), "More odds.");
    }

    public void FullResult() {
        clickElement(ResultElement, "Pick result 2-0.");
        Assert.assertEquals(VirtualTicketCountElement.getText(), "4");
    }

    public void InsertAmount(String amount) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", amountElement);
        insertText(amountElement, amount);

        if (Integer.parseInt(amount) < 10) {
            Assert.assertEquals(ErrorMessageSmall.getText(), "Minimalni ulog na virtuelni tiket je 10,00 RSD");
            System.out.println(ErrorMessageSmall.getText());
        } else if (Integer.parseInt(amount) > 20000) {
            System.out.println(ErrorMessageOver.getText());
            Assert.assertEquals(ErrorMessageOver.getText(), "Maksimalni ulog na virtuelni tiket je 20.000,00 RSD");
        } else {
            clickElement(PayingButton, "Uplati.");
            WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
            webDriverWait.until(ExpectedConditions.visibilityOf(ErrorMessageNoMoney));
            System.out.println(ErrorMessageNoMoney.getText());
            Assert.assertEquals(ErrorMessageNoMoney.getText(), "Na vaÅ¡em raÄ\u008Dunu nema dovoljno novca.");
        }
    }


    public void CreateVirtualTicket() throws InterruptedException {
        OpenVirtualPage();
        PickVirtualFootbalLink();
        PickNextDay();
        SelectGames();
        MoreOddsFourthGame();
        FullResult();
        InsertAmount("5");
        InsertAmount("21000");
        InsertAmount("15");
        clickElement(ConfirmationButton, "U redu.");
    }

}
