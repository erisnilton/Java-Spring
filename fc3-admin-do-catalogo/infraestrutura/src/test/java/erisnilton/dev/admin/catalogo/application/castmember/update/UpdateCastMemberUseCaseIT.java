package erisnilton.dev.admin.catalogo.application.castmember.update;

import erisnilton.dev.admin.catalogo.Fixture;
import erisnilton.dev.admin.catalogo.IntegrationTest;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotificationException;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence.CastMemberJpaEntity;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@IntegrationTest
public class UpdateCastMemberUseCaseIT {

    @Autowired
    private UpdateCastMemberUseCase useCase;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @SpyBean
    private CastMemberGateway castMemberGateway;


    @Test
    public void givenAValidCommand_whenCallsUpdateCastMember_shouldReturnGenreId() {
        // given
        final var aCastMember = CastMember.newMember("vin", CastMemberType.DIRECTOR);

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aCastMember));
        Assertions.assertEquals(1, this.castMemberRepository.count());

        final var expectedId = aCastMember.getId();
        final var expectedName = Fixture.name();
        final var expectedType = CastMemberType.ACTOR;

        final var aCommand = UpdateCastMemberCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedType
        );

        // when
        final var actualOutput = useCase.execute(aCommand);
        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        final var actualPersistedMember =
                this.castMemberRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedName, actualPersistedMember.getName());
        Assertions.assertEquals(expectedType, actualPersistedMember.getType());
        Assertions.assertEquals(aCastMember.getCreatedAt(), actualPersistedMember.getCreatedAt());
        Assertions.assertTrue(aCastMember.getUpdatedAt().isBefore(actualPersistedMember.getUpdatedAt()));

        Mockito.verify(castMemberGateway).findById(any());
        Mockito.verify(castMemberGateway).update(any());
    }

    @Test
    public void givenAnInValidNullName_whenCallsUpdateCastMember_shouldReturnNotificationException() {
        // given
        final var aCastMember = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aCastMember));
        Assertions.assertEquals(1, this.castMemberRepository.count());

        final var expectedId = aCastMember.getId();
        final String expectedName = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var aCommand = UpdateCastMemberCommand.with(
                expectedId.getValue(),
                expectedName,
                aCastMember.getType()
        );


        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(castMemberGateway).findById(any());
        Mockito.verify(castMemberGateway, times(0)).update(any());

        Assertions.assertEquals(1, this.castMemberRepository.count());
    }

    @Test
    public void givenAnInValidEmptyName_whenCallsUpdateCastMember_shouldReturnNotificationException() {
        // given
        final var aCastMember = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aCastMember));
        Assertions.assertEquals(1, this.castMemberRepository.count());

        final var expectedId = aCastMember.getId();
        final var expectedName = " ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var aCommand = UpdateCastMemberCommand.with(
                expectedId.getValue(),
                expectedName,
                aCastMember.getType()
        );


        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(castMemberGateway).findById(any());
        Mockito.verify(castMemberGateway, times(0)).update(any());
        Assertions.assertEquals(1, this.castMemberRepository.count());

    }

    @Test
    public void givenAnInValidNullType_whenCallsUpdateCastMember_shouldReturnNotificationException() {
        // given
        final var aCastMember = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aCastMember));
        Assertions.assertEquals(1, this.castMemberRepository.count());;

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


        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(castMemberGateway).findById(any());
        Mockito.verify(castMemberGateway, times(0)).update(any());

        Assertions.assertEquals(1, this.castMemberRepository.count());
    }
}
