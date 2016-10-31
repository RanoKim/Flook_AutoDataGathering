package automation.manager;

import automation.DAO.UserDAO;
import automation.VO.UserVO;

public class UserManager {
	private UserDAO userDAO;
	{
		userDAO=UserDAO.getInstance();
	}
	public UserVO seachEmail(String email)
	{
		return userDAO.findEmail(email);
	}
	public boolean isUserImageLink(String userImageLink) {
		return userDAO.isUserImageLink(userImageLink);
	}
	public static void main(String[]args)
	{
		UserManager man = new UserManager();
		
		//UserWithFollowOrNotVO vo = man.searchEmailAndFollowOrNot("kaka9887@naver.com", null);
		//System.out.println(vo.getFollowCode());
	}
}
