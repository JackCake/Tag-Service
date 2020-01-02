package ntut.csie.tagService.useCase.tag.edit;

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
		Tag tag = tagRepository.getTagById(tagId);
		if(tag == null) {
			output.setEditSuccess(false);
			output.setErrorMessage("Sorry, the tag is not exist!");
			return;
		}
		
		tag.setName(input.getName());
		
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
