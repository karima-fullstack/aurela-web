package web.aurela.aurelaweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import web.aurela.aurelaweb.Entities.SubCategory;

@RepositoryRestResource
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
}
