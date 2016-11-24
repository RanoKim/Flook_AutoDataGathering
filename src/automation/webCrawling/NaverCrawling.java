package automation.webCrawling;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import automation.DAO.RecipeUrlDAO;
import automation.VO.CookingVO;
import automation.VO.IngredientVO;
import automation.VO.PostVO;
import automation.VO.RecipeVO;
import automation.manager.RecipeManager;

public class NaverCrawling {
	public static String userCode = "UT1479473135646";
	public RecipeVO crawling(String html,String url){
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String postDate = sd.format(gc.getTime());
		RecipeVO recipeVO =new RecipeVO();
		recipeVO.setCompleteImage(getImageUrl(html));
		recipeVO.setRecipeName(getRecipeName(html));
		recipeVO.setRecipeDescription(getDescription(html));
		recipeVO.setPersonNumber(1);
		recipeVO.setCookingTime("30");
		recipeVO.setCookingLevel("초급");
		recipeVO.setRecipeCode(codeGenerator2());
		
		String recipeCode = recipeVO.getRecipeCode();
		
		PostVO postVO = new PostVO(recipeCode,null,userCode,0,0,"-",postDate);
		
		ArrayList<CookingVO> clist = new ArrayList<CookingVO>();
		clist.add(new CookingVO(recipeCode,null,1,recipeVO.getCompleteImage(),getCookingList(html)));
		ArrayList<String> ingreList= getIngredientList(html);//
		ArrayList<IngredientVO> ingrevoList = new ArrayList<IngredientVO>();
		for(int i=0;i<ingreList.size();i++) {
			ingrevoList.add(new IngredientVO(
					recipeCode,
					null,
					ingreList.get(i),
					0,
					"M",
					"IUC007"
					));
		}
		RecipeManager manager = new RecipeManager();
		manager.writeRecipe(recipeVO, postVO, clist, ingrevoList, new String[]{"-","-"});
		RecipeUrlDAO.getInstance().insertRecipeUrl(recipeCode, url);
		return recipeVO;
	}
	public String getRecipeName(String html) {
		Document doc = Jsoup.parse(html);
		String title = doc.select("div.h_group h2").text();
		return title;
		
	}
	public String getImageUrl(String html ) {
		Document doc = Jsoup.parse(html);
		String url = doc.select("img#innerImage0").attr("src");
		return url;
	}
	public String getDescription(String html) {
		//Document doc = Jsoup.parse(html);
		return "농촌진흥청 건강식단에서 제공하는 우리가 매일 식탁에서 만나는 다양한 음식들의 영양정보와 간단한 요리법입니다.";
		//String description = doc.select("p.txt").text().;
		//return description;
	}
	public ArrayList<String> getIngredientList(String html) {
		Document doc = Jsoup.parse(html);
		String ingredient = doc.select("p.txt").text().split(":")[1].split("조리시간")[0];
		ArrayList<String> list = new ArrayList<String>(); 
		String[] ingredientArray =  ingredient.split(",");
		for(int i=0;;i++) {
			try {
				list.add(ingredientArray[i]);
			} catch(Exception ex) {
				break;
			}
		}
		return list;
		
	}
	public String getCookingList(String html) {
		Document doc = Jsoup.parse(html);
		ArrayList<String> list = new ArrayList<String>();
		String ingredient = doc.select("p.txt").text().split("칼로리")[1].split("인분")[1].substring(2).split("영양성분")[0];
		return ingredient;
	}
	public String codeGenerator2(){
		return "RC"+System.currentTimeMillis();
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
			new NaverCrawling().crawling(builder.toString(),"-");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("끝");
	}
}
