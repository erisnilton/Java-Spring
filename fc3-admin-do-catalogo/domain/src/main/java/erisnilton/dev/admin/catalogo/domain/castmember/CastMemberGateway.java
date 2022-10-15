package erisnilton.dev.admin.catalogo.domain.castmember;

import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import erisnilton.dev.admin.catalogo.domain.pagination.Pagination;
import erisnilton.dev.admin.catalogo.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface CastMemberGateway {

    CastMember create(CastMember aCategory);

    void deleteById(CategoryID anId);

    Optional<CastMember> findById(CastMemberID anId);

    CastMember update(CastMember aCastMember);

    Pagination<CastMember> findAll(SearchQuery aQuery);

    List<CategoryID> existsByIds(Iterable<CategoryID> ids);
}
