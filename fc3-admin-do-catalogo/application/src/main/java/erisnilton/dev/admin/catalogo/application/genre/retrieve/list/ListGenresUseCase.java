package erisnilton.dev.admin.catalogo.application.genre.retrieve.list;

import erisnilton.dev.admin.catalogo.application.UseCase;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;
import erisnilton.dev.admin.catalogo.domain.pagination.SearchQuery;

public abstract class ListGenresUseCase extends UseCase<SearchQuery, Pagination<GenreListOutput>> {
}
