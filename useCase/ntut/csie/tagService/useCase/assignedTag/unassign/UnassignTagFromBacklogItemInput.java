package ntut.csie.tagService.useCase.assignedTag.unassign;

import ntut.csie.tagService.useCase.Input;

public interface UnassignTagFromBacklogItemInput extends Input {
	public String getAssignedTagId();
	
	public void setAssignedTagId(String assignedTagId);
}
