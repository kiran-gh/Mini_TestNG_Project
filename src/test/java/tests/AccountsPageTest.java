package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AccountsPage;
import pages.HomePage;
import pages.LoginPage;
import utility.ConfigReader;

import java.time.Duration;
import java.util.Properties;

public class AccountsPageTest {

    // Class members
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private HomePage homePage;
    private AccountsPage accountsPage;
    private Properties properties;

    /**
     * Sets up the WebDriver and navigates to the Accounts Page before each test method.
     *
     * @param browser Browser type to be passed via TestNG parameters (chrome, firefox, edge).
     */
    @Parameters("browser")
    @BeforeMethod
    public void setUp(String browser) {
        // Initialize WebDriver based on the browser parameter
        driver = initializeDriver(browser);

        // Maximize the browser window
        driver.manage().window().maximize();

        // Load configuration properties (like URLs, credentials) using ConfigReader utility class
        ConfigReader configReader = new ConfigReader();
        properties = configReader.init_prop();

        // Initialize WebDriverWait for explicit waits
        wait = new WebDriverWait(driver, Duration.ofSeconds(35));

        // Perform login action
        loginPage = new LoginPage(driver);
        loginPage.successfulLogin(properties.getProperty("userName"), properties.getProperty("password"));

        // Wait for Home Page URL to confirm successful login
        wait.until(ExpectedConditions.urlContains(properties.getProperty("homePageUrl")));

        // Navigate to Accounts Page
        homePage = new HomePage(driver);
        homePage.accountsPageUrl();

        // Initialize AccountsPage object
        accountsPage = new AccountsPage(driver);
    }

    /**
     * Initializes the WebDriver for the specified browser.
     *
     * @param browser Browser type (chrome, firefox, edge)
     * @return WebDriver instance
     */
    private WebDriver initializeDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    /**
     * Verifies if all essential elements are displayed on the Accounts Page.
     */
    @Test(priority = 1, description = "Verify that all account-related elements are displayed on the Accounts Page")
    public void isAccountPageElementsDisplayed() {
        Assert.assertTrue(accountsPage.isAccountHeadingDisplayed(), "Account heading is not displayed");
        Assert.assertTrue(accountsPage.isMembershipHeadingDisplayed(), "Membership heading is not displayed");
        Assert.assertTrue(accountsPage.isMembershipUsernameDisplayed(), "Membership username is not displayed");
        Assert.assertTrue(accountsPage.isMembershipPasswordDisplayed(), "Membership password is not displayed");
        Assert.assertTrue(accountsPage.isPlanDetailsHeadingDisplayed(), "Plan details heading is not displayed");
        Assert.assertTrue(accountsPage.isPlanParagraphDisplayed(), "Plan paragraph is not displayed");
        Assert.assertTrue(accountsPage.isPlanDetailsDisplayed(), "Plan details are not displayed");
        Assert.assertTrue(accountsPage.isLogoutButtonDisplayed(), "Logout button is not displayed");
    }

    /**
     * Verifies the logout functionality on the Accounts Page.
     */
    @Test(priority = 2, description = "Test the logout functionality and ensure redirection to the login page")
    public void logoutFunctionality() {
        // Perform logout action
        accountsPage.clickLogoutButton();

        // Verify that the user is redirected to the login page
        Assert.assertEquals(driver.getCurrentUrl(), properties.getProperty("loginPageUrl"),
                "User is not redirected to the login page after logout");
    }

    /**
     * Cleans up resources after each test method, such as quitting the WebDriver instance.
     */
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Close browser and end WebDriver session
        }
    }
}

















//package tests;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//import pages.AccountsPage;
//import pages.HomePage;
//import pages.LoginPage;
//import utility.ConfigReader;
//
//import java.time.Duration;
//import java.util.Properties;
//
//public class AccountsPageTest {
//    private WebDriver driver;
//    private WebDriverWait wait;
//    private LoginPage loginPage;
//    private HomePage homePage;
//    private AccountsPage accountsPage;
//    private Properties properties;
//
//    @Parameters("browser")
//    @BeforeMethod
//    public void setUp(String browser) {
//        // Browser initialization logic using WebDriverManager
//        switch (browser.toLowerCase()) {
//            case "chrome":
//                WebDriverManager.chromedriver().setup();
//                driver = new ChromeDriver();
//                break;
//            case "firefox":
//                WebDriverManager.firefoxdriver().setup();
//                driver = new FirefoxDriver();
//                break;
//            case "edge":
//                WebDriverManager.edgedriver().setup();
//                driver = new EdgeDriver();
//                break;
//            default:
//                throw new IllegalArgumentException("Unsupported browser: " + browser);
//        }
//        driver.manage().window().maximize();
//        ConfigReader configReader = new ConfigReader();
//        properties = configReader.init_prop();
//        wait = new WebDriverWait(driver, Duration.ofSeconds(35));
//        loginPage = new LoginPage(driver);
//        loginPage.successfulLogin(properties.getProperty("userName"),properties.getProperty("password"));
////        wait.until(ExpectedConditions.urlToBe("homePageUrl"));
//        homePage = new HomePage(driver);
//        homePage.accountsPageUrl();
//        accountsPage = new AccountsPage(driver);
//
//    }
//
//
//    @Test(priority = 1)
//    public void isAccountPageElementsDisplayed() {
//        Assert.assertTrue(accountsPage.isAccountHeadingDisplayed());
//        Assert.assertTrue(accountsPage.isMembershipHeadingDisplayed());
//        Assert.assertTrue(accountsPage.isMembershipUsernameDisplayed());
//        Assert.assertTrue(accountsPage.isMembershipPasswordDisplayed());
//        Assert.assertTrue(accountsPage.isPlanDetailsHeadingDisplayed());
//        Assert.assertTrue(accountsPage.isPlanParagraphDisplayed());
//        Assert.assertTrue(accountsPage.isPlanDetailsDisplayed());
//        Assert.assertTrue(accountsPage.isLogoutButtonDisplayed());
//    }
//
//    @Test(priority = 2)
//    public void logoutFunctionality() {
//        accountsPage.logoutButton();
//        Assert.assertEquals(driver.getCurrentUrl(),properties.getProperty("loginPageUrl"));
//    }
//    @AfterMethod
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//
//}