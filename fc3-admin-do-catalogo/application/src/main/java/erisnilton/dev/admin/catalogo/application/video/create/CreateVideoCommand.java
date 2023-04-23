package erisnilton.dev.admin.catalogo.application.video.create;

import erisnilton.dev.admin.catalogo.domain.video.Resource;

import java.util.Set;

public record CreateVideoCommand(
        String title,
        String description,
        int launchedAt,
        double duration,
        boolean opened,
        boolean published,
        String rating,
        Set<String> categories,
        Set<String> members,
        Set<String> genres,
        Resource video,
        Resource trailer,
        Resource banner,
        Resource thumbnail,
        Resource thumbnailHalf
) {

    public static CreateVideoCommand with(
            final String title,
            final String description,
            final int launchedAt,
            final double duration,
            final boolean opened,
            final boolean published,
            final String rating,
            final Set<String> categories,
            final Set<String> members,
            final Set<String> genres,
            final Resource video,
            final Resource trailer,
            final Resource banner,
            final Resource thumbnail,
            final Resource thumbnailHalf
    ) {
        return new CreateVideoCommand(
                title, description, launchedAt, duration, opened, published, rating, categories, members, genres, video, trailer, banner, thumbnail, thumbnailHalf
        );
    }
}
