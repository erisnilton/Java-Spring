package erisnilton.dev.admin.catalogo.application.castmember.retrieve.get;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;

import java.time.Instant;

public record CastMemberOutput(
        CastMemberID id,
        String name,
        CastMemberType type,
        Instant createdAt,
        Instant updatedAt
        ) {

    public static CastMemberOutput from(final CastMember aCastMember) {
        return new CastMemberOutput(
                aCastMember.getId(),
                aCastMember.getName(),
                aCastMember.getType(),
                aCastMember.getCreatedAt(),
                aCastMember.getUpdatedAt()
        );
    }
}
