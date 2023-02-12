package erisnilton.dev.admin.catalogo.domain.video;

import erisnilton.dev.admin.catalogo.domain.ValueObject;

import java.util.Objects;

public class ImageMedia extends ValueObject {

    private final String checkSum;
    private final String name;
    private final String location;

    private ImageMedia(final String checkSum, final String name, final String location) {
        this.checkSum = Objects.requireNonNull(checkSum);
        this.name = Objects.requireNonNull(name);
        this.location = Objects.requireNonNull(location);
    }

    public static ImageMedia with(final String checkSum, final String name, final String location) {
        return new ImageMedia(checkSum,name, location);
    }

    public String checkSum() {
        return checkSum;
    }

    public String name() {
        return name;
    }

    public String location() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ImageMedia that = (ImageMedia) o;
        return Objects.equals(checkSum, that.checkSum) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkSum, location);
    }
}
