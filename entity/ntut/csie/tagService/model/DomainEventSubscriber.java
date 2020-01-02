package ntut.csie.tagService.model;

public interface DomainEventSubscriber<T> {
	public void handleEvent(final T domainEvent);
	public Class<T> subscribedToEventType();
}
