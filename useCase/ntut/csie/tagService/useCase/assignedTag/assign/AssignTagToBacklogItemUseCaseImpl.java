package ntut.csie.tagService.useCase.assignedTag.assign;

import ntut.csie.tagService.model.tag.Tag;
import ntut.csie.tagService.useCase.assignedTag.AssignedTag;
import ntut.csie.tagService.useCase.assignedTag.AssignedTagBuilder;
import ntut.csie.tagService.useCase.assignedTag.AssignedTagRepository;
import ntut.csie.tagService.useCase.tag.TagRepository;

public class AssignTagToBacklogItemUseCaseImpl implements AssignTagToBacklogItemUseCase, AssignTagToBacklogItemInput {
	private TagRepository tagRepository;
	private AssignedTagRepository assignedTagRepository;
	
	private String backlogItemId;
	private String tagId;
	
	public AssignTagToBacklogItemUseCaseImpl(TagRepository tagRepository, AssignedTagRepository assignedTagRepository) {
		this.tagRepository = tagRepository;
		this.assignedTagRepository = assignedTagRepository;
	}
	
	@Override
	public void execute(AssignTagToBacklogItemInput input, AssignTagToBacklogItemOutput output) {
		String tagId = input.getTagId();
		Tag tag = tagRepository.getTagById(tagId);
		if(tag == null) {
			output.setAssignSuccess(false);
			output.setErrorMessage("Sorry, the tag is not exist!");
			return;
		}
		try {
			AssignedTag assignedTag = AssignedTagBuilder.newInstance()
					.backlogItemId(input.getBacklogItemId())
					.tagId(tagId)
					.build();
			assignedTagRepository.save(assignedTag);
		} catch (Exception e) {
			output.setAssignSuccess(false);
			output.setErrorMessage(e.getMessage());
			return;
		}
		output.setAssignSuccess(true);
	}

	@Override
	public String getBacklogItemId() {
		return backlogItemId;
	}

	@Override
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}

	@Override
	public String getTagId() {
		return tagId;
	}

	@Override
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
}
