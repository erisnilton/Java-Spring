package erisnilton.dev.admin.catalogo.domain.video;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ImageMediaTest {

    @Test
    public void givenAValidParams_whenCallsNewImage_shouldReturnInstanceIt() {
        // given
        final var expectedChecksum = "abc";
        final var expectedName = "banner.jpg";
        final var expectedLocation = "/image/abc";

        // when
        final var actualImage = ImageMedia.with(expectedChecksum, expectedName, expectedLocation);

        // then
        Assertions.assertNotNull(actualImage);
        Assertions.assertEquals(expectedChecksum, actualImage.checkSum());
        Assertions.assertEquals(expectedName, actualImage.name());
        Assertions.assertEquals(expectedLocation, actualImage.location());

    }

    @Test
    public void givenATwoImagesWithSameChecksumAndLocation_whenCallsNewImage_shouldReturnTrue() {
        // given
        final var expectedChecksum = "abc";
        final var expectedLocation = "/image/abc";

        final var img1 = ImageMedia.with(expectedChecksum, "random", expectedLocation);
        final var img2 = ImageMedia.with(expectedChecksum, "simple", expectedLocation);

        // then
        Assertions.assertEquals(img1, img2);
        Assertions.assertNotSame(img1, img2);

    }

    @Test
    public void givenInvalidParams_whenCallsWiith_shouldReturnError() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> ImageMedia.with(null, "random", "/imagem/abc")
        );
        Assertions.assertThrows(
                NullPointerException.class,
                () -> ImageMedia.with("abc", null, "/imagem/abc")
        );
        Assertions.assertThrows(
                NullPointerException.class,
                () -> ImageMedia.with("abc", "random", null)
        );

    }
}