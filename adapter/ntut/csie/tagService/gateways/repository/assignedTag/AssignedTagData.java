package ntut.csie.tagService.gateways.repository.assignedTag;

public class AssignedTagData {
	private String assignedTagId;
	private String backlogItemId;
	private String tagId;
	
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
