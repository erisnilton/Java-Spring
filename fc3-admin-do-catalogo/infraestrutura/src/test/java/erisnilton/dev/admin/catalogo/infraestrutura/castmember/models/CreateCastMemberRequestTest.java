package erisnilton.dev.admin.catalogo.infraestrutura.castmember.models;

import erisnilton.dev.admin.catalogo.Fixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class CreateCastMemberRequestTest {

    @Autowired
    private JacksonTester<CreateCastMemberRequest> json;

    @Test
    public void testUnmashall() throws Exception {

        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMember.type();

        final var json = """
                    {
                        "name": "%s",
                        "type": "%s"
                    }
                """.formatted(expectedName, expectedType);

        final var actualJson = this.json.parse(json);

        System.out.println(actualJson);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("name", expectedName)
                .hasFieldOrPropertyWithValue("type", expectedType);
    }

}