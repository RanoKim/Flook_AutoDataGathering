package automation.DTO;

public class RecipeInfoDTO {
	private String userImageLink;
	private String userName;
	private String recipeName;
	private String cookingTime;
	
	public RecipeInfoDTO(String userImageLink, String userName, String recipeName, String cookingTime) {
		super();
		this.userImageLink = userImageLink;
		this.userName = userName;
		this.recipeName = recipeName;
		this.cookingTime = cookingTime;
	}
	public String getUserImageLink() {
		return userImageLink;
	}
	public void setUserImageLink(String userImageLink) {
		this.userImageLink = userImageLink;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public String getCookingTime() {
		return cookingTime;
	}
	public void setCookingTime(String cookingTime) {
		this.cookingTime = cookingTime;
	}
	@Override
	public String toString() {
		return "userImageLink=" + userImageLink + ", userName=" + userName + ", recipeName=" + recipeName
				+ ", cookingTime=" + cookingTime;
	}
	
}
