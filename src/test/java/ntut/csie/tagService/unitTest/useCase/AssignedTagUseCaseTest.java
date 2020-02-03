package ntut.csie.tagService.unitTest.useCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import ntut.csie.tagService.useCase.assignedTag.AssignedTag;
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
	private String tagId;
	
	@Before
	public void setUp() {
		fakeTagRepository = new FakeTagRepository();
		fakeAssignedTagRepository = new FakeAssignedTagRepository();
		
		testFactory = new TestFactory(fakeTagRepository, fakeAssignedTagRepository);
		
		backlogItemId = "1";
		productId = "2";
		String name = "Thesis Tag";
		tag = testFactory.newTag(name, productId);
		tagId = tag.getTagId();
	}
	
	@After
	public void tearDown() {
		fakeTagRepository.clearAll();
		fakeAssignedTagRepository.clearAll();
	}
	
	@Test
	public void Should_Success_When_AssignTagToBacklogItem() {
		AssignTagToBacklogItemOutput output = assignTagToBacklogItem(backlogItemId, tagId);
		
		boolean isAssignSuccess = output.isAssignSuccess();
		assertTrue(isAssignSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_AssignNotExistTagToBacklogItem() {
		AssignTagToBacklogItemOutput output = assignTagToBacklogItem(backlogItemId, null);
		
		boolean isAssignSuccess = output.isAssignSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Sorry, the tag is not exist!";
		assertFalse(isAssignSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	public void Should_ReturnErrorMessage_When_AssignTagToBacklogItemWithNullBacklogItemId() {
		AssignTagToBacklogItemOutput output = assignTagToBacklogItem(null, tagId);
		
		boolean isAssignSuccess = output.isAssignSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "The backlog item id of the assigned tag should be required!\n";
		assertFalse(isAssignSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_AssignTagToBacklogItemWithEmptyBacklogItemId() {
		AssignTagToBacklogItemOutput output = assignTagToBacklogItem("", tagId);
		
		boolean isAssignSuccess = output.isAssignSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "The backlog item id of the assigned tag should be required!\n";
		assertFalse(isAssignSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnAssignedTagList_When_GetAssignedTagsOfBacklogItem() {
		Tag otherTag = testFactory.newTag("Other", productId);
		String[] tagIds = {tagId, otherTag.getTagId()};
		
		int numberOfAssignedTags = tagIds.length;
		
		for(int i = 0; i < numberOfAssignedTags; i++) {
			assignTagToBacklogItem(backlogItemId, tagIds[i]);
		}
		
		GetAssignedTagsByBacklogItemIdOutput output = getAssignedTagsByBacklogItemId(backlogItemId);
		List<AssignedTagModel> assignedTagList = output.getAssignedTagList();
		
		for(int i = 0; i < numberOfAssignedTags; i++) {
			assertEquals(backlogItemId, assignedTagList.get(i).getBacklogItemId());
			assertEquals(tagIds[i], assignedTagList.get(i).getTagId());
		}
		assertEquals(numberOfAssignedTags, assignedTagList.size());
	}
	
	@Test
	public void Should_Success_When_UnassignTagFromBacklogItem() {
		AssignedTag assignedTag = testFactory.newAssignedTag(backlogItemId, tagId);
		String assignedTagId = assignedTag.getAssignedTagId();
		
		UnassignTagFromBacklogItemOutput output = unassignTagFromBacklogItem(assignedTagId);
		
		boolean isUnassignSuccess = output.isUnassignSuccess();
		assertTrue(isUnassignSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_UnassignNotExistAssignedTag() {
		String assignedTagId = null;
		UnassignTagFromBacklogItemOutput output = unassignTagFromBacklogItem(assignedTagId);
		
		boolean isUnassignSuccess = output.isUnassignSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Sorry, the assigned tag is not exist!";
		assertFalse(isUnassignSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	private AssignTagToBacklogItemOutput assignTagToBacklogItem(String backlogItemId, String tagId) {
		AssignTagToBacklogItemUseCase assignTagToBacklogItemUseCase = new AssignTagToBacklogItemUseCaseImpl(fakeTagRepository, fakeAssignedTagRepository);
		AssignTagToBacklogItemInput input = (AssignTagToBacklogItemInput) assignTagToBacklogItemUseCase;
		input.setBacklogItemId(backlogItemId);
		input.setTagId(tagId);
		AssignTagToBacklogItemOutput output = new AssignTagToBacklogItemRestfulAPI();
		assignTagToBacklogItemUseCase.execute(input, output);
		return output;
	}
	
	private GetAssignedTagsByBacklogItemIdOutput getAssignedTagsByBacklogItemId(String backlogItemId) {
		GetAssignedTagsByBacklogItemIdUseCase getAssignedTagsByBacklogItemIdUseCase = new GetAssignedTagsByBacklogItemIdUseCaseImpl(fakeAssignedTagRepository);
		GetAssignedTagsByBacklogItemIdInput input = (GetAssignedTagsByBacklogItemIdInput) getAssignedTagsByBacklogItemIdUseCase;
		input.setBacklogItemId(backlogItemId);
		GetAssignedTagsByBacklogItemIdOutput output = new GetAssignedTagsByBacklogItemIdRestfulAPI();
		getAssignedTagsByBacklogItemIdUseCase.execute(input, output);
		return output;
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
