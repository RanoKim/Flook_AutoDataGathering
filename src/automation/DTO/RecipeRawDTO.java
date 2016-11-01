package automation.DTO;

public class RecipeRawDTO {
	private String recipeName;
	private String completeImage;
	private String recipeRawLink;
	private String platform;
	
	public RecipeRawDTO(){}
	public RecipeRawDTO(String recipeName, String completeImage, String recipeRawLink, String platform) {
		this.completeImage = completeImage;
		this.recipeName = recipeName;
		this.recipeRawLink = recipeRawLink;
		this.platform = platform;
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
		return "RecipeRawDTO [recipeName=" + recipeName + ", completeImage=" + completeImage + ", recipeRawLink="
				+ recipeRawLink + ", platform=" + platform + "]";
	}
	
}
