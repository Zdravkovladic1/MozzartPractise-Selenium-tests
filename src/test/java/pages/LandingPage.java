package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LandingPage extends BasePage {

    public LandingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".user-nav >.user-nav-group input[type='text']")
    WebElement UsernameField;

    @FindBy(css = ".user-nav >.user-nav-group input[type='password']")
    WebElement PasswordField;

    @FindBy(css = ".user-nav >.user-nav-group .login-btn")
    WebElement SubmitButton;

    @FindBy(css = ".user-nav-group .avatar-image")
    WebElement AvatarImageElement;


    public void Login(String username, String password){
    insertText(UsernameField, username);
    insertText(PasswordField, password);
    clickElement(SubmitButton, "Submit login.");
    WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
    webDriverWait.until(ExpectedConditions.visibilityOf(AvatarImageElement));
    webDriverWait.until(ExpectedConditions.elementToBeClickable(AvatarImageElement));
    System.out.println("Successfully logged in.");
    }

    public void LandingNavigate(String username, String password) {
        Login(username,password);
    }


}
