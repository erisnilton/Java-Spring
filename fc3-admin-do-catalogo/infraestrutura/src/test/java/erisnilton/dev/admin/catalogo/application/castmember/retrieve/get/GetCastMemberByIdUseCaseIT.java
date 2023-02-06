package erisnilton.dev.admin.catalogo.application.castmember.retrieve.get;

import erisnilton.dev.admin.catalogo.Fixture;
import erisnilton.dev.admin.catalogo.IntegrationTest;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotFoundException;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence.CastMemberJpaEntity;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@IntegrationTest
public class GetCastMemberByIdUseCaseIT {

    @Autowired
    private GetCastMemberByIdUseCase useCase;

    @SpyBean
    private CastMemberGateway castMemberGateway;

    @Autowired
    private CastMemberRepository repository;


    @Test
    public void givenAValidId_whenCallsGetCastMember_shouldReturnCastMember() {
        // given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMember.type();
        final var aCastMember = CastMember.newMember(expectedName, expectedType);
        final var expectedId = aCastMember.getId();

        this.repository.saveAndFlush(CastMemberJpaEntity.from(aCastMember));

        Assertions.assertEquals(1, this.repository.count());

        // when
        final var actualCastMember = useCase.execute(expectedId.getValue());

        // then
        Assertions.assertEquals(expectedId.getValue(), actualCastMember.id().getValue());
        Assertions.assertEquals(expectedName, actualCastMember.name());
        Assertions.assertEquals(expectedType, actualCastMember.type());
        Assertions.assertEquals(aCastMember.getCreatedAt(), actualCastMember.createdAt());
        Assertions.assertEquals(aCastMember.getUpdatedAt(), actualCastMember.updatedAt());

        Mockito.verify(castMemberGateway, times(1)).findById(any());
    }

    @Test
    public void givenAValidId_whenCallsGetCastMemberDoesNotExists_shouldReturnNotFound() {
        // given
        final var expectedErrorMessage = "CastMember with ID 123 was not found";
        final var expectedId = CastMemberID.from("123");

        // when
        final var actualExpection =
                Assertions.assertThrows(NotFoundException.class, () -> {
                    useCase.execute(expectedId.getValue());
                });

        // then
        Assertions.assertEquals(expectedErrorMessage, actualExpection.getMessage());

        Mockito.verify(castMemberGateway, times(1)).findById(expectedId);
    }
}
