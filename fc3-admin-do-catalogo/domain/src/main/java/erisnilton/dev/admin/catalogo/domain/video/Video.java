package erisnilton.dev.admin.catalogo.domain.video;

import erisnilton.dev.admin.catalogo.domain.AggregateRoot;
import erisnilton.dev.admin.catalogo.domain.Genre.GenreID;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import erisnilton.dev.admin.catalogo.domain.utils.InstantUtils;
import erisnilton.dev.admin.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;
import java.time.Year;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Video extends AggregateRoot<VideoID> {

    private String title;
    private String description;
    private Year launchedAt;
    private double duration;
    private Rating rating;

    private boolean opened;
    private boolean published;

    private Instant createdAt;
    private Instant updatedAt;

    private ImageMedia banner;
    private ImageMedia thumbnail;
    private ImageMedia thumbnailHalf;

    private AudioVideoMedia trailer;
    private AudioVideoMedia video;

    private Set<CategoryID> categories;
    private Set<GenreID> genres;
    private Set<CastMemberID> castMembers;

    private Video(
            final VideoID anId,
            final String aTitle,
            final String aDescription,
            final Year aLaunchedAt,
            final Double aDuration,
            final Rating aRating,

            final boolean wasOpended,
            final boolean wasPublished,

            final Instant aCreatedAtDate,
            final Instant aUpdatedAtDate,

            final ImageMedia aBanner,
            final ImageMedia aThumbnail,
            final ImageMedia aThumbnailHalf,

            final AudioVideoMedia aTrailer,
            final AudioVideoMedia aVideo,

            final Set<CategoryID> anCategories,
            final Set<GenreID> anGenres,
            final Set<CastMemberID> anCastMembers


    ) {
        super(anId);
        this.title = aTitle;
        this.description = aDescription;
        this.launchedAt = aLaunchedAt;
        this.duration = aDuration;
        this.rating = aRating;

        this.opened = wasOpended;
        this.published = wasPublished;

        this.createdAt = aCreatedAtDate;
        this.updatedAt = aUpdatedAtDate;

        this.banner = aBanner;
        this.thumbnail = aThumbnail;
        this.thumbnailHalf = aThumbnailHalf;

        this.trailer = aTrailer;
        this.video = aVideo;

        this.categories = anCategories;
        this.genres = anGenres;
        this.castMembers = anCastMembers;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Year getLaunchedAt() {
        return launchedAt;
    }
    public double getDuration() {
        return duration;
    }
    public Rating getRating() {
        return rating;
    }
    public boolean getOpened() {
        return opened;
    }
    public boolean getPublished() {
        return published;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public Optional<ImageMedia> getBanner() {
        return Optional.ofNullable(banner);
    }
    public Video setBanner(final ImageMedia banner) {
        this.banner = banner;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Optional<ImageMedia> getThumbnail() {
        return Optional.ofNullable(thumbnail);
    }

    public Video setThumbnail(final ImageMedia thumbnail) {
        this.thumbnail = thumbnail;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Optional<ImageMedia> getThumbnailHalf() {
        return Optional.ofNullable(thumbnailHalf);
    }

    public Video setThumbnailHalf(final ImageMedia thumbnailHalf) {
        this.thumbnailHalf = thumbnailHalf;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Optional<AudioVideoMedia> getTrailer() {
        return Optional.ofNullable(trailer);
    }

    public Video setTrailer(final AudioVideoMedia trailer) {
        this.trailer = trailer;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Optional<AudioVideoMedia> getVideo() {
        return Optional.ofNullable(video);
    }

    public Video setVideo(final AudioVideoMedia video) {
        this.video  = video;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Set<CategoryID> getCategories() {
        return categories != null ? Collections.unmodifiableSet(categories) : Collections.emptySet();
    }

    private Video setCategories(final Set<CategoryID> categories) {
        this.categories = categories != null ? new HashSet<>(categories) : Collections.emptySet();
        return this;
    }

    public Set<GenreID> getGenres() {
        return genres != null ? Collections.unmodifiableSet(genres) : Collections.emptySet();
    }

    private Video setGenres(final Set<GenreID> genres) {
        this.genres = genres != null ? new HashSet<>(genres) : Collections.emptySet();
        return this;
    }

    public Set<CastMemberID> getCastMembers() {
        return castMembers != null ? Collections.unmodifiableSet(castMembers) : Collections.emptySet();
    }

    private Video setCastMembers(final Set<CastMemberID> castMembers) {
        this.castMembers = castMembers != null ? new HashSet<>(castMembers) : Collections.emptySet();
        return this;
    }

    @Override
    public void validate(ValidationHandler handler) {
        super.validate(handler);
    }

    public Video update(
            final String aTitle,
            final String aDescription,
            final Year aLaunchedAt,
            final Double aDuration,
            final Rating aRating,
            final boolean wasOpended,
            final boolean wasPublished,
            final Set<CategoryID> anCategories,
            final Set<GenreID> anGenres,
            final Set<CastMemberID> anCastMembers

    ) {
        this.title = aTitle;
        this.description = aDescription;
        this.launchedAt = aLaunchedAt;
        this.duration = aDuration;
        this.rating = aRating;
        this.opened = wasOpended;
        this.published = wasPublished;
        this.setCategories(anCategories);
        this.setGenres(anGenres);
        this.setCastMembers(anCastMembers);
        this.updatedAt = InstantUtils.now();
        return this;
    }


    public static Video newVideo(
            final String aTitle,
            final String aDescription,
            final Year aLaunchedAt,
            final Double aDuration,
            final Rating aRating,

            final boolean wasOpended,
            final boolean wasPublished,

            final Set<CategoryID> anCategories,
            final Set<GenreID> anGenres,
            final Set<CastMemberID> anCastMembers
    ) {
        final var now = InstantUtils.now();
        final var anId = VideoID.unique();
        return new Video(
                anId,
                aTitle,
                aDescription,
                aLaunchedAt,
                aDuration,
                aRating,
                wasOpended,
                wasPublished,
                now,
                now,
                null,
                null,
                null,
                null,
                null,
                anCategories,
                anGenres,
                anCastMembers


        );
    }

    public static Video with(final Video aVideo) {
        return new Video(
                aVideo.getId(),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getLaunchedAt(),
                aVideo.getDuration(),
                aVideo.getRating(),
                aVideo.getOpened(),
                aVideo.getPublished(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt(),
                aVideo.getBanner().orElse(null),
                aVideo.getThumbnail().orElse(null),
                aVideo.getThumbnailHalf().orElse(null),
                aVideo.getTrailer().orElse(null),
                aVideo.getVideo().orElse(null),
                new HashSet<>(aVideo.getCategories()),
                new HashSet<>(aVideo.getGenres()),
                new HashSet<>(aVideo.getCastMembers())
        );
    }
}
