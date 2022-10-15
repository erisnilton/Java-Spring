package erisnilton.dev.admin.catalogo.domain.castmember;

import erisnilton.dev.admin.catalogo.domain.category.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CastMemberTest {

    @Test
    public void giveValidParams_whenCallANewCastmember_thenInstantiateACastmember() {

        final var expectedName = "Vin Diesel";
        final var expectedType = CastMemberType.ACTOR;

        final var actualCastMember = CastMember.newMember(expectedName,expectedType);

        Assertions.assertNotNull(actualCastMember);
        Assertions.assertNotNull(actualCastMember.getId());
        Assertions.assertEquals( expectedName, actualCastMember.getName());
        Assertions.assertEquals(expectedType, actualCastMember.getType());
        Assertions.assertNotNull(actualCastMember.getCreatedAt());
        Assertions.assertNotNull(actualCastMember.getUpdatedAt());
    }
}
