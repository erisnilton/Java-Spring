package erisnilton.dev.admin.catalogo.domain.castmember;

import erisnilton.dev.admin.catalogo.domain.AggregateRoot;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotificationException;
import erisnilton.dev.admin.catalogo.domain.utils.InstantUtils;
import erisnilton.dev.admin.catalogo.domain.validation.ValidationHandler;
import erisnilton.dev.admin.catalogo.domain.validation.handler.Notification;

import java.time.Instant;

public class CastMember extends AggregateRoot<CastMemberID> implements Cloneable {

    private  String name;
    private  CastMemberType type;
    private  Instant createdAt;
    private  Instant updatedAt;

    private CastMember(
            final CastMemberID anId,
            final String aName,
            final CastMemberType anType,
            Instant aCreationDate,
            Instant aUpdateDate) {
        super(anId);
        this.name = aName;
        this.type = anType;
        this.createdAt = aCreationDate;
        this.updatedAt = aUpdateDate;
        selfValidate();
    }

    public static CastMember newMember(final String aName, final CastMemberType anType) {
        final var id = CastMemberID.unique();
        final var now = InstantUtils.now();
        return new CastMember(id, aName, anType, now, now);
    }

    public static CastMember with(
            final CastMemberID anId,
            final String aName,
            final CastMemberType anType,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new CastMember(anId, aName, anType, createdAt, updatedAt);
    }

    public static CastMember with(
            final CastMember castMember
    ) {
        return new CastMember(
                castMember.getId(),
                castMember.getName(),
                castMember.getType(),
                castMember.getCreatedAt(),
                castMember.getUpdatedAt()
        );
    }

    public CastMember update(final String aName, final CastMemberType aType) {
        this.name = aName;
        this.type = aType;
        this.updatedAt = InstantUtils.now();
        selfValidate();
        return this;
    }

    public void validate(final ValidationHandler handler) {
        new CastMemberValidator(this, handler).validate();
    }


    public String getName() {
        return name;
    }

    public CastMemberType getType() {
        return type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public CastMember clone() {
        try {
            CastMember clone = (CastMember) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasErrror()) {
            throw new NotificationException("Failed to create a aggregate CastMember", notification);
        }
    }
}
