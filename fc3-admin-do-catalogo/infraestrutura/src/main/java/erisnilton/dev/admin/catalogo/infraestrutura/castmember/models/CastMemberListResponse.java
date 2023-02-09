package erisnilton.dev.admin.catalogo.infraestrutura.castmember.models;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;

public record CastMemberListResponse(
        String id,
        String name,
        String type,
        String createdAt
) {
}
