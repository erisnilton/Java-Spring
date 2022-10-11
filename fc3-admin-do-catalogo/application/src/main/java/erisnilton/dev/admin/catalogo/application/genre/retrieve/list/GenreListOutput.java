package erisnilton.dev.admin.catalogo.application.genre.retrieve.list;

import erisnilton.dev.admin.catalogo.domain.Genre.Genre;
import erisnilton.dev.admin.catalogo.domain.Genre.GenreID;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;

import java.time.Instant;

public record GenreListOutput(
        GenreID id,
        String name,
        boolean isActive,
        Instant createdAt,
        Instant deletedAt
) {
    public static GenreListOutput from(final Genre aGenre) {
        return new GenreListOutput(
                aGenre.getId(),
                aGenre.getName(),
                aGenre.isActive(),
                aGenre.getCreatedAt(),
                aGenre.getDeletedAt()
        );
    }

}
