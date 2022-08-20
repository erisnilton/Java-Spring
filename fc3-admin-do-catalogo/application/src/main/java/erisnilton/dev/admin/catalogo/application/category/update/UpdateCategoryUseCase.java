package erisnilton.dev.admin.catalogo.application.category.update;

import erisnilton.dev.admin.catalogo.application.UseCase;
import erisnilton.dev.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends
        UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
