package erisnilton.dev.admin.catalogo.infraestrutura.castmember;

import erisnilton.dev.admin.catalogo.domain.Genre.Genre;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMember;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberID;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;
import erisnilton.dev.admin.catalogo.domain.pagination.SearchQuery;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence.CastMemberJpaEntity;
import erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence.CastMemberRepository;
import erisnilton.dev.admin.catalogo.infraestrutura.category.persistence.CategoryJpaEntity;
import erisnilton.dev.admin.catalogo.infraestrutura.genre.persistence.GenreJpaEntity;
import erisnilton.dev.admin.catalogo.infraestrutura.utils.SpeficicationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CastMemberMySQLGateway implements CastMemberGateway {

    private CastMemberRepository castMemberRepository;

    public CastMemberMySQLGateway(CastMemberRepository castMemberRepository) {
        this.castMemberRepository = castMemberRepository;
    }

    @Override
    public CastMember create(final CastMember aCastMember) {
        return save(aCastMember);
    }

    @Override
    public void deleteById(final CastMemberID aMemberId) {
        final var anId = aMemberId.getValue();
        if(this.castMemberRepository.existsById(anId)) {
            this.castMemberRepository.deleteById(anId);
        }
    }

    @Override
    public Optional<CastMember> findById(final CastMemberID aMemberId) {
        return this.castMemberRepository
                .findById(aMemberId.getValue())
                .map(CastMemberJpaEntity::toAgregate);
    }

    @Override
    public CastMember update(final CastMember aCastMember) {
        return save(aCastMember);
    }

    @Override
    public Pagination<CastMember> findAll(final SearchQuery aQuery) {

        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())

        );

        final var where = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);
        final var pageResult = this.castMemberRepository.findAll(where, page);
        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CastMemberJpaEntity::toAgregate).toList()
        );
    }

    @Override
    public List<CastMemberID> existsByIds(final Iterable<CastMemberID> ids) {
        throw new UnsupportedOperationException();
    }

    private CastMember save(final CastMember aCastMember) {
        return this.castMemberRepository.save(CastMemberJpaEntity.from(aCastMember)).toAgregate();
    }

    private Specification<CastMemberJpaEntity> assembleSpecification(final String terms ) {
        return SpeficicationUtils.like("name", terms);
    }

}


