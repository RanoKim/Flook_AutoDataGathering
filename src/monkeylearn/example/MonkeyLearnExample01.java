package monkeylearn.example;

import com.monkeylearn.MonkeyLearn;
import com.monkeylearn.MonkeyLearnException;
import com.monkeylearn.MonkeyLearnResponse;

public class MonkeyLearnExample01 {
	
	public static void main( String[] args ) throws MonkeyLearnException {
        MonkeyLearn ml = new MonkeyLearn("38ce2c0babe928f2fd87502826615b8e36413cea");
        String moduleId = "cl_YaWXPijE";
        
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
        		
        		"소 갈비 의 것 ) 식사 라고"
        
        		//"의 것 )"
        };
        
        //ArrayList samples = new ArrayList();
        int category_id_1 = 5638342;  // Use real category ids!
        //int category_id_2 = 1001;  // Use real category ids!
        //samples.add(new Tuple<String, Integer>("The movie was terrible, I hated it.", category_id_1));
        //MonkeyLearnResponse res = ml.classifiers.uploadSamples(moduleId, samples);
        
        //MonkeyLearnResponse res = ml.classifiers.classify(moduleId, textList, true);
        //ml.classifiers.c
        //MonkeyLearnResponse res = ml.extractors.extract(moduleId, textList);
        
        
        MonkeyLearnResponse res = ml.classifiers.list();

        
        System.out.println( res.arrayResult );
    }

}
