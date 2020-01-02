package ntut.csie.tagService.useCase.tag;

import java.util.Collection;

import ntut.csie.tagService.model.tag.Tag;

public interface TagRepository {
	public void save(Tag tag) throws Exception;
	
	public void remove(Tag tag) throws Exception;
	
	public Tag getTagById(String tagId);
	
	public Collection<Tag> getTagsByProductId(String productId);
}
