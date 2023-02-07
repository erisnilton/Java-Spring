package erisnilton.dev.admin.catalogo.application.castmember.retrieve.list;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;
import erisnilton.dev.admin.catalogo.domain.pagination.SearchQuery;

public class DefaultListCastMemberUseCase extends ListCastMemberUseCase {

    private CastMemberGateway castMemberGateway;

    public DefaultListCastMemberUseCase(CastMemberGateway castMemberGateway) {
        this.castMemberGateway = castMemberGateway;
    }

    @Override
    public Pagination<ListCastMemberOutput> execute(SearchQuery aQuery) {
        return this.castMemberGateway.findAll(aQuery).map(ListCastMemberOutput::from);
    }
}
