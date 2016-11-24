package monkeylearn.example;

import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.monkeylearn.MonkeyLearn;
import com.monkeylearn.MonkeyLearnException;
import com.monkeylearn.MonkeyLearnResponse;

import automation.DAO.CookingDAO;
import automation.DAO.RecipeRawDAO;
import automation.VO.CookingVO;
import automation.VO.RecipeRawVO;
import automation.staticValue.AutoStatic;

public class MonkeyLearnExample01 {
	
	public static LinkedList<String> monkeyPara = new LinkedList<String>();
	public static LinkedList<LinkedList<String>> monkeys = new LinkedList<LinkedList<String>>();
	
	public static LinkedList<RecipeRawVO> manRec;
	public static LinkedList<RecipeRawVO> haRec;
	public static LinkedList<RecipeRawVO> naverRec;
	
	
	public static void main( String[] args ) throws MonkeyLearnException {
		
		AutoStatic.who("daesub");
		
        MonkeyLearn ml = new MonkeyLearn("38ce2c0babe928f2fd87502826615b8e36413cea");
        String moduleId1 = "cl_DofyL3Rx"; // 종류별. Kind
        String moduleId2 = "cl_PGRPZLTy"; // 상황별. Situation
        String moduleId3 = "cl_nvgg3LB6"; // 방법별. Method
        
        
        
        
        
        /*String[] textList = {"울남편님 힘내서 파이팅하라고 고기반찬을 올렸네요 ^^ 그 메뉴 중 하나 만들어놓은 맛간장을 이용해 소갈비찜을 만들었어요~! 맛간장이 있어서 양념장을 간단하게 만들었어요^^", 
        		"소갈비", "소갈비는 흐르는 물에 씻어주고 한나절 동안 물에 담가 불순물과 핏물을 제거했어요."
        		, "핏물 뺀 갈비에 양념장을 넣어 40분간 중불로 끓여줍니다.", "맛있는 갈비찜이 완성이 되었어요.", "소고기 두루치기",
        		"소고기볶음"};*/
        
        String[] textList = {
        		
        		/*"울남편님 힘내서 파이팅하라고 고기반찬을 올렸네요 ^^ 그 메뉴 중 하나 만들어놓은 맛간장을 "
        		+ "이용해 소갈비찜을 만들었어요~! 맛간장이 있어서 양념장을 간단하게 만들었어요^^"
        				,*/
        		
        		/*"파베초콜릿 파베초콜릿입니다 생초콜릿용 종이트레이 1개분량이에요 밀크/다크 커버춰 생크림 버터 "
        		+ "따뜻하게 데운 생크림에 초콜릿을 넣고 저으면서 생크림의 열로 녹여주세요 그다음 버터와물엿을 "
        		+ "넣고 섞어주세요 트레이에 부워 3~4시간 굳혀주세요 썰어서 코코아 파우더 뭍히고 포장하면 끝 "
        		+ "자를때 칼을 불에달궈서 자르면 잘 잘려요 초콜릿에 물안들어가야해요"
        			,*/
        		
        		"[가지요리]만능간장을 이용해 가지요리로 장아찌만들었어요", 
        		"가지가 건강에 좋은것 다들아시지요 색다른 가지요리 소개합니다 그저 볶아먹고 쪄서 무침을 만들어먹는데 다오네 가지장아찌를 만들어 보았는데 넘 부드럽고 맛이 있어 소개해드립니다 주변에 흔히 볼수 있는가지 장아찌만들어 오래보관하시며 드실수 있으니 시간 나실때 도전해보세요^^ 가지장아찌 간단하고 쉽게 만드는법 요리에 자신없으신분도 만들수 있으니 잘 보시와요~~"
        
        		//"의 것 )"
        };
        
        //ArrayList samples = new ArrayList();
        //int category_id_1 = 5638342;  // Use real category ids!
        //int category_id_2 = 1001;  // Use real category ids!
        //samples.add(new Tuple<String, Integer>("The movie was terrible, I hated it.", category_id_1));
        //MonkeyLearnResponse res = ml.classifiers.uploadSamples(moduleId, samples);
        
        //MonkeyLearnResponse res = ml.classifiers.classify(moduleId1, textList, true);
        //ml.classifiers.c
        //MonkeyLearnResponse res = ml.extractors.extract(moduleId, textList);
        
        
        //MonkeyLearnResponse res = ml.classifiers.list();

        //System.out.println( res.arrayResult );
        
        //manRec = getRecipes("ma");
        //haRec = getRecipes("ha");
        naverRec = getRecipes("naver");
        
        
        
        
        
        LinkedList<CookingVO> cookingList = null;
        for (RecipeRawVO dev : naverRec) {
        	
        	//System.out.println(CookingDAO.getInstance().searchCookingList(dev.getRecipeCode())+"\n\n");
        	
        	//System.out.println("Name : " + dev.getRecipeName() + "  " + dev.getRecipeCode());
        	
        	monkeyPara.add(dev.getRecipeName());
        	
        	/*
        	String recipeCode = dev.getRecipeCode();
        	
        	cookingList = CookingDAO.getInstance().searchCookingList(recipeCode);
        	//System.out.println(cookingList);
        	
        	
        	if (cookingList != null) {
        		for (CookingVO cook : cookingList) {
            		monkeyPara.add(cook.getCookingCaption());
            	}
            	//monkeys.add(monkeyPara);
            	//System.out.println(monkeyPara + "\n\n");
            	
            	cookingList.clear();
        	}*/
        	
        	
        	/*
        	for (CookingVO cook : cookingList) {
        		monkeyPara.add(cook.getCookingCaption());
        	}
        	monkeys.add(monkeyPara);  
        	System.out.println(monkeyPara + "\n\n");*/
        	//System.out.println(cookingList + "\n\n");
        	
        	//monkeys.add(monkeyPara); 
        }
        
        //LinkedList<String> arr = monkeys.get(0);
        
        
        String[] str = monkeyPara.toArray(new String[monkeyPara.size()]);
        MonkeyLearnResponse res = ml.classifiers.classify(moduleId1, str, true);
        
        System.out.println(res.arrayResult);
        
        
        
        /*
        for (RecipeRawVO dev : getRecipes("ma")) {
        	//System.out.println(CookingDAO.getInstance().searchCookingList(dev.getRecipeCode()));
        }*/

        /*
		for (RecipeRawVO dev : getRecipes("ma")) {
			//System.out.println(CookingDAO.getInstance().searchCookingList(dev.getRecipeCode()));
		}*/
		
		//System.out.println(cookingList.size() + "\n" + cookingList);
        
    }
	
	public static LinkedList<RecipeRawVO> getRecipes(String platform) {
		
		LinkedList<RecipeRawVO> recipeList = null;
		
		if (platform.equals("ma")) {
			
			System.out.println("----- 만개 -----");
			recipeList = RecipeRawDAO.getInstance().getRecipes(platform);
			
			
		} else if (platform.equals("ha")) {
			
			System.out.println("----- 해먹 -----");
			recipeList = RecipeRawDAO.getInstance().getRecipes(platform);
			
		} else if (platform.equals("naver")) {
			
			System.out.println("----- 네이버 -----");
			recipeList = RecipeRawDAO.getInstance().getRecipes(platform);
			
		}
		//System.out.println(recipeList.size() + "\n" + recipeList);
		
		return recipeList;
	}
	
	

}
