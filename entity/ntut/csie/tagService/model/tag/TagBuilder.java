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
		String exceptionMessage = "";
		if(name == null || name.isEmpty()) {
			exceptionMessage += "The name of the tag should be required!\n";
		}
		if(productId == null || productId.isEmpty()) {
			exceptionMessage += "The product id of the tag should be required!\n";
		}
		if(!exceptionMessage.isEmpty()) {
			throw new Exception(exceptionMessage);
		}
		
		tagId = UUID.randomUUID().toString();
		Tag tag = new Tag(tagId, name, productId);
		tag.setOrderId(orderId);
		return tag;
	}
}
