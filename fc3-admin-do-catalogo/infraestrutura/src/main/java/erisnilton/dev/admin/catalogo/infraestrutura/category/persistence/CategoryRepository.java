package erisnilton.dev.admin.catalogo.infraestrutura.category.persistence;

import erisnilton.dev.admin.catalogo.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryJpaEntity, String> {
}
