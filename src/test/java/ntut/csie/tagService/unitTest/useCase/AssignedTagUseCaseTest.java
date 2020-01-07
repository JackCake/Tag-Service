package ntut.csie.tagService.unitTest.useCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.tagService.controller.assignedTag.AssignTagToBacklogItemRestfulAPI;
import ntut.csie.tagService.controller.assignedTag.GetAssignedTagsByBacklogItemIdRestfulAPI;
import ntut.csie.tagService.controller.assignedTag.UnassignTagFromBacklogItemRestfulAPI;
import ntut.csie.tagService.model.tag.Tag;
import ntut.csie.tagService.unitTest.factory.TestFactory;
import ntut.csie.tagService.unitTest.repository.FakeAssignedTagRepository;
import ntut.csie.tagService.unitTest.repository.FakeTagRepository;
import ntut.csie.tagService.useCase.assignedTag.AssignedTagModel;
import ntut.csie.tagService.useCase.assignedTag.assign.AssignTagToBacklogItemInput;
import ntut.csie.tagService.useCase.assignedTag.assign.AssignTagToBacklogItemOutput;
import ntut.csie.tagService.useCase.assignedTag.assign.AssignTagToBacklogItemUseCase;
import ntut.csie.tagService.useCase.assignedTag.assign.AssignTagToBacklogItemUseCaseImpl;
import ntut.csie.tagService.useCase.assignedTag.get.GetAssignedTagsByBacklogItemIdInput;
import ntut.csie.tagService.useCase.assignedTag.get.GetAssignedTagsByBacklogItemIdOutput;
import ntut.csie.tagService.useCase.assignedTag.get.GetAssignedTagsByBacklogItemIdUseCase;
import ntut.csie.tagService.useCase.assignedTag.get.GetAssignedTagsByBacklogItemIdUseCaseImpl;
import ntut.csie.tagService.useCase.assignedTag.unassign.UnassignTagFromBacklogItemInput;
import ntut.csie.tagService.useCase.assignedTag.unassign.UnassignTagFromBacklogItemOutput;
import ntut.csie.tagService.useCase.assignedTag.unassign.UnassignTagFromBacklogItemUseCase;
import ntut.csie.tagService.useCase.assignedTag.unassign.UnassignTagFromBacklogItemUseCaseImpl;

public class AssignedTagUseCaseTest {
	private FakeTagRepository fakeTagRepository;
	private FakeAssignedTagRepository fakeAssignedTagRepository;
	
	private TestFactory testFactory;
	
	private Tag tag;
	private String backlogItemId;
	private String productId;
	
	@Before
	public void setUp() {
		fakeTagRepository = new FakeTagRepository();
		fakeAssignedTagRepository = new FakeAssignedTagRepository();
		
		testFactory = new TestFactory(fakeTagRepository, fakeAssignedTagRepository);
		
		backlogItemId = "1";
		productId = "2";
		String name = "Thesis Tag";
		tag = testFactory.newTag(name, productId);
	}
	
	@After
	public void tearDown() {
		fakeTagRepository.clearAll();
		fakeAssignedTagRepository.clearAll();
	}
	
	@Test
	public void Should_Success_When_AssignTagToBacklogItem() {
		AssignTagToBacklogItemOutput output = assignTagToBacklogItem(backlogItemId, tag.getTagId());
		
		boolean isAssignSuccess = output.isAssignSuccess();
		assertTrue(isAssignSuccess);
	}
	
	@Test
	public void Should_ReturnAssignedTagList_When_GetAssignedTagsByBacklogItemId() {
		Tag otherTag = testFactory.newTag("Other", productId);
		String[] tagIds = {tag.getTagId(), otherTag.getTagId()};
		
		int numberOfCommittedBacklogItems = tagIds.length;
		
		for(int i = 0; i < numberOfCommittedBacklogItems; i++) {
			assignTagToBacklogItem(backlogItemId, tagIds[i]);
		}
		
		assertEquals(numberOfCommittedBacklogItems, getAssignedTagsByBacklogItemId(backlogItemId).size());
	}
	
	@Test
	public void Should_Success_When_UnassignTagFromBacklogItem() {
		assignTagToBacklogItem(backlogItemId, tag.getTagId());
		List<AssignedTagModel> assignedTagList = new ArrayList<>(getAssignedTagsByBacklogItemId(backlogItemId));
		String assignedTagId = assignedTagList.get(assignedTagList.size() - 1).getAssignedTagId();
		
		UnassignTagFromBacklogItemOutput output = unassignTagFromBacklogItem(assignedTagId);
		
		boolean isUnassignSuccess = output.isUnassignSuccess();
		assertTrue(isUnassignSuccess);
	}
	
	private AssignTagToBacklogItemOutput assignTagToBacklogItem(String backlogItemId, String tagId) {
		AssignTagToBacklogItemUseCase assignTagToBacklogItemUseCase = new AssignTagToBacklogItemUseCaseImpl(fakeAssignedTagRepository);
		AssignTagToBacklogItemInput input = (AssignTagToBacklogItemInput) assignTagToBacklogItemUseCase;
		input.setBacklogItemId(backlogItemId);
		input.setTagId(tagId);
		AssignTagToBacklogItemOutput output = new AssignTagToBacklogItemRestfulAPI();
		assignTagToBacklogItemUseCase.execute(input, output);
		return output;
	}
	
	private List<AssignedTagModel> getAssignedTagsByBacklogItemId(String backlogItemId) {
		GetAssignedTagsByBacklogItemIdUseCase getAssignedTagsByBacklogItemIdUseCase = new GetAssignedTagsByBacklogItemIdUseCaseImpl(fakeAssignedTagRepository);
		GetAssignedTagsByBacklogItemIdInput input = (GetAssignedTagsByBacklogItemIdInput) getAssignedTagsByBacklogItemIdUseCase;
		input.setBacklogItemId(backlogItemId);
		GetAssignedTagsByBacklogItemIdOutput output = new GetAssignedTagsByBacklogItemIdRestfulAPI();
		getAssignedTagsByBacklogItemIdUseCase.execute(input, output);
		return output.getAssignedTagList();
	}
	
	private UnassignTagFromBacklogItemOutput unassignTagFromBacklogItem(String assignedTagId) {
		UnassignTagFromBacklogItemUseCase unassignTagFromBacklogItemUseCase = new UnassignTagFromBacklogItemUseCaseImpl(fakeAssignedTagRepository);
		UnassignTagFromBacklogItemInput input = (UnassignTagFromBacklogItemInput) unassignTagFromBacklogItemUseCase;
		input.setAssignedTagId(assignedTagId);
		UnassignTagFromBacklogItemOutput output = new UnassignTagFromBacklogItemRestfulAPI();
		unassignTagFromBacklogItemUseCase.execute(input, output);
		return output;
	}
}
