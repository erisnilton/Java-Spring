package erisnilton.dev.admin.catalogo.infraestrutura.castmember;

import erisnilton.dev.admin.catalogo.MySQLGatewayTest;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence.CastMemberJpaEntity;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static erisnilton.dev.admin.catalogo.Fixture.CastMember.type;
import static erisnilton.dev.admin.catalogo.Fixture.name;

@MySQLGatewayTest
public class CastMemberMySQLGatewayTest {


    @Autowired
    private CastMemberMySQLGateway castMemberGateway;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @Test
    public void testDependenciesInjected() {

        Assertions.assertNotNull(castMemberGateway);
        Assertions.assertNotNull(castMemberGateway);

    }

    @Test
    public void givenAValidCastMember_whenCallsCreateCastMember_shouldPersistIt() {
        // given
        final var expectedName = name();
        final var expectedType = type();

        final var aCastMember = CastMember.newMember(expectedName, expectedType);

        final var expectedId = aCastMember.getId();

        Assertions.assertEquals(0, castMemberRepository.count());

        // when
        final var actualCastMember = castMemberGateway.create(CastMember.with(aCastMember));

        // then
        Assertions.assertEquals(1, castMemberRepository.count());
        Assertions.assertEquals(expectedId, actualCastMember.getId());
        Assertions.assertEquals(expectedName, actualCastMember.getName());
        Assertions.assertEquals(expectedType, actualCastMember.getType());
        Assertions.assertEquals(aCastMember.getCreatedAt(), actualCastMember.getCreatedAt());
        Assertions.assertEquals(aCastMember.getUpdatedAt(), actualCastMember.getUpdatedAt());

        final var persistedGenre = castMemberRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedName, persistedGenre.getName());
        Assertions.assertEquals(expectedType, persistedGenre.getType());
        Assertions.assertEquals(aCastMember.getCreatedAt(), persistedGenre.getCreatedAt());
        Assertions.assertEquals(aCastMember.getUpdatedAt(), persistedGenre.getUpdatedAt());
    }

    @Test
    public void givenAValidCastMember_whenCallsUpdateCastMember_shouldRefleshIt() {

        // given
        final var expectedName = name();
        final var expectedType = CastMemberType.ACTOR;

        final var aCastMember = CastMember.newMember("Vind", CastMemberType.DIRECTOR);

        final var expectedId = aCastMember.getId();

        final var currentMember = castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aCastMember));

        Assertions.assertEquals(1, castMemberRepository.count());

        Assertions.assertEquals(expectedId.getValue(), currentMember.getId());
        Assertions.assertEquals(aCastMember.getName(), currentMember.getName());
        Assertions.assertEquals(aCastMember.getType(), currentMember.getType());

        // when
        final var actualCastMember = castMemberGateway.update(
                CastMember.with(aCastMember).update(expectedName, expectedType)
        );

        // then
        Assertions.assertEquals(1, castMemberRepository.count());

        Assertions.assertEquals(expectedId, actualCastMember.getId());
        Assertions.assertEquals(expectedName, actualCastMember.getName());
        Assertions.assertEquals(expectedType, actualCastMember.getType());
        Assertions.assertEquals(aCastMember.getCreatedAt(), actualCastMember.getCreatedAt());
        Assertions.assertTrue(aCastMember.getUpdatedAt().isBefore(actualCastMember.getUpdatedAt()));

        final var persistedGenre = castMemberRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedName, persistedGenre.getName());
        Assertions.assertEquals(expectedType, persistedGenre.getType());
        Assertions.assertEquals(aCastMember.getCreatedAt(), persistedGenre.getCreatedAt());
        Assertions.assertTrue(aCastMember.getUpdatedAt().isBefore(persistedGenre.getUpdatedAt()));

    }

    @Test
    public void givenAValidCastMember_whenCallsDeleteById_shouldDeleteIt() {

        // given
        final var aMember = CastMember.newMember(name(), type());

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));
        Assertions.assertEquals(1, castMemberRepository.count());

        // when
        castMemberGateway.deleteById(aMember.getId());

        // then

        Assertions.assertEquals(0, castMemberRepository.count());


    }

    @Test
    public void givenAnInValidCastMember_whenCallsDeleteById_shouldBeIgnored() {
        // given
        final var aMember = CastMember.newMember(name(), type());

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());

        // when
        castMemberGateway.deleteById(CastMemberID.from("123"));

        // then
        Assertions.assertEquals(1, castMemberRepository.count());
    }

    @Test
    public void givenAValidCastMember_whenCallsFindById_shouldReturnIt() {

        // given
        final var expectedName = name();
        final var expectedType = type();

        final var aMember = CastMember.newMember(expectedName, expectedType);
        final var expectedId = aMember.getId();

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));
        Assertions.assertEquals(1, castMemberRepository.count());

        // when
        final var actualMember = castMemberGateway.findById(expectedId).get();

        // then

        Assertions.assertEquals(expectedId, actualMember.getId());
        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), actualMember.getCreatedAt());
        Assertions.assertEquals(aMember.getUpdatedAt(), actualMember.getUpdatedAt());

    }

    @Test
    public void givenAnInValidCastMember_whenCallsFindById_shouldReturnEmpty() {

        // given

        final var aMember = CastMember.newMember(name(), type());

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));
        Assertions.assertEquals(1, castMemberRepository.count());

        // when
        final var actualMember = castMemberGateway.findById(CastMemberID.from("123"));

        // then

        Assertions.assertTrue(actualMember.isEmpty());
    }
}
