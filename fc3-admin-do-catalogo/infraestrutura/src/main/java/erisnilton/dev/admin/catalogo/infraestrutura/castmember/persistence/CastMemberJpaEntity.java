package erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence;

import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "CastMember")
@Table(name = "cast_members")
public class CastMemberJpaEntity {

    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CastMemberType type;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    public CastMemberJpaEntity() {
    }

    private CastMemberJpaEntity(
            final String id,
            final String name,
            final CastMemberType type,
            final Instant createdAt,
            final Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CastMemberJpaEntity from (CastMember aCastMember) {
        return  new CastMemberJpaEntity(
                aCastMember.getId().getValue(),
                aCastMember.getName(),
                aCastMember.getType(),
                aCastMember.getCreatedAt(),
                aCastMember.getUpdatedAt()
        );
    }

    public CastMember toAgregate() {
        return CastMember.with(
                CastMemberID.from(getId()),
                getName(),
                getType(),
                getCreatedAt(),
                getUpdatedAt()
        );
    }

    public String getId() {
        return id;
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

    public void setId(final String id) {
        this.id = id;
    }
    public void setName(final String name) {
        this.name = name;
    }

    public void setType(final CastMemberType type) {
        this.type = type;
    }

    public void setCreatedAt(final Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(final Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
