package ntut.csie.tagService.useCase.tag.add;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.tagService.model.tag.Tag;
import ntut.csie.tagService.model.tag.TagBuilder;
import ntut.csie.tagService.useCase.tag.TagRepository;

public class AddTagUseCaseImpl implements AddTagUseCase, AddTagInput {
	private TagRepository tagRepository;
	
	private String name;
	private String productId;
	
	public AddTagUseCaseImpl(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}
	
	@Override
	public void execute(AddTagInput input, AddTagOutput output) {
		String name = input.getName();
		String productId = input.getProductId();
		List<Tag> tagList = new ArrayList<>(tagRepository.getTagsByProductId(productId));
		for(Tag tag : tagList) {
			if(tag.getName().equals(name)) {
				output.setAddSuccess(false);
				output.setErrorMessage("There is the same name of the tag!");
				return;
			}
		}
		int orderId = tagRepository.getTagsByProductId(productId).size() + 1;
		try {
			Tag tag = TagBuilder.newInstance()
					.orderId(orderId)
					.name(name)
					.productId(productId)
					.build();
			tagRepository.save(tag);
		} catch (Exception e) {
			output.setAddSuccess(false);
			output.setErrorMessage(e.getMessage());
			return;
		}
		output.setAddSuccess(true);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getProductId() {
		return productId;
	}

	@Override
	public void setProductId(String productId) {
		this.productId = productId;
	}
}
