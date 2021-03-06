package automation.module;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import automation.VO.RecipeRawVO;
import automation.VO.RecipeVO;
import automation.manager.RecipeRawManager;
import automation.staticValue.AutoStatic;
import automation.webCrawling.HamukCrawling;
import automation.webCrawling.NaverCrawling;
import automation.webdriver.MyFirefoxDriver;

public class AutoNaverRecipeMain {
	
	private static final ArrayList<String> recipeHTMLStringArr = new ArrayList<String>();
	private static WebDriver driver;
	private static String checkUrl;
	private static String recipeHtmlSource;
	
	public static void main(String[] args) {
		AutoStatic.who("daesub");
		//AutoStatic.who("giho");
		AutoStatic.URL_STATUS="haemukja";
		operateAutomaticDataGathering();
	}
	
	public static void operateAutomaticDataGathering() {
		
		// WebDriver종류, 해당 WebDriver 저장경로 Setting.
		System.setProperty(AutoStatic.FIREFOX_DRIVER, AutoStatic.FIREFOX_PATH);
		driver = new MyFirefoxDriver();
		
		
		//RecipeRawVO recipeRaw;
		RecipeRawManager rawMngr = new RecipeRawManager();
		
		final NaverCrawling crawling = new NaverCrawling();
		// 실제 레시피로 접근... Code 시작.
		// for 문에서 i로 레시피 번호 조절. 
		// 100 개 !!!! 
		for (int i=1990007; i<1991530; i+=7) {
			//1990000 ~ 1993000
			checkUrl = "http://terms.naver.com/entry.nhn?docId=" + i;
			if(checkURLvalidation(checkUrl)) {
				
				System.out.println(i + " : Recipe Exist.");
				
				driver.get(checkUrl);
				checkPageIsReady();
				
				recipeHtmlSource = "<html>\n" + driver.findElement(By.tagName("html")).getAttribute("innerHTML") + "\n</html>";
				System.out.println(recipeHtmlSource);
				try {
					new Thread() {
						public void run(){
							RecipeVO recipeVO = crawling.crawling(recipeHtmlSource,checkUrl);
							RecipeRawVO recipeRaw = new RecipeRawVO(recipeVO.getRecipeCode() , recipeVO.getRecipeName(), recipeVO.getCompleteImage(), checkUrl, "naver");			
							rawMngr.insertRecipeRaw(recipeRaw);
						}
					}.start();
					
					// 기호야 여기부부분 채워줘... crawling 후에 리턴값은 RecipeVO 으로 해줘!!
					/*
					RecipeVO recipeVO = crawling.hamuk(recipeHtmlSource,checkUrl);
			
					recipeRaw = new RecipeRawVO(recipeVO.getRecipeCode() , recipeVO.getRecipeName(), recipeVO.getCompleteImage(), checkUrl, "ha");			
					rawMngr.insertRecipeRaw(recipeRaw);*/	
				} catch(Exception ex) {
					ex.printStackTrace();
				}
				
			} else {
				System.out.println(i + " : No Recipe.");
			}
		}
	
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
				
				if (huc.getURL().toString().equals("http://m.haemukja.com/")) {
					return false;
				} else {
					return true;
				}
			}
			
			// 노력의 흔적.........
			//고생하셨습니다 형님..............
			/*huc.setRequestMethod ("GET"); 
			huc.connect () ; 
			String urlChk = huc.getURL().toString();
			System.out.println(huc.getURL());*/
			//if (urlChk.equals(anObject))
			
			//Document doc = Jsoup.connect(url).get();
		
			//System.out.println("제목 : " + doc.title() );
			
			//String check = null;
			//Elements meta = doc.head().select("meta[property=og:description]");
			/*Elements meta = doc.select("meta[name=author]");
		    if (meta!=null) {
		    	String check = null;
		    	check = meta.attr("content");
		    	System.out.println("Content : " + check);
		    } else {
		    	System.out.println("존재하지 않음");
		    }*/
			
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