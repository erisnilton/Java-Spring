package erisnilton.dev.admin.catalogo.domain.genre;

import erisnilton.dev.admin.catalogo.domain.Genre.Genre;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotificationExeption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    public void givenAnActiveGenre_whenCallsDeactivate_shouldReceiveOK() {

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
    public void givenAnInactiveGenre_whenCallsActivate_shouldReceiveOK() {

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

    @Test
    public void givenAValidInctiveGenre_whenCallsUpdateWithActivate_shouldReceiveGenreUpdated() {

        final var expectedName = "Ação";
        final var expectedActive = true;
        final var expectedCategories = List.of(CategoryID.from("123"));

        final var actualGenre = Genre.newGenre("assao", false);

        Assertions.assertNotNull(actualGenre);
        Assertions.assertFalse(actualGenre.isActive());
        Assertions.assertNotNull(actualGenre.getDeletedAt());
        Assertions.assertNotNull(actualGenre.getCreatedAt());

        final var actualCreatedAt = actualGenre.getCreatedAt();
        final var actualUpdatedAt = actualGenre.getUpdatedAt();

        actualGenre.update(expectedName, expectedActive, expectedCategories);

        Assertions.assertNotNull(actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedActive, actualGenre.isActive());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());
        Assertions.assertEquals(actualCreatedAt, actualGenre.getCreatedAt());
        Assertions.assertTrue(actualUpdatedAt.isBefore(actualGenre.getUpdatedAt()));
        Assertions.assertNull(actualGenre.getDeletedAt());
    }

    @Test
    public void givenAValidActiveGenre_whenCallsUpdateWithInactivate_shouldReceiveGenreUpdated() {

        final var expectedName = "Ação";
        final var expectedActive = false;
        final var expectedCategories = List.of(CategoryID.from("123"));

        final var actualGenre = Genre.newGenre("assao", true);

        Assertions.assertNotNull(actualGenre);
        Assertions.assertTrue(actualGenre.isActive());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());

        final var actualCreatedAt = actualGenre.getCreatedAt();
        final var actualUpdatedAt = actualGenre.getUpdatedAt();

        actualGenre.update(expectedName, expectedActive, expectedCategories);

        Assertions.assertNotNull(actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedActive, actualGenre.isActive());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());
        Assertions.assertEquals(actualCreatedAt, actualGenre.getCreatedAt());
        Assertions.assertTrue(actualUpdatedAt.isBefore(actualGenre.getUpdatedAt()));
        Assertions.assertNotNull(actualGenre.getDeletedAt());
    }

    @Test
    public void givenAValidGenre_whenCallsUpdateWithEmptyName_shouldReceiveNotificationException() {
        // given
        final var expectedName = " ";
        final var expectedActive = true;
        final var expectedCategories = List.of(CategoryID.from("123"));
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualGenre = Genre.newGenre("assao", expectedActive);

        // when
        final var actualException = Assertions.assertThrows(NotificationExeption.class, () ->
                actualGenre.update(expectedName, expectedActive, expectedCategories)
        );

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidGenre_whenCallsUpdateWithNullName_shouldReceiveNotificationException() {
        // given
        final String expectedName = null;
        final var expectedActive = true;
        final var expectedCategories = List.of(CategoryID.from("123"));
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualGenre = Genre.newGenre("assao", expectedActive);

        // when
        final var actualException = Assertions.assertThrows(NotificationExeption.class, () ->
                actualGenre.update(expectedName, expectedActive, expectedCategories)
        );

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidGenre_whenCallsUpdateWithNullCategories_shouldReceiveOK() {
        // given
        final String expectedName = "Ação";
        final var expectedActive = true;
        final var expectedCategories = new ArrayList<CategoryID>();

        final var actualGenre = Genre.newGenre(expectedName, expectedActive);

        final var actualCreatedAt = actualGenre.getCreatedAt();
        final var actualUpdatedAt = actualGenre.getUpdatedAt();

        // when
        final var actualException = Assertions.assertDoesNotThrow(() ->
                actualGenre.update(expectedName, expectedActive, null)
        );

        // then
        Assertions.assertNotNull(actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedActive, actualGenre.isActive());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());
        Assertions.assertEquals(actualCreatedAt, actualGenre.getCreatedAt());
        Assertions.assertTrue(actualUpdatedAt.isBefore(actualGenre.getUpdatedAt()));
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());

    }

    @Test
    public void givenAValidEmptyCategoriesGenre_whenCallsAddCategory_shouldReceiveOK() {
        // given
        final var seriesID = CategoryID.from("123");
        final var moviesID = CategoryID.from("456");

        final String expectedName = "Ação";
        final var expectedActive = true;
        final var expectedCategories = List.of(seriesID, moviesID);

        final var actualGenre = Genre.newGenre(expectedName, expectedActive);

        Assertions.assertEquals(0, actualGenre.getCategories().size());

        final var actualCreatedAt = actualGenre.getCreatedAt();
        final var actualUpdatedAt = actualGenre.getUpdatedAt();

        // when
        actualGenre.addCategory(seriesID);
        actualGenre.addCategory(moviesID);

        // then
        Assertions.assertNotNull(actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedActive, actualGenre.isActive());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());
        Assertions.assertEquals(actualCreatedAt, actualGenre.getCreatedAt());
        Assertions.assertTrue(actualUpdatedAt.isBefore(actualGenre.getUpdatedAt()));
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());
    }

    @Test
    public void givenAValidGenreWithTwoCategories_whenCallsRemoveCategory_shouldReceiveOK() {
        // given
        final var seriesID = CategoryID.from("123");
        final var moviesID = CategoryID.from("456");

        final String expectedName = "Ação";
        final var expectedActive = true;
        final var expectedCategories = List.of(moviesID);

        final var actualGenre = Genre.newGenre(expectedName, expectedActive);
        actualGenre.update(expectedName,expectedActive, List.of(seriesID, moviesID));

        Assertions.assertEquals(2, actualGenre.getCategories().size());

        final var actualCreatedAt = actualGenre.getCreatedAt();
        final var actualUpdatedAt = actualGenre.getUpdatedAt();

        // when
        actualGenre.RemoveCategory(seriesID);

        // then
        Assertions.assertNotNull(actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedActive, actualGenre.isActive());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());
        Assertions.assertEquals(actualCreatedAt, actualGenre.getCreatedAt());
        Assertions.assertTrue(actualUpdatedAt.isBefore(actualGenre.getUpdatedAt()));
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());

    }

    @Test
    public void givenAInvalidNullAsCategoryID_whenCallsAddCategory_shouldReceiveOK() {
        // given
        final var seriesID = CategoryID.from("123");
        final var moviesID = CategoryID.from("456");

        final String expectedName = "Ação";
        final var expectedActive = true;
        final var expectedCategories = List.of(seriesID, moviesID);

        final var actualGenre = Genre.newGenre(expectedName, expectedActive);
        actualGenre.update(expectedName, expectedActive, expectedCategories);

        Assertions.assertEquals(2, actualGenre.getCategories().size());

        final var actualCreatedAt = actualGenre.getCreatedAt();
        final var actualUpdatedAt = actualGenre.getUpdatedAt();

        // when
        actualGenre.addCategory(null);

        // then
        Assertions.assertNotNull(actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedActive, actualGenre.isActive());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());
        Assertions.assertEquals(actualCreatedAt, actualGenre.getCreatedAt());
        Assertions.assertEquals(actualUpdatedAt, actualGenre.getUpdatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());
    }

    @Test
    public void givenAInvalidNullAsCategoryID_whenCallsRemoveCategory_shouldReceiveOK() {
        // given
        final var seriesID = CategoryID.from("123");
        final var moviesID = CategoryID.from("456");

        final String expectedName = "Ação";
        final var expectedActive = true;
        final var expectedCategories = List.of(seriesID, moviesID);

        final var actualGenre = Genre.newGenre(expectedName, expectedActive);
        actualGenre.update(expectedName,expectedActive, expectedCategories);

        Assertions.assertEquals(2, actualGenre.getCategories().size());

        final var actualCreatedAt = actualGenre.getCreatedAt();
        final var actualUpdatedAt = actualGenre.getUpdatedAt();

        // when
        actualGenre.RemoveCategory(null);

        // then
        Assertions.assertNotNull(actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedActive, actualGenre.isActive());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());
        Assertions.assertEquals(actualCreatedAt, actualGenre.getCreatedAt());
        Assertions.assertEquals(actualUpdatedAt, actualGenre.getUpdatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());

    }
}
