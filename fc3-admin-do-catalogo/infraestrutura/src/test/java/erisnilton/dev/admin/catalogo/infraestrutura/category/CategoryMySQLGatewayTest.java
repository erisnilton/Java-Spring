package erisnilton.dev.admin.catalogo.infraestrutura.category;

import erisnilton.dev.admin.catalogo.infraestrutura.MySQLGatewayTest;
import erisnilton.dev.admin.catalogo.infraestrutura.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@MySQLGatewayTest
public class CategoryMySQLGatewayTest {
    @Autowired
    private CategoryMySQLGateway categoryGateway;
    @Autowired
    private CategoryRepository repository;

    @BeforeEach
    void cleanUp() {
        this.repository.deleteAll();
    }

    @Test
    public void testInjectedDependencies() {
        Assertions.assertNotNull(categoryGateway);
        Assertions.assertNotNull(repository);
    }

}



