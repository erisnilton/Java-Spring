package erisnilton.dev.admin.catalogo.application.category.retrieve.list;

import erisnilton.dev.admin.catalogo.application.UseCase;
import erisnilton.dev.admin.catalogo.domain.category.CategorySearchQuery;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery,
        Pagination<CategoryListOutput>> {
}
