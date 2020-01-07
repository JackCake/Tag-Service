package ntut.csie.tagService.useCase.assignedTag.get;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.tagService.useCase.assignedTag.AssignedTag;
import ntut.csie.tagService.useCase.assignedTag.AssignedTagModel;
import ntut.csie.tagService.useCase.assignedTag.AssignedTagRepository;
import ntut.csie.tagService.useCase.assignedTag.ConvertAssignedTagToDTO;

public class GetAssignedTagsByBacklogItemIdUseCaseImpl implements GetAssignedTagsByBacklogItemIdUseCase, GetAssignedTagsByBacklogItemIdInput {
	private AssignedTagRepository assignedTagRepository;
	
	private String backlogItemId;
	
	public GetAssignedTagsByBacklogItemIdUseCaseImpl(AssignedTagRepository assignedTagRepository) {
		this.assignedTagRepository = assignedTagRepository;
	}
	
	@Override
	public void execute(GetAssignedTagsByBacklogItemIdInput input,
			GetAssignedTagsByBacklogItemIdOutput output) {
		List<AssignedTagModel> assignedTagList = new ArrayList<>();
		for(AssignedTag assignedTag : assignedTagRepository.getAssignedTagsByBacklogItemId(input.getBacklogItemId())) {
			assignedTagList.add(ConvertAssignedTagToDTO.transform(assignedTag));
		}
		output.setAssignedTagList(assignedTagList);
	}

	@Override
	public String getBacklogItemId() {
		return backlogItemId;
	}

	@Override
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}
}
