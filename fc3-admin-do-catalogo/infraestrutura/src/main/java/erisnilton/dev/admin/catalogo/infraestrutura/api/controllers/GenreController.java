package erisnilton.dev.admin.catalogo.infraestrutura.api.controllers;

import erisnilton.dev.admin.catalogo.application.genre.create.CreateGenreCommand;
import erisnilton.dev.admin.catalogo.application.genre.create.CreateGenreUseCase;
import erisnilton.dev.admin.catalogo.application.genre.delete.DeleteGenreUseCase;
import erisnilton.dev.admin.catalogo.application.genre.retrieve.get.GetGenreByIdUseCase;
import erisnilton.dev.admin.catalogo.application.genre.update.UpdateGenreCommand;
import erisnilton.dev.admin.catalogo.application.genre.update.UpdateGenreUseCase;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;
import erisnilton.dev.admin.catalogo.infraestrutura.api.GenreAPI;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.models.CreateGenreRequest;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.models.GenreListResponse;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.models.GenreResponse;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.models.UpdateGenreRequest;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.presenters.GenreApiPresenters;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static java.util.Objects.requireNonNull;

@RestController
public class GenreController implements GenreAPI {

    private final CreateGenreUseCase createGenreUseCase;

    private final GetGenreByIdUseCase getGenreByIdUseCase;

    private final UpdateGenreUseCase updateGenreUseCase;

    private final DeleteGenreUseCase deleteGenreUseCase;

    public GenreController(
            final CreateGenreUseCase createGenreUseCase,
            final GetGenreByIdUseCase getGenreByIdUseCase,
            final UpdateGenreUseCase updateGenreUseCase,
            final DeleteGenreUseCase deleteGenreUseCase) {
        this.createGenreUseCase = requireNonNull(createGenreUseCase);
        this.getGenreByIdUseCase = requireNonNull(getGenreByIdUseCase);
        this.updateGenreUseCase = requireNonNull(updateGenreUseCase);
        this.deleteGenreUseCase = requireNonNull(deleteGenreUseCase);
    }

    @Override
    public ResponseEntity<?> createGenre(final CreateGenreRequest input) {

        final var aCommand = CreateGenreCommand.with(input.name(), input.isActive(), input.categories());

        final var output = this.createGenreUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/genres/" + output.id())).body(output);
    }

    @Override
    public Pagination<GenreListResponse> listGenres(final String search, final int page, final int perPage, final String sort, final String dir) {
        return null;
    }

    @Override
    public GenreResponse getById(final String id) {
        return GenreApiPresenters.present(this.getGenreByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateGenreRequest input) {

        final var aCommand =
                UpdateGenreCommand.with(id, input.name(), input.isActive(), input.categories());

        final var output = this.updateGenreUseCase.execute(aCommand);
        return ResponseEntity.ok(output);
    }

    @Override
    public void deleteById(final String id) {
        this.deleteGenreUseCase.execute(id);
    }
}
