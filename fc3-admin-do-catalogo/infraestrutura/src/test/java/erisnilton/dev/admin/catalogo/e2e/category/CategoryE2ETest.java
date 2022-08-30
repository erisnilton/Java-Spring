package erisnilton.dev.admin.catalogo.e2e.category;

import erisnilton.dev.admin.catalogo.E2ETest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@E2ETest
@Testcontainers
public class CategoryE2ETest {

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
    public void testWorks() {
        Assertions.assertTrue(MY_SQL_CONTAINER.isRunning());
    }
}
