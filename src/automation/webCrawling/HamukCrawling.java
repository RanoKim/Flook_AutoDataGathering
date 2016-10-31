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
import automation.DTO.CommentDTO;
import automation.DTO.IngredientDTO;
import automation.DTO.RecipeInfoDTO;
import automation.VO.CookingVO;
import automation.VO.IngredientVO;
import automation.VO.PostVO;
import automation.VO.RecipeVO;
import automation.manager.RecipeManager;
import automation.manager.UserManager;

public class HamukCrawling {
	private UserDAO userDAO = UserDAO.getInstance();
	UserManager userManager = new UserManager();
	public static int userNo = 1;
	
	public HamukCrawling(){
		
	}
	public void hamuk(String html) throws Exception {
		//설명리스
		ArrayList<String> descriptionList = getDescription(html);
		//레시피 이미지리스
		ArrayList<String> imageList = getImageLink(html);
		//레시피설
		RecipeInfoDTO recipeInfoDTO = getRecipeInfo(html);
		//기준인
		String standardPerson = getRecipePersonStandard(html);
		//재료리스
		ArrayList<IngredientDTO> ingredientList = getIngredientList(html);
		//코멘트리스
		ArrayList<CommentDTO> commentList = getCommentDTO(html);
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String postDate = sd.format(gc.getTime());
		//가져오기 
		
		if( !userManager.isUserImageLink(recipeInfoDTO.getUserImageLink()) ) {
			int user = userDAO.insertUser(recipeInfoDTO.getUserName(), recipeInfoDTO.getUserImageLink(), "flook"+(++userNo)+"@naver.com", "0000", "female", postDate);
			System.out.println("code - "+user);
		}
		String userCode = userDAO.getUserCode(recipeInfoDTO.getUserName());
		
		
		//recipeVO 이게 젤 중요하지..ㅅ
		RecipeVO recipeVO = new RecipeVO(
				null, // recipeCode
				imageList.get(0), // completeImage
				recipeInfoDTO.getRecipeName(), // recipeName
				" ", // recipeDescription
				Integer.parseInt(standardPerson.substring(0, 1)),
				recipeInfoDTO.getCookingTime(),
				"-"); // personNum
		
		// postVO 를 만들어야 해..
		PostVO postVO = new PostVO(null, null, userCode, 0, 0, "-", postDate);
		
		//cookingList를 만들어야 해..
		ArrayList<CookingVO> cookingList = new ArrayList<CookingVO>();
		for(int i=0;i<imageList.size();i++)
			cookingList.add( new CookingVO(null, null, i, imageList.get(i), descriptionList.get(i)) );
		
		ArrayList<IngredientVO> ingredientVOList = new ArrayList<IngredientVO>();
		for(int i=0;i<ingredientList.size();i++ ){
			ingredientVOList.add(new IngredientVO(
						null,
						null,
						ingredientList.get(i).getIngredientName(),
						getValue(ingredientList.get(i).getIngredientVolume()),
						"M",
						"IUC007"
					));
		}
		RecipeManager manager = new RecipeManager();
		manager.writeRecipe(recipeVO, postVO, cookingList, ingredientVOList, new String[]{"-","-"});
		
		//IngredientVO ingredientVO = new IngredientVO(null, null,mainIngredient[i], amount, "M", mainIngredientUnit[i]);
		
		/*
		System.out.println(recipeInfoDTO);
		//데이터 잘 들어왔나 확코
		for(int i=0;i<descriptionList.size();i++) 
			System.out.println(descriptionList.get(i));
		for(int i=0;i<imageList.size();i++)
			System.out.println(imageList.get(i));
		//기준인
		System.out.println(standardPerson);
		//재료리스
		for(int i=0;i<ingredientList.size();i++)
			System.out.println(ingredientList.get(i));
		//댓글리스
		for(int i=0;i<commentList.size();i++)
			System.out.println(commentList.get(i));
*/
		
	} 
	public int getValue(String value) {
		String val[]=value.split(" ");
		if(val[0].split("/").length> 1 ) {
			return 1;
		}
		return Integer.parseInt(val[0]);
		
	}
	public ArrayList<CommentDTO> getCommentDTO(String html){
		Document doc = Jsoup.parse(html);
		ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
		Elements elements = doc.select("section.sec_comment ul.lst_comment");
		for(Element e :elements) {
			Iterator<Element> userImage = e.getElementsByClass("img_user").iterator();
			Iterator<Element> userName = e.getElementsByTag("strong").iterator();
			Iterator<Element> userDate = e.getElementsByTag("time").iterator();
			Iterator<Element> comment = e.getElementsByTag("p").iterator();
			while(userImage.hasNext() & userName.hasNext() & userDate.hasNext() & comment.hasNext() ) {
				list.add(new CommentDTO(
						userImage.next().attr("src"),
						userName.next().text(),
						userDate.next().text(),
						comment.next().text()
						));
			}
				
		}
		return list;
		
	}
	public RecipeInfoDTO getRecipeInfo(String html) {
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("div.aside div.top");
		String userImageLink = doc.select("div.aside div.top div.img-cover img").attr("src");
		String userName = doc.select("div.aside div.top div.user strong a").text();
		String recipeName = doc.select("div.aside div.top h1").text();//info_basic
		String cookingTime = doc.select("div.aside div.top dl.info_basic dd").text();//info_basic
		return new RecipeInfoDTO(
				userImageLink,
				userName,
				recipeName,
				cookingTime.split(" ")[0]
				);
				
	}
	
