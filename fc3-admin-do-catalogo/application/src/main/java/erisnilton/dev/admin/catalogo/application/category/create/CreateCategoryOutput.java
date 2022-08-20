package erisnilton.dev.admin.catalogo.application.category.create;

import erisnilton.dev.admin.catalogo.domain.category.Category;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;

public record CreateCategoryOutput(CategoryID id) {
    public static CreateCategoryOutput from(Category aCategory) {
        return new CreateCategoryOutput(aCategory.getId());
    }
}
