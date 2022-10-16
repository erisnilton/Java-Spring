package erisnilton.dev.admin.catalogo.application.castmember.update;

import erisnilton.dev.admin.catalogo.application.Fixture;
import erisnilton.dev.admin.catalogo.application.UseCaseTest;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class UpdateCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCastMember_shouldReturnGenreId() {
        // given
        final var aCastMember = CastMember.newMember("vin", CastMemberType.ACTOR);

        final var expectedId = aCastMember.getId();
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMember.type();

        final var aCommand = UpdateCastMemberCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedType
        );

        when(castMemberGateway.update(any())).thenAnswer(returnsFirstArg());
        when(castMemberGateway.findById(any())).thenReturn(Optional.of(CastMember.with(aCastMember)));

        // when
        final var actualOutput = useCase.execute(aCommand);
        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        Mockito.verify(castMemberGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(castMemberGateway, times(1)).update(argThat(aUpdateCastMember ->
                Objects.equals(expectedId, aUpdateCastMember.getId())
                        && Objects.equals(expectedName, aUpdateCastMember.getName())
                        && Objects.equals(expectedType, aUpdateCastMember.getType())
                        && Objects.equals(aCastMember.getCreatedAt(), aUpdateCastMember.getCreatedAt())
                        && aCastMember.getUpdatedAt().isBefore(aUpdateCastMember.getUpdatedAt())
        ));
    }

    @Test
    public void givenAnInValidNullName_whenCallsUpdateCastMember_shouldReturnNotificationException() {
        // given
        final var aCastMember = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());

        final var expectedId = aCastMember.getId();
        final String expectedName = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var aCommand = UpdateCastMemberCommand.with(
                expectedId.getValue(),
                expectedName,
                aCastMember.getType()
        );

        when(castMemberGateway.findById(any())).thenReturn(Optional.of(CastMember.with(aCastMember)));

        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(castMemberGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(castMemberGateway, times(0)).update(any());
    }

    @Test
    public void givenAnInValidEmptyName_whenCallsUpdateCastMember_shouldReturnNotificationException() {
        // given
        final var aCastMember = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());

        final var expectedId = aCastMember.getId();
        final var expectedName = " ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var aCommand = UpdateCastMemberCommand.with(
                expectedId.getValue(),
                expectedName,
                aCastMember.getType()
        );

        when(castMemberGateway.findById(any())).thenReturn(Optional.of(CastMember.with(aCastMember)));

        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(castMemberGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(castMemberGateway, times(0)).update(any());
    }

    @Test
    public void givenAnInValidNullType_whenCallsUpdateCastMember_shouldReturnNotificationException() {
        // given
        final var aCastMember = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());

        final var expectedId = aCastMember.getId();
        final var expectedName = " ";
        final CastMemberType expectedType = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'type' should not be null";

        final var aCommand = UpdateCastMemberCommand.with(
                expectedId.getValue(),
                aCastMember.getName(),
                expectedType
        );

        when(castMemberGateway.findById(any())).thenReturn(Optional.of(CastMember.with(aCastMember)));

        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(castMemberGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(castMemberGateway, times(0)).update(any());
    }
}
