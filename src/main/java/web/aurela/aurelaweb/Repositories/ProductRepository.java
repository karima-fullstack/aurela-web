package web.aurela.aurelaweb.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import web.aurela.aurelaweb.Entities.Category;
import web.aurela.aurelaweb.Entities.Product;
import web.aurela.aurelaweb.Entities.SubCategory;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    Page<Product> findAll(Pageable pageable);
    Page<Product> findProductByCategory(Category category, Pageable pageable);
    Page<Product> findProductBySubCategory(SubCategory subCategory, Pageable pageable);
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<Product> findAllByOrderByPriceAsc(Pageable pageable);
    Page<Product> findAllByOrderByPriceDesc(Pageable pageable);
    Page<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findByCategory_NameContainingIgnoreCase(String categoryName, Pageable pageable);

}
