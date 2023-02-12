package erisnilton.dev.admin.catalogo.domain.video;

import erisnilton.dev.admin.catalogo.domain.Genre.GenreID;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import erisnilton.dev.admin.catalogo.domain.exceptions.DomainException;
import erisnilton.dev.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Set;

public class VideoValidatorTest {

    @Test
    public void givenNullTitle_whenCallsValidate_shouldReceiveError() {
        // given
        final String expectedTitle = null;
        final var expectedDescription = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossa opniões pessoais.
                Esse video faz parte da Imersão Full Cycle Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse;
                https://imersao.fullcycle.com.br/
                """;
        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErroCount = 1;
        final var expectedErrorMessage = "'title' should not be null";

        final var actualVideo = Video.newVideo(expectedTitle, expectedDescription, expectedLaunchedAt, expectedDuration, expectedRating, expectedOpened, expectedPublished, expectedCategories, expectedGenres, expectedMembers);
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        //then
        Assertions.assertEquals(expectedErroCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());


    }

    @Test
    public void givenEmptyTitle_whenCallsValidate_shouldReceiveError() {
        // given
        final String expectedTitle = "";
        final var expectedDescription = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossa opniões pessoais.
                Esse video faz parte da Imersão Full Cycle Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse;
                https://imersao.fullcycle.com.br/
                """;
        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErroCount = 1;
        final var expectedErrorMessage = "'title' should not be empty";

        final var actualVideo = Video.newVideo(expectedTitle, expectedDescription, expectedLaunchedAt, expectedDuration, expectedRating, expectedOpened, expectedPublished, expectedCategories, expectedGenres, expectedMembers);
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        //then
        Assertions.assertEquals(expectedErroCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());


    }

