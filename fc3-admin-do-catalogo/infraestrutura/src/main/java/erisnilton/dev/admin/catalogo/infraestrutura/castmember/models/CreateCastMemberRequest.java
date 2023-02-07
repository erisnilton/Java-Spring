package erisnilton.dev.admin.catalogo.infraestrutura.castmember.models;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;

public record CreateCastMemberRequest(
        String name,
        CastMemberType type
) {
}
