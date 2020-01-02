package ntut.csie.tagService.useCase.tag.delete;

import ntut.csie.tagService.model.tag.Tag;
import ntut.csie.tagService.useCase.tag.TagRepository;

public class DeleteTagUseCaseImpl implements DeleteTagUseCase, DeleteTagInput {
	private TagRepository tagRepository;
	
	private String tagId;
	
	public DeleteTagUseCaseImpl(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}
	
	@Override
	public void execute(DeleteTagInput input, DeleteTagOutput output) {
		String tagId = input.getTagId();
		Tag tag = tagRepository.getTagById(tagId);
		if(tag == null) {
			output.setDeleteSuccess(false);
			output.setErrorMessage("Sorry, the tag is not exist!");
			return;
		}
		try {
			tagRepository.remove(tag);
		} catch (Exception e) {
			output.setDeleteSuccess(false);
			output.setErrorMessage(e.getMessage());
			return;
		}
		tag.markAsRemoved();
		output.setDeleteSuccess(true);
	}

	@Override
	public String getTagId() {
		return tagId;
	}

	@Override
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
}
