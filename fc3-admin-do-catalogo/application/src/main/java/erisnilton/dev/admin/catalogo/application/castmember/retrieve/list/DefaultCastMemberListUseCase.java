package erisnilton.dev.admin.catalogo.application.castmember.retrieve.list;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;
import erisnilton.dev.admin.catalogo.domain.pagination.SearchQuery;

public class DefaultCastMemberListUseCase extends CastMemberListUseCase {

    private CastMemberGateway castMemberGateway;

    public DefaultCastMemberListUseCase(CastMemberGateway castMemberGateway) {
        this.castMemberGateway = castMemberGateway;
    }

    @Override
    public Pagination<CastMemberListOutput> execute(SearchQuery aQuery) {
        return this.castMemberGateway.findAll(aQuery).map(CastMemberListOutput::from);
    }
}
