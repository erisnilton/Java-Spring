package erisnilton.dev.admin.catalogo.application.castmember.retrieve.get;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotFoundException;

import java.util.function.Supplier;

public class DefaultGetCastMemberByIdUseCase extends GetCastMemberByIdUseCase {

    private CastMemberGateway castMemberGateway;

    public DefaultGetCastMemberByIdUseCase(CastMemberGateway castMemberGateway) {
        this.castMemberGateway = castMemberGateway;
    }

    @Override
    public CastMemberOutput execute(String anId) {
        return this.castMemberGateway.findById(CastMemberID.from(anId))
                .map(CastMemberOutput::from)
                .orElseThrow(notFound(CastMemberID.from(anId)));
    }

    private static Supplier<NotFoundException> notFound(final CastMemberID anId) {
        return () -> NotFoundException.with(CastMember.class, anId);
    }
}
