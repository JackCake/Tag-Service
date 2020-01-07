package ntut.csie.tagService.useCase.assignedTag;

import java.util.Collection;

public interface AssignedTagRepository {
	public void save(AssignedTag assignedTag) throws Exception;
	
	public void remove(AssignedTag assignedTag) throws Exception;
	
	public AssignedTag getAssignedTagById(String assignedTagId);
	
	public Collection<AssignedTag> getAssignedTagsByBacklogItemId(String backlogItemId);
	
	public Collection<AssignedTag> getAssignedTagsByTagId(String tagId);
}
