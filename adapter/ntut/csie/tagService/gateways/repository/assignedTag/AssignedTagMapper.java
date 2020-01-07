package ntut.csie.tagService.gateways.repository.assignedTag;

import ntut.csie.tagService.useCase.assignedTag.AssignedTag;

public class AssignedTagMapper {
	public AssignedTag transformToAssignedTag(AssignedTagData data) {
		AssignedTag assignedTag = new AssignedTag();
		assignedTag.setAssignedTagId(data.getAssignedTagId());
		assignedTag.setBacklogItemId(data.getBacklogItemId());
		assignedTag.setTagId(data.getTagId());
		return assignedTag;
	}
	
	public AssignedTagData transformToAssignedTagData(AssignedTag assignedTag) {
		AssignedTagData data = new AssignedTagData();
		data.setAssignedTagId(assignedTag.getAssignedTagId());
		data.setBacklogItemId(assignedTag.getBacklogItemId());
		data.setTagId(assignedTag.getTagId());
		return data;
	}
}
