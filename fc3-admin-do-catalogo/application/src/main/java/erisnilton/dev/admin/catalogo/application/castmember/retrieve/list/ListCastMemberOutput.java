package erisnilton.dev.admin.catalogo.application.castmember.retrieve.list;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;

import java.time.Instant;

public record ListCastMemberOutput(
        String id,
        String name,
        String type,
        Instant createdAt
) {
    public static ListCastMemberOutput from(final CastMember aCastMember) {
        return new ListCastMemberOutput(
                aCastMember.getId().getValue(),
                aCastMember.getName(),
                aCastMember.getType().name(),
                aCastMember.getCreatedAt()
        );
    }
}
