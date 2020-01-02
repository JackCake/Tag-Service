package ntut.csie.tagService.useCase.tag.add;

import ntut.csie.tagService.useCase.Input;

public interface AddTagInput extends Input {
	public String getName();
	
	public void setName(String name);
	
	public String getProductId();
	
	public void setProductId(String productId);
}
