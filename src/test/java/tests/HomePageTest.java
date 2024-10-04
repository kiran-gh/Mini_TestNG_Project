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
import pages.HomePage;
import pages.LoginPage;
import utility.ConfigReader;

import java.time.Duration;
import java.util.Properties;


public class HomePageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private HomePage homePage;
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
        loginPage.successfulLogin(properties.getProperty("userName"),properties.getProperty("password"));
//        wait.until(ExpectedConditions.urlToBe("homePageUrl"));
        homePage = new HomePage(driver);
    }


    @Test(priority = 1)
    public void isHomePageElementsDisplayed() {
        Assert.assertTrue(homePage.isHomePageLogoDisplayed());
        Assert.assertTrue(homePage.isNavBarLinksDisplayed());
        Assert.assertTrue(homePage.isSearchEmptyButtonDisplayed());
        Assert.assertTrue(homePage.isAvatarButtonDisplayed());
        Assert.assertTrue(homePage.isHomePageHeadingDisplayed());
        Assert.assertTrue(homePage.isHomePageDescriptionDisplayed());
        Assert.assertTrue(homePage.isHomePagePlayButtonDisplayed());
        Assert.assertTrue(homePage.isTrendingNowHeadingDisplayed());
        Assert.assertTrue(homePage.isOriginalsHeadingDisplayed());
    }

    @Test(priority = 2)
    public void popularPageLinkCheck(){
        String ExpectedUrl = properties.getProperty("popularPageUrl");
        Assert.assertEquals(homePage.popularPageUrl(),ExpectedUrl);
    }

    @Test(priority = 3)
    public void accountsPageLinkCheck(){
        String ExpectedUrl = properties.getProperty("accountsPageUrl");
        Assert.assertEquals(homePage.accountsPageUrl(),ExpectedUrl);
    }

    @Test(priority = 4)
    public void homePageLinkCheck(){
        String ExpectedUrl = properties.getProperty("homePageUrl");
        Assert.assertEquals(homePage.homePageUrl(),ExpectedUrl);
    }

    @Test(priority = 5)
    public void searchForAMovie() throws InterruptedException {
        homePage.movieSearch();
        wait.until(ExpectedConditions.visibilityOf(homePage.movieTitle));
        String ExpectedMovieTitle= homePage.movieTitle.getText();
        Assert.assertEquals(homePage.movieTitle.getText(),ExpectedMovieTitle);
    }





    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}