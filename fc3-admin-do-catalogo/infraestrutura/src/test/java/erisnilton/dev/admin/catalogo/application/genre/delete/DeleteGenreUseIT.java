package erisnilton.dev.admin.catalogo.application.genre.delete;

import erisnilton.dev.admin.catalogo.IntegrationTest;
import erisnilton.dev.admin.catalogo.domain.Genre.Genre;
import erisnilton.dev.admin.catalogo.domain.Genre.GenreGateway;
import erisnilton.dev.admin.catalogo.domain.Genre.GenreID;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class DeleteGenreUseIT {

    @Autowired
    private DeleteGenreUseCase useCase;

    @Autowired
    private GenreGateway genreGateway;

    @Autowired
    private GenreRepository genreRepository;


    @Test
    public void givenAValidGenreId_whenCallsDeleteGenre_shouldDeleteGenre() {
        // given
        final var aGenre = genreGateway.create(
                Genre.newGenre("Ação", true)
        );

        final var expectedId = aGenre.getId();

        Assertions.assertEquals(1, genreRepository.count());

        // when

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        Assertions.assertEquals(0, genreRepository.count());

    }

    @Test
    public void givenAnInValidGenreId_whenCallsDeleteGenre_shouldBeOk() {
        // given

        genreGateway.create( Genre.newGenre("filmes", true) );

        Assertions.assertEquals(1, genreRepository.count());

        final var expectedId = GenreID.from("123");

        // when

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then

        Assertions.assertEquals(1, genreRepository.count());
    }

}
