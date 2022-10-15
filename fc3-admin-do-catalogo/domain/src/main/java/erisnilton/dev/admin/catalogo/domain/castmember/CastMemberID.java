package erisnilton.dev.admin.catalogo.domain.castmember;

import erisnilton.dev.admin.catalogo.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class CastMemberID extends Identifier {

    private final String value;


    private CastMemberID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static CastMemberID unique() {
        return CastMemberID.from(UUID.randomUUID());
    }

    public static CastMemberID from(final String anId) {
        return new CastMemberID(anId);
    }

    public static CastMemberID from(final UUID anId) {
        return new CastMemberID(anId.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CastMemberID that = (CastMemberID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
