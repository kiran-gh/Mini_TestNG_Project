package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import utility.ConfigReader;
import org.testng.annotations.Listeners;

import java.time.Duration;
import java.util.Properties;


public class LoginPageTest  {

    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private Properties properties;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(String browser) {
        // Browser initialization logic using WebDriverManager
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().window().maximize();
        ConfigReader configReader = new ConfigReader();
        properties = configReader.init_prop();
        wait = new WebDriverWait(driver, Duration.ofSeconds(35));

        loginPage = new LoginPage(driver);

    }

    @DataProvider
    public Object[][] loginData() {
        return new Object[][]{
                {"rahul", "rahul@2022"},
                {"rahu", "rahul@2021"},
                {"", "rahul@2021"},
                {"rahul", ""},
                {"", ""}
        };
    }

    @Test(priority = 1)
    public void loginPageElementsStatus() {
        driver.get(properties.getProperty("loginPageUrl"));
        Assert.assertTrue(loginPage.isLoginPageLogoDisplayed(), "Login page logo is not displayed");
        Assert.assertTrue(loginPage.isSignInHeadingDisplayed(), "Sign-in heading is not displayed");
        Assert.assertTrue(loginPage.isUserNameLabelDisplayed(), "Username label is not displayed");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button is not displayed");
    }

    @Test(priority = 2)
    public void welcome() {
        loginPage.successfulLogin(properties.getProperty("userName"), properties.getProperty("password"));
    }

    @Test(priority = 3, dataProvider = "loginData")
    public void loginError(String username, String password) {
        String errorMessage = loginPage.loginError(username, password);
        Assert.assertFalse(errorMessage.isEmpty(), "Error message should be displayed for invalid login");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}











//package tests;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.junit.Assert;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.annotations.*;
//import pages.LoginPage;
//import utility.ConfigReader;
//
//import java.time.Duration;
//import java.util.Properties;
//
//public class LoginPageTest {
//
//    public static WebDriver driver;
//    public static WebDriverWait wait;
//    LoginPage loginPage;
//    public Properties properties;
//
//    @Parameters("browser")
//    @BeforeMethod
//    public static void setUp(String browser) {
//        // Check browser parameter and initialize the driver accordingly
//        if (browser.equalsIgnoreCase("chrome")) {
//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
//        }
//        else if (browser.equalsIgnoreCase("firefox")) {
//            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver();
//        }
//        else if (browser.equalsIgnoreCase("edge")) {
//            WebDriverManager.edgedriver().setup();
//            driver = new EdgeDriver();
//        } else {
//            throw new IllegalArgumentException("Browser type not supported");
//        }
//
//        // Maximize window and navigate to a sample URL
//        driver.manage().window().maximize();
//    }
//
//    public LoginPageTest(){
//        properties = new Properties();
//        ConfigReader configReader = new ConfigReader();
//        properties = configReader.init_prop();
//        wait = new WebDriverWait(driver, Duration.ofSeconds(35));
//        loginPage = new LoginPage(driver);
//    }
//
//    @DataProvider
//    public Object[][] loginData(){
//        return new Object[][]{
//                {"rahul","rahul@2022"},
//                {"rahu","rahul@2021"},
//                {"","rahul@2021"},
//                {"rahul",""},
//                {"",""}
//        };
//    }
//
//
//    @Test(priority = 1)
//    public void loginPageElementsStatus(){
//        loginPage = new LoginPage(driver);
//        driver.get(properties.getProperty("loginPageUrl"));
//        wait.until(ExpectedConditions.visibilityOf(loginPage.loginWebsiteLogoEle));
//        Assert.assertTrue(loginPage.isLoginPageLogoDisplayed());
//        Assert.assertTrue(loginPage.isSignInHeadingDisplayed());
//        Assert.assertTrue(loginPage.isUserNameLabelDisplayed());
//        Assert.assertTrue(loginPage.isUserNameInputDisplayed());
//        Assert.assertTrue(loginPage.isPasswordLabelDisplayed());
//        Assert.assertTrue(loginPage.isPasswordInputDisplayed());
//        Assert.assertTrue(loginPage.isLoginButtonDisplayed());
//    }
//
//    @Test(priority = 2)
//    public void welcome(){
//        loginPage = new LoginPage(driver);
//        loginPage.successfulLogin(properties.getProperty("userName"), properties.getProperty("password"));
//    }
//
//    @Test(priority = 3, dataProvider = "loginData")
//    public void loginError(String username, String password){
//        loginPage = new LoginPage(driver);
//        loginPage.loginError(username, password);
//        wait.until(ExpectedConditions.visibilityOf(loginPage.loginErrorEle));
//        Assert.assertTrue(loginPage.loginErrorEle.isDisplayed());
//    }
//
//    @AfterMethod
//    public void tearDown() {
//            driver.close();
//    }
//}