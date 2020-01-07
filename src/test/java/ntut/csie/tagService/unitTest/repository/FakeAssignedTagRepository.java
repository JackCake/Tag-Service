package ntut.csie.tagService.unitTest.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ntut.csie.tagService.useCase.assignedTag.AssignedTag;
import ntut.csie.tagService.useCase.assignedTag.AssignedTagRepository;

public class FakeAssignedTagRepository implements AssignedTagRepository {
	private Map<String, AssignedTag> assignedTags;

	public FakeAssignedTagRepository() {
		assignedTags = Collections.synchronizedMap(new LinkedHashMap<String, AssignedTag>());
	}
	
	@Override
	public void save(AssignedTag assignedTag) {
		assignedTags.put(assignedTag.getAssignedTagId(), assignedTag);
	}

	@Override
	public void remove(AssignedTag assignedTag) {
		assignedTags.remove(assignedTag.getAssignedTagId());
	}

	@Override
	public AssignedTag getAssignedTagById(String assignedTagId) {
		return assignedTags.get(assignedTagId);
	}

	@Override
	public Collection<AssignedTag> getAssignedTagsByBacklogItemId(String backlogItemId) {
		List<AssignedTag> assignedTagList = new ArrayList<>();
		for(AssignedTag assignedTag : assignedTags.values()) {
			if(assignedTag.getBacklogItemId().equals(backlogItemId)) {
				assignedTagList.add(assignedTag);
			}
		}
		return assignedTagList;
	}

	@Override
	public Collection<AssignedTag> getAssignedTagsByTagId(String tagId) {
		List<AssignedTag> assignedTagList = new ArrayList<>();
		for(AssignedTag assignedTag : assignedTags.values()) {
			if(assignedTag.getTagId().equals(tagId)) {
				assignedTagList.add(assignedTag);
			}
		}
		return assignedTagList;
	}

	public void clearAll() {
		assignedTags.clear();
	}
}
