package erisnilton.dev.admin.catalogo.infraestrutura.castmember.models;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;

import java.time.Instant;

public record CastMemberResponse(
        String id,
        String name,
        String type,
        String createdAt,
        String updatedAt
) {
}
