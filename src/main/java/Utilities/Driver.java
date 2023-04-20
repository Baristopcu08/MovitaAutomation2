package Utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static Utilities.DriverFactory.*;

public class Driver {

    public static ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> waits = new ThreadLocal<>();

    public static WebDriver getDriver(){
        return getDriver(Browsers.CHROME);
    }
    public static WebDriver getDriver(Browsers browser){
        if (drivers.get() == null){
            switch (browser){
                case FIREFOX:
                    drivers.set(createFirefox());
                    break;
                case EDGE:
                    drivers.set(createEdge());
                    break;
                case IE:
                    drivers.set(createIe());
                    break;
                case SAFARI:
                    drivers.set(createSafari());
                    break;
                default:
                    drivers.set(createChrome());
                    break;
            }
        }
        waits.set(new WebDriverWait(drivers.get(), Duration.ofSeconds(10)));
        return drivers.get();
    }

    public static WebDriverWait getWait(){
        return waits.get();
    }

    public static void quitDriver(){
        if (drivers.get() != null) {
            drivers.get().quit();
            drivers.set(null);
            waits.set(null);
        }
    }
}
