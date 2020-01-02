package ntut.csie.tagService.model.tag;

import ntut.csie.tagService.model.DomainEventPublisher;

public class Tag {
	private String tagId;
	private int orderId;
	private String name;
	private String productId;
	
	public Tag() {}
	
	public Tag(String tagId, String name, String productId) {
		this.setTagId(tagId);
		this.setName(name);
		this.setProductId(productId);
	}
	
	public void markAsRemoved() {
		DomainEventPublisher.getInstance().publish(new TagDeleted(
				tagId, productId));
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
}
