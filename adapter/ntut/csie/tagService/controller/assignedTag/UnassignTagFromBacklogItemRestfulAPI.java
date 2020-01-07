package ntut.csie.tagService.controller.assignedTag;

import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import ntut.csie.tagService.ApplicationContext;
import ntut.csie.tagService.useCase.assignedTag.unassign.UnassignTagFromBacklogItemInput;
import ntut.csie.tagService.useCase.assignedTag.unassign.UnassignTagFromBacklogItemOutput;
import ntut.csie.tagService.useCase.assignedTag.unassign.UnassignTagFromBacklogItemUseCase;

@Path("/assigned_tags")
@Singleton
public class UnassignTagFromBacklogItemRestfulAPI implements UnassignTagFromBacklogItemOutput {
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private UnassignTagFromBacklogItemUseCase unassignTagFromBacklogItemUseCase = applicationContext.newUnassignTagFromBacklogItemUseCase();

	private boolean unassignSuccess;
	private String errorMessage;
	
	@DELETE
	@Path("/{assigned_tag_id}")
	public synchronized UnassignTagFromBacklogItemOutput unassignTagFromBacklogItem(@PathParam("assigned_tag_id") String assignedTagId) {
		UnassignTagFromBacklogItemOutput output = this;
		
		UnassignTagFromBacklogItemInput input = (UnassignTagFromBacklogItemInput) unassignTagFromBacklogItemUseCase;
		input.setAssignedTagId(assignedTagId);
		
		unassignTagFromBacklogItemUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public boolean isUnassignSuccess() {
		return unassignSuccess;
	}

	@Override
	public void setUnassignSuccess(boolean unassignSuccess) {
		this.unassignSuccess = unassignSuccess;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
