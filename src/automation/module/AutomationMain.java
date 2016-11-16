package automation.module;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automation.DTO.RecipeInfoDTO;
import automation.VO.RecipeRawVO;
import automation.VO.RecipeVO;
import automation.manager.RecipeRawManager;
import automation.staticValue.AutoStatic;
import automation.webCrawling.HamukCrawling;
import automation.webdriver.MyFirefoxDriver;

/**
 *해먹남
 * @author kwongiho
 *
 */
public class AutomationMain {
	
	private static final ArrayList<String> recipeHTMLStringArr = new ArrayList<String>();
	private static WebDriver driver;
	private static String checkUrl;
	private static String recipeHtmlSource;
	
	public static void main(String[] args) {
		
		//AutoStatic.who("daesub");
		AutoStatic.who("giho");
		AutoStatic.URL_STATUS="haemukja";
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
		//driver.manage().window().setSize(new Dimension(700, 768));
		
		checkPageIsReady();
		
		WebElement searchBox = driver.findElement(By.name("query"));
		searchBox.sendKeys("해먹남녀");
		searchBox.submit();
		
		sleep(3000);
		
		String oldTab = driver.getWindowHandle();
		WebElement clickElement = driver.findElement(By.className("type01"));
		clickElement.findElements(By.tagName("a")).get(0).click();
		
		checkPageIsReady();
		sleep(2000);
		
		// 현재 새로 열린 Page에 포커스 
	    ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
	    newTab.remove(oldTab);
	    driver.switchTo().window(newTab.get(0));
		
	    checkPageIsReady();
		
		clickElement = driver.findElement(By.className("btn_all"));
		System.out.println("btn_all : " + clickElement);
		clickElement.click();
		sleep(1000);
		clickElement = driver.findElement(By.className("btn_search"));
		clickElement.click();
		
		checkPageIsReady();
		
		clickElement = driver.findElement(By.className("call_recipe"));
		clickElement.sendKeys();
		/*
		 * 보여주기위한 용도 끝!!!!!!
		 * 
		 * */
		
		
		// 리스트에서 레시피 선택하는 부분인데 나중에 구현... 남겨둬보셈.. 
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
		
		
		RecipeRawVO recipeRaw;
		RecipeRawManager rawMngr = new RecipeRawManager();
		
		HamukCrawling crawling = new HamukCrawling();
		// 실제 레시피로 접근... Code 시작.
		// for 문에서 i로 레시피 번호 조절. 
		for (int i=642; i<3650; i++) {
			
			checkUrl = "http://haemukja.com/recipes/" + i;
			if(checkURLvalidation(checkUrl)) {
				System.out.println(i + " : Recipe Exist.");
				
				driver.get(checkUrl);
				checkPageIsReady();
				
				recipeHtmlSource = "<html>\n" + driver.findElement(By.tagName("html")).getAttribute("innerHTML") + "\n</html>";
				//recipeHTMLStringArr.add("<html>\n" + recipeHtmlSource + "\n</html>");
				try {
					RecipeVO recipeVO = crawling.hamuk(recipeHtmlSource,checkUrl);
					recipeRaw = new RecipeRawVO(recipeVO.getRecipeCode(), recipeVO.getRecipeName(), recipeVO.getCompleteImage(), checkUrl, "1");
					
					// 1114 발표
					recipeRaw = new RecipeRawVO(null, crawling.getRecipeInfo(recipeHtmlSource).getRecipeName(), crawling.getImageLink(recipeHtmlSource).get(0), checkUrl, "1");		
					rawMngr.insertRecipeRaw(recipeRaw);
					
					
				} catch(Exception ex) {
					ex.printStackTrace();
				}
				
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
