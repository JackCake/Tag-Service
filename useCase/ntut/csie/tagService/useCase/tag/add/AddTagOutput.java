package ntut.csie.tagService.useCase.tag.add;

import ntut.csie.tagService.useCase.Output;

public interface AddTagOutput extends Output {
	public boolean isAddSuccess();
	
	public void setAddSuccess(boolean addSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
}
