package pages;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BettingPage extends BasePage {

    public BettingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".time-filters > .time-filters__body li:nth-child(3)")
    WebElement timeFilter;

    @FindBy(css = ".sports-left-menu .regular-filter-holder:nth-child(3) ul li:nth-child(2)")
    WebElement footballSection;

    @FindBy(css = ".sports-left-menu .regular-filter-holder:nth-child(3) ul>li:nth-child(2)>ul>li> .counter")
    List<WebElement> footballLeagues;

    @FindBy(css = ".match>.part2>.part2wrapper:nth-child(1)>div:nth-child(1) .odd-font")
    List<WebElement> matches;

    @FindBy(css = ".match>.part2>.part2wrapper:nth-child(1)>div:nth-child(2)")
    List<WebElement> matchesPlayers;

    @FindBy(css = "#bettingAmount")
    WebElement paymentLabel;

    @FindBy(css = "#pay-ticket")
    WebElement payTicket;

    @FindBy(css = ".actions>.fast-ticket")
    WebElement fastTicket;

    @FindBy(css = ".wrapper>.fast-message span")
    WebElement paymentCode;

    @FindBy(css = ".wrapper>.actions>.button")
    WebElement okButton;

    @FindBy(css = ".nav-top>.h100> :nth-child(1)")
    WebElement bettingElement;

    @FindBy(css = ".regular-filter-holder .all-active>div>.name")
    WebElement FootballElement;

    @FindBy(css = "#vbar-center .summary>.summary-header>.ticket-info>.match-number>span")
    WebElement PairsNumberElement;

    @FindBy(css = ".ticket-content-v1 .overlay>.ticket-confirmation .title")
    WebElement PaymentWindowElement;

    @FindBy(css = ".wrapper .fast-message")
    WebElement FastTicketTextElement;



    public void GoToBettingPage() {
        clickElement(bettingElement, "Betting section.");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.mozzartbet.com/sr#/betting");
    }

    public void ChooseTimeFilter() {
        clickElement(timeFilter, "Three days.");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.mozzartbet.com/sr#/date/three_days");
    }

    public void PickSport() {
        clickElement(footballSection, "Football section.");
        Assert.assertEquals(FootballElement.getText().toUpperCase(), "FUDBAL");
    }

    public void SecondLeagueMoreThanFour() {
        List<WebElement> leaguesWithMoreThanFour = new ArrayList<>();
        for (WebElement league :
                footballLeagues) {
            if (Integer.parseInt(league.getText()) > 4) {
                leaguesWithMoreThanFour.add(league);
            }
        }
        clickElement(leaguesWithMoreThanFour.get(1), "Football league with more than four matches.");
    }

    public void PickMatches() throws InterruptedException {
        Thread.sleep(5000);
        if (matches.isEmpty()) {
            SelectMatches(4, matchesPlayers);
        } else {
            SelectMatches(4, matches);
        }
        Assert.assertEquals(PairsNumberElement.getText(), "4");
    }

    public void InsertBettingAmount() {
        insertText(paymentLabel, "50");
        clickElement(payTicket, "Pay the ticket.");
        Assert.assertTrue(PaymentWindowElement.getText().contains("Klađenje tiket"));
    }

    public void PickPaymentMethod() {
        clickElement(fastTicket, "Fast ticket.");
        Assert.assertTrue(FastTicketTextElement.getText().contains("Brzzi tiket sa kodom:"));
    }

    public String GetCode() {
        String code = paymentCode.getText();
        return code;
    }


    public void setExcelRow(String fileName, String sheetName, String code) throws IOException {

        FileOutputStream fos = new FileOutputStream("src/test/test_data/" + fileName + ".xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);
        Cell cell0 = row.createCell(0);
        cell0.setCellValue(code);
        System.out.println("Updated code in xlsx file with code:" + code);
        workbook.write(fos);
        fos.flush();
        fos.close();
    }

    public void CloseTicket() {
        clickElement(okButton, "Ok, the ticket is paid.");
    }


    public void CreateTicket() throws IOException, InterruptedException {
        GoToBettingPage();
        ChooseTimeFilter();
        PickSport();
        SecondLeagueMoreThanFour();
        PickMatches();
        InsertBettingAmount();
        PickPaymentMethod();
        setExcelRow("CodeFile", "CodeSheet", GetCode());
        CloseTicket();
    }
}
