package ntut.csie.tagService.useCase.assignedTag.assign;

import ntut.csie.tagService.useCase.Input;

public interface AssignTagToBacklogItemInput extends Input {
	public String getBacklogItemId();
	
	public void setBacklogItemId(String backlogItemId);
	
	public String getTagId();
	
	public void setTagId(String tagId);
}
