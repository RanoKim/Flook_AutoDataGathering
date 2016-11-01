package automation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;


import automation.DB.DBConnection;
import automation.VO.UserVO;

public class UserDAO {
	private static UserDAO userDAO;
	private ArrayList<UserVO> userVOList;
	private DBConnection conn;
	
	static {
		userDAO =new UserDAO();
	}
	private UserDAO() {
		// TODO Auto-generated constructor stub
		this(new ArrayList<UserVO>());
	}
	private UserDAO(ArrayList<UserVO> userVOList) {
		this.userVOList = userVOList;
		conn=DBConnection.getInstance();
	}
	public static UserDAO getInstance() {
		
		return userDAO;
	}
	
	private UserDAO(ArrayList<UserVO> userVOList, DBConnection dbConnection) {
		super();
		this.userVOList = userVOList;
		this.conn = dbConnection;
	}

	public ArrayList<UserVO> getUserVOList() {
		return userVOList;
	}

	public void setUserVOList(ArrayList<UserVO> userVOList) {
		this.userVOList = userVOList;
	}
	public boolean isUserImageLink(String name) {
		PreparedStatement pstmt=null;
		UserVO userVO=null;
		
		String userImage="";
		
	
		try
		{
			String sql = "select * from user_tb where user_image = ?";
			/*String sql="select user_name,user_image,user_email,user_gender,user_birthday,user_sns from user_tb "
					+ "where user_name='?'";*/
					
			pstmt=conn.getConn().prepareStatement(sql);
			pstmt.setString(1,name);
			

			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				
				userImage=rs.getString("user_image");
			}			 
				

		}
		
