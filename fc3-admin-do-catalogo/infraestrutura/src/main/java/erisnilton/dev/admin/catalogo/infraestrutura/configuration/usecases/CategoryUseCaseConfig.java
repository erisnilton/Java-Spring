package erisnilton.dev.admin.catalogo.infraestrutura.configuration.usecases;

import erisnilton.dev.admin.catalogo.application.category.create.CreateCategoryUseCase;
import erisnilton.dev.admin.catalogo.application.category.create.DefaultCreateCategoryUseCase;
import erisnilton.dev.admin.catalogo.application.category.delete.DefaultDeleteCategoryUseCase;
import erisnilton.dev.admin.catalogo.application.category.delete.DeleteCategoryUseCase;
import erisnilton.dev.admin.catalogo.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import erisnilton.dev.admin.catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import erisnilton.dev.admin.catalogo.application.category.retrieve.list.DefaultListCategoryUseCase;
import erisnilton.dev.admin.catalogo.application.category.retrieve.list.ListCategoriesUseCase;
import erisnilton.dev.admin.catalogo.application.category.update.DefaultUpdateCategoryUseCase;
import erisnilton.dev.admin.catalogo.application.category.update.UpdateCategoryUseCase;
import erisnilton.dev.admin.catalogo.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase  createCategoryUseCase () {
        return  new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase () {
        return  new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new DefaultListCategoryUseCase(categoryGateway);
    }
}
