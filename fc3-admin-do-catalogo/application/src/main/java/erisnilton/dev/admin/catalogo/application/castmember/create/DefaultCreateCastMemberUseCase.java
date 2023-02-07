package erisnilton.dev.admin.catalogo.application.castmember.create;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotificationException;
import erisnilton.dev.admin.catalogo.domain.validation.handler.Notification;

public non-sealed class DefaultCreateCastMemberUseCase extends CreateCastMemberUseCase {

    private CastMemberGateway castMemberGateway;

    public DefaultCreateCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = castMemberGateway;
    }


    @Override
    public CreateCastMemberOutput execute(CreateCastMemberCommand aCommand) {

        final var aName = aCommand.name();
        final var aType = aCommand.type();

        final var notification = Notification.create();
        final var aCastMember = notification.validate(() -> CastMember.newMember(aName, aType));

        if (notification.hasErrror()) {
            throw new NotificationException("Could not create Aggregate CastMember", notification);
        }
        return CreateCastMemberOutput.from(this.castMemberGateway.create(aCastMember));
    }
}
