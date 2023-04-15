package tests;

import Selenium.DriverManager;
import Selenium.DriverManagerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    public WebDriver driver;
    DriverManager driverManager;

    public void init() throws Exception {
        driverManager = DriverManagerFactory.getDriverManager("CHROME");
        driver = driverManager.getWebDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void openApp(String env) {
        switch (env.toUpperCase()){
            case "PROD" :{
                driver.get("https://www.mozzartbet.com/");
            }
            break;
            case "QA" :{
                driver.get("https://qa.mozzartbet.com/");
            }
            break;
            case "DEV" :{
                driver.get("https://dev.mozzartbet.com/");
            }
            break;
        }

    }

    public void quit(){
        driverManager.quitWebDriver();
    }

    public void prepareLandingPage(){
        driver.findElement(By.cssSelector("#onesignal-slidedown-cancel-button")).click();
        driver.findElement(By.cssSelector(".accept-button")).click();
        driver.findElement(By.cssSelector(".landing-page > .content :nth-child(1)")).click();
    }


}
