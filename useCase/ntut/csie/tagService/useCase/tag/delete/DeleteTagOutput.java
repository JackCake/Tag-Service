package ntut.csie.tagService.useCase.tag.delete;

import ntut.csie.tagService.useCase.Output;

public interface DeleteTagOutput extends Output {
	public boolean isDeleteSuccess();
	
	public void setDeleteSuccess(boolean deleteSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
}
