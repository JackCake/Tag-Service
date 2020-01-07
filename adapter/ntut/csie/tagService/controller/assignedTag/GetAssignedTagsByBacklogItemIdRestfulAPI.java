package ntut.csie.tagService.controller.assignedTag;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.tagService.ApplicationContext;
import ntut.csie.tagService.useCase.assignedTag.AssignedTagModel;
import ntut.csie.tagService.useCase.assignedTag.get.GetAssignedTagsByBacklogItemIdInput;
import ntut.csie.tagService.useCase.assignedTag.get.GetAssignedTagsByBacklogItemIdOutput;
import ntut.csie.tagService.useCase.assignedTag.get.GetAssignedTagsByBacklogItemIdUseCase;

@Path("/backlog_items/{backlog_item_id}/assigned_tags")
@Singleton
public class GetAssignedTagsByBacklogItemIdRestfulAPI implements GetAssignedTagsByBacklogItemIdOutput {
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private GetAssignedTagsByBacklogItemIdUseCase getAssignedTagsByBacklogItemIdUseCase = applicationContext.newGetAssignedTagsByBacklogItemIdUseCase();
	
	private List<AssignedTagModel> assignedTagList;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized GetAssignedTagsByBacklogItemIdOutput getAssignedTagsByBacklogItemId(@PathParam("backlog_item_id") String backlogItemId) {
		GetAssignedTagsByBacklogItemIdOutput output = this;
		
		GetAssignedTagsByBacklogItemIdInput input = (GetAssignedTagsByBacklogItemIdInput) getAssignedTagsByBacklogItemIdUseCase;
		input.setBacklogItemId(backlogItemId);
		
		getAssignedTagsByBacklogItemIdUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public List<AssignedTagModel> getAssignedTagList() {
		return assignedTagList;
	}

	@Override
	public void setAssignedTagList(List<AssignedTagModel> assignedTagList) {
		this.assignedTagList = assignedTagList;
	}
}
