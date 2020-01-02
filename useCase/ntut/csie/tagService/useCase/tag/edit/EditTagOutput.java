package ntut.csie.tagService.useCase.tag.edit;

import ntut.csie.tagService.useCase.Output;

public interface EditTagOutput extends Output {
	public boolean isEditSuccess();
	
	public void setEditSuccess(boolean editSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
}
