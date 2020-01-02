package ntut.csie.tagService.useCase.tag;

import ntut.csie.tagService.model.tag.Tag;

public class ConvertTagToDTO {
	public static TagModel transform(Tag tag) {
		TagModel dto = new TagModel();
		dto.setTagId(tag.getTagId());
		dto.setOrderId(tag.getOrderId());
		dto.setName(tag.getName());
		dto.setProductId(tag.getProductId());
		return dto;
	}
}
