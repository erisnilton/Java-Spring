package erisnilton.dev.admin.catalogo.application.video.create;

import erisnilton.dev.admin.catalogo.application.Fixture;
import erisnilton.dev.admin.catalogo.application.UseCaseTest;
import erisnilton.dev.admin.catalogo.domain.Genre.GenreGateway;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.category.CategoryGateway;
import erisnilton.dev.admin.catalogo.domain.video.Resource;
import erisnilton.dev.admin.catalogo.domain.video.VideoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

public class CreateVideoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateVideoUseCase useCase;
    @Mock
    private GenreGateway genreGateway;
    @Mock
    private VideoGateway videoGateway;
    @Mock
    private CategoryGateway categoryGateway;
    @Mock
    private CastMemberGateway castMemberGateway;


    @Override
    protected List<Object> getMocks() {
        return List.of(videoGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreateVideo_shouldReturnVideoId() {
        // Given
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchedAt = Fixture.year();
        final var expectedDuration = Fixture.duration();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedOpended = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedCategories = Set.of(Fixture.Categories.document().getId());
        final var expectedGenres = Set.of(Fixture.Genres.tech().getId());
        final var expectedMembers = Set.of(
                Fixture.CastMembers.wesley().getId(),
                Fixture.CastMembers.gabriel().getId()
        );
        final Resource expectedVideo = Fixture.Videos.resource(Resource.Type.VIDEO);
        final Resource expectedTrailer = Fixture.Videos.resource(Resource.Type.TRAILER);
        final Resource expectedBanner = Fixture.Videos.resource(Resource.Type.BANNER);
        final Resource expectedThumb = Fixture.Videos.resource(Resource.Type.THUMBNAIL);
        final Resource expectedThumbHalf = Fixture.Videos.resource(Resource.Type.THUMBNAIL_HALF);

        final var aCommand = CreateVideoCommand.with(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpended,
                expectedPublished,
                expectedRating,
                asString(expectedCategories),
                asString(expectedMembers),
                asString(expectedGenres),
                expectedVideo,
                expectedTrailer,
                expectedBanner,
                expectedThumb,
                expectedThumbHalf

        );
        when(categoryGateway.existsByIds(any())).thenReturn(new ArrayList<>(expectedCategories));
        when(castMemberGateway.existsByIds(any())).thenReturn(new ArrayList<>(expectedMembers));
        when(genreGateway.existsByIds(any())).thenReturn(new ArrayList<>(expectedGenres));
        when(videoGateway.create(any())).thenAnswer(returnsFirstArg());

        // When
        final var actualResult = useCase.execute(aCommand);

        // Then
        Assertions.assertNotNull(actualResult);
        Assertions.assertNotNull(actualResult.id());

        Mockito.verify(videoGateway).create(argThat(actualVideo ->
                Objects.equals(expectedTitle, actualVideo.getTitle())
                        && Objects.equals(expectedDescription, actualVideo.getDescription())
                        && Objects.equals(expectedLaunchedAt, actualVideo.getLaunchedAt().getValue())
                        && Objects.equals(expectedDuration, actualVideo.getDuration())
                        && Objects.equals(expectedRating, actualVideo.getRating().getName())
                        && Objects.equals(expectedOpended, actualVideo.getOpened())
                        && Objects.equals(expectedPublished, actualVideo.getPublished())
                        && Objects.equals(expectedCategories, actualVideo.getCategories())
                        && Objects.equals(expectedGenres, actualVideo.getGenres())
                        && Objects.equals(expectedMembers, actualVideo.getCastMembers())
//                        && actualVideo.getVideo().isPresent()
//                        && actualVideo.getTrailer().isPresent()
//                        && actualVideo.getBanner().isPresent()
//                        && actualVideo.getThumbnail().isPresent()
//                        && actualVideo.getThumbnailHalf().isPresent()
        ));
    }
}
