package ntut.csie.tagService.useCase.tag.get;

import java.util.List;

import ntut.csie.tagService.useCase.Output;
import ntut.csie.tagService.useCase.tag.TagModel;

public interface GetTagsByProductIdOutput extends Output {
	public List<TagModel> getTagList();
	
	public void setTagList(List<TagModel> tagList); 
}
