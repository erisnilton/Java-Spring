package erisnilton.dev.admin.catalogo.application.castmember.create;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;

public record CreateCastMemberOutput(
        String id
) {

    public static CreateCastMemberOutput from(final String anId) {
        return  new CreateCastMemberOutput(anId);
    }

    public static CreateCastMemberOutput from(final CastMemberID anId) {
        return  new CreateCastMemberOutput(anId.getValue());
    }
    public static CreateCastMemberOutput from(final CastMember aMember) {
        return  from(aMember.getId());
    }
}
