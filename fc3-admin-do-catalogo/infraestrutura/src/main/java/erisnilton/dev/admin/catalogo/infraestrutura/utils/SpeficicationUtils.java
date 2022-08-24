package erisnilton.dev.admin.catalogo.infraestrutura.utils;

import org.springframework.data.jpa.domain.Specification;

public final class SpeficicationUtils {

    private SpeficicationUtils(){}

    public static <T> Specification<T> like(final String props, final String terms) {
        return  (root, query, cb) ->
                cb.like(cb.upper(root.get(props)), like(terms.toUpperCase()));
    }

    private static String like(final String terms) {
        return "%" + terms + "%";
    }

}
