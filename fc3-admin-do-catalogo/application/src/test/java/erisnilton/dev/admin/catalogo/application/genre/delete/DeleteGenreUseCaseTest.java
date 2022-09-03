package erisnilton.dev.admin.catalogo.application.genre.delete;

import erisnilton.dev.admin.catalogo.application.UseCaseTest;
import erisnilton.dev.admin.catalogo.domain.Genre.Genre;
import erisnilton.dev.admin.catalogo.domain.Genre.GenreGateway;
import erisnilton.dev.admin.catalogo.domain.Genre.GenreID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeleteGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(genreGateway);
    }

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenre_shouldDeleteGenre() {
        // given
        final var aGenre = Genre.newGenre("Ação", true);

        final var expectedId = aGenre.getId();

        doNothing().when(genreGateway).deleteById(any());

        // when

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then

        Mockito.verify(genreGateway, times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAnInValidGenreId_whenCallsDeleteGenre_shouldBeOk() {
        // given

        final var expectedId = GenreID.from("123");

        doNothing().when(genreGateway).deleteById(any());

        // when

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then

        Mockito.verify(genreGateway, times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenreAndGatewayThrowsUnexpectedError_shouldReceiveException() {
        // given

        final var aGenre = Genre.newGenre("Ação", true);

        final var expectedId = aGenre.getId();

        doThrow(new IllegalStateException("Gateway error")).when(genreGateway).deleteById(any());

        // when

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        // then

        Mockito.verify(genreGateway, times(1)).deleteById(expectedId);
    }
}
