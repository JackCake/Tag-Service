package ntut.csie.tagService.useCase.assignedTag.assign;

import ntut.csie.tagService.useCase.assignedTag.AssignedTag;
import ntut.csie.tagService.useCase.assignedTag.AssignedTagBuilder;
import ntut.csie.tagService.useCase.assignedTag.AssignedTagRepository;

public class AssignTagToBacklogItemUseCaseImpl implements AssignTagToBacklogItemUseCase, AssignTagToBacklogItemInput {
	private AssignedTagRepository assignedTagRepository;
	
	private String backlogItemId;
	private String tagId;
	
	public AssignTagToBacklogItemUseCaseImpl(AssignedTagRepository assignedTagRepository) {
		this.assignedTagRepository = assignedTagRepository;
	}
	
	@Override
	public void execute(AssignTagToBacklogItemInput input, AssignTagToBacklogItemOutput output) {
		try {
			AssignedTag assignedTag = AssignedTagBuilder.newInstance()
					.backlogItemId(input.getBacklogItemId())
					.tagId(input.getTagId())
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
