package erisnilton.dev.admin.catalogo.application.castmember.update;

import erisnilton.dev.admin.catalogo.domain.Genre.Genre;
import erisnilton.dev.admin.catalogo.domain.Identifier;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.exceptions.DomainException;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotFoundException;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotificationException;
import erisnilton.dev.admin.catalogo.domain.validation.handler.Notification;

import java.util.function.Supplier;

public class DefaultUpdateCastMemberUseCase extends UpdateCastMemberUseCase{

    private CastMemberGateway gateway;

    public DefaultUpdateCastMemberUseCase(final CastMemberGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public UpdateCastMemberOutput execute(UpdateCastMemberCommand aCommand) {
        final var anId = CastMemberID.from(aCommand.id());
        final var name = aCommand.name();
        final var type =  aCommand.type();

        final var aCastmember = this.gateway.findById(anId)
                .orElseThrow(notFound(anId));

        final var notification = Notification.create();
        notification.validate(() -> aCastmember.update(name, type));

        if(notification.hasErrror()) {
            throw new NotificationException(
                    "Could not update Aggregate CastMember %s".formatted(aCommand.id()), notification);
        }
        return UpdateCastMemberOutput.from(this.gateway.update(aCastmember));
    }


    private Supplier<DomainException> notFound(final Identifier anId) {
        return () -> NotFoundException.with(Genre.class, anId);
    }
}
