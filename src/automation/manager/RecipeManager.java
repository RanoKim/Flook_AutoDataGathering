package automation.manager;

import java.util.ArrayList;
import java.util.LinkedList;

import automation.DAO.CookingDAO;
import automation.DAO.IngredientDAO;
import automation.DAO.PostDAO;
import automation.DAO.RecipeCategoryDAO;
import automation.DAO.RecipeDAO;
import automation.VO.CookingVO;
import automation.VO.IngredientVO;
import automation.VO.PostVO;
import automation.VO.RecipeVO;

public class RecipeManager {
	private RecipeDAO recipeDAO;
	private PostDAO postDAO;
	private CookingDAO cookingDAO;
	private IngredientDAO ingredientDAO;
	private RecipeCategoryDAO recipeCategoryDAO;

	
	public RecipeManager()
	{
		this.recipeDAO=RecipeDAO.getInstance();
		this.postDAO=PostDAO.getInstance();
		this.cookingDAO=CookingDAO.getInstance();
		this.ingredientDAO=IngredientDAO.getInstance();
		
		this.recipeCategoryDAO=RecipeCategoryDAO.getInstance();
		
	}
	public int writeRecipe(RecipeVO recipeVO,PostVO postVO,ArrayList<CookingVO> cookingList,ArrayList<IngredientVO> ingredientList,String[] recipeCategory)
	{
		String recipeCode=recipeDAO.insertRecipe(recipeVO);
		System.out.println("recipeCode Check : "+recipeCode+"\n");
		if(recipeCode==null)
		{
			return 0;
		}
		postVO.setRecipeCode(recipeCode);
		String postCode=postDAO.insertPost(postVO);
		System.out.println("postCode check : "+postCode+"\n");
		if(postCode==null)
		{
			return 0;
		}
		for(IngredientVO ingredientVO:ingredientList)
		{
			ingredientVO.setRecipeCode(recipeCode);
			System.out.println("ingredient Check : "+ingredientDAO.insertIngredient(ingredientVO));
		}
		System.out.println("listL : "+cookingList.size());
		for(CookingVO cookingVO:cookingList)
		{
			cookingVO.setRecipeCode(recipeCode);
			cookingDAO.insertCooking(cookingVO);
		}
		System.out.println("check");
		System.out.println("RCcheck : "+recipeCategoryDAO.insertCategory(recipeCode, recipeCategory));
		System.out.println("check2");
		return 1;
	}

	
}
