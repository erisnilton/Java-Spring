package erisnilton.dev.admin.catalogo.infraestrutura.category;

import erisnilton.dev.admin.catalogo.domain.category.Category;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import erisnilton.dev.admin.catalogo.domain.pagination.SearchQuery;
import erisnilton.dev.admin.catalogo.MySQLGatewayTest;
import erisnilton.dev.admin.catalogo.infraestrutura.category.persistence.CategoryJpaEntity;
import erisnilton.dev.admin.catalogo.infraestrutura.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@MySQLGatewayTest
public class CategoryMySQLGatewayTest {
    @Autowired
    private CategoryMySQLGateway categoryGateway;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAValidCategory_whenCallsCreate_shouldReturnANewCategory() {
        var expectedName = "Filmes";
        var expectedDescription = "A Categoria mais assistida";
        var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());

        final var actualCategory = categoryGateway.create(aCategory);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        final var actualEntity = categoryRepository.findById(aCategory.getId().getValue()).get();

        Assertions.assertEquals(aCategory.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualEntity.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallsUpdate_shouldReturnACategoryUpdated() {
        var expectedName = "Filmes";
        var expectedDescription = "A Categoria mais assistida";
        var expectedIsActive = true;

        final var aCategory = Category.newCategory("Film", null, true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));

        final var actualInvalidEntity = categoryRepository
                .findById(aCategory.getId().getValue()).get();
        Assertions.assertEquals("Film", actualInvalidEntity.getName());
        Assertions.assertNull(actualInvalidEntity.getDescription());
        Assertions.assertTrue(actualInvalidEntity.isActive());

        Assertions.assertEquals(1, categoryRepository.count());

        final var aUpdatedCategory = aCategory
                .clone().update(expectedName, expectedDescription,expectedIsActive);

        final var actualCategory = categoryGateway.update(aUpdatedCategory);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertTrue(aCategory.getUpdatedAt().isBefore(actualCategory.getUpdatedAt()));
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        final var actualEntity = categoryRepository.findById(aCategory.getId().getValue()).get();

        Assertions.assertEquals(aCategory.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertTrue(aCategory.getUpdatedAt().isBefore(actualCategory.getUpdatedAt()));
        Assertions.assertEquals(aCategory.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());
    }
    @Test
    public void givenPrePersistedCategoryAndValidCategoryId_whenTryDeleteIt_shouldDeletedCategory(){
        final var aCategory = Category.newCategory("Filmes", null, true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));

        Assertions.assertEquals(1, categoryRepository.count());

        categoryGateway.deleteById(aCategory.getId());

        Assertions.assertEquals(0, categoryRepository.count());

    }
    @Test
    public void givenAndValidCategoryId_whenTryDeleteIt_shouldDeletedCategory() {

        Assertions.assertEquals(0, categoryRepository.count());

        categoryGateway.deleteById(CategoryID.from("invalid"));

        Assertions.assertEquals(0, categoryRepository.count());

    }

    @Test
    public void givenPrePersistedCategoryAndValidCategoryId_whenCallsFindById_shouldReturnACategory() {

        var expectedName = "Filmes";
        var expectedDescription = "A Categoria mais assistida";
        var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));

        final var actualCategory = categoryGateway.findById(aCategory.getId()).get();

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

    }

    @Test
    public void givenAValidCategoryIdNotStored_whenCallsFindById_shouldReturnEmpty() {

        Assertions.assertEquals(0, categoryRepository.count());

        final var actualCategory = categoryGateway.findById(CategoryID.from("invalid"));

        Assertions.assertTrue(actualCategory.isEmpty());
    }

    @Test
    public void givenPrePersistedCategories_WhenCallsFindAll_shouldReturnPaginated() {

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var filmes = Category.newCategory("Filmes", null, true);
        final var series= Category.newCategory("Series", null, true);
        final var documentarios= Category.newCategory("Documentários", null, true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        Assertions.assertEquals(3, categoryRepository.count());

        final var aQuery = new SearchQuery(0,1,"","name", "asc");

        final var actualResult = categoryGateway.findAll(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());

        Assertions.assertEquals(documentarios.getId(), actualResult.items().get(0).getId());


    }

    @Test
    public void givenEmptyCategoriesTable_WhenCallsFindAll_shouldReturnEmptyPaginated() {

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        Assertions.assertEquals(0, categoryRepository.count());

        final var aQuery = new SearchQuery(0,1,"","name", "asc");

        final var actualResult = categoryGateway.findAll(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(0, actualResult.items().size());
    }

    @Test
    public void givenFollowPagination_WhenCallsFindAllWithPage1_shouldReturnPaginated() {

        var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var filmes = Category.newCategory("Filmes", null, true);
        final var series= Category.newCategory("Series", null, true);
        final var documentarios= Category.newCategory("Documentários", null, true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        Assertions.assertEquals(3, categoryRepository.count());

       var aQuery = new SearchQuery(0,1,"","name", "asc");

       var actualResult = categoryGateway.findAll(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());

        Assertions.assertEquals(documentarios.getId(), actualResult.items().get(0).getId());

        // Page 1

        aQuery = new SearchQuery(1,1,"","name", "asc");
        expectedPage = 1;
        actualResult = categoryGateway.findAll(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());

        Assertions.assertEquals(filmes.getId(), actualResult.items().get(0).getId());

        // Page 2

        aQuery = new SearchQuery(2,1,"","name", "asc");
        expectedPage = 2;
        actualResult = categoryGateway.findAll(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());

        Assertions.assertEquals(series.getId(), actualResult.items().get(0).getId());

    }

    @Test
    public void givenPrePersistedCategoriesDocsAsTerms_WhenCallsFindAll_shouldReturnPaginated() {

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var filmes = Category.newCategory("Filmes", null, true);
        final var series= Category.newCategory("Series", null, true);
        final var documentarios= Category.newCategory("Documentários", null, true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        Assertions.assertEquals(3, categoryRepository.count());

        final var aQuery = new SearchQuery(0,1,"doc","name", "asc");

        final var actualResult = categoryGateway.findAll(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(1, actualResult.items().size());

        Assertions.assertEquals(documentarios.getId(), actualResult.items().get(0).getId());

    }

    @Test
    public void givenPrePersistedCategoriesMaisAssistidaAsTerms_WhenCallsFindAllAndTermsMatchsCategoryDescription_shouldReturnPaginated() {

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var filmes = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var series= Category.newCategory("Series", "Uma categoria assistida", true);
        final var documentarios= Category.newCategory("Documentários", "A categoria menos assistida", true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        Assertions.assertEquals(3, categoryRepository.count());

        final var aQuery = new SearchQuery(0,1,"MAIS ASSISTIDA","name", "asc");

        final var actualResult = categoryGateway.findAll(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());

        Assertions.assertEquals(filmes.getId(), actualResult.items().get(0).getId());


    }
    @Test
    public void givenPrePersistedCategories_WhenCallsExistsByIds_shouldReturnIds() {

        final var filmes = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var series= Category.newCategory("Series", "Uma categoria assistida", true);
        final var documentarios= Category.newCategory("Documentários", "A categoria menos assistida", true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        Assertions.assertEquals(3, categoryRepository.count());

        final var expectedIds = List.of(filmes.getId(), series.getId());
        final var ids = List.of(filmes.getId(), series.getId(), CategoryID.from("123"));
        final var actualResult = categoryGateway.existsByIds(ids);

//        Assertions.assertEquals(actualResult, expectedIds);
    }
}



