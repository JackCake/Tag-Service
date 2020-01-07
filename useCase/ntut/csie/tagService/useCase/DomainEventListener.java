package ntut.csie.tagService.useCase;

import ntut.csie.tagService.model.DomainEventPublisher;
import ntut.csie.tagService.model.DomainEventSubscriber;
import ntut.csie.tagService.model.tag.Tag;
import ntut.csie.tagService.model.tag.TagDeleted;
import ntut.csie.tagService.useCase.assignedTag.AssignedTag;
import ntut.csie.tagService.useCase.assignedTag.AssignedTagRepository;
import ntut.csie.tagService.useCase.tag.TagRepository;

public class DomainEventListener {
	private static DomainEventListener instance = null;
	
	private TagRepository tagRepository;
	private AssignedTagRepository assignedTagRepository;
	
	private DomainEventListener() {}
	
	public static synchronized DomainEventListener getInstance() {
		if(instance == null) {
			instance = new DomainEventListener();
		}
		return instance;
	}
	
	public void init(TagRepository tagRepository, AssignedTagRepository assignedTagRepository) {
		this.tagRepository = tagRepository;
		this.assignedTagRepository = assignedTagRepository;
		DomainEventPublisher.getInstance().reset();
		eventSubscribe();
	}
	
	private void eventSubscribe() {
		DomainEventPublisher.getInstance().subscribe(new DomainEventSubscriber<TagDeleted>() {

			@Override
			public void handleEvent(TagDeleted domainEvent) {
				int newOrderId = 0;
				for(Tag tag : tagRepository.getTagsByProductId(domainEvent.productId())) {
					tag.setOrderId(++newOrderId);
					try {
						tagRepository.save(tag);
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
				for(AssignedTag assignedTag : assignedTagRepository.getAssignedTagsByTagId(domainEvent.tagId())) {
					try {
						assignedTagRepository.remove(assignedTag);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public Class<TagDeleted> subscribedToEventType() {
				return TagDeleted.class;
			}
           
        });
	}
}
