package ntut.csie.tagService.controller.assignedTag;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.tagService.ApplicationContext;
import ntut.csie.tagService.useCase.assignedTag.assign.AssignTagToBacklogItemInput;
import ntut.csie.tagService.useCase.assignedTag.assign.AssignTagToBacklogItemOutput;
import ntut.csie.tagService.useCase.assignedTag.assign.AssignTagToBacklogItemUseCase;

@Path("/backlog_items/{backlog_item_id}/assigned_tags")
@Singleton
public class AssignTagToBacklogItemRestfulAPI implements AssignTagToBacklogItemOutput {
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private AssignTagToBacklogItemUseCase assignTagToBacklogItemUseCase = applicationContext.newAssignTagToBacklogItemUseCase();
	
	private boolean assignSuccess;
	private String errorMessage;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized AssignTagToBacklogItemOutput assignTagToBacklogItem(
			@PathParam("backlog_item_id") String backlogItemId, 
			String assignedTagInfo) {
		String tagId = "";
		
		AssignTagToBacklogItemOutput output = this;
		
		try {
			JSONObject assignedTagJSON = new JSONObject(assignedTagInfo);
			tagId = assignedTagJSON.getString("tagId");
		} catch (JSONException e) {
			e.printStackTrace();
			output.setAssignSuccess(false);
			output.setErrorMessage("Sorry, there is the service problem when assign the tag to the backlog item. Please contact to the system administrator!");
			return output;
		}
		
		AssignTagToBacklogItemInput input = (AssignTagToBacklogItemInput) assignTagToBacklogItemUseCase;
		input.setBacklogItemId(backlogItemId);
		input.setTagId(tagId);
		
		assignTagToBacklogItemUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public boolean isAssignSuccess() {
		return assignSuccess;
	}

	@Override
	public void setAssignSuccess(boolean assignSuccess) {
		this.assignSuccess = assignSuccess;
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
