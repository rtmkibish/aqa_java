package locators;

import org.openqa.selenium.By;

public class GoogleSearchLocators {
    public static By searchFieldLocator = By.xpath("//input[@name=\"q\"]");
    public static By adBlockLinksLocator = By.xpath("//div[@aria-label=\"Ads\"]/div/div/div/div/div/span[2]"); // It's not an auto generated path. I just cannot find a better one
    public static By regularBlockLinksLocator = By.xpath("//a//cite[1]");
    public static By nextPageBtnLocator = By.xpath("//span[text()=\"Next\"]");
}
