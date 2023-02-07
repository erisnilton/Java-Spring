package erisnilton.dev.admin.catalogo.infraestrutura.api.controllers;

import erisnilton.dev.admin.catalogo.application.castmember.create.CreateCastMemberCommand;
import erisnilton.dev.admin.catalogo.application.castmember.create.CreateCastMemberUseCase;
import erisnilton.dev.admin.catalogo.infraestrutura.api.CastmemberAPI;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.models.CreateCastMemberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class CastmemberController implements CastmemberAPI {

    private final CreateCastMemberUseCase createCastMemberUseCase;

    public CastmemberController(final CreateCastMemberUseCase createCastMemberUseCase) {
        this.createCastMemberUseCase = Objects.requireNonNull(createCastMemberUseCase);
    }

    @Override
    public ResponseEntity<?> create(final CreateCastMemberRequest input) {
        final var aCommand = CreateCastMemberCommand.with(input.name(), input.type());
        final var output = this.createCastMemberUseCase.execute(aCommand);
        return ResponseEntity.created(URI.create("/cast_members/" + output.id())).body(output);
    }
}
