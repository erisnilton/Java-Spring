package erisnilton.dev.admin.catalogo.domain.category;

import erisnilton.dev.admin.catalogo.domain.exceptions.DomainException;
import erisnilton.dev.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class CategoryTest {

    @Test
    public void giveValidParams_whenCallANewCategory_thenInstantiateACategory() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActivate = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActivate);

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), expectedName);
        Assertions.assertEquals(actualCategory.getDescription(), expectedDescription);
        Assertions.assertEquals(actualCategory.isActive(), expectedActivate);
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void giveanInValidNullName_whenCallANewCategoryAndValidate_thenShouldReciveError() {

        final String expectedName = null;
        final var expectedErrorCounter = 1;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActivate = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActivate);

        final var actulException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCounter, actulException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actulException.getErrors().get(0).message());

    }

    @Test
    public void giveanInValidEmptyName_whenCallANewCategoryAndValidate_thenShouldReciveError() {

        final String expectedName = "  ";
        final var expectedErrorCounter = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActivate = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActivate);

        final var actulException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCounter, actulException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actulException.getErrors().get(0).message());

    }

    @Test
    public void giveanInValidNameLengthLessThan3_whenCallANewCategoryAndValidate_thenShouldReciveError() {

        final String expectedName = "Fi ";
        final var expectedErrorCounter = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 character";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActivate = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActivate);

        final var actulException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCounter, actulException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actulException.getErrors().get(0).message());

    }

    @Test
    public void giveanInValidNameLengthMoreThan255_whenCallANewCategoryAndValidate_thenShouldReciveError() {

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
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActivate = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActivate);

        final var actulException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCounter, actulException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actulException.getErrors().get(0).message());

    }

    @Test
    public void giveAValidEmptyDecription_whenCallANewCategoryAndValidate_thenShouldReciveError() {

        final var expectedName = "Filmes";
        final var expectedDescription = "";
        final var expectedActivate = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActivate);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), expectedName);
        Assertions.assertEquals(actualCategory.getDescription(), expectedDescription);
        Assertions.assertEquals(actualCategory.isActive(), expectedActivate);
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void giveAValidFalseIsActive_whenCallANewCategoryAndValidate_thenShouldReciveError() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActivate = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActivate);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), expectedName);
        Assertions.assertEquals(actualCategory.getDescription(), expectedDescription);
        Assertions.assertEquals(actualCategory.isActive(), expectedActivate);
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidActiveCategory_WhenCallDeactive_ThenReturnCategoryInactivated() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActivate = false;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, true);


        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualCategory = aCategory.deactivate();

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), expectedName);
        Assertions.assertEquals(actualCategory.getDescription(), expectedDescription);
        Assertions.assertEquals(actualCategory.isActive(), expectedActivate);
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidActiveCategory_WhenCallActivate_ThenReturnCategoryAtivated() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActivate = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, false);

        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertFalse(aCategory.isActive());
        Assertions.assertNotNull(aCategory.getDeletedAt());

        final var actualCategory = aCategory.activate();

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), expectedName);
        Assertions.assertEquals(actualCategory.getDescription(), expectedDescription);
        Assertions.assertEquals(actualCategory.isActive(), expectedActivate);
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActivate = true;

        final var aCategory = Category.newCategory("film", "categoria", expectedActivate);

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedActivate);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), expectedName);
        Assertions.assertEquals(actualCategory.getDescription(), expectedDescription);
        Assertions.assertEquals(actualCategory.isActive(), expectedActivate);
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateToInactive_thenReturnCategoryUpdated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActivate = false;

        final var aCategory = Category.newCategory("film", "categoria", true);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedActivate);



        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), expectedName);
        Assertions.assertEquals(actualCategory.getDescription(), expectedDescription);
        Assertions.assertEquals(actualCategory.isActive(), expectedActivate);
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertFalse(aCategory.isActive());
        Assertions.assertNotNull(aCategory.getDeletedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
    }

    @Test
    public void givenAValidCategory_whenCallUpdateToInactiveWithInvalidParams_thenReturnCategoryUpdated() {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActivate = true;

        final var aCategory = Category.newCategory("filmes", "categoria", expectedActivate);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedActivate);

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), expectedName);
        Assertions.assertEquals(actualCategory.getDescription(), expectedDescription);
        Assertions.assertEquals(actualCategory.isActive(), expectedActivate);
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
    }
}



