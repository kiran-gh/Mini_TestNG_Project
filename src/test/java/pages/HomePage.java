package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utility.ConfigReader;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Properties properties;

    @FindBy(className = "website-logo")
    public WebElement homePageLogo;

    @FindBy(xpath = "//ul[@class=\"nav-menu-list\"]")
    public List<WebElement> navBarLinks;

    @FindBy(linkText = "Popular")
    public WebElement popularLink;

    @FindBy(linkText = "Home")
    public WebElement homePageLink;

    @FindBy(xpath = "//button[@data-testid=\"searchButton\"]")
    public WebElement searchButton;

    @FindBy(xpath = "//input[@class=\"search-input-field\"]")
    public WebElement searchField;

    @FindBy(xpath = "//li[@class=\"movie-icon-item\"]")
    public List<WebElement> searchMoviesList;

    @FindBy(xpath = "//h1[@class=\"movie-title\"]")
    public WebElement movieTitle;

    @FindBy(xpath ="//li[@class=\"movie-icon-item\"]")
    public WebElement moviesList;

    @FindBy(xpath = "(//div[@class=\"slick-track\"])[1]//img")
    public List<WebElement> trendingNowMoviesList;

    @FindBy(xpath = "(//div[@class=\"slick-track\"])[1]//img")
    public List<WebElement> OriginalMoviesList;


    @FindBy(xpath = "//button[@class=\"avatar-button\"]")
    public WebElement avatarButton;

    @FindBy(xpath = "//div[@class=\"home-movie-details-container\"]")
    public WebElement homeMovieDetailsContainer;

    @FindBy(xpath = "//h1[@class=\"home-movie-heading\"]")
    public WebElement homePageHeading;

    @FindBy(xpath = "//p[@class=\"home-movie-description\"]")
    public WebElement homePageDescription;

    @FindBy(xpath = "//button[@class=\"home-movie-play-button\"]")
    public WebElement homePagePlayButton;

    @FindBy(xpath = "//h1[contains(text(),\"Trending Now\")]")
    public WebElement trendingNowHeading;

    @FindBy(xpath = "//h1[contains(text(),\"Originals\")]")
    public WebElement originalsHeading;

    @FindBy(xpath = "//button[@class=\"slick-arrow slick-prev\"]")
    public WebElement trendingNowPrevButton;

    @FindBy(xpath = "//button[@class=\"slick-arrow slick-next slick-disabled\"]")
    public WebElement trendingNowNextButton;

    @FindBy(xpath = "//button[@class=\"slick-arrow slick-prev slick-disabled\"]")
    public WebElement originalsPrevButton;

    @FindBy(xpath = "//button[@class=\"slick-arrow slick-next\"]")
    public WebElement originalsNextButton;


    public HomePage(WebDriver driver) {
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


    // Specific Element Display Check Methods
    public boolean isHomePageLogoDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(homePageLogo));
        return isElementDisplayed(homePageLogo);
    }

    public boolean isNavBarLinksDisplayed() {
        wait.until(ExpectedConditions.visibilityOfAllElements(navBarLinks)); // Wait until all navbar links are visible.
        List<WebElement> navLinks = navBarLinks; // Get the list of navbar links.
        for (WebElement navLink : navLinks) {
            if (!navLink.isDisplayed()) { // If any link is not displayed, return false.
                return false;
            }
        }
        return true; // Return true if all links are displayed.
    }

    public boolean isMoviesDisplayingUnderTrendingSection() throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> trendingMovies = trendingNowMoviesList;
        for(WebElement trendingMovie : trendingMovies){
            if (trendingMovie.isDisplayed()) {
                return true;
            }
        }
        return false;
       }

    public boolean isMoviesDisplayingUnderOriginalSection() throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> originalMovies = OriginalMoviesList;
        for(WebElement originalMovie : originalMovies){
            if (originalMovie.isDisplayed()) {
                return true;
            }
        }
        return false;
    }

    public boolean isSearchEmptyButtonDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(searchButton));
        return isElementDisplayed(searchButton);
    }

    public boolean isAvatarButtonDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(avatarButton));
        return isElementDisplayed(avatarButton);
    }

    public boolean isHomePageHeadingDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(homePageHeading));
        return isElementDisplayed(homePageHeading);
    }

    public boolean isHomePageDescriptionDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(homePageDescription));
        return isElementDisplayed(homePageDescription);
    }

    public boolean isHomePagePlayButtonDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(homePagePlayButton));
        return isElementDisplayed(homePagePlayButton);
    }

    public boolean isTrendingNowHeadingDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(trendingNowHeading));
        return isElementDisplayed(trendingNowHeading);
    }

    public boolean isOriginalsHeadingDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(originalsHeading));
        return isElementDisplayed(originalsHeading);
    }

    public String popularPageUrl(){
        wait.until(ExpectedConditions.visibilityOf(popularLink));
        popularLink.click();
        wait.until(ExpectedConditions.urlToBe(properties.getProperty("popularPageUrl")));
        return driver.getCurrentUrl();
    }

    public String accountsPageUrl(){
        wait.until(ExpectedConditions.visibilityOf(avatarButton));
        avatarButton.click();
        wait.until(ExpectedConditions.urlToBe(properties.getProperty("accountsPageUrl")));
        return driver.getCurrentUrl();
    }

    public String homePageUrl(){
        wait.until(ExpectedConditions.visibilityOf(homePageLink));
        homePageLink.click();
        wait.until(ExpectedConditions.urlToBe(properties.getProperty("homePageUrl")));
        return driver.getCurrentUrl();
    }

    public void movieSearch() {
        wait.until(ExpectedConditions.visibilityOf(searchButton));
        searchButton.click();
        wait.until(ExpectedConditions.visibilityOf(searchField));
        searchField.sendKeys(properties.getProperty("searchMovie"));
        searchButton.click();
        wait.until(ExpectedConditions.visibilityOf(moviesList));
        moviesList.click();
    }

    public void getMoviesDisplayStatus(){
        wait.until(ExpectedConditions.visibilityOf(moviesList));
        Assert.assertTrue(moviesList.isDisplayed());
    }

    public boolean isSearchMovieDisplayed(){
//        wait.until(ExpectedConditions.visibilityOfAllElements(searchMoviesList));
        List<WebElement> searchMovies = searchMoviesList;
        for(WebElement searchMovie : searchMovies){
            if(!(searchMovie.isDisplayed())){
                return false;
            }
        }
        return true;
    }

}