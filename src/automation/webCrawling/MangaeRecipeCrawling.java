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

import automation.DAO.RecipeUrlDAO;
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
	public void crawling(String html,String url){
		RecipeVO recipeVO = getRecipeInfo(html);
		System.out.println(recipeVO);
		ArrayList<IngredientVO> ingredientList = getIngredientInfo(html);
		ArrayList<CookingVO> cookingList = getCookingList(html);
		String []userInfo = getUserInfo(html);
		String []recipeCategory = getRecipeCategory(html);
		
		
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
		String recipeCode = manager.writeRecipe(recipeVO, postVO, cookingList, ingredientList, recipeCategory);
		RecipeUrlDAO.getInstance().insertRecipeUrl(recipeCode, url);
		
		
		return ;
	}
	public String[] getRecipeCategory(String html){
		Document doc = Jsoup.parse(html);
		String recipeCategory = doc.select("div.info_title").text().trim();
		String recipeName = doc.select("div.info_title p").text().trim();
		String recipe[] = recipeCategory.split(recipeName);
		String recipeCategoryArr[] = recipe[0].split("·");
		String kindCategory = getKindOfCategory(recipeCategoryArr[0].trim());
		String situationCategory = getSituationCategory(recipeCategoryArr[1].trim());
		String methodCategory = getMethodCategory(recipeCategoryArr[2].trim());
		String ingredientCategory = getIngredientCategory(recipeCategoryArr[3].trim());
		String returnValue[] = new String[]{ingredientCategory,kindCategory,situationCategory,methodCategory};
	
		return returnValue;
	}
	public String getMethodCategory(String category) {
		switch(category) {
			case "끓이기" : return "MCT001";
			case "볶음": return "MCT002";
			case "부침": return "MCT003";
			case "찜": return "MCT004";
			case "튀김": return "MCT005";
			case "절이기": return "MCT006";
			case "구이": return "MCT007";
			case "조림": return "MCT008";
			case "회": return "MCT009";
			case "삶기": return "MCT010";
			case "데치기": return "MCT011";
			case "무침": return "MCT012";
			case "비빔": return "MCT013";
			case "굽기": return "MCT014";
			case "기타": return "MCT015";
		}
		return "";
	}
	public String getIngredientCategory(String category) {
		switch(category) {
			case "소고기": return "ICT001";
			case "돼지고기" : return "ICT002";
			case "닭고기": return "ICT003";
			case "육류" :return "ICT004";
			case "채소류" :return "ICT005";
			case "해물류" :return "ICT006";
			case "달걀/유제품" :return "ICT007";
			case "가공식품류" :return "ICT008";
			case "쌀" :return "ICT009";
			case "밀가루" :return "ICT010";
			case "건어물류" :return "ICT011";
			case "버섯류" :return "ICT012";
			case "과일류" :return "ICT013";
			case "콩/견과류" :return "ICT014";
			case "곡류" :return "ICT015";
			case "기타" :return "ICT016";
		}
		return "";
	}
	public String getKindOfCategory(String category) {
		switch(category) {
			case "밑반찬": return "KCT001";
			case "메인반찬": return "KCT002";
			case "국/탕": return "KCT003";
			case "찌개": return "KCT004";
			case "디저트": return "KCT005";
			case "면/만두": return "KCT006";
			case "밥/죽/떡": return "KCT007";
			case "전": return "KCT008";
			case "김치젓갈/장류": return "KCT009";
			case "양념/소스/잼": return "KCT010";
			case "양식": return "KCT011";
			case "샐러드": return "KCT012";
			case "스프": return "KCT013";
			case "빵": return "KCT014";
			case "과자": return "KCT015";
			case "차/음료/술": return "KCT016";
			case "기타": return "KCT017";
		}
		return "";
	}
	public String getSituationCategory(String category) {
		switch(category) {
			case "일상" : return "SCT001";
			case "손님접대" : return "SCT002";
			case "이유식" : return "SCT003";
			case "나들이" : return "SCT004";
			case "간식" : return "SCT005";
			case "초스피드" : return "SCT006";
			case "술안주" : return "SCT007";
			case "푸드스타일링" : return "SCT008";
			case "다이어트" : return "SCT009";
			case "영양식" : return "SCT010";
			case "명절" : return "SCT011";
			case "야식" : return "SCT012";
			case "해장" : return "SCT013";
			case "아침식사" : return "SCT014";
			case "채식" : return "SCT015";
			case "데이트" : return "SCT017";
			case "도시락" : return "SCT018";
			case "기타" : return "SCT019";
		}
		return "";
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
		
		String recipeDescription = doc.select("div.view_cont div.cont_intro").text();
		String pn =  doc.select("div.view_info div.info_cont span.info_cont_1").text().trim();
		String ct =  doc.select("div.view_info div.info_cont span.info_cont_2").text().trim();
		String cl =  doc.select("div.view_info div.info_cont span.info_cont_3").text().trim();
		int personNumber = pn.indexOf(0);
		String recipeName = "";
		recipeName=rn.text();
		
		String cookingTime = "";
		String cookingLevel = "";
		try {
			cookingTime = ct.split(":")[1].split("분")[0];
			cookingLevel = cl.split(":")[1];	
		} catch (Exception ex) {
			cookingTime = "30";
			cookingLevel = "초급";
		}
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
			//new MangaeRecipeCrawling().crawling(builder.toString());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("끝");
	}
}
