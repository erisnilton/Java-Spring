package erisnilton.dev.admin.catalogo.application.video.create;

import erisnilton.dev.admin.catalogo.domain.Genre.GenreGateway;
import erisnilton.dev.admin.catalogo.domain.Genre.GenreID;
import erisnilton.dev.admin.catalogo.domain.Identifier;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.category.CategoryGateway;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import erisnilton.dev.admin.catalogo.domain.exceptions.DomainException;
import erisnilton.dev.admin.catalogo.domain.exceptions.NotificationException;
import erisnilton.dev.admin.catalogo.domain.validation.Error;
import erisnilton.dev.admin.catalogo.domain.validation.ValidationHandler;
import erisnilton.dev.admin.catalogo.domain.validation.handler.Notification;
import erisnilton.dev.admin.catalogo.domain.video.Rating;
import erisnilton.dev.admin.catalogo.domain.video.Video;
import erisnilton.dev.admin.catalogo.domain.video.VideoGateway;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DefaultCreateVideoUseCase extends CreateVideoUseCase {

    private final GenreGateway genreGateway;
    private final CategoryGateway categoryGateway;
    private final CastMemberGateway castMemberGateway;
    private final VideoGateway videoGateway;

    public DefaultCreateVideoUseCase(
            final GenreGateway genreGateway,
            final CategoryGateway categoryGateway,
            final CastMemberGateway castMemberGateway,
            final VideoGateway videoGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public CreateVideoOutput execute(final CreateVideoCommand aCommand) {
        final var aRating = Rating.of(aCommand.rating()).orElseThrow(invalidRating(aCommand.rating()));
        final var categories = toIdentifier(aCommand.categories(), CategoryID::from);
        final var genres = toIdentifier(aCommand.genres(), GenreID::from);
        final var members = toIdentifier(aCommand.members(), CastMemberID::from);

        System.out.println(aCommand);

        final var notification = Notification.create();
        notification.append(validateCategories(categories));
        notification.append(validateGenres(genres));
        notification.append(validateMember(members));
        final var aVideo = Video.newVideo(
                aCommand.title(),
                aCommand.description(),
                Year.of(aCommand.launchedAt()),
                aCommand.duration(),
                aRating,
                aCommand.opened(),
                aCommand.published(),
                categories,
                genres,
                members
        );
        aVideo.validate(notification);

        if (notification.hasErrror()){
            throw new NotificationException("Could not create Aggregate video", notification);
        }
        return CreateVideoOutput.from(create(aCommand, aVideo));
    }

    private Video create(final CreateVideoCommand aCommand, final Video aVideo){
        return this.videoGateway.create(aVideo);
    }

    private ValidationHandler validateCategories(final Set<CategoryID> ids) {
        return validateAggregate("categories", ids, categoryGateway::existsByIds);

    }

    private ValidationHandler validateGenres(final Set<GenreID> ids) {
        return validateAggregate("genres", ids, genreGateway::existsByIds);
    }

    private ValidationHandler validateMember(final Set<CastMemberID> ids) {
        return validateAggregate("castmembers", ids, castMemberGateway::existsByIds);
    }

    private <T extends Identifier> ValidationHandler validateAggregate(
            final String aggregate,
            final Set<T> ids,
            final Function<Iterable<T>, List<T>> existsByIds
    ) {
        final var notification = Notification.create();

        if (ids == null || ids.isEmpty()) {
            return notification;
        }

        final var retrieveIds = existsByIds.apply(ids);

        if (ids.size() != retrieveIds.size()) {
            final var missingIds = new ArrayList<>(ids);
            missingIds.removeAll(retrieveIds);

            final var missingIdsMessage = missingIds.stream()
                    .map(Identifier::getValue)
                    .collect(Collectors.joining(", "));

            notification.append(new Error("Some %s could not be found: %s".formatted(aggregate, missingIdsMessage)));
        }
        return notification;
    }

    private <T> Set<T> toIdentifier(final Set<String> ids, final Function<String, T> mapper) {
        return ids.stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }


    private Supplier<DomainException> invalidRating(final String rating) {
        return () -> DomainException.with(new Error("Rating not found %s".formatted(rating)));
    }
}
