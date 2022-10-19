package erisnilton.dev.admin.catalogo.application.castmember.create;

import erisnilton.dev.admin.catalogo.Fixture;
import erisnilton.dev.admin.catalogo.IntegrationTest;
import erisnilton.dev.admin.catalogo.application.genre.create.CreateGenreCommand;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.category.Category;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

@IntegrationTest
public class CreateCastMemberUseCaseIT {

    @Autowired
    private CreateCastMemberUseCase useCase;

    @SpyBean
    private CastMemberGateway castMemberGateway;


    @Test
    public void givenAValidCommand_whenCallsCreateCastMember_shouldReturnGenreId() {
        // given
        final var expectedName = Fixture.name();
        final var expectedType  = Fixture.CastMember.type();

        final var aCommand =
                CreateCastMemberCommand.with(expectedName, expectedType);


        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());


        final var actualCastMember = castMemberGateway.findById(CastMemberID.from(actualOutput.id())).get();
        Assertions.assertEquals(expectedName, actualCastMember.getName());
        Assertions.assertEquals(expectedType, actualCastMember.getType());
        Assertions.assertNotNull(actualCastMember.getCreatedAt());
        Assertions.assertNotNull(actualCastMember.getUpdatedAt());
    }
}
