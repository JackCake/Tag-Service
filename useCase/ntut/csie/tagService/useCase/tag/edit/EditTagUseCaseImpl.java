package ntut.csie.tagService.useCase.tag.edit;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.tagService.model.tag.Tag;
import ntut.csie.tagService.useCase.tag.TagRepository;

public class EditTagUseCaseImpl implements EditTagUseCase, EditTagInput {
	private TagRepository tagRepository;
	
	private String tagId;
	private String name;
	
	public EditTagUseCaseImpl(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}
	
	@Override
	public void execute(EditTagInput input, EditTagOutput output) {
		String tagId = input.getTagId();
		String name = input.getName();
		Tag tag = tagRepository.getTagById(tagId);
		if(tag == null) {
			output.setEditSuccess(false);
			output.setErrorMessage("Sorry, the tag is not exist!");
			return;
		}
		String exceptionMessage = "";
		if(name == null || name.isEmpty()) {
			exceptionMessage += "The name of the tag should be required!\n";
		}
		if(!exceptionMessage.isEmpty()) {
			output.setEditSuccess(false);
			output.setErrorMessage(exceptionMessage);
			return;
		}
		String productId = tag.getProductId();
		List<Tag> tagList = new ArrayList<>(tagRepository.getTagsByProductId(productId));
		for(Tag _tag : tagList) {
			if(!_tag.getTagId().equals(tagId) && _tag.getName().equals(name)) {
				output.setEditSuccess(false);
				output.setErrorMessage("There is the same name of the tag!");
				return;
			}
		}
		
		tag.setName(name);
		
		try {
			tagRepository.save(tag);
		} catch (Exception e) {
			output.setEditSuccess(false);
			output.setErrorMessage(e.getMessage());
			return;
		}
		output.setEditSuccess(true);
	}

	@Override
	public String getTagId() {
		return tagId;
	}

	@Override
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}
