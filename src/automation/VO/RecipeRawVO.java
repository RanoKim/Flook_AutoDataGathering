package automation.VO;

public class RecipeRawVO {
	private String recipeCode;
	private String recipeName;
	private String completeImage;
	private String recipeRawLink;
	private String platform;
	
	public RecipeRawVO() {}
	public RecipeRawVO(String recipeCode, String recipeName, String completeImage, String recipeRawLink, String platform) {
		this.recipeCode = recipeCode;
		this.completeImage = completeImage;
		this.recipeName = recipeName;
		this.recipeRawLink = recipeRawLink;
		this.platform = platform;
	}

	public String getRecipeCode() {
		return recipeCode;
	}
	public void setRecipeCode(String recipeCode) {
		this.recipeCode = recipeCode;
	}

	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getCompleteImage() {
		return completeImage;
	}
	public void setCompleteImage(String completeImage) {
		this.completeImage = completeImage;
	}

	public String getRecipeRawLink() {
		return recipeRawLink;
	}
	public void setRecipeRawLink(String recipeRawLink) {
		this.recipeRawLink = recipeRawLink;
	}
	
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	@Override
	public String toString() {
		return "RecipeRawVO [recipeCode=" + recipeCode + ", recipeName=" + recipeName + ", completeImage="
				+ completeImage + ", recipeRawLink=" + recipeRawLink + ", platform=" + platform + "]";
	}

}
