package erisnilton.dev.admin.catalogo.application.category.retrieve.get;


import erisnilton.dev.admin.catalogo.IntegrationTest;
import erisnilton.dev.admin.catalogo.domain.category.Category;
import erisnilton.dev.admin.catalogo.domain.category.CategoryGateway;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import erisnilton.dev.admin.catalogo.domain.exceptions.DomainException;
import erisnilton.dev.admin.catalogo.infraestrutura.category.persistence.CategoryJpaEntity;
import erisnilton.dev.admin.catalogo.infraestrutura.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

@IntegrationTest
public class GetCategoryByIdUseCaseIT {

    @Autowired
    private GetCategoryByIdUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        System.out.println(aCategory.getCreatedAt());

        final var expectedId = aCategory.getId();

        save(aCategory);

        final var actualCategory = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(expectedId, actualCategory.id());
        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedDescription, actualCategory.description());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt().truncatedTo(ChronoUnit.DAYS), actualCategory.createdAt().truncatedTo(ChronoUnit.DAYS));
        Assertions.assertEquals(aCategory.getUpdatedAt().truncatedTo(ChronoUnit.DAYS), actualCategory.updatedAt().truncatedTo(ChronoUnit.DAYS));
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.deletedAt());
    }

    @Test
    public void givenInValidId_whenCallsGetCategory_shouldReturnNotFound() {
        final var expectedMessage = "Category with ID 123 was not found";
        final var  expectedId = CategoryID.from("123");

        final var actualException = Assertions.assertThrows(DomainException.class,
                () -> useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var expectedErrorMessage = "Geteway Error";
        final var  expectedId = CategoryID.from("123");

        doThrow(new IllegalStateException(expectedErrorMessage)).when(categoryGateway).findById(eq(expectedId));

        final var actualException = Assertions.assertThrows(IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    private void save(final Category... aCategory) {
        categoryRepository
                .saveAllAndFlush(Arrays.stream(aCategory).map(CategoryJpaEntity::from).toList());
    }
}
