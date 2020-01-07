package ntut.csie.tagService.useCase.assignedTag;

public class AssignedTag {
	private String assignedTagId;
	private String backlogItemId;
	private String tagId;
	
	public AssignedTag() {}
	
	public AssignedTag(String assignedTagId, String backlogItemId, String tagId) {
		this.setAssignedTagId(assignedTagId);
		this.setBacklogItemId(backlogItemId);
		this.setTagId(tagId);
	}
	
	public String getAssignedTagId() {
		return assignedTagId;
	}
	
	public void setAssignedTagId(String assignedTagId) {
		this.assignedTagId = assignedTagId;
	}
	
	public String getBacklogItemId() {
		return backlogItemId;
	}
	
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}
	
	public String getTagId() {
		return tagId;
	}
	
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
}
