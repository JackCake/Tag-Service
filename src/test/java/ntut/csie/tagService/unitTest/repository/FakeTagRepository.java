package ntut.csie.tagService.unitTest.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ntut.csie.tagService.model.tag.Tag;
import ntut.csie.tagService.useCase.tag.TagRepository;

public class FakeTagRepository implements TagRepository {
	private Map<String, Tag> tags;

	public FakeTagRepository() {
		tags = Collections.synchronizedMap(new LinkedHashMap<String, Tag>());
	}
	
	@Override
	public void save(Tag tag) {
		tags.put(tag.getTagId(), tag);
	}

	@Override
	public void remove(Tag tag) {
		tags.remove(tag.getTagId());
	}

	@Override
	public Tag getTagById(String tagId) {
		return tags.get(tagId);
	}

	@Override
	public Collection<Tag> getTagsByProductId(String productId) {
		List<Tag> tagList = new ArrayList<>();
		for(Tag tag : tags.values()) {
			if(tag.getProductId().equals(productId)) {
				tagList.add(tag);
			}
		}
		return tagList;
	}
	
	public void clearAll() {
		tags.clear();
	}
}
