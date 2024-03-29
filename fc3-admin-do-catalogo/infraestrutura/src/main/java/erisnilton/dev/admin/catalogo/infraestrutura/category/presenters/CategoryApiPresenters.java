package erisnilton.dev.admin.catalogo.infraestrutura.category.presenters;

import erisnilton.dev.admin.catalogo.application.category.retrieve.get.CategoryOutput;
import erisnilton.dev.admin.catalogo.application.category.retrieve.list.CategoryListOutput;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CategoryResponse;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CategoryListResponse;

public interface    CategoryApiPresenters {

    static CategoryResponse present(final CategoryOutput output) {
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static CategoryListResponse present(final CategoryListOutput output) {
        return new CategoryListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
