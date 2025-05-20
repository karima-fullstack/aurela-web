package web.aurela.aurelaweb.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import web.aurela.aurelaweb.Dtos.ImageDto;
import web.aurela.aurelaweb.Dtos.MinimalProductDto;
import web.aurela.aurelaweb.Dtos.ProductDto;
import web.aurela.aurelaweb.Dtos.ProductRequest;
import web.aurela.aurelaweb.Entities.Category;
import web.aurela.aurelaweb.Entities.Image;
import web.aurela.aurelaweb.Entities.Product;
import web.aurela.aurelaweb.Entities.SubCategory;
import web.aurela.aurelaweb.Mappers.ProductMapper;
import web.aurela.aurelaweb.Repositories.CategoryRepository;
import web.aurela.aurelaweb.Repositories.ProductRepository;
import web.aurela.aurelaweb.Repositories.SubCategoryRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductMapper productMapper;

    //Create product with productRequest Dto
    public Long createProduct(ProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid subcategory ID"));

        Product product = productMapper.toProduct(request);
        product.setCategory(category);
        product.setSubCategory(subCategory);
        product.setAvailable( request.getQuantity() > 0 );

        Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }

    public Page<MinimalProductDto> findProductByCategoryId(Long categoryId, int page, int size) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + categoryId));

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findProductByCategory(category, pageable);
        return productPage.map(productMapper::toMinimalProductDto);
    }


    public Page<MinimalProductDto> findProductBySubCategoryId(Long subCategoryId, int page, int size) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new EntityNotFoundException("SubCategory not found with id " + subCategoryId));
        return productRepository.findProductBySubCategory(subCategory, PageRequest.of(page, size))
                .map(productMapper::toMinimalProductDto);
    }

    public Page<MinimalProductDto> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, int page, int size) {
        return productRepository.findByPriceBetween(minPrice, maxPrice, PageRequest.of(page, size))
                .map(productMapper::toMinimalProductDto);
    }

    public Page<MinimalProductDto> findAllByOrderByPriceAsc(int page, int size) {
        return productRepository.findAllByOrderByPriceAsc(PageRequest.of(page, size))
                .map(productMapper::toMinimalProductDto);
    }

    public Page<MinimalProductDto> findAllByOrderByPriceDesc(int page, int size) {
        return productRepository.findAllByOrderByPriceDesc(PageRequest.of(page, size))
                .map(productMapper::toMinimalProductDto);
    }

    public Page<MinimalProductDto> findAllByOrderByCreatedAtDesc(int page, int size) {
        return productRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))
                .map(productMapper::toMinimalProductDto);
    }

    public Page<MinimalProductDto> findProductByName(String keyword, int page, int size){
        return productRepository.findByNameContainingIgnoreCase(keyword, PageRequest.of(page, size))
                .map(productMapper::toMinimalProductDto);
    }

    public Page<MinimalProductDto> getAllMinimalProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size))
                .map(productMapper::toMinimalProductDto);
    }

    public Page<MinimalProductDto> findProductsByCategoryName(String name, int page, int size) {
        return productRepository.findByCategory_NameContainingIgnoreCase(name, PageRequest.of(page, size))
                .map(productMapper::toMinimalProductDto);
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return productMapper.toDto(product);
    }

    public ProductDto updateProduct(Long productId, ProductRequest request) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid subcategory ID"));

        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setLongDescription(request.getLongDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setQuantity(request.getQuantity());
        existingProduct.setAvailable(request.getQuantity() > 0);
        existingProduct.setCategory(category);
        existingProduct.setSubCategory(subCategory);

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDto(updatedProduct);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        productRepository.delete(product);
    }


}
