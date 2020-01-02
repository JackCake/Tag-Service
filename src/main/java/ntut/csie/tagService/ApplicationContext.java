package ntut.csie.tagService;

import ntut.csie.tagService.gateways.repository.tag.MySqlTagRepositoryImpl;
import ntut.csie.tagService.useCase.tag.TagRepository;
import ntut.csie.tagService.useCase.tag.add.AddTagUseCase;
import ntut.csie.tagService.useCase.tag.add.AddTagUseCaseImpl;
import ntut.csie.tagService.useCase.tag.delete.DeleteTagUseCase;
import ntut.csie.tagService.useCase.tag.delete.DeleteTagUseCaseImpl;
import ntut.csie.tagService.useCase.tag.edit.EditTagUseCase;
import ntut.csie.tagService.useCase.tag.edit.EditTagUseCaseImpl;
import ntut.csie.tagService.useCase.tag.get.GetTagsByProductIdUseCase;
import ntut.csie.tagService.useCase.tag.get.GetTagsByProductIdUseCaseImpl;

public class ApplicationContext {
	private static ApplicationContext instance = null;
	
	private static TagRepository tagRepository = null;
	
	private AddTagUseCase addTagUseCase;
	private GetTagsByProductIdUseCase getTagsByProductIdUseCase;
	private EditTagUseCase editTagUseCase;
	private DeleteTagUseCase deleteTagUseCase;
	
	private ApplicationContext() {}
	
	public static synchronized ApplicationContext getInstance() {
		if(instance == null){
			return new ApplicationContext();
		}
		return instance;
	}
	
	public TagRepository newTagRepository() {
		if(tagRepository == null) {
			tagRepository = new MySqlTagRepositoryImpl();
		}
		return tagRepository;
	}
	
	public AddTagUseCase newAddTagUseCase() {
		addTagUseCase = new AddTagUseCaseImpl(newTagRepository());
		return addTagUseCase;
	}
	
	public GetTagsByProductIdUseCase newGetTagsByProductIdUseCase() {
		getTagsByProductIdUseCase = new GetTagsByProductIdUseCaseImpl(newTagRepository());
		return getTagsByProductIdUseCase;
	}
	
	public EditTagUseCase newEditTagUseCase() {
		editTagUseCase = new EditTagUseCaseImpl(newTagRepository());
		return editTagUseCase;
	}
	
	public DeleteTagUseCase newDeleteTagUseCase() {
		deleteTagUseCase = new DeleteTagUseCaseImpl(newTagRepository());
		return deleteTagUseCase;
	}
}
