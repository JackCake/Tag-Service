package ntut.csie.tagService.useCase.assignedTag.get;

import ntut.csie.tagService.useCase.Input;

public interface GetAssignedTagsByBacklogItemIdInput extends Input {
	public String getBacklogItemId();
	
	public void setBacklogItemId(String backlogItemId);
}
