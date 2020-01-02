package ntut.csie.tagService.model.tag;

import java.util.UUID;

public class TagBuilder {
	private String tagId;
	private int orderId;
	private String name;
	private String productId;
	
	public static TagBuilder newInstance() {
		return new TagBuilder();
	}
	
	public TagBuilder orderId(int orderId) {
		this.orderId = orderId;
		return this;
	}
	
	public TagBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public TagBuilder productId(String productId) {
		this.productId = productId;
		return this;
	}
	
	public Tag build() throws Exception {
		if(name == null) {
			throw new Exception("The name of the tag should not be null.");
		}
		tagId = UUID.randomUUID().toString();
		Tag tag = new Tag(tagId, name, productId);
		tag.setOrderId(orderId);
		return tag;
	}
}
