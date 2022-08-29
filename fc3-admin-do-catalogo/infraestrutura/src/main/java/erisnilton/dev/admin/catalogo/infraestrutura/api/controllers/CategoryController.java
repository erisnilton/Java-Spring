package erisnilton.dev.admin.catalogo.infraestrutura.api.controllers;

import erisnilton.dev.admin.catalogo.application.category.create.CreateCategoryCommand;
import erisnilton.dev.admin.catalogo.application.category.create.CreateCategoryOutput;
import erisnilton.dev.admin.catalogo.application.category.create.CreateCategoryUseCase;
import erisnilton.dev.admin.catalogo.application.category.delete.DeleteCategoryUseCase;
import erisnilton.dev.admin.catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import erisnilton.dev.admin.catalogo.application.category.retrieve.list.ListCategoriesUseCase;
import erisnilton.dev.admin.catalogo.application.category.update.UpdateCategoryCommand;
import erisnilton.dev.admin.catalogo.application.category.update.UpdateCategoryOutput;
import erisnilton.dev.admin.catalogo.application.category.update.UpdateCategoryUseCase;
import erisnilton.dev.admin.catalogo.domain.category.CategorySearchQuery;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;
import erisnilton.dev.admin.catalogo.domain.validation.handler.Notification;
import erisnilton.dev.admin.catalogo.infraestrutura.api.CategoryAPI;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CategoryResponse;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CategoryListResponse;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CreateCategoryResquest;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.UpdateCategoryRequest;
import erisnilton.dev.admin.catalogo.infraestrutura.category.presenters.CategoryApiPresenters;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;

    private final DeleteCategoryUseCase deleteCategoryUseCase;

    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(
            final CreateCategoryUseCase createCategoryUseCase,
            final GetCategoryByIdUseCase getCategoryByIdUseCase,
            final UpdateCategoryUseCase updateCategoryUseCase,
            final DeleteCategoryUseCase deleteCategoryUseCase,
            final ListCategoriesUseCase listCategoriesUseCase)
    {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
        this.listCategoriesUseCase = Objects.requireNonNull(listCategoriesUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(CreateCategoryResquest input) {
        final var aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );
        Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;
        Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess =
                output -> ResponseEntity.created(URI.create("/categories/" + output.id())).build();
        return this.createCategoryUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public Pagination<CategoryListResponse> listCategories(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String dir
    ) {
        return this.listCategoriesUseCase.execute(
                new CategorySearchQuery(page,perPage,search, sort,dir))
                .map(CategoryApiPresenters::present);

    }

    @Override
    public CategoryResponse getById(final String id) {
        return CategoryApiPresenters.present(this.getCategoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCategoryRequest input) {
        final var aCommand = UpdateCategoryCommand.with(
                id,
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );
        Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;
        Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.updateCategoryUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public void deleteById(final String anId) {
        this.deleteCategoryUseCase.execute(anId);
    }
}
