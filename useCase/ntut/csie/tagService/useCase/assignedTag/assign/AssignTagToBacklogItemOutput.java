package ntut.csie.tagService.useCase.assignedTag.assign;

import ntut.csie.tagService.useCase.Output;

public interface AssignTagToBacklogItemOutput extends Output {
	public boolean isAssignSuccess();
	
	public void setAssignSuccess(boolean assignSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
}
