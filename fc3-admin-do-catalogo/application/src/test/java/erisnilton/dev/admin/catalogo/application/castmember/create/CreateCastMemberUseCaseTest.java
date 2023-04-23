package erisnilton.dev.admin.catalogo.application.castmember.create;

import erisnilton.dev.admin.catalogo.application.Fixture;
import erisnilton.dev.admin.catalogo.application.UseCaseTest;
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

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CreateCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateCastMemberUseCase useCase;
    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreateCastMember_shouldReturnCastMemberId() {
        // given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type();

        final var aCommand =
                CreateCastMemberCommand.with(expectedName, expectedType);

        when(castMemberGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(castMemberGateway, times(1)).create(argThat((aCastMember) ->
                Objects.equals(expectedName, aCastMember.getName())
                        && Objects.equals(expectedType, aCastMember.getType())
                        && Objects.nonNull(aCastMember.getId())
                        && Objects.nonNull(aCastMember.getCreatedAt())
                        && Objects.nonNull(aCastMember.getUpdatedAt())
        ));
    }

    @Test
    public void givenAInvalidEmptyName_whenCallsCreateCastMember_shouldReturnNotificationException() {
        // given
        final var expectedName = " ";
        final var expectedType = Fixture.CastMembers.type();

        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var aCommand =
                CreateCastMemberCommand.with(expectedName, expectedType);

        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () ->
                useCase.execute(aCommand)
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAInvalidNullName_whenCallsCreateCastMember_shouldReturnNotificationException() {
        // given
        final String expectedName = null;
        final var expectedType = Fixture.CastMembers.type();

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand =
                CreateCastMemberCommand.with(expectedName,expectedType);

        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () ->
                useCase.execute(aCommand)
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAInvalidNullType_whenCallsCreateCastMember_shouldReturnNotificationException() {
        // given
        final var expectedName = Fixture.name();
        final CastMemberType expectedType = null;

        final var expectedErrorMessage = "'type' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand =
                CreateCastMemberCommand.with(expectedName,expectedType);

        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () ->
                useCase.execute(aCommand)
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }
}
