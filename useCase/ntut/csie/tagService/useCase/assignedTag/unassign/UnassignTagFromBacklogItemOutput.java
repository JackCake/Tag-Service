package ntut.csie.tagService.useCase.assignedTag.unassign;

import ntut.csie.tagService.useCase.Output;

public interface UnassignTagFromBacklogItemOutput extends Output {
	public boolean isUnassignSuccess();
	
	public void setUnassignSuccess(boolean unassignSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
}
