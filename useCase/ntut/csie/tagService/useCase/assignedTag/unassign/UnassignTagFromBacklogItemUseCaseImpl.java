package ntut.csie.tagService.useCase.assignedTag.unassign;

import ntut.csie.tagService.useCase.assignedTag.AssignedTag;
import ntut.csie.tagService.useCase.assignedTag.AssignedTagRepository;

public class UnassignTagFromBacklogItemUseCaseImpl implements UnassignTagFromBacklogItemUseCase, UnassignTagFromBacklogItemInput {
	private AssignedTagRepository assignedTagRepository;
	
	private String assignedTagId;
	
	public UnassignTagFromBacklogItemUseCaseImpl(AssignedTagRepository assignedTagRepository) {
		this.assignedTagRepository = assignedTagRepository;
	}
	
	@Override
	public void execute(UnassignTagFromBacklogItemInput input, UnassignTagFromBacklogItemOutput output) {
		String assignedTagId = input.getAssignedTagId();
		AssignedTag assignedTag = assignedTagRepository.getAssignedTagById(assignedTagId);
		if(assignedTag == null) {
			output.setUnassignSuccess(false);
			output.setErrorMessage("Sorry, the assigned tag is not exist!");
			return;
		}
		try {
			assignedTagRepository.remove(assignedTag);
		} catch (Exception e) {
			output.setUnassignSuccess(false);
			output.setErrorMessage(e.getMessage());
			return;
		}
		output.setUnassignSuccess(true);
	}

	@Override
	public String getAssignedTagId() {
		return assignedTagId;
	}

	@Override
	public void setAssignedTagId(String assignedTagId) {
		this.assignedTagId = assignedTagId;
	}
}
