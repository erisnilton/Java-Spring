package erisnilton.dev.admin.catalogo.infraestrutura.castmember.persistence;

import erisnilton.dev.admin.catalogo.infraestrutura.category.persistence.CategoryJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CastMemberRepository extends JpaRepository<CastMemberJpaEntity, String> {

    Page<CastMemberJpaEntity> findAll(Specification<CastMemberRepository> whereClause, Pageable page);
}