	/**
	 * 기준인원 return해주는 메소드.
	 * @param html
	 * @return
	 */
	public String getRecipePersonStandard(String html) {
		Document doc = Jsoup.parse(html);
		String recipePerson = doc.select("div.btm div.dropdown").text();
		return recipePerson;
	}
	/**
	 * 재료 리스트 목록을 return 해주는 메소드.
	 * @param html
	 * @return
	 */
	public ArrayList<IngredientDTO> getIngredientList(String html) {
		Document doc = Jsoup.parse(html);
		ArrayList<IngredientDTO> list = new ArrayList<IngredientDTO>();
		Elements elements = doc.select("div.btm ul.lst_ingrd");
		for(Element e :elements) {
			Iterator<Element> ingredientName = e.getElementsByTag("span").iterator();
			Iterator<Element> ingredientVolume = e.getElementsByTag("em").iterator();
			while(ingredientName.hasNext() && ingredientVolume.hasNext())  {
				System.out.println("ingredientName - "+ingredientName.next().text()+"/ ingredientVolum - "+ingredientVolume.next().text());
				list.add(new IngredientDTO(ingredientName.next().text() , ingredientVolume.next().text() ) );
				System.out.println("ingredient add");
			}
		}
		return list;
	}
	/**
	 * JSoup에 의존적인 메소드.
	 * 단위테스트가 완료된 코드.
	 * @param elements
	 * @return Description List
	 */
	public ArrayList<String> getDescription(Elements elements) {
		ArrayList<String> list = new ArrayList<String>();
		for(Element e :elements) {
			Iterator<Element> description = e.getElementsByTag("p").iterator();
			while(description.hasNext()) 
				list.add(description.next().text());
		}
		return list;
	}
	/**
	 * html코드를 받아와서 Description의 List를 return해주는 메소드.
	 * 단위테스트가 완료된 코드.
	 * @author kwongiho
	 * @param html
	 * @return Description List
	 */
	public ArrayList<String> getDescription(String html) {
		ArrayList<String> list = new ArrayList<String>();
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select(".lst_step");
		for(Element e :elements) {
			Iterator<Element> description = e.getElementsByTag("p").iterator();
			while(description.hasNext()) 
				list.add(description.next().text());
		}
		return list;
	}
	
	/**
	 * JSoup에 의존적인 메소드. Elements를 전달인자로 받아이미지링크 리스트를 return 해준다.
	 * 단위테스트가 완료된 코드.
	 * @param elements
	 * @return
	 */
	public ArrayList<String> getImageLink(Elements elements) {
		ArrayList<String> list = new ArrayList<String>();
		for(Element e :elements) {
			Iterator<Element> imgLink = e.getElementsByTag("img").iterator();
			while(imgLink.hasNext()) 
				list.add(imgLink.next().attr("src"));
		}
		return list;
	}
	
	/**
	 * html코드를 전달인자로 받아 ImageList를 return해준다.
	 * 단위테스트가 완료된 코드.
	 * @param html
	 * @return
	 */
	public ArrayList<String> getImageLink(String html) {
		ArrayList<String> list = new ArrayList<String>();
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select(".lst_step");
		for(Element e :elements) {
			Iterator<Element> imgLink = e.getElementsByTag("img").iterator();
			while(imgLink.hasNext()) 
				list.add(imgLink.next().attr("src"));
		}
		System.out.println("ingredientSize - "+list.size());
		return list;
	}
	
	public static void main(String[] args) {
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
			new HamukCrawling().hamuk(builder.toString());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("끝");
	}
	
}