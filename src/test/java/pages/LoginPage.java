package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.ConfigReader;

import java.time.Duration;
import java.util.Properties;

public class LoginPage {

    private final WebDriver driver;
    private final Properties properties;
    private final WebDriverWait wait;

    // Locators using PageFactory
    @FindBy(id = "usernameInput")
    public WebElement usernameInputEle;

    @FindBy(id = "passwordInput")
    public WebElement passwordInputEle;

    @FindBy(className = "login-button")
    public WebElement loginButtonEle;

    @FindBy(className = "sign-in-heading")
    public WebElement signInHeadingEle;

    @FindBy(className = "login-website-logo")
    public WebElement loginWebsiteLogoEle;

    @FindBy(xpath = "//label[contains(text(),\"USERNAME\")]")
    public WebElement usernameLabelEle;

    @FindBy(xpath = "//label[contains(text(),\"PASSWORD\")]")
    public WebElement passwordLabelEle;

    @FindBy(xpath = "//p[@class=\"error-message\"]")
    public WebElement loginErrorEle;

    // Constructor to initialize elements and properties
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        PageFactory.initElements(driver, this);

        ConfigReader configReader = new ConfigReader();
        this.properties = configReader.init_prop();
    }

    // Utility methods to interact with elements
    public boolean isElementDisplayed(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.isDisplayed();
    }

    public void successfulLogin(String username, String password) {
        driver.get(properties.getProperty("loginPageUrl"));
        wait.until(ExpectedConditions.visibilityOf(usernameInputEle));
        usernameInputEle.sendKeys(username);
        passwordInputEle.sendKeys(password);
        loginButtonEle.click();
    }

    public String loginError(String username, String password) {
        successfulLogin(username, password);
        wait.until(ExpectedConditions.visibilityOf(loginErrorEle));
        return loginErrorEle.getText();
    }

    // Specific Element Display Check Methods
    public boolean isSignInHeadingDisplayed() {
        return isElementDisplayed(signInHeadingEle);
    }

    public boolean isLoginPageLogoDisplayed() {
        return isElementDisplayed(loginWebsiteLogoEle);
    }

    public boolean isUserNameLabelDisplayed() {
        return isElementDisplayed(usernameLabelEle);
    }

    public boolean isPasswordLabelDisplayed() {
        return isElementDisplayed(passwordLabelEle);
    }

    public boolean isLoginButtonDisplayed() {
        return isElementDisplayed(loginButtonEle);
    }
}