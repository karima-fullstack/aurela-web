package web.aurela.aurelaweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import web.aurela.aurelaweb.Entities.Category;

@RepositoryRestResource
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByName(String name);
}
