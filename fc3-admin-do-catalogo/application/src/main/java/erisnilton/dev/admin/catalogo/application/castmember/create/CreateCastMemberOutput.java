package erisnilton.dev.admin.catalogo.application.castmember.create;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;

public record CreateCastMemberOutput(
        String id
) {

    public static CreateCastMemberOutput from(final String anId) {
        return  new CreateCastMemberOutput(anId);
    }

    public static CreateCastMemberOutput from(final CastMember aMember) {
        return  new CreateCastMemberOutput(aMember.getId().getValue());
    }
}
