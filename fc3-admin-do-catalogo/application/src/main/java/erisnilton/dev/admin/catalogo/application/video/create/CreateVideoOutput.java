package erisnilton.dev.admin.catalogo.application.video.create;

import erisnilton.dev.admin.catalogo.domain.video.Video;

public record CreateVideoOutput(String id) {
    public static  CreateVideoOutput from(final Video video){
        return new CreateVideoOutput(video.getId().getValue());
    }
}
