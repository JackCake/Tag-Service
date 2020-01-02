package ntut.csie.tagService.model.tag;

import java.util.Date;

import ntut.csie.tagService.model.DateProvider;
import ntut.csie.tagService.model.DomainEvent;

public class TagDeleted implements DomainEvent {
	private Date occurredOn;
	private String tagId;
	private String productId;

	public TagDeleted(String tagId, String productId) {
		this.occurredOn = DateProvider.now();
		this.tagId = tagId;
		this.productId = productId;
	}
	
	@Override
	public Date occurredOn() {
		return occurredOn;
	}
	
	public String tagId() {
		return tagId;
	}
	
	public String productId() {
		return productId;
	}
}
