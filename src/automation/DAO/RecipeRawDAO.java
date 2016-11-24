package automation.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import automation.DB.DBConnection;
import automation.VO.CookingVO;
import automation.VO.RecipeRawVO;
import automation.VO.RecipeVO;

public class RecipeRawDAO {
	
	private static String INSERT_RECIPE_RAW = "INSERT INTO recipe_raw_tb VALUES(?,?,?,?,?)"; 
	private static String INSERT_RECIPE_DEV = "INSERT INTO recipe_dev_tb VALUES(?,?,?,?,?)";  
	
	private DBConnection dbConnection;
	private static RecipeRawDAO dao;
	private static int codeNum = 1;
	private static final String codeType="RR";
	static {
		dao = new RecipeRawDAO();
	}
	
	public static RecipeRawDAO getInstance() {
		return dao;
	}
	private RecipeRawDAO() { 
		dbConnection = DBConnection.getInstance();
	} 
	public DBConnection getDbConnection() {
		return dbConnection;
	}
	public void setDbConnection(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	} 
	
	public String codeGenerator2(){
		return codeType+System.currentTimeMillis();
	}
	
	public String insertRecipeRaw(RecipeRawVO recipeRawVO)
	{
		
		String recipeRawCode=recipeRawVO.getRecipeCode();
		String recipeName=recipeRawVO.getRecipeName();
		String completeImage=recipeRawVO.getCompleteImage();
		String recipeRawLink=recipeRawVO.getRecipeRawLink();
		String platform=recipeRawVO.getPlatform();
		
		//if(recipeRawCode!=null) {
		return this.insertRecipe(recipeRawCode, recipeName, completeImage, recipeRawLink, platform);
		//}
		/*else
		{
			System.out.println(recipeRawVO);
			return this.insertRecipe(recipeName, completeImage, recipeRawLink, platform);
		}*/
	}
	public String insertRecipe(String recipeName, String completeImage, String recipeRawLink, String platform)
	{
		PreparedStatement pstmt=null;
		//String recipeCode="'0101UR'||user_recipe_seq.nextval";//CalculateCode.makeCode(codeType, codeNum);
		String recipeRawCode=codeGenerator2();
				//CalculateCode.makeCode(codeType, codeNum);
		int row = 0; 
		try
		{
			//String sql = INSERT_RECIPE_RAW;
			//String sql = INSERT_RECIPE_DEV;
			String sql = "INSERT INTO recipe_dev_tb VALUES(?,?,?,?,?)";
			System.out.println("recipeRawCode : " +recipeRawCode);
			
			Connection conn = dbConnection.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,recipeRawCode);
			//pstmt.setString(1,codeType+"||recipe_seq.nextval");
			pstmt.setString(2,recipeName);
			pstmt.setString(3,completeImage);
			pstmt.setString(4,recipeRawLink);
			pstmt.setString(5,platform);
			
			row=pstmt.executeUpdate();
			if(row!=0)
			{
				codeNum++;
				conn.commit(); 
			}

		}
		catch(Exception se){
			System.out.println(se.getMessage());
		}finally{
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception se) {
				System.out.println(se.getMessage());
			}
		}   
		if(row!=0)
		{ 
			codeNum++;
			return recipeRawCode;
		}			
		return null; 	
	}
	public String insertRecipe(String recipeRawCode,String recipeName,String completeImage, String recipeRawLink, String platform)
	{
		//System.out.println("@!@#!@#!@#!@# recipeRawCode :::  " + recipeRawCode);
		PreparedStatement pstmt=null;
		int row = 0; 
		try
		{
			//String sql = INSERT_RECIPE_RAW;
			//String sql = INSERT_RECIPE_DEV;
			String sql = "INSERT INTO recipe_dev_tb VALUES(?,?,?,?,?)";
			Connection conn = dbConnection.getConn();
			pstmt=conn.prepareStatement(sql);
			//System.out.println("@!@#!@#!@#!@# recipeRawCode :::  " + recipeRawCode);
			pstmt.setString(1,recipeRawCode);
			pstmt.setString(2,recipeName);
			pstmt.setString(3,completeImage);
			pstmt.setString(4,recipeRawLink);
			pstmt.setString(5,platform);
			
			row=pstmt.executeUpdate();
			conn.commit(); 
			/*if(row!=0)
			{
				conn.commit(); 
			}
*/
		}
		catch(Exception se){
			System.out.println(se.getMessage());
		}finally{
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception se) {
				System.out.println(se.getMessage());
			}
		}   
		if(row!=0)
		{ 
			return recipeRawCode;
		}			
		return recipeRawCode; 	
	}
	
	public LinkedList<RecipeRawVO> getRecipes(String platform)
	{

		
		PreparedStatement pstmt = null;
		RecipeRawVO recipeRawVO=null;
		LinkedList<RecipeRawVO> recipeList=new LinkedList<RecipeRawVO>();
		try {
			String sql = "SELECT * FROM recipe_dev_tb where platform=?;";
			Connection conn = dbConnection.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, platform); 

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) { 
				
				 String recipeCode=rs.getString("recipe_code");
				 String recipeName=rs.getString("recipe_name");
				 String completeImage=rs.getString("complete_image");
				 String recipeRawLink=rs.getString("recipe_raw_link");
				 platform=rs.getString("platform");
				
				 recipeRawVO=new RecipeRawVO(recipeCode, recipeName, completeImage, recipeRawLink, platform);
				 recipeList.add(recipeRawVO);
			}
		} catch (Exception se) {
			se.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		if(recipeList.size()!=0)
		{
			return recipeList;
		}
		return null;
	}

}
