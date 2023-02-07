package erisnilton.dev.admin.catalogo.infraestrutura.castmember.presenters;

import erisnilton.dev.admin.catalogo.application.castmember.retrieve.get.CastMemberOutput;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.models.CastMemberResponse;

import java.time.format.DateTimeFormatter;

public interface CastMemberPresenter {

    static CastMemberResponse present(final CastMemberOutput aMember) {
        return new CastMemberResponse(
                aMember.id().getValue(),
                aMember.name(),
                aMember.type().name(),
                aMember.createdAt().toString(),
                aMember.updatedAt().toString()

        );
    }
}
