package erisnilton.dev.admin.catalogo.e2e.genre;

import erisnilton.dev.admin.catalogo.E2ETest;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import erisnilton.dev.admin.catalogo.e2e.MockDsl;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@E2ETest
@Testcontainers
public class GenreE2ETest implements MockDsl {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private GenreRepository genreRepository;


    @Container
    private static final MySQLContainer MY_SQL_CONTAINER
            = new MySQLContainer("mysql:latest")
            .withUsername("root")
            .withPassword("123456")
            .withDatabaseName("adm_videos");

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("mysql.port", () -> MY_SQL_CONTAINER.getMappedPort(3306));
    }

    @Override
    public MockMvc mvc() {
        return this.mvc;
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToCreateANewGenreWithValidValues() throws Exception {

        Assertions.assertTrue(MY_SQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, genreRepository.count());

        final var expectedName = "Ação";
        final var expectedCategories = List.<CategoryID>of();
        final var expectedIsActive = true;

        final var actualId = givenAGenre(expectedName, expectedCategories, expectedIsActive);

        final var actualGenre = this.genreRepository.findById(actualId.getValue()).get();

        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertTrue(expectedCategories.size() == actualGenre.getCategoriesIDs().size()
                && expectedCategories.containsAll(actualGenre.getCategoriesIDs()));
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToCreateANewGenreWithCategories() throws Exception {

        Assertions.assertTrue(MY_SQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, genreRepository.count());

        final var filmes = givenACategory("Filmes", null, true);

        final var expectedName = "Ação";
        final var expectedCategories = List.<CategoryID>of(filmes);
        final var expectedIsActive = true;

        final var actualId = givenAGenre(expectedName, expectedCategories, expectedIsActive);

        final var actualGenre = this.genreRepository.findById(actualId.getValue()).get();

        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertTrue(expectedCategories.size() == actualGenre.getCategoriesIDs().size()
                && expectedCategories.containsAll(actualGenre.getCategoriesIDs()));
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());
    }
}
