package erisnilton.dev.admin.catalogo.e2e;

import erisnilton.dev.admin.catalogo.domain.Genre.GenreID;
import erisnilton.dev.admin.catalogo.domain.Identifier;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CategoryResponse;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CreateCategoryResquest;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.UpdateCategoryRequest;
import erisnilton.dev.admin.catalogo.infraestrutura.configuration.json.Json;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.models.CreateGenreRequest;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.models.GenreResponse;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.models.UpdateGenreRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.function.Function;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface MockDsl {

    MockMvc mvc();

    default CategoryID givenACategory(final String aName, final String aDescription, final boolean isActive) throws Exception {
        final var aRequestBody = new CreateCategoryResquest(aName, aDescription, isActive);

        final var actualId = this.given("/categories", aRequestBody);

        return CategoryID.from(actualId);
    }

    default ResultActions deleteACategory(final Identifier anId) throws Exception {
        return delete("/categories/", anId);
    }

    default ResultActions updateACategory(final Identifier anId, final UpdateCategoryRequest aRequest) throws Exception {
        return update("/categories/", anId, aRequest );
    }

    default ResultActions listCategories(final int page, final int perPage, final String search, final String sort, final String direction) throws Exception {

        return list("/categories", page, perPage, search, sort, direction);
    }

    default ResultActions listCategories(final int page, final int perPage, final String search) throws Exception {

        return this.listCategories(page, perPage, search, "", "");
    }

    default ResultActions listCategories(final int page, final int perPage) throws Exception {

        return listCategories(page, perPage, "", "", "");
    }

    default CategoryResponse retrieveACategory(final Identifier anId) throws Exception {

        return retrieve("/categories/", anId, CategoryResponse.class);
    }

    default GenreID givenAGenre(final String aName, final List<CategoryID> categories, final boolean isActive) throws Exception {
        final var aRequestBody = new CreateGenreRequest(aName, mapTo(categories, CategoryID::getValue), isActive);

        final var actualId = given("/genres", aRequestBody);

        return GenreID.from(actualId);
    }


    default GenreResponse retrieveGenre(final Identifier anId) throws Exception {

        return retrieve("/genres/", anId, GenreResponse.class);
    }

    default ResultActions updateAGenre(final Identifier anId, final UpdateGenreRequest aRequest) throws Exception {
        return update("/genres/", anId, aRequest );
    }

    default ResultActions deleteAGenre(final Identifier anId) throws Exception {
        return delete("/genres/", anId);
    }

    default ResultActions listGenres(final int page, final int perPage, final String search, final String sort, final String direction) throws Exception {

        return list("/genres", page, perPage, search, sort, direction);
    }

    default ResultActions listGenres(final int page, final int perPage, final String search) throws Exception {

        return this.listGenres(page, perPage, search, "", "");
    }

    default ResultActions listGenres(final int page, final int perPage) throws Exception {

        return listGenres(page, perPage, "", "", "");
    }

    default <A, D> List<D> mapTo(final List<A> actual, final Function<A, D> mapper) {
        return actual.stream().map(mapper).toList();
    }

    private String given(final String url, final Object body) throws Exception {

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

    private ResultActions list(final String url, final int page, final int perPage, final String search, final String sort, final String direction) throws Exception {
        final var aRequest = get(url)
                .queryParam("page", String.valueOf(page))
                .queryParam("perPage", String.valueOf(perPage))
                .queryParam("search", search)
                .queryParam("sort", sort)
                .queryParam("dir", direction)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        return this.mvc().perform(aRequest);
    }

    private <T> T retrieve(final String url, final Identifier anId, final Class<T> clazz) throws Exception {
        final var aRequest = get(url + anId.getValue())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
        final var json = this.mvc().perform(aRequest)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return Json.readValue(json, clazz);
    }

    private ResultActions delete(final String url, Identifier anId) throws Exception {
        final var aRequest = MockMvcRequestBuilders.delete(url + anId.getValue())
                .accept(MediaType.APPLICATION_JSON);
        return this.mvc().perform(aRequest);
    }

    private ResultActions update(final String url, Identifier anId, final Object body) throws Exception {
        final var aRequest = put(url + anId.getValue())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(body));

        return this.mvc().perform(aRequest);
    }
}
