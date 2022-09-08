package erisnilton.dev.admin.catalogo.infraestrutura.genre;

import erisnilton.dev.admin.catalogo.domain.Genre.Genre;
import erisnilton.dev.admin.catalogo.domain.Genre.GenreGateway;
import erisnilton.dev.admin.catalogo.domain.Genre.GenreID;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;
import erisnilton.dev.admin.catalogo.domain.pagination.SearchQuery;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.persistence.GenreJpaEntity;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.persistence.GenreRepository;
import erisnilton.dev.admin.catalogo.infraestrutura.utils.SpeficicationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static erisnilton.dev.admin.catalogo.infraestrutura.utils.SpeficicationUtils.like;
import static org.springframework.data.domain.Sort.Direction;
import static org.springframework.data.domain.Sort.by;

@Component
public class GenreMySQLGateway implements GenreGateway {

    private final GenreRepository genreRepository;

    public GenreMySQLGateway(final GenreRepository genreRepository) {
        this.genreRepository = Objects.requireNonNull(genreRepository);
    }

    @Override
    public Genre create(final Genre aGenre) {
        return save(aGenre);
    }

    private Genre save(final Genre aGenre) {
        return this.genreRepository.save(GenreJpaEntity.from(aGenre)).toAggregate();
    }

    @Override
    public void deleteById(final GenreID anId) {
        final var aGenreId = anId.getValue();

        if (genreRepository.existsById(aGenreId)) {
            this.genreRepository.deleteById(aGenreId);
        }
    }

    @Override
    public Optional<Genre> findById(final GenreID anId) {
        return this.genreRepository
                .findById(anId.getValue())
                .map(GenreJpaEntity::toAggregate);
    }

    @Override
    public Genre update(final Genre aGenre) {
        return save(aGenre);
    }

    @Override

    public Pagination<Genre> findAll(final SearchQuery aQuery) {
        //paginação
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                by(Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        // Busca dinamica pelo criterio tems (name ou createAt)
        final var specifications =
                Optional.ofNullable(aQuery.terms())
                        .filter(str -> !str.isBlank())
                        .map(str -> SpeficicationUtils.<GenreJpaEntity>like("name", str))
                        .orElse(null);

        final var pageResult = this.genreRepository.findAll(Specification.where(specifications), page);
        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(GenreJpaEntity::toAggregate).toList()
        );
    }
}
