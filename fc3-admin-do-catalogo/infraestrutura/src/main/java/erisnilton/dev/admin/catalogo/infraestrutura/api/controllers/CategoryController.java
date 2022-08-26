package erisnilton.dev.admin.catalogo.infraestrutura.api.controllers;

import erisnilton.dev.admin.catalogo.application.category.create.CreateCategoryCommand;
import erisnilton.dev.admin.catalogo.application.category.create.CreateCategoryOutput;
import erisnilton.dev.admin.catalogo.application.category.create.CreateCategoryUseCase;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;
import erisnilton.dev.admin.catalogo.domain.validation.handler.Notification;
import erisnilton.dev.admin.catalogo.infraestrutura.api.CategoryAPI;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CreateCategoryApiInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private CreateCategoryUseCase createCategoryUseCase;

    public CategoryController(final CreateCategoryUseCase createCategoryUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(CreateCategoryApiInput input) {
        final var aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );
        Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;
        Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess =
                output -> ResponseEntity.created(URI.create("/categories/" + output.id().getValue())).build();
        return this.createCategoryUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, String sort, String dir) {
        return null;
    }
}
