package ntut.csie.tagService.controller.tag;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.tagService.ApplicationContext;
import ntut.csie.tagService.useCase.tag.edit.EditTagInput;
import ntut.csie.tagService.useCase.tag.edit.EditTagOutput;
import ntut.csie.tagService.useCase.tag.edit.EditTagUseCase;

@Path("/tags")
@Singleton
public class EditTagRestfulAPI implements EditTagOutput {
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private EditTagUseCase editTagUseCase = applicationContext.newEditTagUseCase();
	
	private boolean editSuccess;
	private String errorMessage;
	
	@PUT
	@Path("/{tag_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized EditTagOutput editTag(
			@PathParam("tag_id") String tagId, 
			String tagInfo) {
		String name = "";
		
		EditTagOutput output = this;
		
		try {
			JSONObject tagJSON = new JSONObject(tagInfo);
			name = tagJSON.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
			output.setEditSuccess(false);
			output.setErrorMessage("Sorry, please try again!");
			return output;
		}
		
		EditTagInput input = (EditTagInput) editTagUseCase;
		input.setTagId(tagId);
		input.setName(name);
		
		editTagUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public boolean isEditSuccess() {
		return editSuccess;
	}

	@Override
	public void setEditSuccess(boolean editSuccess) {
		this.editSuccess = editSuccess;
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
