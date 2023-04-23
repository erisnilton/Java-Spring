package erisnilton.dev.admin.catalogo.application.castmember.delete;

import erisnilton.dev.admin.catalogo.application.Fixture;
import erisnilton.dev.admin.catalogo.application.UseCaseTest;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeleteCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    public void givenAValidCastMemberId_whenCallsDeleteCastMember_shouldDeleteCastMember() {
        // given
        final var aCastMember = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());

        final var expectedId = aCastMember.getId();

        doNothing().when(castMemberGateway).deleteById(any());

        // when

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then

        Mockito.verify(castMemberGateway, times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAnInValidCastMemberId_whenCallsDeleteGenre_shouldBeOk() {
        // given

        final var expectedId = CastMemberID.from("123");

        doNothing().when(castMemberGateway).deleteById(any());

        // when

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then

        Mockito.verify(castMemberGateway, times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAValidCastMemberId_whenCallsDeleteCastMemberAndGatewayThrowsUnexpectedError_shouldReceiveException() {
        // given

        final var aCastMember = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());

        final var expectedId = aCastMember.getId();

        doThrow(new IllegalStateException("Gateway error")).when(castMemberGateway).deleteById(any());

        // when

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        // then

        Mockito.verify(castMemberGateway, times(1)).deleteById(expectedId);
    }
}
