package erisnilton.dev.admin.catalogo.application.genre.update;

import erisnilton.dev.admin.catalogo.domain.Genre.Genre;

public record UpdateGenreOutput(
        String id
) {
    public static UpdateGenreOutput from(final String anId) {
        return new UpdateGenreOutput(anId);
    }

    public static UpdateGenreOutput from(Genre aGenre) {
        return new UpdateGenreOutput(aGenre.getId().getValue());
    }
}
