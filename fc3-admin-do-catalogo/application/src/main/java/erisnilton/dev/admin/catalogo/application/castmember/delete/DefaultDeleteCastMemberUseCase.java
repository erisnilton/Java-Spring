package erisnilton.dev.admin.catalogo.application.castmember.delete;

import erisnilton.dev.admin.catalogo.domain.Genre.GenreID;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;

public non-sealed class DefaultDeleteCastMemberUseCase extends DeleteCastMemberUseCase{

    private CastMemberGateway castMemberGateway;

    public DefaultDeleteCastMemberUseCase(CastMemberGateway castMemberGateway) {
        this.castMemberGateway = castMemberGateway;
    }

    @Override
    public void execute(String anId) {
        this.castMemberGateway.deleteById(CastMemberID.from(anId));

    }
}
