package ntut.csie.tagService.controller.tag;

import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import ntut.csie.tagService.ApplicationContext;
import ntut.csie.tagService.useCase.tag.delete.DeleteTagInput;
import ntut.csie.tagService.useCase.tag.delete.DeleteTagOutput;
import ntut.csie.tagService.useCase.tag.delete.DeleteTagUseCase;

@Path("/tags")
@Singleton
public class DeleteTagRestfulAPI implements DeleteTagOutput {
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private DeleteTagUseCase deleteTagUseCase = applicationContext.newDeleteTagUseCase();

	private boolean deleteSuccess;
	private String errorMessage;
	
	@DELETE
	@Path("/{tag_id}")
	public synchronized DeleteTagOutput deleteTag(@PathParam("tag_id") String tagId) {
		DeleteTagOutput output = this;
		
		DeleteTagInput input = (DeleteTagInput) deleteTagUseCase;
		input.setTagId(tagId);
		
		deleteTagUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public boolean isDeleteSuccess() {
		return deleteSuccess;
	}

	@Override
	public void setDeleteSuccess(boolean deleteSuccess) {
		this.deleteSuccess = deleteSuccess;
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
