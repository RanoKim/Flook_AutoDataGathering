package automation.module;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import automation.staticValue.AutoStatic;
import automation.webdriver.MyFirefoxDriver;

public class AutomationMain {
	
	private static WebDriver driver;
	
	public static void main(String[] args) {
		operateAutomaticDataGathering();
	}
	
	public static void operateAutomaticDataGathering() {
		
		//System.setProperty(AutoStatic.FIREFOX_DRIVER, AutoStatic.DAESUB_FIREFOX);
		
		//driver = new MyFirefoxDriver();
		
		/*
		driver.get("http://www.naver.com");
		driver.manage().window().maximize();
		
		checkPageIsReady();
		
		WebElement searchBox = driver.findElement(By.name("query"));
		searchBox.sendKeys("해먹남녀");
		searchBox.submit();
		
		sleep(3000);
		
		String oldTab = driver.getWindowHandle();
		WebElement clickElement = driver.findElement(By.className("type01"));
		clickElement.findElements(By.tagName("a")).get(0).click();
		
		checkPageIsReady();
		
		// 현재 새로 열린 Page에 포커
	    ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
	    newTab.remove(oldTab);
	    driver.switchTo().window(newTab.get(0));
		
	    checkPageIsReady();
		
		
		clickElement = driver.findElement(By.className("btn_all"));
		clickElement.click();
		sleep(1000);
		clickElement = driver.findElement(By.className("btn_search"));
		clickElement.click();
		
		checkPageIsReady();
		
		clickElement = driver.findElement(By.className("lst_recipe"));
		clickElement.sendKeys("");
		*/
		sleep(1000);
		
		// 리스트에서 레시피 선택하는 부분인데 나중에 구현.
		/*
		clickElement = driver.findElement(By.className("lst_recipe"));
		WebElement recipeElement = null;
		List<WebElement> webList =  clickElement.findElements(By.tagName("li"));
		for (WebElement recipeWeb : webList) {
			
			recipeWeb.findElement(By.tagName("a")).click();
			
			//wait = new WebDriverWait(driver,20);
			//recipeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn_ly_close"))); 
			//recipeElement.click();
			
			//String htmlSource = driver.findElement(By.tagName("html")).getAttribute("innerHTML");
			//System.out.println(htmlSource);
			
			sleep(4000);
			
			String htmlSource = driver.findElement(By.tagName("html")).getAttribute("innerHTML");
			System.out.println(htmlSource);
			
			driver.findElement(By.className("btn_ly_close")).click();
			sleep(2000);
		}
		*/
		
		//driver.get("http://haemukja.com/recipes/1");
		checkURLvalidation("http://haemukja.com/recipes/1");
		
		//driver.get("http://haemukja.com/recipes/667");
		checkURLvalidation("http://haemukja.com/recipes/667");
		//checkPageIsReady();
		
		//String htmlSource = driver.findElement(By.tagName("html")).getAttribute("innerHTML");
		//System.out.println(htmlSource);
		
	}
	
	public static void checkURLvalidation(String url) {
		try {
			
			URL u = new URL (url);
			
			System.out.println("path : " + u.getRef() );
		
			
			Document doc = Jsoup.connect(url).get();
			
			//HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
			
			System.out.println("제목 : " + doc.title() );
			
			
			String check = null;
		    //Elements metaOgImage = doc.head().select("meta[property=og:description]");
			Elements metaOgImage = doc.head().select("meta[name=description]");
		    if (metaOgImage!=null) {
		    	check = metaOgImage.attr("content");
		    	System.out.println(check);
		    } else {
		    	System.out.println("존재하지 않음");
		    }
		    
			
			/*String ogImage = getMetaTag(doc, "og:image") 
			
			huc.setRequestMethod ("GET"); 
			huc.connect () ; 
			int code = huc.getResponseCode() ;
			System.out.println(code);*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void checkPageIsReady() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Initially bellow given if condition will check ready state of page.
		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			System.out.println("Page Is loaded.");
			return;
		}

		// This loop will rotate for 25 times to check If page Is ready after
		// every 1 second.
		// You can replace your value with 25 If you wants to Increase or
		// decrease wait time.
		for (int i = 0; i < 25; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// To check page ready state.
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}
	}
	
	public static void sleep (int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	
}
