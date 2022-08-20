package erisnilton.dev.admin.catalogo.domain.pagination;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public record Pagination<T>(
        int currentPage,
        int perPage,
        long total,
        List<T> items

) {
    public <R> Pagination<R> map(final Function<T, R> mapper) {
        final var aNewList = this.items.stream().map(mapper).toList();
        return new Pagination<>(currentPage(),perPage(),total(),aNewList);
    }
}
