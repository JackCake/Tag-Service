package ntut.csie.tagService.controller.tag;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.tagService.ApplicationContext;
import ntut.csie.tagService.useCase.tag.TagModel;
import ntut.csie.tagService.useCase.tag.get.GetTagsByProductIdInput;
import ntut.csie.tagService.useCase.tag.get.GetTagsByProductIdOutput;
import ntut.csie.tagService.useCase.tag.get.GetTagsByProductIdUseCase;

@Path("/products/{product_id}/tags")
@Singleton
public class GetTagsByProductIdRestfulAPI implements GetTagsByProductIdOutput {
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private GetTagsByProductIdUseCase getTagsByProductIdUseCase = applicationContext.newGetTagsByProductIdUseCase();
	
	private List<TagModel> tagList;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized GetTagsByProductIdOutput getTagsByProductId(@PathParam("product_id") String productId) {
		GetTagsByProductIdOutput output = this;
		
		GetTagsByProductIdInput input = (GetTagsByProductIdInput) getTagsByProductIdUseCase;
		input.setProductId(productId);
		
		getTagsByProductIdUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public List<TagModel> getTagList() {
		return tagList;
	}

	@Override
	public void setTagList(List<TagModel> tagList) {
		this.tagList = tagList;
	}
}
