package erisnilton.dev.admin.catalogo.infraestrutura.castmember.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;

public record CastMemberListResponse(
        String id,
        String name,
        String type,
        @JsonProperty("created_at") String createdAt
) {
}
