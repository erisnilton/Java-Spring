package erisnilton.dev.admin.catalogo.domain.genre;

import erisnilton.dev.admin.catalogo.domain.Genre.Genre;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotificationExeption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenreTest {

    @Test
    public void givenValidParams_whenCallNewGenre_shouldIntanciateAGenre() {

        final var expectedName = "Ação";
        final var expectedActive = true;
        final var expectedCategories = 0;

        final var actualGenre = Genre.newGenre(expectedName, expectedActive);

        Assertions.assertNotNull(actualGenre);
        Assertions.assertNotNull(actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedActive, actualGenre.isActive());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories().size());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());
    }

    @Test
    public void givenInvalidNullName_whenCallNewGenreAndValidate_shouldReceiveError() {

        final String expectedName = null;
        final var expectedActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualException = Assertions.assertThrows(NotificationExeption.class, () -> {
            Genre.newGenre(expectedName, expectedActive);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenInvalidEmptyName_whenCallNewGenreAndValidate_shouldReceiveError() {

        final var expectedName = " ";
        final var expectedActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";


        final var actualException = Assertions.assertThrows(NotificationExeption.class, () -> {
            Genre.newGenre(expectedName, expectedActive);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenInvalidNameWithLengthGreaterThan255_whenCallNewGenreAndValidate_shouldReceiveError() {

        final var expectedName = """
                
                Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se o julgamento imparcial das 
                eventualidades garante a contribuição de um grupo importante na determinação das novas proposições. 
                É importante questionar o quanto a hegemonia do ambiente político cumpre um papel essencial na 
                formulação das direções preferenciais no sentido do progresso.O incentivo ao avanço tecnológico, assim 
                como o acompanhamento das preferências de consumo afeta positivamente a correta previsão do sistema de 
                formação de quadros que corresponde às necessidades.";
                                
                """;
        final var expectedActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 1 and 255 characters";

        final var actualException = Assertions.assertThrows(NotificationExeption.class, () -> {
            Genre.newGenre(expectedName, expectedActive);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnActiveGenre_whenCallsDeactivate_shouldrReceiveOK() {

        final var expectedName = "Ação";
        final var expectedActive = false;
        final var expectedCategories = 0;

        final var actualGenre = Genre.newGenre(expectedName, true);

        Assertions.assertNotNull(actualGenre);
        Assertions.assertTrue(actualGenre.isActive());
        Assertions.assertNull(actualGenre.getDeletedAt());
        Assertions.assertNotNull(actualGenre.getCreatedAt());

        final var actualCreatedAt = actualGenre.getCreatedAt();
        final var actualUpdatedAt = actualGenre.getUpdatedAt();

        actualGenre.deactivate();

        Assertions.assertNotNull(actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedActive, actualGenre.isActive());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories().size());
        Assertions.assertEquals(actualCreatedAt, actualGenre.getCreatedAt());
        Assertions.assertTrue(actualUpdatedAt.isBefore(actualGenre.getUpdatedAt()));
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNotNull(actualGenre.getDeletedAt());

    }

    @Test
    public void givenAnInactiveGenre_whenCallsActivate_shouldrReceiveOK() {

        final var expectedName = "Ação";
        final var expectedActive = true;
        final var expectedCategories = 0;

        final var actualGenre = Genre.newGenre(expectedName, false);

        Assertions.assertNotNull(actualGenre);
        Assertions.assertFalse(actualGenre.isActive());
        Assertions.assertNotNull(actualGenre.getDeletedAt());
        Assertions.assertNotNull(actualGenre.getCreatedAt());

        final var actualCreatedAt = actualGenre.getCreatedAt();
        final var actualUpdatedAt = actualGenre.getUpdatedAt();

        actualGenre.activate();

        Assertions.assertNotNull(actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedActive, actualGenre.isActive());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories().size());
        Assertions.assertEquals(actualCreatedAt, actualGenre.getCreatedAt());
        Assertions.assertTrue(actualUpdatedAt.isBefore(actualGenre.getUpdatedAt()));
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());
    }
}
