package content;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import static locators.GoogleSearchLocators.*;

@Test
public class GooglSearchCitrusLinkTest {
    private String baseUrl = "https://google.com";
    private WebDriver browser;

    @BeforeSuite
    public void driverSetUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeTest
    public void browserSetUp() {
        browser = new ChromeDriver();
        WebDriver.Options manager = browser.manage();
        manager.timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        manager.window().maximize();
    }

    @AfterTest
    public void browserTearDown() {
        browser.quit();
    }

    @Test
    public void searchForCitrusLinkInSearchFeed() {
        browser.get(baseUrl);

        WebElement searchField = browser.findElement(searchFieldLocator);
        searchField.sendKeys("iPhone odessa buy", Keys.ENTER);

        int citrusLinkPageLocation = searchDomainOnFirstFifePages("citrus.ua");
        if (citrusLinkPageLocation != -1) {
            System.out.printf("CITRUS.UA found on %d page", citrusLinkPageLocation);
        } else {
            System.out.println("CITRUS.UA not found on first 5 pages");
        }
    }

    private int searchDomainOnFirstFifePages(String domain) {
        for (int pageNumber = 1; pageNumber <=5; pageNumber++) {
            if (isPhraseInWebLinks(adBlockLinksLocator, domain)) {
                return pageNumber;
            } else if (isPhraseInWebLinks(regularBlockLinksLocator, domain)) {
                return pageNumber;
            }
            goToNextPage();
        }
        return -1;
    }

    private void goToNextPage() {
        WebElement nextPageButton = browser.findElement(nextPageBtnLocator);
        nextPageButton.click();
    }

    private boolean isPhraseInWebLinks(By locator, String phrase) {
        List<WebElement> links = browser.findElements(locator);
        for (WebElement link : links) {
            if (link.getText().contains(phrase)) {
                return true;
            }
        }
        return false;
    }
}
