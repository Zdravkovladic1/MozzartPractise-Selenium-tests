package tests;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.*;


public class VirtualTicket extends BaseTest {


    @BeforeMethod
    public void setup() throws Exception {
        init();
        openApp("Prod");
        prepareLandingPage();
    }

    @AfterMethod(enabled = false)
    public void tearDown() {
        quit();
    }


    @Parameters({"username", "password"})
    @Test(enabled = true)
    public void CreateVirtualSportTicket(String username, String password) throws InterruptedException{
        new LandingPage(driver).LandingNavigate(username, password);
        new VirtualBettingPage(driver).CreateVirtualTicket();

    }
}
