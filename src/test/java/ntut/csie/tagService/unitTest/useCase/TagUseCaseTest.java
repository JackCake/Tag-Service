package ntut.csie.tagService.unitTest.useCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.tagService.controller.tag.AddTagRestfulAPI;
import ntut.csie.tagService.controller.tag.DeleteTagRestfulAPI;
import ntut.csie.tagService.controller.tag.EditTagRestfulAPI;
import ntut.csie.tagService.controller.tag.GetTagsByProductIdRestfulAPI;
import ntut.csie.tagService.model.tag.Tag;
import ntut.csie.tagService.unitTest.factory.TestFactory;
import ntut.csie.tagService.unitTest.repository.FakeAssignedTagRepository;
import ntut.csie.tagService.unitTest.repository.FakeTagRepository;
import ntut.csie.tagService.useCase.DomainEventListener;
import ntut.csie.tagService.useCase.tag.TagModel;
import ntut.csie.tagService.useCase.tag.add.AddTagInput;
import ntut.csie.tagService.useCase.tag.add.AddTagOutput;
import ntut.csie.tagService.useCase.tag.add.AddTagUseCase;
import ntut.csie.tagService.useCase.tag.add.AddTagUseCaseImpl;
import ntut.csie.tagService.useCase.tag.delete.DeleteTagInput;
import ntut.csie.tagService.useCase.tag.delete.DeleteTagOutput;
import ntut.csie.tagService.useCase.tag.delete.DeleteTagUseCase;
import ntut.csie.tagService.useCase.tag.delete.DeleteTagUseCaseImpl;
import ntut.csie.tagService.useCase.tag.edit.EditTagInput;
import ntut.csie.tagService.useCase.tag.edit.EditTagOutput;
import ntut.csie.tagService.useCase.tag.edit.EditTagUseCase;
import ntut.csie.tagService.useCase.tag.edit.EditTagUseCaseImpl;
import ntut.csie.tagService.useCase.tag.get.GetTagsByProductIdInput;
import ntut.csie.tagService.useCase.tag.get.GetTagsByProductIdOutput;
import ntut.csie.tagService.useCase.tag.get.GetTagsByProductIdUseCase;
import ntut.csie.tagService.useCase.tag.get.GetTagsByProductIdUseCaseImpl;

public class TagUseCaseTest {
	private FakeTagRepository fakeTagRepository;
	private FakeAssignedTagRepository fakeAssignedTagRepository;
	
	private TestFactory testFactory;
	
	private String productId;
	
	@Before
	public void setUp() {
		fakeTagRepository = new FakeTagRepository();
		fakeAssignedTagRepository = new FakeAssignedTagRepository();
		DomainEventListener.getInstance().init(fakeTagRepository, fakeAssignedTagRepository);
		
		testFactory = new TestFactory(fakeTagRepository, fakeAssignedTagRepository);
		
		productId = "1";
	}
	
	@After
	public void tearDown() {
		fakeTagRepository.clearAll();
	}
	
