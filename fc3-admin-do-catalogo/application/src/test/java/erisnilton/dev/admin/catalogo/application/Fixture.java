package erisnilton.dev.admin.catalogo.application;

import com.github.javafaker.Faker;
import erisnilton.dev.admin.catalogo.domain.Genre.Genre;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;
import erisnilton.dev.admin.catalogo.domain.category.Category;
import erisnilton.dev.admin.catalogo.domain.video.Rating;
import erisnilton.dev.admin.catalogo.domain.video.Resource;

import java.util.Arrays;

import static io.vavr.API.*;

public final class Fixture {

    private static final Faker FAKER = new Faker();

    public static String name() {
        return FAKER.name().fullName();
    }

    public static Integer year() {
        return FAKER.random().nextInt(2010, 2030);
    }

    public static Double duration() {
        return FAKER.options().option(120.0, 15.5, 35.5, 10.0, 2.0);
    }

    public static String title() {
        return FAKER.options().option(
                "System Design no Mercado Livre na prática",
                "Não cometa esses erros ao trabahar com Microsserviços",
                "Testes de Mutação. Você não testa seu software corretamente"
        );
    }

    public static boolean bool() {
        return FAKER.bool().bool();
    }


    public static final class Categories {
        private static final Category DOCUMENTATIOS = Category.newCategory(
                "Documentarios", "Uma categoria de documentarios", true
        );
        public static Category document () {
            return Category.with(DOCUMENTATIOS);
        }
    }

    public static final class CastMembers {

        private static final CastMember WESLEY =
                CastMember.newMember("wesley full cycle", CastMemberType.ACTOR);
        private static final CastMember GABRIEL =
                CastMember.newMember("gabriel full cycle", CastMemberType.DIRECTOR);

        public static CastMemberType type() {
            return FAKER.options()
                    .option(CastMemberType.values());
        }

        public static CastMember wesley() {
            return CastMember.with(WESLEY);
        }
        public static CastMember gabriel() {
            return CastMember.with(GABRIEL);
        }

    }

    public static final class Genres {
        private static final Genre TECH = Genre.newGenre("Terror", true);

            public static Genre tech(){
            return Genre.with(TECH);
        }
    }

    public static final class Videos {

        public static String rating() {
            final var values = Arrays.stream(Rating.values())
                    .map(Rating::getName)
                    .toList()
                    .toArray(new String[0]);
            return FAKER.options().option(values);
        }

        public static Resource resource(final Resource.Type type) {
            final String contentType = Match(type).of(
                    Case($(List(Resource.Type.VIDEO, Resource.Type.TRAILER)::contains),"video/mp4"),
                    Case($(), "image/jpg")
            );
            final byte[] content = "Conteudo".getBytes();
            return Resource.with(content, contentType, type.name(), type);
        }

        public static String description() {
            return FAKER.options().option(
                    """
                            Disclamer: o estudo de caso apresentado tem fins e representa nossas opiniões pessoais:
                            Esse vídeo faz parte da Imersão Full Stack  && Full Cycle.
                            """,
                    """
                            Nesse vídeo você aprenderá o que é DTO (Data Transfer Object), quando e como utilizar no
                            dia a dia e bem como sua  importância para criar aplicações de qualidade.
                            """
            );
        }


    }

}
