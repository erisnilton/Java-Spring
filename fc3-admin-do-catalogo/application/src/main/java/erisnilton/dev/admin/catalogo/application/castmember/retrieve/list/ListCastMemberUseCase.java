package erisnilton.dev.admin.catalogo.application.castmember.retrieve.list;

import erisnilton.dev.admin.catalogo.application.UseCase;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;
import erisnilton.dev.admin.catalogo.domain.pagination.SearchQuery;

public sealed abstract class ListCastMemberUseCase extends UseCase<SearchQuery, Pagination<ListCastMemberOutput>> permits DefaultListCastMemberUseCase {
}
