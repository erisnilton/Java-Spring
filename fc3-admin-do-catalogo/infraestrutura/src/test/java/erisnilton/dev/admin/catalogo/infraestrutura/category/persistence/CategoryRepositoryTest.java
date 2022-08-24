package erisnilton.dev.admin.catalogo.infraestrutura.category.persistence;


import erisnilton.dev.admin.catalogo.domain.category.Category;
import erisnilton.dev.admin.catalogo.MySQLGatewayTest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@MySQLGatewayTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository  categoryRepository;

    @Test
    public void givenAnInvalidNullName_whenCallsSave_shouldReturnError() {

        final var expectedProperty = "name";
        final var expectedMessage = "not-null property references a null or transient value : erisnilton.dev.admin.catalogo.infraestrutura.category.persistence.CategoryJpaEntity.name";

        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);

        final var anEntity = CategoryJpaEntity.from(aCategory);
        anEntity.setName(null);

        final var acutalException =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        final var actualCouse = Assertions.assertInstanceOf(PropertyValueException.class, acutalException.getCause());

        Assertions.assertEquals(expectedProperty, actualCouse.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCouse.getMessage());
    }

    @Test
    public void givenAnInvalidNullCreatedAt_whenCallsSave_shouldReturnError() {

        final var expectedProperty = "createdAt";
        final var expectedMessage = "not-null property references a null or transient value : erisnilton.dev.admin.catalogo.infraestrutura.category.persistence.CategoryJpaEntity.createdAt";

        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);

        final var anEntity = CategoryJpaEntity.from(aCategory);
        anEntity.setCreatedAt(null);

        final var acutalException =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        final var actualCouse = Assertions.assertInstanceOf(PropertyValueException.class, acutalException.getCause());

        Assertions.assertEquals(expectedProperty, actualCouse.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCouse.getMessage());
    }

    @Test
    public void givenAnInvalidNullUpdatedAt_whenCallsSave_shouldReturnError() {

        final var expectedProperty = "updatedAt";
        final var expectedMessage = "not-null property references a null or transient value : erisnilton.dev.admin.catalogo.infraestrutura.category.persistence.CategoryJpaEntity.updatedAt";

        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);

        final var anEntity = CategoryJpaEntity.from(aCategory);
        anEntity.setUpdatedAt(null);

        final var acutalException =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        final var actualCouse = Assertions.assertInstanceOf(PropertyValueException.class, acutalException.getCause());

        Assertions.assertEquals(expectedProperty, actualCouse.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCouse.getMessage());
    }
}
