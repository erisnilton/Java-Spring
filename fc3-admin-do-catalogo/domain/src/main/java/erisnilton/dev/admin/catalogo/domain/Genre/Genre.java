package erisnilton.dev.admin.catalogo.domain.Genre;

import erisnilton.dev.admin.catalogo.domain.AggregateRoot;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotificationException;
import erisnilton.dev.admin.catalogo.domain.utils.InstantUtils;
import erisnilton.dev.admin.catalogo.domain.validation.ValidationHandler;
import erisnilton.dev.admin.catalogo.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Genre extends AggregateRoot<GenreID> {

    private String name;
    private boolean active;
    private List<CategoryID> categories;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;


    private Genre(
            final GenreID anId,
            final String aName,
            final boolean isActive,
            final List<CategoryID> aCategories,
            final Instant aCreationDate,
            final Instant aUpdateDate,
            final Instant aDeleteDate
    ) {
        super(anId);
        this.name = aName;
        this.active = isActive;
        this.categories = aCategories;
        this.createdAt = Objects.requireNonNull(aCreationDate, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(aUpdateDate, "'updatedAt' should not be null");
        this.deletedAt = aDeleteDate;

        selfValidate();
    }

    public static Genre newGenre(final String name, final boolean isActive) {
        final var anId = GenreID.unique();
        final var now = InstantUtils.now();
        final var deletedAt = isActive ? null : now;
        return new Genre(anId, name, isActive, new ArrayList<>(), now, now, deletedAt);
    }

    public static Genre with(
            final GenreID anId,
            final String aName,
            final boolean isActive,
            final List<CategoryID> aCategories,
            final Instant aCreationDate,
            final Instant aUpdateDate,
            final Instant aDeleteDate
    ) {
        return new Genre(anId, aName, isActive, aCategories, aCreationDate, aUpdateDate, aDeleteDate);
    }

    public static Genre with(final Genre aGenre) {
        return new Genre(
                aGenre.id,
                aGenre.name,
                aGenre.active,
                new ArrayList<>(aGenre.categories),
                aGenre.createdAt,
                aGenre.updatedAt,
                aGenre.deletedAt
        );
    }

    @Override
    public void validate(final ValidationHandler aHandler) {
        new GenreValidator(this, aHandler).validate();
    }

    public Genre activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = InstantUtils.now();
        }
        this.active = false;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre update(final String aName, final boolean isActive, final List<CategoryID> categories) {
        if (isActive) {
            activate();
        } else {
            deactivate();
        }

        this.name = aName;
        this.categories = new ArrayList<>(categories != null ? categories : Collections.emptyList());
        this.updatedAt = InstantUtils.now();

        selfValidate();
        return this;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public List<CategoryID> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasErrror()) {
            throw new NotificationException("Failed to create a aggregate Genre", notification);
        }
    }

    public Genre addCategory(final CategoryID aCategoryID) {

        if (aCategoryID == null) {
            return this;
        }

        this.categories.add(aCategoryID);
        this.updatedAt = InstantUtils.now();

        return this;
    }

    public Genre addCategories(final List<CategoryID> categories) {

        if (categories == null || categories.isEmpty()) {
            return this;
        }

        this.categories.addAll(categories);
        this.updatedAt = InstantUtils.now();

        return this;
    }

    public Genre RemoveCategory(final CategoryID aCategoryID) {
        if (aCategoryID == null) {
            return this;
        }
        this.categories.remove(aCategoryID);
        this.updatedAt = InstantUtils.now();
        return this;
    }
}
