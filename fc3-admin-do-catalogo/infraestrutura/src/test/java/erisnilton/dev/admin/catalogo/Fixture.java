package erisnilton.dev.admin.catalogo;

import com.github.javafaker.Faker;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;

public final class Fixture {

    private static final Faker FAKER = new Faker();

    public static String name() {
        return FAKER.name().fullName();
    }

    public static final class CastMember {

        public static CastMemberType type() {
            return FAKER.options()
                    .option(CastMemberType.ACTOR, CastMemberType.DIRECTOR);
        }

    }

}
