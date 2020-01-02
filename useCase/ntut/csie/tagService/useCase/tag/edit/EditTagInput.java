package ntut.csie.tagService.useCase.tag.edit;

import ntut.csie.tagService.useCase.Input;

public interface EditTagInput extends Input {
	public String getTagId();
	
	public void setTagId(String tagId);
	
	public String getName();
	
	public void setName(String name);
}
