package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class BasePage {

    WebDriver driver;
    int waitTime = 60;
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickElement(WebElement element, String log) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));

        try {
            new Actions(driver).moveToElement(element).click().perform();
            System.out.println("Clicked element: " + log);
        } catch (Exception e) {
            e.printStackTrace();
            element.click();
            System.out.println("Clicked element: " + log);
        }
    }

    public void clickElementJS(WebElement element, String log) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));

        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            System.out.println("Clicked element: " + log);
        } catch (Exception e) {
            e.printStackTrace();
            element.click();
            System.out.println("Clicked element: " + log);
        }
    }

    public void insertText(WebElement element, String text) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    public void SelectMatches(int numberOfMatches, List<WebElement> elements){
        for (int i = 0; i < numberOfMatches; i++) {
            WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
            webDriverWait.until(ExpectedConditions.visibilityOf(elements.get(i)));
            webDriverWait.until(ExpectedConditions.elementToBeClickable(elements.get(i)));
            clickElement(elements.get(i), "Pick match " + (i+1) + ".");

        }
    }

    public void scrollToElementOffset(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -60);", element);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true)",element);
    }


}
