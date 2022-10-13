package erisnilton.dev.admin.catalogo.infraestrutura.genre.models;

import erisnilton.dev.admin.catalogo.JacksonTest;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CreateCategoryResquest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.util.List;

@JacksonTest
class CreateGenreResquestTest {

    @Autowired
    private JacksonTester<CreateGenreRequest> json;

    @Test
    public void testMashall() throws Exception {

        final var expectedName = "Ação";
        final var expectedCategories = List.of("123");
        final var expectedIsActive = true;

        final var request = new CreateGenreRequest(
                expectedName,
                expectedCategories,
                expectedIsActive
        );

        final var actualJson = this.json.write(request);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.categories_id", expectedCategories)
                .hasJsonPathValue("$.is_active", expectedIsActive);
    }

    @Test
    public void testUnmashall() throws Exception {

        final var expectedName = "Ação";
        final var expectedCategory = "124";
        final var expectedIsActive = true;

        final var json = """
                    {
                        "name": "%s",
                        "categories_id": ["%s"],
                        "is_active": %s
                    }
                """.formatted(expectedName, expectedCategory, expectedIsActive);

        final var actualJson = this.json.parse(json);

        System.out.println(actualJson);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("name", expectedName)
                .hasFieldOrPropertyWithValue("categories", List.of(expectedCategory))
                .hasFieldOrPropertyWithValue("active", expectedIsActive);
    }
}