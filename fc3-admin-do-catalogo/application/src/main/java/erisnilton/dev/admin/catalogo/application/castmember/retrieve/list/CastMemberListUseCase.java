package erisnilton.dev.admin.catalogo.application.castmember.retrieve.list;

import erisnilton.dev.admin.catalogo.application.UnitUseCase;
import erisnilton.dev.admin.catalogo.application.UseCase;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;
import erisnilton.dev.admin.catalogo.domain.pagination.SearchQuery;

public  abstract class CastMemberListUseCase extends UseCase<SearchQuery, Pagination<CastMemberListOutput>> {
}
