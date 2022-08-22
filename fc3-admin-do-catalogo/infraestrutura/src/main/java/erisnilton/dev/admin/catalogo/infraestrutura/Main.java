package erisnilton.dev.admin.catalogo.infraestrutura;

import erisnilton.dev.admin.catalogo.domain.category.Category;
import erisnilton.dev.admin.catalogo.infraestrutura.category.persistence.CategoryJpaEntity;
import erisnilton.dev.admin.catalogo.infraestrutura.category.persistence.CategoryRepository;
import erisnilton.dev.admin.catalogo.infraestrutura.configuration.WebServerConfig;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "production");
        SpringApplication.run(WebServerConfig.class ,args);
    }

    public ApplicationRunner runner (CategoryRepository repository) {
        return  args -> {
            List<CategoryJpaEntity> all = repository.findAll();

            Category filmes = Category.newCategory("Filmes", null, true);
            repository.saveAndFlush(CategoryJpaEntity.from(filmes));

            repository.deleteAll();;

        };
    }
}