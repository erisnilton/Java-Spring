package erisnilton.dev.admin.catalogo.application.genre.retrieve.list;

import erisnilton.dev.admin.catalogo.domain.Genre.Genre;

import java.time.Instant;

public record GenreListOutput(
        String name,
        boolean isActive,
        Instant createdAt,
        Instant deletedAt
) {
    public static GenreListOutput from(final Genre aGenre) {
        return new GenreListOutput(
                aGenre.getName(),
                aGenre.isActive(),
                aGenre.getCreatedAt(),
                aGenre.getDeletedAt()
        );
    }

}
