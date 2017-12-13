package tools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class DriverManager {
    private static DriverManager instanceDriver;
    private WebDriver driver;


    public static WebDriver getDriver() {
        if (instanceDriver == null) {
            instanceDriver = new DriverManager();
            instanceDriver.driver = new FirefoxDriver();
        }
        return instanceDriver.driver;
    }


}