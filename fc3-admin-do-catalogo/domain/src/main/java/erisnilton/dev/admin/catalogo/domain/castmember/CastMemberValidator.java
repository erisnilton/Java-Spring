package erisnilton.dev.admin.catalogo.domain.castmember;

import erisnilton.dev.admin.catalogo.domain.category.Category;
import erisnilton.dev.admin.catalogo.domain.validation.Error;
import erisnilton.dev.admin.catalogo.domain.validation.ValidationHandler;
import erisnilton.dev.admin.catalogo.domain.validation.Validator;

public class CastMemberValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 255;

    public static final int NAME_MIN_LENGTH = 3;

    private CastMember castMember;

public CastMemberValidator(final CastMember aCastMember, final ValidationHandler aHandler) {
        super(aHandler);
        this.castMember = aCastMember;
    }

    @Override
    public void validate() {
        checkNameConstraints();

    }

    private void checkNameConstraints() {
        var name = this.castMember.getName();
        if(name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if(name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final var length = name.trim().length();
        if(length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 character"));
            return;
        }
    }
}
