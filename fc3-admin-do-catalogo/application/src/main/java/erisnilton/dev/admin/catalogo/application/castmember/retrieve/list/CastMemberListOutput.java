package erisnilton.dev.admin.catalogo.application.castmember.retrieve.list;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;

import java.time.Instant;

public record CastMemberListOutput(
        CastMemberID id,
        String name,
        CastMemberType type,
        Instant createdAt
) {
    public static CastMemberListOutput from(final CastMember aCastMember) {
        return new CastMemberListOutput(
                aCastMember.getId(),
                aCastMember.getName(),
                aCastMember.getType(),
                aCastMember.getCreatedAt()
        );
    }
}
