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

public class MoviesPage {

    private final WebDriver driver;
    private final Properties properties;
    private final WebDriverWait wait;


    @FindBy(xpath = "//img[@alt=\"Venom\"]")
    public WebElement clickOnAMovie;

    @FindBy(xpath = "//h1[@class=\"movie-title\"]")
    public WebElement movieTitle;

    @FindBy(xpath = "//p[@class=\"watch-time\"]")
    public WebElement watchTime;

    @FindBy(xpath = "//p[@class=\"sensor-rating\"]")
    public WebElement sensorRating;

    @FindBy(xpath = "//p[@class=\"release-year\"]")
    public WebElement releaseYear;

    @FindBy(xpath = "//p[@class=\"movie-overview\"]")
    public WebElement movieOverview;

    @FindBy(xpath = "//button[@class=\"play-button\"]")
    public WebElement playButton;


    // Constructor to initialize elements and properties
    public MoviesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        PageFactory.initElements(driver, this);

        ConfigReader configReader = new ConfigReader();
        this.properties = configReader.init_prop();
    }
    public void clickOnAMovie(){
        wait.until(ExpectedConditions.visibilityOf(clickOnAMovie));
        clickOnAMovie.click();
    }

    public String getMovieTitle(){
        wait.until(ExpectedConditions.visibilityOf(movieTitle));
        return movieTitle.getText();
    }

    public boolean isMoviePageLoaded(){
        wait.until(ExpectedConditions.visibilityOf(movieTitle));
        return movieTitle.isDisplayed();
    }

    public boolean isMovieReviewContainerDisplaying(){
        wait.until(ExpectedConditions.visibilityOf(watchTime));
        if (!(watchTime.isDisplayed()) && !(movieTitle.isDisplayed()) && !(releaseYear.isDisplayed()) && !(movieOverview.isDisplayed()) && !(playButton.isDisplayed())){
            return false;
        }
        return true;
    }
}