import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class scraperUtil {

    static WebDriver driver;

    public static List findAllLinks(WebDriver driver) {
        List<WebElement> elementList = new ArrayList();
        elementList = driver.findElements(By.tagName("a"));
        List finalList = new ArrayList();
        ;
        for (WebElement element : elementList) {
            if (element.getAttribute("href") != null) {
                finalList.add(element);

            }
        }
        return finalList;
    }

    public static String isLinkBroken(URL url) throws Exception {
        String response = "";
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.connect();
            response = connection.getResponseMessage();
            connection.disconnect();
            return response;
        } catch (Exception exp) {
            return exp.getMessage();
        }
    }


    public static void main(String args[]) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("url here");
        List<WebElement> allLinks = findAllLinks(driver);
        System.out.println("Total number of elements found " + allLinks.size());
        for (WebElement element : allLinks) {
            try {
                if (isLinkBroken(new URL(element.getAttribute("href"))).equalsIgnoreCase("ok")) {
                    System.out.println("OK");
                } else {
                    System.out.println("URL: " + element.getAttribute("href") + " returned " + isLinkBroken(new URL(element.getAttribute("href"))));
                }
            } catch (Exception exp) {
                System.out.println("At " + element.getAttribute("innerHTML") + " Exception occured -&gt; " + exp.getMessage());
            }
        }
    }
}


