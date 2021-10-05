package content;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.bytebuddy.utility.RandomString;

import static locators.LoginPageLocators.*;

@Test
public class LoginPageTest {

    private WebDriver browser;
    private final String loginUrl = "http://demo.guru99.com/Agile_Project/Agi_V1/index.php";
    private final String homepageUrl = "http://demo.guru99.com/Agile_Project/Agi_V1/customer/Customerhomepage.php";
    private final String invalidCredentialsErrorMessage = "User or Password is not valid";

    @BeforeSuite
    public void chromeDriverSetUp() {
        // System.setProperty("webdriver.chrome.driver",
        // "/Users/akibish/Downloads/chromedriver");
        WebDriverManager.chromedriver().setup();
    }

    @AfterSuite
    public void browserTearDown() {
        System.clearProperty("webdriver.chrome.driver");
    }

    @BeforeTest
    public void chromeBrowserSetUp() {
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void clearBrowserCookies() {
        browser.manage().deleteAllCookies();
        browser.quit();
    }

    @Test
    public void shouldLoginWithValidCredentials() {
        browser.get(loginUrl);

        WebElement loginField = browser.findElement(loginFieldLocator);
        WebElement passwordField = browser.findElement(passwordFieldLocator);
        WebElement submitButton = browser.findElement(submitButtonLoccator);

        String userId = getValidUserId();
        String userPassword = getValidUserPassword();

        loginField.sendKeys(userId);
        passwordField.sendKeys(userPassword);
        submitButton.click();

        new WebDriverWait(browser, 5).until(ExpectedConditions.urlToBe(homepageUrl));

        assertEquals(browser.getTitle().trim(), "Guru99 Bank Customer HomePage");
    }

    @Test
    public void shouldFailWithInvalidUserId() {
        browser.get(loginUrl);

        WebElement loginField = browser.findElement(loginFieldLocator);
        WebElement passwordField = browser.findElement(passwordFieldLocator);
        WebElement submitButton = browser.findElement(submitButtonLoccator);

        String invalidUserId = getInvalidUserId();
        String validUserPassword = getValidUserPassword();

        loginField.sendKeys(invalidUserId);
        passwordField.sendKeys(validUserPassword);
        submitButton.click();

        Alert alert = browser.switchTo().alert();

        String errorMessage = alert.getText();
        alert.accept();

        assertEquals(errorMessage, invalidCredentialsErrorMessage);
    }

    @Test
    public void shouldFailWithInvalidUserPassword() {
        browser.get(loginUrl);

        WebElement loginField = browser.findElement(loginFieldLocator);
        WebElement passwordField = browser.findElement(passwordFieldLocator);
        WebElement submitButton = browser.findElement(submitButtonLoccator);

        String validUserId = getValidUserId();
        String invalidUserPassword = getInvalidUserPassword();

        loginField.sendKeys(validUserId);
        passwordField.sendKeys(invalidUserPassword);
        submitButton.click();

        Alert alert = browser.switchTo().alert();

        String errorMessage = alert.getText();
        alert.accept();

        assertEquals(errorMessage, invalidCredentialsErrorMessage);
    }

    @Test
    public void shouldFailWithInvalidCredentials() {
        browser.get(loginUrl);

        WebElement loginField = browser.findElement(loginFieldLocator);
        WebElement passwordField = browser.findElement(passwordFieldLocator);
        WebElement submitButton = browser.findElement(submitButtonLoccator);

        String invalidUserId = getInvalidUserId();
        String invalidUserPassword = getInvalidUserPassword();

        loginField.sendKeys(invalidUserId);
        passwordField.sendKeys(invalidUserPassword);
        submitButton.click();

        Alert alert = browser.switchTo().alert();

        String errorMessage = alert.getText();
        alert.accept();

        assertEquals(errorMessage, invalidCredentialsErrorMessage);
    }

    @Test
    public void shouldFailWithValidUserIdAndEmptyPassword() {
        browser.get(loginUrl);

        WebElement loginField = browser.findElement(loginFieldLocator);
        WebElement passwordField = browser.findElement(passwordFieldLocator);
        WebElement submitButton = browser.findElement(submitButtonLoccator);

        String validUserId = getValidUserId();
        String emptyUserPassword = "";

        loginField.sendKeys(validUserId);
        passwordField.sendKeys(emptyUserPassword);
        submitButton.click();
        submitButton.click();

        Alert alert = browser.switchTo().alert();

        String errorMessage = alert.getText();
        alert.accept();

        assertEquals(errorMessage, invalidCredentialsErrorMessage);
    }

    @Test
    public void shouldFailWithValidPasswordAndEmptyUserId() {
        browser.get(loginUrl);

        WebElement loginField = browser.findElement(loginFieldLocator);
        WebElement passwordField = browser.findElement(passwordFieldLocator);
        WebElement submitButton = browser.findElement(submitButtonLoccator);

        String emptyUserId = "";
        String validUserPassword = getValidUserPassword();

        loginField.sendKeys(emptyUserId);
        passwordField.sendKeys(validUserPassword);
        submitButton.click();

        Alert alert = browser.switchTo().alert();

        String errorMessage = alert.getText();
        alert.accept();

        assertEquals(errorMessage, invalidCredentialsErrorMessage);
    }

    @Test
    public void shouldFailWithEmptyCredentials() {
        browser.get(loginUrl);

        WebElement loginField = browser.findElement(loginFieldLocator);
        WebElement passwordField = browser.findElement(passwordFieldLocator);
        WebElement submitButton = browser.findElement(submitButtonLoccator);

        String emptyUserId = "";
        String emptyUserPassword = "";

        loginField.sendKeys(emptyUserId);
        passwordField.sendKeys(emptyUserPassword);
        submitButton.click();

        Alert alert = browser.switchTo().alert();

        String errorMessage = alert.getText();
        alert.accept();

        assertEquals(errorMessage, invalidCredentialsErrorMessage);
    }

    private String getInvalidUserId() {
        String randomId = String.valueOf((int) (Math.random() * 100000));
        return randomId;
    }

    private String getInvalidUserPassword() {
        RandomString randomzer = new RandomString(7);
        return randomzer.toString();
    }

    private String getValidUserId() {
        String validLoginSource = browser.findElement(validLoginUserIdValueLocator).getText();
        String validUserId = validLoginSource.split(" ")[2];
        return validUserId;
    }

    private String getValidUserPassword() {
        String validPasswordSource = browser.findElement(validLoginUserPasswordValueLocator).getText();
        String validUserPassword = validPasswordSource.split(" ")[2];
        return validUserPassword;
    }
}