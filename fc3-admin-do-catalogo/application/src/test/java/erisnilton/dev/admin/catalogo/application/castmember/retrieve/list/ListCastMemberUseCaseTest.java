package erisnilton.dev.admin.catalogo.application.castmember.retrieve.list;

import erisnilton.dev.admin.catalogo.application.Fixture;
import erisnilton.dev.admin.catalogo.application.UseCaseTest;
import erisnilton.dev.admin.catalogo.application.castmember.retrieve.get.CastMemberOutput;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;
import erisnilton.dev.admin.catalogo.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class ListCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    public void givenAValidQuery_whenCallsListCastMember_shouldReturnCastMembers() {
        // given
        final var castMembers = List.of(
                CastMember.newMember("Erisnilton", Fixture.CastMembers.type()),
                CastMember.newMember("Ana", Fixture.CastMembers.type())
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "A";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedTotal = 2;

        final var expectedItems = castMembers.stream()
                .map(CastMemberOutput::from)
                .toList();

        final var expectedPagination = new Pagination<>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                castMembers
        );

        when(castMemberGateway.findAll(any())).thenReturn(expectedPagination);

        final var aQuery = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        // when
        final var actualOutput = useCase.execute(aQuery);

        // then

        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
//        Assertions.assertEquals(expectedItems, actualOutput.items());

        Mockito.verify(castMemberGateway, times(1)).findAll(eq(aQuery));
    }

    @Test
    public void givenAValidQuery_whenCallsListCastMemberAndResultIsEmpty_shouldReturnCastMember() {
        // given
        final var castMembers = List.<CastMember>of();

        final var expectedPage = 0;
        final var expectedPerPage = 0;
        final var expectedTerms = "A";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedTotal = 0;

        final var expectedItems = List.<CastMemberOutput>of();

        final var expectedPagination = new Pagination<>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                castMembers
        );

        when(castMemberGateway.findAll(any())).thenReturn(expectedPagination);

        final var aQuery = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        // when
        final var actualOutput = useCase.execute(aQuery);

        // then

        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertEquals(expectedItems, actualOutput.items());

        Mockito.verify(castMemberGateway, times(1)).findAll(eq(aQuery));
    }

    @Test
    public void givenAValidQuery_whenCallsListCastMemberAndGatewayThrowsRandomError_shouldReturnException() {
        // given

        final var expectedPage = 0;
        final var expectedPerPage = 0;
        final var expectedTerms = "A";
        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var expectedErrorMassage = "Gateway error";

        when(castMemberGateway.findAll(any())).thenThrow(new IllegalStateException(expectedErrorMassage));

        final var aQuery = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        // when
        final var actualOutput =
                Assertions.assertThrows(IllegalStateException.class,
                        () -> useCase.execute(aQuery)
                );

        // then
        Assertions.assertEquals(expectedErrorMassage, actualOutput.getMessage());

        Mockito.verify(castMemberGateway, times(1)).findAll(eq(aQuery));
    }
}
