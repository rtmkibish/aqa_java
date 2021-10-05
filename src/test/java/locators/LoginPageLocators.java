package locators;

import org.openqa.selenium.By;

public class LoginPageLocators {

    public static By loginFieldLocator = By.xpath("//input[@name=\"uid\"]");
    public static By passwordFieldLocator = By.xpath("//input[@name=\"password\"]");
    public static By submitButtonLoccator = By.xpath("//input[@type=\"submit\" and @name=\"btnLogin\"]");
    public static By validLoginUserIdValueLocator = By.xpath("//ol/li[1]");
    public static By validLoginUserPasswordValueLocator = By.xpath("//ol/li[2]");
}
