package erisnilton.dev.admin.catalogo.application.genre.update;

import erisnilton.dev.admin.catalogo.application.UseCaseTest;
import erisnilton.dev.admin.catalogo.domain.Genre.Genre;
import erisnilton.dev.admin.catalogo.domain.Genre.GenreGateway;
import erisnilton.dev.admin.catalogo.domain.category.CategoryGateway;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
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
import static org.mockito.Mockito.*;

public class UpdateGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Mock
    private CategoryGateway categoryGateway;


    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway, genreGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateGenre_shouldReturnGenreId() {
        // given
        final var aGenre = Genre.newGenre("asão", false);

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

        when(genreGateway.update(any())).thenAnswer(returnsFirstArg());
        when(genreGateway.findById(any())).thenReturn(Optional.of(Genre.with(aGenre)));

        // when
        final var actualOutput = useCase.execute(aCommand);
        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(genreGateway, times(1)).update(argThat(aUpdateGenre ->
                Objects.equals(expectedId, aUpdateGenre.getId())
                        && Objects.equals(expectedName, aUpdateGenre.getName())
                        && Objects.equals(expectedIsActive, aUpdateGenre.isActive())
                        && Objects.equals(expectedCategories, aUpdateGenre.getCategories())
                        && Objects.equals(aGenre.getUpdatedAt(), aUpdateGenre.getCreatedAt())
                        && aGenre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt())
                        && Objects.isNull(aUpdateGenre.getDeletedAt())

        ));
    }

    @Test
    public void givenAValidCommandWithCategories_whenCallsUpdateGenre_shouldReturnGenreId() {
        // given
        final var aGenre = Genre.newGenre("asão", false);

        final var expectedId = aGenre.getId();
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("456")
        );

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        when(genreGateway.update(any())).thenAnswer(returnsFirstArg());
        when(genreGateway.findById(any())).thenReturn(Optional.of(Genre.with(aGenre)));
        when(categoryGateway.existsByIds(any())).thenReturn(expectedCategories);

        // when
        final var actualOutput = useCase.execute(aCommand);
        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, times(1)).existsByIds(expectedCategories);
        Mockito.verify(genreGateway, times(1)).update(argThat(aUpdateGenre ->
                Objects.equals(expectedId, aUpdateGenre.getId())
                        && Objects.equals(expectedName, aUpdateGenre.getName())
                        && Objects.equals(expectedIsActive, aUpdateGenre.isActive())
                        && Objects.equals(expectedCategories, aUpdateGenre.getCategories())
                        && Objects.equals(aGenre.getUpdatedAt(), aUpdateGenre.getCreatedAt())
                        && aGenre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt())
                        && Objects.isNull(aUpdateGenre.getDeletedAt())

        ));
    }

    @Test
    public void givenAValidCommandWithInativeGenre_whenCallsUpdateGenre_shouldReturnGenreId() {
        // given
        final var aGenre = Genre.newGenre("asão", true);

        final var expectedId = aGenre.getId();
        final var expectedName = "Ação";
        final var expectedIsActive = false;
        final var expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("456")
        );

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        when(genreGateway.update(any())).thenAnswer(returnsFirstArg());
        when(genreGateway.findById(any())).thenReturn(Optional.of(Genre.with(aGenre)));
        when(categoryGateway.existsByIds(any())).thenReturn(expectedCategories);

        Assertions.assertTrue(aGenre.isActive());
        Assertions.assertNull(aGenre.getDeletedAt());

        // when
        final var actualOutput = useCase.execute(aCommand);
        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, times(1)).existsByIds(expectedCategories);
        Mockito.verify(genreGateway, times(1)).update(argThat(aUpdateGenre ->
                Objects.equals(expectedId, aUpdateGenre.getId())
                        && Objects.equals(expectedName, aUpdateGenre.getName())
                        && Objects.equals(expectedIsActive, aUpdateGenre.isActive())
                        && Objects.equals(expectedCategories, aUpdateGenre.getCategories())
                        && Objects.equals(aGenre.getUpdatedAt(), aUpdateGenre.getCreatedAt())
                        && aGenre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt())
                        && Objects.nonNull(aUpdateGenre.getDeletedAt())

        ));
    }

    @Test
    public void givenAnInValidName_whenCallsUpdateGenre_shouldReturnNotificationException() {
        // given
        final var aGenre = Genre.newGenre("asão", false);

        final var expectedId = aGenre.getId();
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        when(genreGateway.findById(any())).thenReturn(Optional.of(Genre.with(aGenre)));

        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, times(0)).existsByIds(any());
        Mockito.verify(genreGateway, times(0)).update(any());
    }

    @Test
    public void givenAnInValidName_whenCallsUpdateGenreAndSomeCategoriesDoesNotExists_shouldReturnNotificationException() {
        // given
        final var filmes = CategoryID.from("123");
        final var series = CategoryID.from("456");
        final var documentarios = CategoryID.from("789");

        final var aGenre = Genre.newGenre("asão", false);

        final var expectedId = aGenre.getId();
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes, series, documentarios);
        final var expectedErrorCount = 2;
        final var expectedErrorMessageOne = "Some categories could not be found: 456, 789";
        final var expectedErrorMessageTwo = "'name' should not be null";

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        when(genreGateway.findById(any())).thenReturn(Optional.of(Genre.with(aGenre)));
        when(categoryGateway.existsByIds(any())).thenReturn(List.of(filmes));

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
}
