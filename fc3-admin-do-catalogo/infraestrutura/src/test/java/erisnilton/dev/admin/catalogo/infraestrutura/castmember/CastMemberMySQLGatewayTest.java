package erisnilton.dev.admin.catalogo.infraestrutura.castmember;

import erisnilton.dev.admin.catalogo.MySQLGatewayTest;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberType;
import erisnilton.dev.admin.catalogo.domain.pagination.SearchQuery;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence.CastMemberJpaEntity;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static erisnilton.dev.admin.catalogo.Fixture.CastMember.type;
import static erisnilton.dev.admin.catalogo.Fixture.name;

@MySQLGatewayTest
public class CastMemberMySQLGatewayTest {


    @Autowired
    private CastMemberMySQLGateway castMemberGateway;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @Test
    public void testDependenciesInjected() {

        Assertions.assertNotNull(castMemberGateway);
        Assertions.assertNotNull(castMemberGateway);

    }

    @Test
    public void givenAValidCastMember_whenCallsCreateCastMember_shouldPersistIt() {
        // given
        final var expectedName = name();
        final var expectedType = type();

        final var aCastMember = CastMember.newMember(expectedName, expectedType);

        final var expectedId = aCastMember.getId();

        Assertions.assertEquals(0, castMemberRepository.count());

        // when
        final var actualCastMember = castMemberGateway.create(CastMember.with(aCastMember));

        // then
        Assertions.assertEquals(1, castMemberRepository.count());
        Assertions.assertEquals(expectedId, actualCastMember.getId());
        Assertions.assertEquals(expectedName, actualCastMember.getName());
        Assertions.assertEquals(expectedType, actualCastMember.getType());
        Assertions.assertEquals(aCastMember.getCreatedAt(), actualCastMember.getCreatedAt());
        Assertions.assertEquals(aCastMember.getUpdatedAt(), actualCastMember.getUpdatedAt());

        final var persistedGenre = castMemberRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedName, persistedGenre.getName());
        Assertions.assertEquals(expectedType, persistedGenre.getType());
        Assertions.assertEquals(aCastMember.getCreatedAt(), persistedGenre.getCreatedAt());
        Assertions.assertEquals(aCastMember.getUpdatedAt(), persistedGenre.getUpdatedAt());
    }

    @Test
    public void givenAValidCastMember_whenCallsUpdateCastMember_shouldRefleshIt() {

        // given
        final var expectedName = name();
        final var expectedType = CastMemberType.ACTOR;

        final var aCastMember = CastMember.newMember("Vind", CastMemberType.DIRECTOR);

        final var expectedId = aCastMember.getId();

        final var currentMember = castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aCastMember));

        Assertions.assertEquals(1, castMemberRepository.count());

        Assertions.assertEquals(expectedId.getValue(), currentMember.getId());
        Assertions.assertEquals(aCastMember.getName(), currentMember.getName());
        Assertions.assertEquals(aCastMember.getType(), currentMember.getType());

        // when
        final var actualCastMember = castMemberGateway.update(
                CastMember.with(aCastMember).update(expectedName, expectedType)
        );

        // then
        Assertions.assertEquals(1, castMemberRepository.count());

        Assertions.assertEquals(expectedId, actualCastMember.getId());
        Assertions.assertEquals(expectedName, actualCastMember.getName());
        Assertions.assertEquals(expectedType, actualCastMember.getType());
        Assertions.assertEquals(aCastMember.getCreatedAt(), actualCastMember.getCreatedAt());
        Assertions.assertTrue(aCastMember.getUpdatedAt().isBefore(actualCastMember.getUpdatedAt()));

        final var persistedGenre = castMemberRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedName, persistedGenre.getName());
        Assertions.assertEquals(expectedType, persistedGenre.getType());
        Assertions.assertEquals(aCastMember.getCreatedAt(), persistedGenre.getCreatedAt());
        Assertions.assertTrue(aCastMember.getUpdatedAt().isBefore(persistedGenre.getUpdatedAt()));

    }

    @Test
    public void givenAValidCastMember_whenCallsDeleteById_shouldDeleteIt() {

        // given
        final var aMember = CastMember.newMember(name(), type());

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));
        Assertions.assertEquals(1, castMemberRepository.count());

        // when
        castMemberGateway.deleteById(aMember.getId());

        // then

        Assertions.assertEquals(0, castMemberRepository.count());


    }

    @Test
    public void givenAnInValidCastMember_whenCallsDeleteById_shouldBeIgnored() {
        // given
        final var aMember = CastMember.newMember(name(), type());

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());

        // when
        castMemberGateway.deleteById(CastMemberID.from("123"));

        // then
        Assertions.assertEquals(1, castMemberRepository.count());
    }

    @Test
    public void givenAValidCastMember_whenCallsFindById_shouldReturnIt() {

        // given
        final var expectedName = name();
        final var expectedType = type();

        final var aMember = CastMember.newMember(expectedName, expectedType);
        final var expectedId = aMember.getId();

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));
        Assertions.assertEquals(1, castMemberRepository.count());

        // when
        final var actualMember = castMemberGateway.findById(expectedId).get();

        // then

        Assertions.assertEquals(expectedId, actualMember.getId());
        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), actualMember.getCreatedAt());
        Assertions.assertEquals(aMember.getUpdatedAt(), actualMember.getUpdatedAt());

    }

    @Test
    public void givenAnInValidCastMember_whenCallsFindById_shouldReturnEmpty() {

        // given

        final var aMember = CastMember.newMember(name(), type());

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));
        Assertions.assertEquals(1, castMemberRepository.count());

        // when
        final var actualMember = castMemberGateway.findById(CastMemberID.from("123"));

        // then

        Assertions.assertTrue(actualMember.isEmpty());
    }

    @Test
    public void givenEmptyCastMember_whenCallsFindAll_shouldReturnEmpty() {

        //given
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedTotal = 0;

        final var aQuery = new SearchQuery(
                expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection
        );

        // when
        final var actualPage = this.castMemberGateway.findAll(aQuery);

        // then

        Assertions.assertEquals(expectedPage, actualPage.currentPage());
        Assertions.assertEquals(expectedPerPage, actualPage.perPage());
        Assertions.assertEquals(expectedTotal, actualPage.total());
        Assertions.assertEquals(expectedTotal, actualPage.items().size());


    }

    @ParameterizedTest
    @CsvSource({
            "vin,0,10,1,1,Vin Diesel",
            "taran,0,10,1,1,Quentin Tarantino",
            "jas,0,10,1,1,Jason Mamoa",
            "har,0,10,1,1,Kit Harington",
            "MAR,0,10,1,1,Martin Scorsese",
    })
    public void givenAValidTerm_whenCallsFindAll_shouldReturnFiltered(
            final String expecteTerms,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedName
    ) {

        // given
        mockMembers();
        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var aQuery = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expecteTerms,
                expectedSort,
                expectedDirection
        );

        // when

        final var actualPage = castMemberGateway.findAll(aQuery);

        // then

        Assertions.assertEquals(expectedPage, actualPage.currentPage());
        Assertions.assertEquals(expectedPerPage, actualPage.perPage());
        Assertions.assertEquals(expectedTotal, actualPage.total());
        Assertions.assertEquals(expectedItemsCount, actualPage.items().size());
        Assertions.assertEquals(expectedName, actualPage.items().get(0).getName());

    }

    @ParameterizedTest
    @CsvSource({
            "name,asc,0,10,5,5,Jason Mamoa",
            "name,desc,0,10,5,5,Vin Diesel",
            "createdAt,asc,0,10,5,5,Kit Harington",
            "createdAt,desc,0,10,5,5,Martin Scorsese"
    })
    public void givenAValidSortAndDirection_whenCallsFindAll_shouldReturnSorted(
            final String expectedSort,
            final String expectedDirection,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedName
    ) {
        // given
        mockMembers();

        final var expecteTerms = "";

        final var aQuery = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expecteTerms,
                expectedSort,
                expectedDirection
        );

        // when

        final var actualPage = castMemberGateway.findAll(aQuery);

        // then

        Assertions.assertEquals(expectedPage, actualPage.currentPage());
        Assertions.assertEquals(expectedPerPage, actualPage.perPage());
        Assertions.assertEquals(expectedTotal, actualPage.total());
        Assertions.assertEquals(expectedItemsCount, actualPage.items().size());
        Assertions.assertEquals(expectedName, actualPage.items().get(0).getName());


    }

    @ParameterizedTest
    @CsvSource({
            "0,2,2,5,Jason Mamoa;Kit Harington",
            "1,2,2,5,Martin Scorsese;Quentin Tarantino",
            "2,2,1,5,Vin Diesel"
    })
    public void givenAValidPagination_whenCallsFindAll_shouldReturnPaginated(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedNames
    ) {
        // given
        mockMembers();

        final var expecteTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var aQuery = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expecteTerms,
                expectedSort,
                expectedDirection
        );

        // when

        final var actualPage = castMemberGateway.findAll(aQuery);

        // then

        Assertions.assertEquals(expectedPage, actualPage.currentPage());
        Assertions.assertEquals(expectedPerPage, actualPage.perPage());
        Assertions.assertEquals(expectedTotal, actualPage.total());
        Assertions.assertEquals(expectedItemsCount, actualPage.items().size());


        int index = 0;
        for (final var expectedName : expectedNames.split(";")) {
            Assertions.assertEquals(expectedName, actualPage.items().get(index).getName());
            index++;
        }
    }

    private void mockMembers() {
        castMemberRepository.saveAllAndFlush(List.of(
                CastMemberJpaEntity.from(CastMember.newMember("Kit Harington", CastMemberType.ACTOR)),
                CastMemberJpaEntity.from(CastMember.newMember("Vin Diesel", CastMemberType.ACTOR)),
                CastMemberJpaEntity.from(CastMember.newMember("Quentin Tarantino", CastMemberType.DIRECTOR)),
                CastMemberJpaEntity.from(CastMember.newMember("Jason Mamoa", CastMemberType.ACTOR)),
                CastMemberJpaEntity.from(CastMember.newMember("Martin Scorsese", CastMemberType.DIRECTOR))
        ));
    }
}
