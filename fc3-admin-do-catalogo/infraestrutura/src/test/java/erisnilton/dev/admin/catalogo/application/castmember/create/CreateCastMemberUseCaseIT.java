package erisnilton.dev.admin.catalogo.application.castmember.create;

import erisnilton.dev.admin.catalogo.Fixture;
import erisnilton.dev.admin.catalogo.IntegrationTest;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotificationException;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;

@IntegrationTest
public class CreateCastMemberUseCaseIT {

    @Autowired
    private CreateCastMemberUseCase useCase;

    @SpyBean
    private CastMemberGateway castMemberGateway;

    @Autowired
    private CastMemberRepository repository;


    @Test
    public void givenAValidCommand_whenCallsCreateCastMember_shouldReturnGenreId() {
        // given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMember.type();

        final var aCommand =
                CreateCastMemberCommand.with(expectedName, expectedType);


        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        final var actualMember = this.repository.findById(actualOutput.id()).get();

        Assertions.assertEquals(expectedName,actualMember.getName());
        Assertions.assertEquals(expectedType,actualMember.getType());
        Assertions.assertNotNull(actualMember.getCreatedAt());
        Assertions.assertEquals(actualMember.getCreatedAt(), actualMember.getUpdatedAt());

        Mockito.verify(castMemberGateway).create(any());
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCastMember_shouldThrowsNotificationException() {
        // given
        final String expectedName = null;
        final var expectedType = Fixture.CastMember.type();

        final var expectedErrorCounter = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var aCommand = CreateCastMemberCommand.with(expectedName, expectedType);

        // when

        final var actualException =
                Assertions.assertThrows(NotificationException.class, () ->
                        useCase.execute(aCommand)
                );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCounter, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenATypeInvalid_whenCallsCreateCastMember_shouldThrowsNotificationExpection(){
        // given
        final String expectedName = Fixture.name();
        final CastMemberType expectedType = null;
        final var expectedErrorCounter = 1;
        final var expectedErrorMessage = "'type' should not be null";

        final var aCommand = CreateCastMemberCommand.with(expectedName, expectedType);
        System.out.println(aCommand);

        // when

        final var actualException =
                Assertions.assertThrows(NotificationException.class, () ->
                        useCase.execute(aCommand)
                );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCounter, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

}
