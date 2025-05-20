package web.aurela.aurelaweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import web.aurela.aurelaweb.Entities.Image;

@RepositoryRestResource
public interface ImageRepository extends JpaRepository<Image,Long> {
}
