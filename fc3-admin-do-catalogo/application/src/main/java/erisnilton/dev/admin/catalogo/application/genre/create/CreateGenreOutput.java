package erisnilton.dev.admin.catalogo.application.genre.create;

import erisnilton.dev.admin.catalogo.domain.Genre.Genre;

public record CreateGenreOutput(String id) {

    public static CreateGenreOutput from(final String anid) {
        return new CreateGenreOutput(anid);
    }

    public static CreateGenreOutput from(final Genre aGenre) {
        return new CreateGenreOutput(aGenre.getId().getValue());
    }

}
