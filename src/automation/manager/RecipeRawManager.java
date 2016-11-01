package automation.manager;

import automation.DAO.RecipeRawDAO;
import automation.VO.RecipeRawVO;

public class RecipeRawManager {
	
	private RecipeRawDAO recipeRawDAO;
	
	public RecipeRawManager() {
		this.recipeRawDAO = RecipeRawDAO.getInstance();
	}
	
	public int insertRecipeRaw(RecipeRawVO recipeRawVO) {
		
		String recipeCode=recipeRawDAO.insertRecipeRaw(recipeRawVO);
		System.out.println("recipeCode Check : "+recipeCode+"\n");
		if (recipeCode == null)
			return 0;
		
		return 1;
		
	}

}
