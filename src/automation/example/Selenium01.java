package automation.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Selenium01 {


	private static WebDriver driver;

	public static void main(String[] args)  {
		try {
			Selenium01.openFirefox();
			//Selenium01.openChrome();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void openFirefox() {
		
			System.setProperty("webdriver.gecko.driver", "/Users/daesub/Documents/selenium/geckodriver");
			
			driver = new FirefoxDriver();
			driver.get("http://52.79.152.130/graduationRepo/hello");
			//driver.get("http://www.naver.com");
			driver.manage().window().maximize();
			
			
			WebElement searchBox = driver.findElement(By.tagName("html"));
			String check = searchBox.getAttribute("innerHTML");
			if(check == null || check.equals("")) {
				System.out.println("404");
			} else {
				System.out.println(check);
			}
			driver.close();
	}
	
	public static void openChrome() {
		
		System.setProperty("webdriver.chrome.driver", "/Users/daesub/Documents/selenium/chromedriver");
		
		driver = new ChromeDriver();
		driver.get("https://www.google.com");
	}
	

	public static int getResponseCode(WebDriver driver) {
        return Integer.parseInt(driver.findElement(By.id("web_response"))
                .getAttribute("content"));
    }

	
}
