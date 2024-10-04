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
import pages.PopularPage;
import utility.ConfigReader;

import java.time.Duration;
import java.util.Properties;

public class PopularPageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private PopularPage popularPage;
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
        HomePage homePage = new HomePage(driver);
        homePage.popularPageUrl();
        popularPage = new PopularPage(driver);
    }


    @Test(priority = 1)
    public void moviesDisplayStatus(){
        Assert.assertTrue(popularPage.moviesList());
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}