package erisnilton.dev.admin.catalogo.infraestrutura.configuration.usecases;

import erisnilton.dev.admin.catalogo.application.genre.create.CreateGenreUseCase;
import erisnilton.dev.admin.catalogo.application.genre.create.DefaultCreateGenreUseCase;
import erisnilton.dev.admin.catalogo.application.genre.delete.DefaultDeleteGenreUseCase;
import erisnilton.dev.admin.catalogo.application.genre.delete.DeleteGenreUseCase;
import erisnilton.dev.admin.catalogo.application.genre.retrieve.get.DefaultGetGenreByIdUseCase;
import erisnilton.dev.admin.catalogo.application.genre.retrieve.get.GetGenreByIdUseCase;
import erisnilton.dev.admin.catalogo.application.genre.retrieve.list.DefaultListGenreUseCase;
import erisnilton.dev.admin.catalogo.application.genre.retrieve.list.ListGenresUseCase;
import erisnilton.dev.admin.catalogo.application.genre.update.DefaultUpdateGenreUseCase;
import erisnilton.dev.admin.catalogo.application.genre.update.UpdateGenreUseCase;
import erisnilton.dev.admin.catalogo.domain.Genre.GenreGateway;
import erisnilton.dev.admin.catalogo.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class GenreUseCaseConfig {

    private final GenreGateway genreGateway;
    private final CategoryGateway categoryGateway;

    public GenreUseCaseConfig(final GenreGateway genreGateway, final CategoryGateway categoryGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Bean
    public CreateGenreUseCase createGenreUseCase() {
        return new DefaultCreateGenreUseCase(genreGateway, categoryGateway);
    }

    @Bean
    public UpdateGenreUseCase updateGenreUseCase() {
        return new DefaultUpdateGenreUseCase(genreGateway, categoryGateway);
    }

    @Bean
    public DeleteGenreUseCase deleteGenreUseCase() {
        return new DefaultDeleteGenreUseCase(genreGateway);
    }

    @Bean
    public GetGenreByIdUseCase getGenreByIdUseCase() {
        return new DefaultGetGenreByIdUseCase(genreGateway);
    }

    @Bean
    public ListGenresUseCase listGenresUseCase() {
        return new DefaultListGenreUseCase(genreGateway);
    }
}
