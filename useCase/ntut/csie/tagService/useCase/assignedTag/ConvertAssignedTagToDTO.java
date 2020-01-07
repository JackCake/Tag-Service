package ntut.csie.tagService.useCase.assignedTag;

public class ConvertAssignedTagToDTO {
	public static AssignedTagModel transform(AssignedTag assignedTag) {
		AssignedTagModel dto = new AssignedTagModel();
		dto.setAssignedTagId(assignedTag.getAssignedTagId());
		dto.setBacklogItemId(assignedTag.getBacklogItemId());
		dto.setTagId(assignedTag.getTagId());
		return dto;
	}
}
