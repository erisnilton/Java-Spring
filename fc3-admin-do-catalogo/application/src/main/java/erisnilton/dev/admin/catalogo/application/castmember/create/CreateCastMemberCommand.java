package erisnilton.dev.admin.catalogo.application.castmember.create;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;

public record CreateCastMemberCommand(
        String name,
        CastMemberType member
) {

    public static CreateCastMemberCommand with(
            final String aName,
            final CastMemberType aType
    ) {
        return new CreateCastMemberCommand(aName, aType);
    }
}
