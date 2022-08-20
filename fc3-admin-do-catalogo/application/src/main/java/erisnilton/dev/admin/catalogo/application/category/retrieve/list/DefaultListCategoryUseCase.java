package erisnilton.dev.admin.catalogo.application.category.retrieve.list;

import erisnilton.dev.admin.catalogo.domain.category.CategoryGateway;
import erisnilton.dev.admin.catalogo.domain.category.CategorySearchQuery;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoryUseCase extends ListCategoriesUseCase{

    private CategoryGateway categoryGateway;

    public DefaultListCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(CategorySearchQuery aQuery) {
        return this.categoryGateway.findAll(aQuery)
                .map(CategoryListOutput::from);
    }
}
