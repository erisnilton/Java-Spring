package erisnilton.dev.admin.catalogo.infraestrutura.api.controllers;

import erisnilton.dev.admin.catalogo.application.category.create.CreateCategoryUseCase;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;
import erisnilton.dev.admin.catalogo.infraestrutura.api.CategoryAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class CategoryController implements CategoryAPI {

    private CreateCategoryUseCase createCategoryUseCase;

    public CategoryController(final CreateCategoryUseCase createCategoryUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory() {
        return null;
    }

    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, String sort, String dir) {
        return null;
    }
}
