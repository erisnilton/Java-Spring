package erisnilton.dev.admin.catalogo.infraestrutura.genre.presenters;

import erisnilton.dev.admin.catalogo.application.category.retrieve.get.CategoryOutput;
import erisnilton.dev.admin.catalogo.application.category.retrieve.list.CategoryListOutput;
import erisnilton.dev.admin.catalogo.application.genre.retrieve.get.GenreOutput;
import erisnilton.dev.admin.catalogo.application.genre.retrieve.list.GenreListOutput;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CategoryListResponse;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CategoryResponse;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.models.GenreListResponse;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.models.GenreResponse;

public interface GenreApiPresenters {

    static GenreResponse present(final GenreOutput output) {
        return new GenreResponse(
                output.id().getValue(),
                output.name(),
                output.categories(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static GenreListResponse present(final GenreListOutput output) {
        return new GenreListResponse(
                output.id().getValue(),
                output.name(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
