package erisnilton.dev.admin.catalogo.application.castmember.retrieve.get;

import erisnilton.dev.admin.catalogo.application.Fixture;
import erisnilton.dev.admin.catalogo.application.UseCaseTest;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class GetCastMemberByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetCastMemberByIdUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    public void givenAValidId_whenCallsGetCastMember_shouldReturnCastMember() {
        // given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMember.type();

        final var aCastMember = CastMember.newMember(expectedName, expectedType);

        final var expectedId = aCastMember.getId();

        when(castMemberGateway.findById(any())).thenReturn(Optional.of(aCastMember));

        // when
        final var actualCastMember = useCase.execute(expectedId.getValue());

        // then
        Assertions.assertEquals(expectedId.getValue(), actualCastMember.id().getValue());
        Assertions.assertEquals(expectedName, actualCastMember.name());
        Assertions.assertEquals(expectedType, actualCastMember.type());
        Assertions.assertEquals(aCastMember.getCreatedAt(), actualCastMember.createdAt());
        Assertions.assertEquals(aCastMember.getUpdatedAt(), actualCastMember.updatedAt());

        Mockito.verify(castMemberGateway, times(1)).findById(eq(expectedId));
    }

    @Test
    public void givenAValidId_whenCallsGetCastMemberDoesNotExists_shouldReturnNotFound() {
        // given
        final var expectedErrorMessage = "CastMember with ID 123 was not found";
        final var expectedId = CastMemberID.from("123");

        when(castMemberGateway.findById(eq(expectedId))).thenReturn(Optional.empty());

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
