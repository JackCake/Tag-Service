package ntut.csie.tagService.gateways.repository.tag;

import ntut.csie.tagService.model.tag.Tag;

public class TagMapper {
	public Tag transformToTag(TagData data) {
		Tag tag = new Tag();
		tag.setTagId(data.getTagId());
		tag.setOrderId(data.getOrderId());
		tag.setName(data.getName());
		tag.setProductId(data.getProductId());
		return tag;
	}
	
	public TagData transformToTagData(Tag tag) {
		TagData data = new TagData();
		data.setTagId(tag.getTagId());
		data.setOrderId(tag.getOrderId());
		data.setName(tag.getName());
		data.setProductId(tag.getProductId());
		return data;
	}
}
