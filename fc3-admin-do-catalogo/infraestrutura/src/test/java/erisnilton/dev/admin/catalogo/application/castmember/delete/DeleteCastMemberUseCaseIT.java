package erisnilton.dev.admin.catalogo.application.castmember.delete;

import erisnilton.dev.admin.catalogo.Fixture;
import erisnilton.dev.admin.catalogo.IntegrationTest;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence.CastMemberJpaEntity;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@IntegrationTest
public class DeleteCastMemberUseCaseIT {

    @Autowired
    private DeleteCastMemberUseCase useCase;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @SpyBean
    private CastMemberGateway castMemberGateway;


    @Test
    public void givenAValidCastMemberId_whenCallsDeleteCastMember_shouldDeleteCastMember() {
        // given
        final var aCastMember = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());
        final var aCastMemberTwo = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());

        final var expectedId = aCastMember.getId();

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aCastMember));
        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aCastMemberTwo));

        Assertions.assertEquals(2, this.castMemberRepository.count());

        // when

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));


        // then

        Mockito.verify(castMemberGateway, times(1)).deleteById(any());
        Assertions.assertEquals(1, this.castMemberRepository.count());
        Assertions.assertFalse(this.castMemberRepository.existsById(expectedId.getValue()));
        Assertions.assertTrue(this.castMemberRepository.existsById(aCastMemberTwo.getId().getValue()));
    }

    @Test
    public void givenAnInValidCastMemberId_whenCallsDeleteGenre_shouldBeOk() {
        // given

       final var aCastMember =  CastMember.newMember(Fixture.name(), Fixture.CastMember.type());

        final var expectedId = CastMemberID.from("123");

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aCastMember));

        // when

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then

        Mockito.verify(castMemberGateway, times(1)).deleteById(expectedId);

        Assertions.assertEquals(1, this.castMemberRepository.count());
    }

    @Test
    public void givenAValidCastMemberId_whenCallsDeleteCastMemberAndGatewayThrowsUnexpectedError_shouldReceiveException() {
        // given

        final var aCastMember = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());

        final var expectedId = aCastMember.getId();

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aCastMember));

        doThrow(new IllegalStateException("Gateway error")).when(castMemberGateway).deleteById(any());

        // when

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        // then

        Mockito.verify(castMemberGateway, times(1)).deleteById(expectedId);

        Assertions.assertEquals(1, this.castMemberRepository.count());
    }
}
