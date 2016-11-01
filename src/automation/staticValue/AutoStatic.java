package automation.staticValue;

public class AutoStatic {
	
	public final static String CHROME_DRIVER = "webdriver.chrome.driver";
	public final static String FIREFOX_DRIVER = "webdriver.gecko.driver";
	
	public static String FIREFOX_PATH = "/Users/daesub/Documents/selenium/geckodriver";
	
	//public final static String DAESUB_FIREFOX = "/Users/daesub/Documents/selenium/geckodriver";
	//public final static String GIHO_FIREFOX = "/Users/kwongiho/Downloads/geckodriver";
	
	public static String MYSQL_PWD = "eo1212";
	
	public static void who(String user) {
		
		if (user.equals("daesub") || user.equals("DAESUB") || user.equals("Daesub") || user.equals("d") || user.equals("D")) {
			
			FIREFOX_PATH = "/Users/daesub/Documents/selenium/geckodriver";
			MYSQL_PWD = "eo1212";
			
		} else if (user.equals("giho") || user.equals("GIHO") || user.equals("Giho") || user.equals("g") || user.equals("G")) {
			
			FIREFOX_PATH = "/Users/kwongiho/Downloads/geckodriver";
			MYSQL_PWD = "siddid";
		}
	}

}
