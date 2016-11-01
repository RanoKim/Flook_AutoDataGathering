package automation.webCrawling;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import automation.DAO.UserDAO;
import automation.VO.CookingVO;
import automation.VO.IngredientVO;
import automation.VO.PostVO;
import automation.VO.RecipeVO;
import automation.manager.RecipeManager;
import automation.manager.UserManager;

public class MangaeRecipeCrawling {
	public static int USER_IMAGE_LINK = 0;
	public static int USER_NAME=1;
	
	public static UserDAO userDAO ;
	public static UserManager userManager;
	static {
		userDAO = UserDAO.getInstance();
		userManager = new UserManager();
	}
	public static int userNo=0;
	public void crawling(String html){
		RecipeVO recipeVO = getRecipeInfo(html);
		ArrayList<IngredientVO> ingredientList = getIngredientInfo(html);
		ArrayList<CookingVO> cookingList = getCookingList(html);
		String []userInfo = getUserInfo(html);
		
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String postDate = sd.format(gc.getTime());
		
		if( !userManager.isUserImageLink(userInfo[USER_IMAGE_LINK])) {
			int user = userDAO.insertUser(userInfo[USER_NAME] , userInfo[USER_IMAGE_LINK], "flook"+(++userNo)+"@gmail.com","0000","female",postDate);
		}
		String userCode = userDAO.getUserCodeByEmail(userInfo[USER_IMAGE_LINK]);
		
		
		PostVO postVO = new PostVO (
				null,
				null,
				userCode,
				0,
				0,
				"-",
				postDate
				);
				
		RecipeManager manager = new RecipeManager();
		manager.writeRecipe(recipeVO, postVO, cookingList, ingredientList, new String[]{"-","-"});
	}
	public String[] getUserInfo(String html){
		Document doc = Jsoup.parse(html);
		String imageLink = doc.select("div.info_chef_pic a img").attr("src");
		String userName = doc.select("div.info_chef_name").text().trim().split("레시피")[0];
		
		String [] group = new String[]{imageLink,userName};
		return group;
	}
	public ArrayList<CookingVO> getCookingList(String html) {
		Document doc = Jsoup.parse(html);
		ArrayList<CookingVO> list = new ArrayList<CookingVO>();
		int i=0;
		Elements e = doc.select("div.view_step");
		for(Element element : e) {
			Iterator<Element> description = element.getElementsByClass("media-body").iterator();
			Iterator<Element> imageLink = element.getElementsByTag("img").iterator();
			System.out.println(description.hasNext());
			System.out.println(imageLink.hasNext());
			while(description.hasNext() && imageLink.hasNext() ) {
				list.add(new CookingVO(null,null,i++,imageLink.next().attr("src"),description.next().text()));
			}
		}
		return list;
	}
	public ArrayList<IngredientVO> getIngredientInfo(String html){
		ArrayList<IngredientVO> list = new ArrayList<IngredientVO>();
		Document doc = Jsoup.parse(html);
		Elements e = doc.select("#divConfirmedMaterialArea");
		String ingredientList[] = e.text().split("]");
		/*첫번째 요리.*/
		String ingredient[] = ingredientList[1].split(" ");
		for( int i=1;i<ingredient.length-1;i+=2 ) {
			list.add(new IngredientVO(
				null,
				null,
				ingredient[i],
				0,
				"M",
				"IUC007"
			));
			/*if(i%2==1)
				System.out.print("재료 : "+ingredient[i]);
			else
				System.out.println("/ 수량 - "+ingredient[i]);*/
		}
		if(ingredientList.length>2) { //여기에 들어오면 하려는 요리가 2가지 이상.
			/*두번째 요리.*/
			String ingredient2[] = ingredientList[2].split(" ");
			for(int i=1;i<ingredient2.length-1;i+=2) {
				list.add(new IngredientVO(
						null,
						null,
						ingredient2[i],
						0,
						"M",
						"IUC007"
					));
				/*if(i%2==1)
					System.out.print("재료 : "+ingredient2[i]);
				else
					System.out.println("/ 수량 - "+ingredient2[i]);*/
			}
			
		}
		//System.out.println(e.text());
		return list;
		
	}
	/*public ArrayList<CookingVO> getCookingInfo(String html) {
		ArrayList<CookingVO> list = new ArrayList<CookingVO>();
		
	}*/
	public RecipeVO getRecipeInfo(String html){
		Document doc = Jsoup.parse(html);
		Elements ci = doc.select("div.recipe_view div.view_pic img");
		String completeImage = ci.attr("src");
		
		Elements rn = doc.select("div.recipe_view div.info_title p");
		String recipeName = rn.text();
		
		String recipeDescription = doc.select("div.view_cont div.cont_intro").text();
		
		String pn =  doc.select("div.view_info div.info_cont span.info_cont_1").text().trim();
		int personNumber = pn.indexOf(0);
		
		String ct =  doc.select("div.view_info div.info_cont span.info_cont_2").text().trim();
		String cookingTime = ct.split(":")[1].split("분")[0];
		
		String cl =  doc.select("div.view_info div.info_cont span.info_cont_3").text().trim();
		String cookingLevel = cl.split(":")[1];
		
		return new RecipeVO(null,completeImage,recipeName,recipeDescription,personNumber,cookingTime,cookingLevel);
	
	}
	public static void main(String[]args){
		try {
			Scanner scan = new Scanner(System.in);
			StringBuilder builder = new StringBuilder();
			while(scan.hasNextLine()){
				String txt = scan.nextLine();
				if(txt.trim().equals("</html>"))
					break;
				builder.append(txt+"\n");
			}
			System.out.println("---------------------");
			new MangaeRecipeCrawling().crawling(builder.toString());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("끝");
	}
}
