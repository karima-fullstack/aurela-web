package web.aurela.aurelaweb.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.aurela.aurelaweb.Dtos.MinimalProductDto;
import web.aurela.aurelaweb.Dtos.ProductDto;
import web.aurela.aurelaweb.Dtos.ProductRequest;
import web.aurela.aurelaweb.Services.ProductService;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aurela/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> createProduct(@Valid @RequestBody ProductRequest request) {
        Long productId = productService.createProduct(request);
        return ResponseEntity.ok(productId);
    }

    @GetMapping("/search")
    public Page<MinimalProductDto> findProductByName(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size
    ){
        return productService.findProductByName(keyword, page, size);
    }

    // Get products by category ID
    @GetMapping("/category/{categoryId}")
    public Page<MinimalProductDto> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.findProductByCategoryId(categoryId, page, size);
    }

    @GetMapping("/subcategory/{subCategoryId}")
    public Page<MinimalProductDto> getProductsBySubCategory(
            @PathVariable Long subCategoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.findProductBySubCategoryId(subCategoryId, page, size);
    }

    @GetMapping("/price-range")
    public Page<MinimalProductDto> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.findByPriceBetween(minPrice, maxPrice, page, size);
    }

    @GetMapping("/price-asc")
    public Page<MinimalProductDto> getProductsSortedByPriceAsc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.findAllByOrderByPriceAsc(page, size);
    }

    @GetMapping("/price-desc")
    public Page<MinimalProductDto> getProductsSortedByPriceDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.findAllByOrderByPriceDesc(page, size);
    }

    @GetMapping("/created-desc")
    public Page<MinimalProductDto> getProductsSortedByCreatedAtDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.findAllByOrderByCreatedAtDesc(page, size);
    }

    @GetMapping("/category-name")
    public Page<MinimalProductDto> findProductsByCategoryName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.findProductsByCategoryName(name, page, size);
    }

    @GetMapping
    public Page<MinimalProductDto> getAllMinimalProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
         return productService.getAllMinimalProducts(page, size);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequest request) {
        ProductDto updatedProduct = productService.updateProduct(id, request);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
