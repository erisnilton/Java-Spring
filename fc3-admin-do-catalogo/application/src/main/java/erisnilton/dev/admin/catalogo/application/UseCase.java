package erisnilton.dev.admin.catalogo.application;

import erisnilton.dev.admin.catalogo.domain.category.Category;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN anIn);
}