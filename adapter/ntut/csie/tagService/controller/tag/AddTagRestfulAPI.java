package ntut.csie.tagService.controller.tag;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.tagService.ApplicationContext;
import ntut.csie.tagService.useCase.tag.add.AddTagInput;
import ntut.csie.tagService.useCase.tag.add.AddTagOutput;
import ntut.csie.tagService.useCase.tag.add.AddTagUseCase;

@Path("/tags")
@Singleton
public class AddTagRestfulAPI implements AddTagOutput {
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private AddTagUseCase addTagUseCase = applicationContext.newAddTagUseCase();
	
	private boolean addSuccess;
	private String errorMessage;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized AddTagOutput addTag(String tagInfo) {
		String name = "";
		String productId = "";
		
		AddTagOutput output = this;
		
		try {
			JSONObject tagJSON = new JSONObject(tagInfo);
			name = tagJSON.getString("name");
			productId = tagJSON.getString("productId");
		} catch (JSONException e) {
			e.printStackTrace();
			output.setAddSuccess(false);
			output.setErrorMessage("Sorry, please try again!");
			return output;
		}
		
		AddTagInput input = (AddTagInput) addTagUseCase;
		input.setName(name);
		input.setProductId(productId);
		
		addTagUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public boolean isAddSuccess() {
		return addSuccess;
	}

	@Override
	public void setAddSuccess(boolean addSuccess) {
		this.addSuccess = addSuccess;
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
