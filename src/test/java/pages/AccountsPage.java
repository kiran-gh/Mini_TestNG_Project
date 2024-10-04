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

/**
 * This class represents the Accounts page of the application, providing methods
 * to interact with and verify different elements on the page.
 */
public class AccountsPage {

    private final WebDriver driver;
    private final Properties properties;
    private final WebDriverWait wait;

    // Locators for different elements on the Accounts page
    @FindBy(xpath = "//h1[@class='account-heading']")
    private WebElement accountHeading;

    @FindBy(xpath = "//p[contains(text(),'Member ship')]")
    private WebElement membershipHeading;

    @FindBy(xpath = "//p[@class='membership-username']")
    private WebElement membershipUsername;

    @FindBy(xpath = "//p[@class='membership-password']")
    private WebElement membershipPassword;

    @FindBy(xpath = "//p[contains(text(),'Plan details')]")
    private WebElement planDetailsHeading;

    @FindBy(xpath = "//p[@class='plan-paragraph']")
    private WebElement planParagraph;

    @FindBy(xpath = "//p[@class='plan-details']")
    private WebElement planDetails;

    @FindBy(xpath = "//button[@class='logout-button']")
    private WebElement logoutButton;

    /**
     * Constructor for the AccountsPage class. Initializes the WebDriver, WebDriverWait,
     * and loads configuration properties.
     *
     * @param driver WebDriver instance used to interact with the page.
     */
    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(35));  // Explicit wait for element visibility
        PageFactory.initElements(driver, this); // Initialize web elements

        // Load configuration properties
        ConfigReader configReader = new ConfigReader();
        this.properties = configReader.init_prop();
    }

    /**
     * Waits for a web element to be visible and checks if it is displayed.
     *
     * @param element WebElement to be checked.
     * @return true if the element is visible; false otherwise.
     */
    public boolean isElementDisplayed(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element)); // Wait for the element to be visible
            return element.isDisplayed();
        } catch (Exception e) {
            System.err.println("Element not displayed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifies if the account heading is displayed.
     *
     * @return true if the account heading is visible; false otherwise.
     */
    public boolean isAccountHeadingDisplayed() {
        return isElementDisplayed(accountHeading);
    }

    /**
     * Verifies if the membership heading is displayed.
     *
     * @return true if the membership heading is visible; false otherwise.
     */
    public boolean isMembershipHeadingDisplayed() {
        return isElementDisplayed(membershipHeading);
    }

    /**
     * Verifies if the membership username is displayed.
     *
     * @return true if the membership username is visible; false otherwise.
     */
    public boolean isMembershipUsernameDisplayed() {
        return isElementDisplayed(membershipUsername);
    }

    /**
     * Verifies if the membership password is displayed.
     *
     * @return true if the membership password is visible; false otherwise.
     */
    public boolean isMembershipPasswordDisplayed() {
        return isElementDisplayed(membershipPassword);
    }

    /**
     * Verifies if the plan details heading is displayed.
     *
     * @return true if the plan details heading is visible; false otherwise.
     */
    public boolean isPlanDetailsHeadingDisplayed() {
        return isElementDisplayed(planDetailsHeading);
    }

    /**
     * Verifies if the plan paragraph is displayed.
     *
     * @return true if the plan paragraph is visible; false otherwise.
     */
    public boolean isPlanParagraphDisplayed() {
        return isElementDisplayed(planParagraph);
    }

    /**
     * Verifies if the plan details section is displayed.
     *
     * @return true if the plan details are visible; false otherwise.
     */
    public boolean isPlanDetailsDisplayed() {
        return isElementDisplayed(planDetails);
    }

    /**
     * Verifies if the logout button is displayed.
     *
     * @return true if the logout button is visible; false otherwise.
     */
    public boolean isLogoutButtonDisplayed() {
        return isElementDisplayed(logoutButton);
    }

    /**
     * Clicks the logout button and returns the current URL after logging out.
     *
     * @return The URL of the current page after logging out.
     */
    public String clickLogoutButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(logoutButton)); // Wait for the button to be clickable
            logoutButton.click();
            return driver.getCurrentUrl();
        } catch (Exception e) {
            System.err.println("Failed to click the logout button: " + e.getMessage());
            return null;
        }
    }
}