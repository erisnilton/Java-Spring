package erisnilton.dev.admin.catalogo.infraestrutura.category.presenters;

import erisnilton.dev.admin.catalogo.application.category.retrieve.get.CategoryOutput;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CategoryApiOutput;

public interface CategoryApiPresenters {

    static CategoryApiOutput present(final CategoryOutput output) {
        return new CategoryApiOutput(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
}
