package erisnilton.dev.admin.catalogo.e2e;

import erisnilton.dev.admin.catalogo.domain.Genre.GenreID;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CreateCategoryResquest;
import erisnilton.dev.admin.catalogo.infraestrutura.configuration.json.Json;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.models.CreateGenreRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.function.Function;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface MockDsl {

    MockMvc mvc();

    default CategoryID givenACategory(final String aName, final String aDescription, final boolean isActive) throws Exception {
        final var aRequestBody = new CreateCategoryResquest(aName, aDescription, isActive);

        final var actualId =  this.given("/categories", aRequestBody);

        return CategoryID.from(actualId);
    }

    default  GenreID givenAGenre(final String aName, final List<CategoryID> categories, final boolean isActive) throws Exception {
        final var aRequestBody = new CreateGenreRequest(aName, mapTo(categories, CategoryID::getValue), isActive);

        final var actualId = given("/genres", aRequestBody);

        return GenreID.from(actualId);
    }

    default  <A, D> List<D> mapTo(final List<A> actual, final Function<A, D> mapper) {
        return actual.stream().map(mapper).toList();
    }

    private String given(
            final String url,
            final Object body
    ) throws Exception {

        final var aRequest = post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(body));

        final var actualId = this.mvc().perform(aRequest)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location")
                .replace("%s/".formatted(url), "");

        return actualId;
    }
}