    @Test
    public void givenTitleWithGreaterThan255_whenCallsValidate_shouldReceiveError() {
        // given
        final String expectedTitle = """ 
                Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis,.
                """;
        final var expectedDescription = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossa opniões pessoais.
                Esse video faz parte da Imersão Full Cycle Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse;
                https://imersao.fullcycle.com.br/
                """;
        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErroCount = 1;
        final var expectedErrorMessage = "'title' must be between 1 and 255 characters";

        final var actualVideo = Video.newVideo(expectedTitle, expectedDescription, expectedLaunchedAt, expectedDuration, expectedRating, expectedOpened, expectedPublished, expectedCategories, expectedGenres, expectedMembers);
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        //then
        Assertions.assertEquals(expectedErroCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());


    }

    @Test
    public void givenNullDescription_whenCallsValidate_shouldReceiveError() {
        // given
        final var expectedTitle = "System Design Interviews";
        final String expectedDescription = null;
        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErroCount = 1;
        final var expectedErrorMessage = "'description' should not be null";

        final var actualVideo = Video.newVideo(expectedTitle, expectedDescription, expectedLaunchedAt, expectedDuration, expectedRating, expectedOpened, expectedPublished, expectedCategories, expectedGenres, expectedMembers);
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        //then
        Assertions.assertEquals(expectedErroCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());


    }

    @Test
    public void givenEmptyDescription_whenCallsValidate_shouldReceiveError() {
        // given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "";
        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErroCount = 1;
        final var expectedErrorMessage = "'description' should not be empty";

        final var actualVideo = Video.newVideo(expectedTitle, expectedDescription, expectedLaunchedAt, expectedDuration, expectedRating, expectedOpened, expectedPublished, expectedCategories, expectedGenres, expectedMembers);
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        //then
        Assertions.assertEquals(expectedErroCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());


    }

    @Test
    public void givenDescriptionWithGreaterThan4000_whenCallsValidate_shouldReceiveError() {
        // given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = """
                Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, quis gravida magna mi a libero. Fusce vulputate eleifend sapien. Vestibulum purus quam, scelerisque ut, mollis sed, nonummy id, metus. Nullam accumsan lorem in dui. Cras ultricies mi eu turpis hendrerit fringilla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In ac dui quis mi consectetuer lacinia. Nam pretium turpis et arcu. Duis arcu tortor, suscipit eget, imperdiet nec, imperdiet iaculis, ipsum. Sed aliquam ultrices mauris. Integer ante arcu, accumsan a, consectetuer eget, posuere ut, mauris. Praesent adipiscing. Phasellus ullamcorper ipsum rutrum nunc. Nunc nonummy metus. Vestibulum volutpat pretium libero. Cras id dui. Aenean ut eros et nisl sagittis vestibulum. Nullam nulla eros, ultricies sit amet, nonummy id, imperdiet feugiat, pede. Sed lectus. Donec mollis hendrerit risus. Phasellus nec sem in justo pellentesque facilisis. Etiam imperdiet imperdiet orci. Nunc nec neque. Phasellus leo dolor, tempus non, auctor et, hendrerit quis, nisi. Curabitur ligula sapien, tincidunt non, euismod vitae, posuere imperdiet, leo. Maecenas malesuada. Praesent congue erat at massa. Sed cursus turpis vitae tortor. Donec posuere vulputate arcu. Phasellus accumsan cursus velit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed aliquam, nisi quis porttitor congue, elit erat euismod orci, ac placerat dolor lectus quis orci. Phasellus consectetuer vestibulum elit. Aenean tellus metus, bibendum sed, posuere ac, mattis non, nunc. Vestibulum fringilla pede sit amet augue. In turpis. Pellentesque posuere. Praesent turpis. Aenean posuere, tortor sed cursus feugiat, nunc augue blandit nunc, eu sollicitudin urna dolor sagittis lacus. Donec elit libero, sodales nec, volutpat a, suscipit non, turpis. Nullam sagittis. Suspendisse pulvinar, augue ac venenatis condimentum, sem libero volutpat nibh, nec pellentesque velit pede quis nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Fusce id purus. Ut varius tincidunt libero. Phasellus dolor. Maecenas vestibulum mollis diam. Pellentesque ut neque. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In dui magna, posuere eget, vestibulum et, tempor auctor, justo. In ac felis quis tortor malesuada pretium. Pellentesque auctor neque nec urna. Proin sapien ipsum, porta a, auctor quis, euismod ut, mi. Aenean viverra rhoncus pede. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Ut non enim eleifend felis pretium feugiat. Vivamus quis mi. Phasellus a est. Phase
                                
                """;
        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErroCount = 1;
        final var expectedErrorMessage = "'description' must be between 1 and 4000 characters";

        final var actualVideo = Video.newVideo(expectedTitle, expectedDescription, expectedLaunchedAt, expectedDuration, expectedRating, expectedOpened, expectedPublished, expectedCategories, expectedGenres, expectedMembers);
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        //then
        Assertions.assertEquals(expectedErroCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

    @Test
    public void givenNullLaunchedAt_whenCallsValidate_shouldReceiveError() {
        // given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "A description";
        final Year expectedLaunchedAt = null;
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErroCount = 1;
        final var expectedErrorMessage = "'launchedAt' should not be null";

        final var actualVideo = Video.newVideo(expectedTitle, expectedDescription, expectedLaunchedAt, expectedDuration, expectedRating, expectedOpened, expectedPublished, expectedCategories, expectedGenres, expectedMembers);
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        //then
        Assertions.assertEquals(expectedErroCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());


    }

    @Test
    public void givenNullRating_whenCallsValidate_shouldReceiveError() {
        // given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "A description";
        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final Rating expectedRating = null;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErroCount = 1;
        final var expectedErrorMessage = "'rating' should not be null";

        final var actualVideo = Video.newVideo(expectedTitle, expectedDescription, expectedLaunchedAt, expectedDuration, expectedRating, expectedOpened, expectedPublished, expectedCategories, expectedGenres, expectedMembers);
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        //then
        Assertions.assertEquals(expectedErroCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

}
