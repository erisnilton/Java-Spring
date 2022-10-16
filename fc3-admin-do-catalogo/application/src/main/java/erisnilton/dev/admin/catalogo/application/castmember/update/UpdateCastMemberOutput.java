package erisnilton.dev.admin.catalogo.application.castmember.update;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;

public record UpdateCastMemberOutput(
        String id
) {

    public static UpdateCastMemberOutput from(final String anId) {
        return new UpdateCastMemberOutput(anId);
    }

    public static UpdateCastMemberOutput from(final CastMember aMember) {
        return new UpdateCastMemberOutput(aMember.getId().getValue());
    }
}
