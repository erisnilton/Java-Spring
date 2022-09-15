package erisnilton.dev.admin.catalogo.application.genre.update;


import erisnilton.dev.admin.catalogo.IntegrationTest;
import erisnilton.dev.admin.catalogo.domain.Genre.Genre;
import erisnilton.dev.admin.catalogo.domain.Genre.GenreGateway;
import erisnilton.dev.admin.catalogo.domain.category.Category;
import erisnilton.dev.admin.catalogo.domain.category.CategoryGateway;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotificationException;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@IntegrationTest
public class UpdateGenreUseIT {

    @Autowired
    private UpdateGenreUseCase useCase;

    @SpyBean
    private GenreGateway genreGateway;

    @SpyBean
    private CategoryGateway categoryGateway;

    @Autowired
    private GenreRepository genreRepository;


    @Test
    public void givenAValidCommand_whenCallsUpdateGenre_shouldReturnGenreId() {
        // given
        final var aGenre = genreGateway.create(Genre.newGenre("asão", false));

        final var expectedId = aGenre.getId();
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );


        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        final var aUpdatedGenre = genreRepository.findById(actualOutput.id()).get();

        Assertions.assertEquals(expectedName, aUpdatedGenre.getName());
        Assertions.assertEquals(expectedIsActive, aUpdatedGenre.isActive());
        Assertions.assertTrue(
                expectedCategories.size() == aUpdatedGenre.getCategoriesIDs().size()
                        && expectedCategories.containsAll(aUpdatedGenre.getCategoriesIDs())
        );
        Assertions.assertNotNull(aUpdatedGenre.getCreatedAt());
        Assertions.assertTrue(aGenre.getUpdatedAt().isBefore(aUpdatedGenre.getUpdatedAt()));
        Assertions.assertNull(aUpdatedGenre.getDeletedAt());
    }

    @Test
    public void givenAValidCommandWithCategories_whenCallsUpdateGenre_shouldReturnGenreId() {
        // given
        final var filmes = categoryGateway.create(
                Category.newCategory("Filmes", null, true));
        final var series = categoryGateway.create(
                Category.newCategory("Series", null, true));

        final var aGenre = genreGateway.create(Genre.newGenre("asão", false));

        final var expectedId = aGenre.getId();
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes.getId(), series.getId());

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );


        // when
        final var actualOutput = useCase.execute(aCommand);
        // then
        final var aUpdatedGenre = genreRepository.findById(actualOutput.id()).get();

        Assertions.assertEquals(expectedName, aUpdatedGenre.getName());
        Assertions.assertEquals(expectedIsActive, aUpdatedGenre.isActive());
        Assertions.assertTrue(
                expectedCategories.size() == aUpdatedGenre.getCategoriesIDs().size()
                        && expectedCategories.containsAll(aUpdatedGenre.getCategoriesIDs())
        );
        Assertions.assertNotNull(aUpdatedGenre.getCreatedAt());
        Assertions.assertTrue(aGenre.getUpdatedAt().isBefore(aUpdatedGenre.getUpdatedAt()));
        Assertions.assertNull(aUpdatedGenre.getDeletedAt());
    }


    @Test
    public void givenAValidCommandWithInativeGenre_whenCallsUpdateGenre_shouldReturnGenreId() {
        // given
        final var filmes = categoryGateway.create(
                Category.newCategory("Filmes", null, true));
        final var series = categoryGateway.create(
                Category.newCategory("Series", null, true));

        final var aGenre = genreGateway.create(Genre.newGenre("asão", true));

        final var expectedId = aGenre.getId();
        final var expectedName = "Ação";
        final var expectedIsActive = false;
        final var expectedCategories = List.of(filmes.getId(), series.getId());

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        // when
        final var actualOutput = useCase.execute(aCommand);
        // then
        final var aUpdatedGenre = genreRepository.findById(actualOutput.id()).get();

        Assertions.assertEquals(expectedName, aUpdatedGenre.getName());
        Assertions.assertEquals(expectedIsActive, aUpdatedGenre.isActive());
        Assertions.assertTrue(
                expectedCategories.size() == aUpdatedGenre.getCategoriesIDs().size()
                        && expectedCategories.containsAll(aUpdatedGenre.getCategoriesIDs())
        );
        Assertions.assertNotNull(aUpdatedGenre.getCreatedAt());
        Assertions.assertTrue(aGenre.getUpdatedAt().isBefore(aUpdatedGenre.getUpdatedAt()));
        Assertions.assertNotNull(aUpdatedGenre.getDeletedAt());
    }


    @Test
    public void givenAnInValidName_whenCallsUpdateGenre_shouldReturnNotificationException() {
        // given
        final var filmes = categoryGateway.create(
                Category.newCategory("Filmes", null, true));
        final var series = categoryGateway.create(
                Category.newCategory("Serie", null, true));

         final var aGenre = genreGateway.create(
                Genre.newGenre("asão", false)
        );

        final var expectedId = aGenre.getId();
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of( filmes.getId(), series.getId());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(genreGateway, times(0)).update(any());
    }

    @Test
    public void givenAnInValidName_whenCallsUpdateGenreAndSomeCategoriesDoesNotExists_shouldReturnNotificationException() {
        // given
        final var filmes = categoryGateway.create(
                Category.newCategory("Filmes", null, true)
        );
        final var series = CategoryID.from("456");
        final var documentarios = CategoryID.from("789");

        final var aGenre = genreGateway.create(
                Genre.newGenre("asão", false)
        );

        final var expectedId = aGenre.getId();
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes.getId(), series, documentarios);
        final var expectedErrorCount = 2;
        final var expectedErrorMessageOne = "Some categories could not be found: 456, 789";
        final var expectedErrorMessageTwo = "'name' should not be null";

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessageOne, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorMessageTwo, actualException.getErrors().get(1).message());

        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, times(1)).existsByIds(eq(expectedCategories));
        Mockito.verify(genreGateway, times(0)).update(any());
    }


    private List<String> asString(List<CategoryID> categories) {
        return categories.stream().map(CategoryID::getValue).toList();
    }
}
