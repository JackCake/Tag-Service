package ntut.csie.tagService.useCase.tag.get;

import ntut.csie.tagService.useCase.Input;

public interface GetTagsByProductIdInput extends Input {
	public String getProductId();
	
	public void setProductId(String productId);
}
