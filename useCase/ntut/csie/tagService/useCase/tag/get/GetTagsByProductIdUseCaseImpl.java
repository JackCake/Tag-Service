package ntut.csie.tagService.useCase.tag.get;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.tagService.model.tag.Tag;
import ntut.csie.tagService.useCase.tag.TagRepository;
import ntut.csie.tagService.useCase.tag.ConvertTagToDTO;
import ntut.csie.tagService.useCase.tag.TagModel;

public class GetTagsByProductIdUseCaseImpl implements GetTagsByProductIdUseCase, GetTagsByProductIdInput {
	private TagRepository tagRepository;
	
	private String productId;
	
	public GetTagsByProductIdUseCaseImpl(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}
	
	@Override
	public void execute(GetTagsByProductIdInput input,
			GetTagsByProductIdOutput output) {
		List<TagModel> tagList = new ArrayList<>();
		for(Tag tag : tagRepository.getTagsByProductId(input.getProductId())) {
			tagList.add(ConvertTagToDTO.transform(tag));
		}
		output.setTagList(tagList);
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
