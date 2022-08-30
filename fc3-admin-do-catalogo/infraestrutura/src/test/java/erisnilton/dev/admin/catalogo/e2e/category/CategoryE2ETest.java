package erisnilton.dev.admin.catalogo.e2e.category;

import erisnilton.dev.admin.catalogo.E2ETest;
import erisnilton.dev.admin.catalogo.application.category.retrieve.get.CategoryOutput;
import erisnilton.dev.admin.catalogo.domain.category.Category;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CategoryResponse;
import erisnilton.dev.admin.catalogo.infraestrutura.category.models.CreateCategoryResquest;
import erisnilton.dev.admin.catalogo.infraestrutura.category.persistence.CategoryRepository;
import erisnilton.dev.admin.catalogo.infraestrutura.configuration.json.Json;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@E2ETest
@Testcontainers
public class CategoryE2ETest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CategoryRepository categoryRepository;

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

    @Test
    public void asACatalogAdminIsShouldBeAbleToCreateANewCategoryWithValidValues() throws Exception {
        Assertions.assertTrue(MY_SQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, categoryRepository.count());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualId  = givenACategory(expectedName, expectedDescription, expectedIsActive);

        final var actualCategory = retrieveACategory(actualId.getValue());

        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedDescription, actualCategory.description());
        Assertions.assertEquals(expectedIsActive, actualCategory.active());
        Assertions.assertNotNull(actualCategory.createdAt());
        Assertions.assertNotNull(actualCategory.updatedAt());
        Assertions.assertNull(actualCategory.deletedAt());

    }

    private CategoryResponse retrieveACategory(final String anId) throws Exception {
        final var aRequest = get("/categories/" + anId)
                .contentType(MediaType.APPLICATION_JSON);
        final var json = this.mvc.perform(aRequest)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return Json.readValue(json, CategoryResponse.class);
    }


    private CategoryID givenACategory(final String aName, final String aDescription, final boolean isActive) throws Exception{
        final var aRequestBody = new CreateCategoryResquest(aName, aDescription, isActive);

       final var aRequest =  post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(aRequestBody));

       final var actualId = this.mvc.perform(aRequest)
               .andExpect(status().isCreated())
               .andReturn()
               .getResponse()
               .getHeader("Location")
               .replace("/categories/", "");

       return CategoryID.from(actualId);
    }
}
