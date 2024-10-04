package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.ConfigReader;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class PopularPage {
    private final WebDriver driver;
    private final Properties properties;
    private final WebDriverWait wait;


    @FindBy(xpath="//li[@class=\"movie-icon-item\"]")
    public List<WebElement> moviesList;



    // Constructor to initialize elements and properties
    public PopularPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        PageFactory.initElements(driver, this);

        ConfigReader configReader = new ConfigReader();
        this.properties = configReader.init_prop();
    }

    public boolean moviesList() {
        wait.until(ExpectedConditions.visibilityOfAllElements(moviesList));
        List<WebElement> movies = moviesList;

        for (WebElement movie : movies) {
            if (!movie.isDisplayed()) {
                return false;  // Return false if any movie is not visible.
            }
        }

        return true;  // Return true only if all movies are visible.
    }



}