package erisnilton.dev.admin.catalogo.domain.video;

import erisnilton.dev.admin.catalogo.domain.validation.Error;
import erisnilton.dev.admin.catalogo.domain.validation.ValidationHandler;
import erisnilton.dev.admin.catalogo.domain.validation.Validator;

public class VideoValidator extends Validator {

    private static final int TITLE_MAX_LENGHT = 255;
    private static final int DESCRIPTION_MAX_LENGHT = 4_000;

    final Video video;

    protected VideoValidator(final Video aVideo, final ValidationHandler aHandler) {
        super(aHandler);
        this.video = aVideo;
    }

    @Override
    public void validate() {
        checkTitleConstraints();
        checkDescriptonConstraints();
        checkLaunchedAtContraints();
        checkRatingContraints();
    }

    private void checkTitleConstraints() {
        final var title = this.video.getTitle();

        if(title == null) {
            this.validationHandler().append(new Error("'title' should not be null"));
            return;
        }
        if(title.isBlank()) {
            this.validationHandler().append(new Error("'title' should not be empty"));
            return;
        }

        if(title.trim().length() > TITLE_MAX_LENGHT) {
            this.validationHandler().append(new Error("'title' must be between 1 and 255 characters"));
        }
    }

    private void checkDescriptonConstraints() {
        final var description = this.video.getDescription();

        if(description == null ) {
            this.validationHandler().append(new Error("'description' should not be null"));
            return;
        }
        if(description.isBlank()) {
            this.validationHandler().append(new Error("'description' should not be empty"));
            return;
        }
        if(description.trim().length() > DESCRIPTION_MAX_LENGHT) {
            this.validationHandler().append(new Error("'description' must be between 1 and 4000 characters"));
        }
    }
    private void checkLaunchedAtContraints() {
        final var launchedAt = this.video.getLaunchedAt();
        if(launchedAt == null) {
            this.validationHandler().append(new Error("'launchedAt' should not be null"));
        }

    }
    private void checkRatingContraints() {
        final var rating = this.video.getRating();
        if(rating == null) {
            this.validationHandler().append(new Error("'rating' should not be null"));
        }

    }
}
