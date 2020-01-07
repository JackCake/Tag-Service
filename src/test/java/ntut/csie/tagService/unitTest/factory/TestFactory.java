package ntut.csie.tagService.unitTest.factory;

import ntut.csie.tagService.model.tag.Tag;
import ntut.csie.tagService.model.tag.TagBuilder;
import ntut.csie.tagService.unitTest.repository.FakeAssignedTagRepository;
import ntut.csie.tagService.unitTest.repository.FakeTagRepository;
import ntut.csie.tagService.useCase.assignedTag.AssignedTag;
import ntut.csie.tagService.useCase.assignedTag.AssignedTagBuilder;

public class TestFactory {
	private FakeTagRepository fakeTagRepository;
	private FakeAssignedTagRepository fakeAssignedTagRepository;
	
	public TestFactory(FakeTagRepository fakeTagRepository, FakeAssignedTagRepository fakeAssignedTagRepository) {
		this.fakeTagRepository = fakeTagRepository;
		this.fakeAssignedTagRepository = fakeAssignedTagRepository;
	}
	
	public Tag newTag(String name, String productId) {
		int orderId = fakeTagRepository.getTagsByProductId(productId).size() + 1;
		Tag tag = null;
		try {
			tag = TagBuilder.newInstance()
					.orderId(orderId)
					.name(name)
					.productId(productId)
					.build();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		fakeTagRepository.save(tag);
		return tag;
	}
	
	public AssignedTag newAssignedTag(String backlogItemId, String tagId) {
		AssignedTag assignedTag = null;
		try {
			assignedTag = AssignedTagBuilder.newInstance()
					.backlogItemId(backlogItemId)
					.tagId(tagId)
					.build();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		fakeAssignedTagRepository.save(assignedTag);
		return assignedTag;
	}
}