	@Test
	public void Should_Success_When_AddTag() {
		String name = "Thesis Tag";
		
		AddTagOutput output = addNewTag(name, productId);
		
		boolean isAddSuccess = output.isAddSuccess();
		assertTrue(isAddSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_AddTagWithNullParamemters() {
		String name = null;
		
		AddTagOutput output = addNewTag(name, null);
		
		boolean isAddSuccess = output.isAddSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "The name of the tag should be required!\n" +
				"The product id of the tag should be required!\n";
		assertFalse(isAddSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_AddTagWithEmptyParamemters() {
		String name = "";
		
		AddTagOutput output = addNewTag(name, "");
		
		boolean isAddSuccess = output.isAddSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "The name of the tag should be required!\n" +
				"The product id of the tag should be required!\n";
		assertFalse(isAddSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_AddSameTag() {
		String name = "Thesis Tag";
		
		testFactory.newTag(name, productId);
		
		AddTagOutput output = addNewTag(name, productId);
		
		boolean isAddSuccess = output.isAddSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "There is the same name of the tag!";
		assertFalse(isAddSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnTagList_When_GetTagsOfProduct() {
		String[] names = {"Bug", "The suggest of the teacher"};
		
		int numberOfTags = names.length;
		
		for(int i = 0; i < numberOfTags; i++) {
			testFactory.newTag(names[i], productId);
		}
		
		GetTagsByProductIdOutput output = getTagsByProductId(productId);
		List<TagModel> tagList = output.getTagList();
		
		for(int i = 0; i < numberOfTags; i++) {
			assertEquals(i + 1, tagList.get(i).getOrderId());
			assertEquals(names[i], tagList.get(i).getName());
		}
		assertEquals(numberOfTags, tagList.size());
	}
	
	@Test
	public void Should_Success_When_EditTag() {
		String name = "Thesis Tag";
		
		Tag editedTag = testFactory.newTag(name, productId);
		String editedTagId = editedTag.getTagId();
		String editedName = "Thesis";
		
		EditTagOutput output = editTag(editedTagId, editedName);
		
		boolean isEditSuccess = output.isEditSuccess();
		assertTrue(isEditSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_EditNotExistTag() {
		String editedTagId = null;
		String editedName = "Thesis";
		
		EditTagOutput output = editTag(editedTagId, editedName);
		
		boolean isEditSuccess = output.isEditSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Sorry, the tag is not exist!";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_EditTagWithNullName() {
		String name = "Thesis";
		
		Tag editedTag = testFactory.newTag(name, productId);
		String editedTagId = editedTag.getTagId();
		String editedName = null;
		
		EditTagOutput output = editTag(editedTagId, editedName);
		
		boolean isEditSuccess = output.isEditSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "The name of the tag should be required!\n";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_EditTagWithEmptyName() {
		String name = "Thesis";
		
		Tag editedTag = testFactory.newTag(name, productId);
		String editedTagId = editedTag.getTagId();
		String editedName = "";
		
		EditTagOutput output = editTag(editedTagId, editedName);
		
		boolean isEditSuccess = output.isEditSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "The name of the tag should be required!\n";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_Success_When_EditTagWithNothing() {
		String name = "Thesis Tag";
		
		Tag editedTag = testFactory.newTag(name, productId);
		String editedTagId = editedTag.getTagId();
		
		EditTagOutput output = editTag(editedTagId, name);
		
		boolean isEditSuccess = output.isEditSuccess();
		assertTrue(isEditSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_EditSameTag() {
		String[] names = {"Bug", "The suggest of the teacher"};
		int numberOfNames = names.length;
		
		List<Tag> tagList = new ArrayList<>();
		for(int i = 0; i < numberOfNames; i++) {
			Tag tag = testFactory.newTag(names[i], productId);
			tagList.add(tag);
		}
		
		String editedTagId = tagList.get(1).getTagId();
		EditTagOutput output = editTag(editedTagId, names[0]);
		
		boolean isEditSuccess = output.isEditSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "There is the same name of the tag!";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_Success_When_DeleteTag() {
		String name = "Thesis Tag";
		
		Tag deletedTag = testFactory.newTag(name, productId);
		
		String deletedTagId = deletedTag.getTagId();
		
		DeleteTagOutput output = deleteTag(deletedTagId);
		
		boolean isDeleteSuccess = output.isDeleteSuccess();
		assertTrue(isDeleteSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_DeleteNotExistTag() {
		String deletedTagId = null;
		
		DeleteTagOutput output = deleteTag(deletedTagId);
		
		boolean isDeleteSuccess = output.isDeleteSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedDeleteMessage = "Sorry, the tag is not exist!";
		assertFalse(isDeleteSuccess);
		assertEquals(expectedDeleteMessage, errorMessage);
	}
	
	@Test
	public void Should_OrderIdUpdated_When_DeleteTag() {
		String[] names = {"Bug", "The suggest of the teacher", "Thesis Tag"};
		int numberOfNames = names.length;
		
		List<Tag> tagList = new ArrayList<>();
		for(int i = 0; i < numberOfNames; i++) {
			Tag tag = testFactory.newTag(names[i], productId);
			tagList.add(tag);
		}
		
		String deletedTagId = tagList.get(1).getTagId();
		deleteTag(deletedTagId);
		
		GetTagsByProductIdOutput output = getTagsByProductId(productId);
		List<TagModel> tagListAfterDeleted = output.getTagList();
		
		for(int i = 0; i < tagListAfterDeleted.size(); i++) {
			assertEquals(i + 1, tagListAfterDeleted.get(i).getOrderId());
		}
	}
	
	@Test
	public void Should_AssignedTagDeleted_When_DeleteTag() {
		String name = "Thesis Tag";
		Tag tag = testFactory.newTag(name, productId);
		String tagId = tag.getTagId();
		
		String[] backlogItemIds = {"1", "2"};
		int numberOfBacklogItemIds = backlogItemIds.length;
		for(int i = 0; i < numberOfBacklogItemIds; i++) {
			testFactory.newAssignedTag(backlogItemIds[i], tagId);
		}
		
		assertEquals(2, fakeAssignedTagRepository.getAssignedTagsByTagId(tagId).size());
		
		deleteTag(tagId);
		
		assertEquals(0, fakeAssignedTagRepository.getAssignedTagsByTagId(tagId).size());
	}
	
	private AddTagOutput addNewTag(String name, String productId) {
		AddTagUseCase addTagUseCase = new AddTagUseCaseImpl(fakeTagRepository);
		AddTagInput input = (AddTagInput) addTagUseCase;
		input.setName(name);
		input.setProductId(productId);
		AddTagOutput output = new AddTagRestfulAPI();
		addTagUseCase.execute(input, output);
		return output;
	}
	
	private GetTagsByProductIdOutput getTagsByProductId(String productId) {
		GetTagsByProductIdUseCase getTagsByProductIdUseCase = new GetTagsByProductIdUseCaseImpl(fakeTagRepository);
		GetTagsByProductIdInput input = (GetTagsByProductIdInput) getTagsByProductIdUseCase;
		input.setProductId(productId);
		GetTagsByProductIdOutput output = new GetTagsByProductIdRestfulAPI();
		getTagsByProductIdUseCase.execute(input, output);
		return output;
	}
	
	private EditTagOutput editTag(String tagId, String editedName) {
		EditTagUseCase editTagUseCase = new EditTagUseCaseImpl(fakeTagRepository);
		EditTagInput input = (EditTagInput) editTagUseCase;
		input.setTagId(tagId);
		input.setName(editedName);
		EditTagOutput output = new EditTagRestfulAPI();
		editTagUseCase.execute(input, output);
		return output;
	}
	
	private DeleteTagOutput deleteTag(String tagId) {
		DeleteTagUseCase deleteTagUseCase = new DeleteTagUseCaseImpl(fakeTagRepository);
		DeleteTagInput input = (DeleteTagInput) deleteTagUseCase;
		input.setTagId(tagId);
		DeleteTagOutput output = new DeleteTagRestfulAPI();
		deleteTagUseCase.execute(input, output);
		return output;
	}
}
