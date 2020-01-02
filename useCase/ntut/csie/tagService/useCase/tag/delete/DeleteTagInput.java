package ntut.csie.tagService.useCase.tag.delete;

import ntut.csie.tagService.useCase.Input;

public interface DeleteTagInput extends Input {
	public String getTagId();
	
	public void setTagId(String tagId);
}