		catch(SQLException se)
		{
				System.out.println(se.getMessage());
			//se.stackTracePrint();
		}
		catch(Exception e)
		{
				System.out.println(e.getMessage());
			//e.stackTracePrint();
		}
		finally
		{
			if(pstmt!=null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException se)
				{
				System.out.println(se.getMessage());
					//se.stackTracePrint();
				}
			}
		}

		if(userImage.trim().equals(""))
			return false;
		else
			return true;
		
	}
	public UserVO seachUser(String email, String password){
		PreparedStatement pstmt=null;
		UserVO userVO=null;
		
		String userCode=null;
		String userName=null;
		String userImage=null;
		String userEmail=null;
		String userPassword=null;
		String userGender= null;
		String userBirthday=null;
		int userSns = 0;
	
		try
		{
			String sql="select user_name,user_image,user_email,user_gender,user_birthday,user_sns from user_tb "
					+ "where user_email='?' AND user_password=?";
					
			pstmt=conn.getConn().prepareStatement(sql);
			pstmt.setString(1,email);
			pstmt.setString(2,password);

			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				userCode=rs.getString("user_code");
				userName=rs.getString("user_name");
				userImage=rs.getString("user_image");
				userEmail=rs.getString("user_email");
				userPassword=rs.getString("user_password");
				userGender=rs.getString("user_gender");
				userBirthday=rs.getString("user_birthday");
				userSns = rs.getInt("user_Sns");
			
			}
				
				userVO=new UserVO(userCode,userName,userImage,userEmail,userPassword,userGender,userBirthday,userSns);
				 
				

		}
		
		catch(SQLException se)
		{
				System.out.println(se.getMessage());
			//se.stackTracePrint();
		}
		catch(Exception e)
		{
				System.out.println(e.getMessage());
			//e.stackTracePrint();
		}
		finally
		{
			if(pstmt!=null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException se)
				{
				System.out.println(se.getMessage());
					//se.stackTracePrint();
				}
			}
		}

		if(userVO!=null){
			return userVO;
		}
		else{
			return null;
		}
		
		
	}
	public int insertUser(UserVO userVO) {
		return insertUser(userVO.getUserName(),userVO.getUserImage(),userVO.getUserEmail(),userVO.getUserPassword(),userVO.getUserGender(),userVO.getBirthday());
	}
	public String codeGenerator(){ 
		return "UT"+System.currentTimeMillis();
	}
	public int insertUser(String userName,String userImage, String userEmail,String userPassword,String userGender,String userBirthday )
	{
		/*INSERT INTO user_tb(user_code,user_name,user_image,user_email,user_password,user_gender,user_birthday)
		VALUES('USER_150729_001','�����','http://www.naver.com','swj1539@naver.com','1539','����',to_date('1990/08/04','yyyy/mm/dd'))
	*/
		PreparedStatement pstmt=null;
		int row=0;
		
		try
		{
			String sql="INSERT INTO user_tb(user_code,user_name,user_image,user_email,user_password,user_gender,user_birthday,user_Sns)"+
					"VALUES(?,?,?,?,?,?,?,?)";
			pstmt=conn.getConn().prepareStatement(sql);
			pstmt.setString(1,codeGenerator());//�ڵ�����ʿ�
			pstmt.setString(2,userName);
			pstmt.setString(3,userImage);
			pstmt.setString(4, userEmail);
			pstmt.setString(5, userPassword);
			pstmt.setString(6, userGender);
			pstmt.setString(7, userBirthday);
			pstmt.setInt(8, 0);

			row=pstmt.executeUpdate();
			if(row!=0)
				conn.getConn().commit();
			else
				conn.getConn().rollback();
			
			
		}
		
		catch(SQLException se)
		{
				System.out.println(se.getMessage());
			//se.stackTracePrint();
		}
		catch(Exception e)
		{
				System.out.println(e.getMessage());
			//e.stackTracePrint();
		}
		finally
		{
			if(pstmt!=null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException se)
				{
					System.out.println(se.getMessage());
					//se.stackTracePrint();
				}
			}
		}

		return (byte) row;
	
	}
	
	public int modifyPassword(String email, String password,String newPassword){

		PreparedStatement pstmt=null;
		int row=0;
		
		try
		{
			String sql="UPDATE user_tb"+
					"SET user_password=?"+
					"WHERE user_email=? AND user_password=?";
			pstmt=conn.getConn().prepareStatement(sql);
			pstmt.setString(1,newPassword);//�ڵ�����ʿ�
			pstmt.setString(2,email);
			pstmt.setString(3,password);

			row=pstmt.executeUpdate();
			if(row!=0)
				conn.getConn().commit();
			else
				conn.getConn().rollback();
			
			
		}
		
		catch(SQLException se)
		{
				System.out.println(se.getMessage());
			//se.stackTracePrint();
		}
		catch(Exception e)
		{
				System.out.println(e.getMessage());
			//e.stackTracePrint();
		}
		finally
		{
			if(pstmt!=null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException se)
				{
					System.out.println(se.getMessage());
					//se.stackTracePrint();
				}
			}
		}

		return (byte) row;
	
	}
	public int modifyPassword(String email,String newPassword) {
		PreparedStatement pstmt = null;
		int rowNum = 0;
		try {
			String sql = "update user_tb set user_password=? where user_email=?";
			pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setString(1,newPassword);
			pstmt.setString(2, email);
			rowNum = pstmt.executeUpdate();
			conn.getConn().commit();
		} catch( Exception ex ) {
			ex.printStackTrace();
		} finally {
			try {
				conn.getConn().rollback();
				pstmt.close();
				
			} catch( Exception ex) {
				ex.printStackTrace();
			}
		}
		return rowNum;
	}
	public int deleteUser(String email, String password)
	{
		int rowNum=0;
		PreparedStatement pstmt = null;
		try
		{

			String sql="DELETE from user_tb where user_email=? AND user_password=?";
			pstmt=conn.getConn().prepareStatement(sql);
			pstmt.setString(1,email);
			pstmt.setString(2, password);
	
			rowNum=pstmt.executeUpdate();
			if(rowNum!=0)
				conn.getConn().commit();
		}
		catch (SQLException se)
		{
			System.out.println(se.getMessage());
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null) {
					pstmt.close();
					
				}
			}
			catch (Exception se)
			{
				System.out.println(se.getMessage());
			}
		}
		return rowNum;
	
	
	}
	public UserVO login(String userEmail,String userPassword) {
		PreparedStatement pstmt = null;
		try {
			String sql = "select * from user_tb where user_email=? and user_password=?";
			pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setString(1, userEmail);
			pstmt.setString(2,userPassword);
			ResultSet res = pstmt.executeQuery();
			while(res.next()) {
				return new UserVO(res.getString("user_Code"), res.getString("user_Name"), res.getString("user_Image"), res.getString("user_Email"), res.getString("user_Password"), res.getString("user_Gender"), res.getString("user_birthday"), res.getInt("user_Sns"));
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
		
	}
	public UserVO registerCheck(String userEmail) {
		PreparedStatement pstmt = null;
		try {
			String sql = "select * from user_tb where user_email=?";
			pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setString(1, userEmail);
			ResultSet res = pstmt.executeQuery();
			while(res.next()) {
				return new UserVO(res.getString("user_Code"), res.getString("user_Name"), res.getString("user_Image"), res.getString("user_Email"), res.getString("user_Password"), res.getString("user_Gender"), res.getString("user_birthday"), res.getInt("user_Sns"));
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
				
	}
	public String getUserCodeByEmail(String imageLink) {
		PreparedStatement pstmt = null;
		String userCode=null;
		System.out.println(imageLink);
		try {
			if(conn==null)
				System.out.println("conn is null");
			String sql = "select * from user_tb where user_image=?";
			pstmt = conn.
					getConn().
					prepareStatement(sql);
			pstmt.setString(1, imageLink);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				userCode=rs.getString("user_code");
				
			}
			if(userCode==null)
			{
				return null;
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return userCode;
	}
	public String getUserCode(String name) {
		PreparedStatement pstmt = null;
		String userCode=null;
		System.out.println(name);
		try {
			if(conn==null)
				System.out.println("conn is null");
			String sql = "select * from user_tb where user_name=?";
			pstmt = conn.
					getConn().
					prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				userCode=rs.getString("user_code");
				
			}
			if(userCode==null)
			{
				return null;
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return userCode;
	}
	public LinkedList<UserVO> searchUserListByUserName(String keyword) {
		PreparedStatement pstmt = null;
		LinkedList<UserVO> userList = new LinkedList<UserVO>();
		try {
			String sql = "select * from user_tb where user_name like ?";
			pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			ResultSet res = pstmt.executeQuery();
			while(res.next()) {
				userList.add(new UserVO(res.getString("user_Code"),res.getString("user_Name"), res.getString("user_Image"), res.getString("user_Email"), res.getString("user_Password"), res.getString("user_Gender"), res.getString("user_birthday"), res.getInt("user_Sns")));
			}
		} catch( Exception ex ) {
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return userList;
	}
	public UserVO findEmail(String email) {
		PreparedStatement pstmt = null;
		String userCode=null;
		String userName=null;
		String userImage=null;
		String userEmail=null;
		String userPassword=null;
		String userGender= null;
		String userBirthday=null;
		UserVO userVO = null;
		int userSns = 0;
		try {
			String sql = "select * from user_tb where user_email=?";
			pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				userCode=rs.getString("user_code");
				userName=rs.getString("user_name");
				userImage=rs.getString("user_image");
				userEmail=rs.getString("user_email");
				userPassword=rs.getString("user_password");
				userGender=rs.getString("user_gender");
				userBirthday=rs.getString("user_birthday");
				userSns = rs.getInt("user_Sns");
			}
			if(userCode==null)
			{
				return null;
			}
			userVO= new UserVO(userCode, userName, userImage, userEmail, userPassword, userGender, userBirthday, userSns);
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return userVO;
	}
	
	
	public static void main(String args[]){
		//System.out.println(new UserDAO().insertUser("������", "www.naverImage.com", "kimkim@naver.com","1539", "����", "1990/08/04"));
		//System.out.println(new UserDAO().deleteUser("swj1539@naver.com", "1539"));
		/*System.out.println(UserDAO.getInstance().login("1234@1234", "1234"));*/
		System.out.println(UserDAO.getInstance().searchUserListByUserName("��ȣ"));
	}
}
	

