package automation.DTO;

public class IngredientDTO {
	private String ingredientName;
	private String ingredientVolume;
	public IngredientDTO(String ingredientName, String ingredientVolume) {
		super();
		this.ingredientName = ingredientName;
		this.ingredientVolume = ingredientVolume;
	}
	
	public String getIngredientName() {
		return ingredientName;
	}
	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}
	public String getIngredientVolume() {
		return ingredientVolume;
	}
	public void setIngredientVolume(String ingredientVolume) {
		this.ingredientVolume = ingredientVolume;
	}

	@Override
	public String toString() {
		return "ingredientName=" + ingredientName + ", ingredientVolume=" + ingredientVolume;
	}
	
	
}
