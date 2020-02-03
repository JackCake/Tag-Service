package ntut.csie.tagService.useCase.assignedTag;

import java.util.UUID;

public class AssignedTagBuilder {
	private String assignedTagId;
	private String backlogItemId;
	private String tagId;
	
	public static AssignedTagBuilder newInstance() {
		return new AssignedTagBuilder();
	}
	
	public AssignedTagBuilder backlogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
		return this;
	}
	
	public AssignedTagBuilder tagId(String tagId) {
		this.tagId = tagId;
		return this;
	}
	
	public AssignedTag build() throws Exception {
		String exceptionMessage = "";
		if(backlogItemId == null || backlogItemId.isEmpty()) {
			exceptionMessage += "The backlog item id of the assigned tag should be required!\n";
		}
		if(!exceptionMessage.isEmpty()) {
			throw new Exception(exceptionMessage);
		}
		
		assignedTagId = UUID.randomUUID().toString();
		AssignedTag assignedTag = new AssignedTag(assignedTagId, backlogItemId, tagId);
		return assignedTag;
	}
}
