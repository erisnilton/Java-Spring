package erisnilton.dev.admin.catalogo.domain.castmember;

import erisnilton.dev.admin.catalogo.domain.exceptions.NotificationException;
import erisnilton.dev.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CastMemberTest {

    @Test
    public void giveValidParams_whenCallANewCastmember_thenInstantiateACastmember() {

        final var expectedName = "Vin Diesel";
        final var expectedType = CastMemberType.ACTOR;

        final var actualCastMember = CastMember.newMember(expectedName, expectedType);

        Assertions.assertNotNull(actualCastMember);
        Assertions.assertNotNull(actualCastMember.getId());
        Assertions.assertEquals(expectedName, actualCastMember.getName());
        Assertions.assertEquals(expectedType, actualCastMember.getType());
        Assertions.assertNotNull(actualCastMember.getCreatedAt());
        Assertions.assertNotNull(actualCastMember.getUpdatedAt());
        Assertions.assertEquals(actualCastMember.getCreatedAt(), actualCastMember.getUpdatedAt());
    }

    @Test
    public void giveanInValidNullName_whenCallANewMemberAndValidate_thenShouldReciveNotification() {

        final String expectedName = null;
        final var expectedErrorCounter = 1;
        final var expectedMemberType = CastMemberType.ACTOR;
        final var expectedErrorMessage = "'name' should not be null";

        final var actulException = Assertions.assertThrows(NotificationException.class, () -> CastMember.newMember(expectedName, expectedMemberType));

        Assertions.assertEquals(expectedErrorCounter, actulException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actulException.getErrors().get(0).message());

    }

    @Test
    public void giveanInValidEmptyName_whenCallANewMemberAndValidate_thenShouldReciveNotification() {
        final var expectedName = " ";
        final var expectedErrorCounter = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedMemberType = CastMemberType.ACTOR;

        final var actulException = Assertions.assertThrows(NotificationException.class, () -> CastMember.newMember(expectedName, expectedMemberType));

        Assertions.assertEquals(expectedErrorCounter, actulException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actulException.getErrors().get(0).message());
    }

    @Test
    public void giveanInValidNameLengthLessThan3_whenCallANewMemberAndValidate_thenShouldReciveNotification() {

        final var expectedName = "vi";
        final var expectedErrorCounter = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 character";
        final var expectedMemberType = CastMemberType.ACTOR;

        final var actulException = Assertions.assertThrows(NotificationException.class, () -> CastMember.newMember(expectedName, expectedMemberType));

        Assertions.assertEquals(expectedErrorCounter, actulException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actulException.getErrors().get(0).message());
    }

    @Test
    public void giveanInValidNameLengthMoreThan255_whenCallANewMemberAndValidate_thenShouldReciveNotification() {

        final var expectedName = """
                                
                Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se o julgamento imparcial das 
                eventualidades garante a contribuição de um grupo importante na determinação das novas proposições. 
                É importante questionar o quanto a hegemonia do ambiente político cumpre um papel essencial na 
                formulação das direções preferenciais no sentido do progresso.O incentivo ao avanço tecnológico, assim 
                como o acompanhamento das preferências de consumo afeta positivamente a correta previsão do sistema de 
                formação de quadros que corresponde às necessidades.";
                                
                """;
        final var expectedErrorCounter = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 character";
        final var expectedMemberType = CastMemberType.ACTOR;

        final var actulException = Assertions.assertThrows(NotificationException.class, () -> CastMember.newMember(expectedName, expectedMemberType));

        Assertions.assertEquals(expectedErrorCounter, actulException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actulException.getErrors().get(0).message());
    }

    @Test
    public void givenAInvalidTypeNull_whenCallsNewMember_shouldReceiveANotification() {

        final var expectedName =  "Vin Diesel";
        final CastMemberType expetedType = null;
        final var expectedErrorCounter = 1;
        final var expectedErrorMessage = "'type' should not be null";

        final var actulException = Assertions.assertThrows(NotificationException.class, () -> CastMember.newMember(expectedName, expetedType));

        Assertions.assertEquals(expectedErrorCounter, actulException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actulException.getErrors().get(0).message());

    }

    @Test
    public void givenAValidCastMember_whenCallsUpdate_shouldReceiveUpdated() {

        final var expectedName = "Vin Diesel";
        final var expectedType =  CastMemberType.ACTOR;

        final var aCastMember = CastMember.newMember("Vin die", CastMemberType.DIRECTOR);


        final var createdAt = aCastMember.getCreatedAt();
        final var updatedAt = aCastMember.getUpdatedAt();


        final var actualCastMember =  aCastMember.update(expectedName, expectedType);

        Assertions.assertDoesNotThrow(() -> aCastMember.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(actualCastMember.getId(), aCastMember.getId());
        Assertions.assertEquals(actualCastMember.getName(), expectedName);
        Assertions.assertEquals(actualCastMember.getType(), expectedType);
        Assertions.assertNotNull(actualCastMember.getCreatedAt());
        Assertions.assertTrue(actualCastMember.getUpdatedAt().isAfter(updatedAt));
    }

    @Test
    public void givenAInValidCastMember_whenCallsUpdateWithEmptyName_shouldReceiveNotificationException() {

        final var expectedName = "Vin Diesel";
        final var expectedType =  CastMemberType.ACTOR;

        final var aCastMember = CastMember.newMember(expectedName, expectedType);

        final var expectedErrorCounter =  1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actulException = Assertions.assertThrows(NotificationException.class, () -> aCastMember.update(" ", expectedType));

        Assertions.assertEquals(expectedErrorCounter, actulException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actulException.getErrors().get(0).message());
    }

    @Test
    public void givenAInValidCastMember_whenCallsUpdateWithNullName_shouldReceiveNotificationException() {

        final var expectedName = "Vin Diesel";
        final var expectedType =  CastMemberType.ACTOR;

        final var aCastMember = CastMember.newMember(expectedName, expectedType);

        final var expectedErrorCounter =  1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actulException = Assertions.assertThrows(NotificationException.class, () -> aCastMember.update(null, expectedType));

        Assertions.assertEquals(expectedErrorCounter, actulException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actulException.getErrors().get(0).message());
    }

    @Test
    public void givenAInValidCastMember_whenCallsUpdateWithNameLengthMoreThan255_shouldReceiveNotificationException() {

        final var expectedType =  CastMemberType.ACTOR;


        final var expectedName = """
                                
                Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se o julgamento imparcial das 
                eventualidades garante a contribuição de um grupo importante na determinação das novas proposições. 
                É importante questionar o quanto a hegemonia do ambiente político cumpre um papel essencial na 
                formulação das direções preferenciais no sentido do progresso.O incentivo ao avanço tecnológico, assim 
                como o acompanhamento das preferências de consumo afeta positivamente a correta previsão do sistema de 
                formação de quadros que corresponde às necessidades.";
                                
                """;
        final var expectedErrorCounter = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 character";


        final var aCastMember = CastMember.newMember("Vin Disiel", expectedType);

        final var actulException = Assertions.assertThrows(NotificationException.class, () -> CastMember.newMember(expectedName, expectedType));

        Assertions.assertEquals(expectedErrorCounter, actulException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actulException.getErrors().get(0).message());
    }

    @Test
    public void givenAInValidCastMember_whenCallsUpdateWithNullType_shouldReceiveNotificationException() {

        final var expectedName = "Vin Diesel";
        final var expectedType =  CastMemberType.ACTOR;

        final var aCastMember = CastMember.newMember(expectedName, expectedType);

        final var expectedErrorCounter =  1;
        final var expectedErrorMessage = "'type' should not be null";

        final var actulException = Assertions.assertThrows(NotificationException.class, () -> aCastMember.update(expectedName, null));

        Assertions.assertEquals(expectedErrorCounter, actulException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actulException.getErrors().get(0).message());
    }

}
