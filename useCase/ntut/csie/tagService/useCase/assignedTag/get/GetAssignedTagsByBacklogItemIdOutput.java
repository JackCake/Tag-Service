package ntut.csie.tagService.useCase.assignedTag.get;

import java.util.List;

import ntut.csie.tagService.useCase.Output;
import ntut.csie.tagService.useCase.assignedTag.AssignedTagModel;

public interface GetAssignedTagsByBacklogItemIdOutput extends Output {
	public List<AssignedTagModel> getAssignedTagList();
	
	public void setAssignedTagList(List<AssignedTagModel> assignedTagList); 
}
