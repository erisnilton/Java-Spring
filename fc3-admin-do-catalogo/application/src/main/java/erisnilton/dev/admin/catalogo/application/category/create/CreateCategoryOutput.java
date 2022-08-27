package erisnilton.dev.admin.catalogo.application.category.create;

import erisnilton.dev.admin.catalogo.domain.category.Category;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;

public record CreateCategoryOutput(String id) {
    public static CreateCategoryOutput from(String anId) {
        return new CreateCategoryOutput(anId);
    }
    public static CreateCategoryOutput from(Category aCategory) {
        return new CreateCategoryOutput(aCategory.getId().getValue());
    }
}
