package erisnilton.dev.admin.catalogo.infraestrutura.api.controllers;

import erisnilton.dev.admin.catalogo.application.genre.create.CreateGenreCommand;
import erisnilton.dev.admin.catalogo.application.genre.create.CreateGenreUseCase;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;
import erisnilton.dev.admin.catalogo.infraestrutura.api.GenreAPI;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.models.CreateGenreRequest;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.models.GenreListResponse;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.models.GenreResponse;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.models.UpdateGenreRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

import static java.util.Objects.*;

@RestController
public class GenreController implements GenreAPI {

    private final CreateGenreUseCase createGenreUseCase;

    public GenreController(final CreateGenreUseCase createGenreUseCase) {
        this.createGenreUseCase = requireNonNull(createGenreUseCase);
    }

    @Override
    public ResponseEntity<?> createGenre(final CreateGenreRequest input) {

        final var aCommand = CreateGenreCommand.with(input.name(), input.isActive(), input.categories());

        final var output =  this.createGenreUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/genres/" + output.id())).body(output);
    }

    @Override
    public Pagination<GenreListResponse> listGenres(final String search, final int page, final int perPage, final String sort, final String dir) {
        return null;
    }

    @Override
    public GenreResponse getById(final String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateGenreRequest input) {
        return null;
    }

    @Override
    public void deleteById(final String id) {

    }
}
