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
import pages.MoviesPage;
import utility.ConfigReader;

import java.time.Duration;
import java.util.Properties;

public class MoviesPageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private HomePage homePage;
    private AccountsPage accountsPage;
    private MoviesPage moviesPage;
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
        homePage = new HomePage(driver);
        homePage.popularPageUrl();
        moviesPage = new MoviesPage(driver);
    }

    @Test(priority = 1)
    public void moviesPageCheck() {
        moviesPage.clickOnAMovie();
        Assert.assertTrue(moviesPage.isMoviePageLoaded());
    }

    @Test(priority = 2)
    public void movieTitleCheck(){
        moviesPage.clickOnAMovie();
        wait.until(ExpectedConditions.visibilityOf(moviesPage.movieTitle));
        Assert.assertTrue(moviesPage.movieTitle.isDisplayed());
    }

    @Test(priority = 3)
    public void reviewSectionCheck(){
        moviesPage.clickOnAMovie();
        wait.until(ExpectedConditions.visibilityOf(moviesPage.watchTime));
        Assert.assertTrue(moviesPage.watchTime.isDisplayed());
        Assert.assertTrue(moviesPage.sensorRating.isDisplayed());
        Assert.assertTrue(moviesPage.releaseYear.isDisplayed());
    }

    @Test(priority = 4)
    public void overViewSectionCheck(){
        moviesPage.clickOnAMovie();
        wait.until(ExpectedConditions.visibilityOf(moviesPage.movieOverview));
        Assert.assertTrue(moviesPage.movieOverview.isDisplayed());
    }

    @Test(priority = 5)
    public void playButtonCheck(){
        moviesPage.clickOnAMovie();
        wait.until(ExpectedConditions.visibilityOf(moviesPage.playButton));
        Assert.assertTrue(moviesPage.playButton.isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}