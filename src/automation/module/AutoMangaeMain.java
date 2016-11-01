package automation.module;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automation.staticValue.AutoStatic;
import automation.webdriver.MyFirefoxDriver;

public class AutoMangaeMain {
	
	private static final ArrayList<String> recipeHTMLStringArr = new ArrayList<String>();
	private static WebDriver driver;
	private static String checkUrl;
	private static String recipeHtmlSource;
	
	public static void main(String[] args) {
		
		AutoStatic.who("daesub");
		//AutoStatic.who("giho");

		operateAutomaticDataGathering();
	}
	
	public static void operateAutomaticDataGathering() {
		
		// WebDriver종류, 해당 WebDriver 저장경로 Setting.
		System.setProperty(AutoStatic.FIREFOX_DRIVER, AutoStatic.FIREFOX_PATH);
		driver = new MyFirefoxDriver();
		
		/*
		 * 네이버로 접속해서 해먹남녀로 들어가서 레시피 리스트 보여주기까지 과정.
		 * 보여주기 위한 용도니까 주석처리해도 괜찮음 !!!!
		 * 
		 * */
		driver.get("http://www.naver.com");
		driver.manage().window().maximize(); // window Size
		
		checkPageIsReady();
		
		WebElement searchBox = driver.findElement(By.name("query"));
		searchBox.sendKeys("만개의 레시피");
		searchBox.submit();
		
		sleep(3000);
		
		String oldTab = driver.getWindowHandle();
		WebElement clickElement = driver.findElement(By.className("type01"));
		clickElement.findElements(By.tagName("a")).get(0).click();
		
		checkPageIsReady();
		
		// 현재 새로 열린 Page에 포커스 
	    ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
	    newTab.remove(oldTab);
	    driver.switchTo().window(newTab.get(0));
	    
	    checkPageIsReady();
	    driver.get("http://www.10000recipe.com/recipe/list.html");
	    checkPageIsReady();
	   
	    //clickElement = driver.findElement(By.className("rcp_m_list2"));
	    //sleep(1000);
	    //clickElement.sendKeys("");
	    
	    //checkPageIsReady();
	    
		/*
		 * 보여주기위한 용도 끝!!!!!!
		 * 
		 * */
	    
	    //레시피 전체보기 마지막 페이지.
	    //http://www.10000recipe.com/recipe/list.html?order=date&page=3771
	    
	    // 레시피 전체 
	    //끝 //http://www.10000recipe.com/recipe/6859466
	    //시작 //http://www.10000recipe.com/recipe/6855677
	    
		// 실제 레시피로 접근... Code 시작.
		// for 문에서 i로 레시피 번호 조절. 
		for (int i=6855677; i<=6855690; i++) {
			
			checkUrl = "http://www.10000recipe.com/recipe/" + i;
			if(checkURLvalidation(checkUrl)) {
				System.out.println(i + " : OK.");
				
				driver.get(checkUrl);
				checkPageIsReady();
				
				recipeHtmlSource = driver.findElement(By.tagName("html")).getAttribute("innerHTML");
				recipeHTMLStringArr.add("<html>\n" + recipeHtmlSource + "\n</html>");
				
			} else {
				System.out.println(i + " : No Recipe.");
			}
		}

	    
		// HtmlStringArray 잘 저장 됐는지 출력 부분. 
		//for(String html:recipeHTMLStringArr) 
			//System.out.println(html+"\n =================================================");
		

	}
	
	public static boolean checkURLvalidation(String url) {
		try {
			
			URL u = new URL (url);
			HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
			
		 	huc.setRequestMethod ("GET"); 
			huc.connect () ; 
			sleep(10);
			
			int code = huc.getResponseCode();
			if(code == 200) {
				
				Document doc = Jsoup.connect(url).get();
				if (doc.title() == null || doc.title().equals("")) {
					return false;
				} else {
					//System.out.println("제목 : " + doc.title() );
					return true;
				}
			} else {
				System.out.println("404");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void checkPageIsReady() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Initially bellow given if condition will check ready state of page.
		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			System.out.println("Page Is loaded.");
			return;
		}

		// This loop will rotate for 25 times to check If page Is ready after
		// every 0.1 second.
		// You can replace your value with 25 If you wants to Increase or
		// decrease wait time.
		for (int i = 0; i < 25; i++) {
			try {
				Thread.sleep(100);
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
