package erisnilton.dev.admin.catalogo.domain.video;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AudioVideoMediaTest {

    @Test
    public void givenAValidParams_whenCallsNewAudioVideo_shouldReturnInstanceIt() {
        // given
        final var expectedChecksum = "abc";
        final var expectedName = "video.mp4";
        final var expectedRawLocation = "/video/abc";
        final var expectedEncodedLocation = "/video/abc-encoded";
        final var expectedStatus = MediaStatus.PENDING;

        // when
        final var actualAudioMedia = AudioVideoMedia.with(
                expectedChecksum, expectedName, expectedRawLocation, expectedEncodedLocation, expectedStatus);

        // then
        Assertions.assertNotNull(actualAudioMedia);
        Assertions.assertEquals(expectedChecksum, actualAudioMedia.checksum());
        Assertions.assertEquals(expectedName, actualAudioMedia.name());
        Assertions.assertEquals(expectedRawLocation, actualAudioMedia.rawLocation());
        Assertions.assertEquals(expectedEncodedLocation, actualAudioMedia.encodedLocation());
        Assertions.assertEquals(expectedStatus, actualAudioMedia.status());

    }

    @Test
    public void givenATwoImagesWithSameChecksumAndLocation_whenCallsNewImage_shouldReturnTrue() {
        // given
        final var expectedChecksum = "abc";
        final var expectedRawLocation = "/image/abc";

        final var img1 = AudioVideoMedia
                .with(expectedChecksum,
                        "random",
                        expectedRawLocation,
                        "/video/abc-encoded",
                        MediaStatus.PENDING
                );
        final var img2 = AudioVideoMedia
                .with(expectedChecksum,
                        "simple",
                        expectedRawLocation,
                        "/video/abc-encoded",
                        MediaStatus.PROCESSING
                );

        // then
        Assertions.assertEquals(img1, img2);
        Assertions.assertNotSame(img1, img2);

    }

    @Test
    public void givenInvalidParams_whenCallsWiith_shouldReturnError() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with(
                        null,
                        "random",
                        "/video/abc",
                        "/video/abc-encoded",
                        MediaStatus.PENDING
                )
        );
        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with(
                        "abc",
                        null,
                        "/video/abc",
                        "/video/abc-encoded",
                        MediaStatus.PENDING
                )
        );
        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with(
                        "abc",
                        "random",
                        null,
                        "/video/abc-encoded",
                        MediaStatus.PENDING
                )
        );
        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with(
                        "abc",
                        "random",
                        "/video/abc",
                        null,
                        MediaStatus.PENDING
                )
        );
        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with(
                        "abc",
                        "random",
                        "/video/abc",
                        "/video/abc-encoded",
                        null
                )
        );

    }

}