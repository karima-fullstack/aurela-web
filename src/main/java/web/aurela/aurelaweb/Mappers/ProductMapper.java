package web.aurela.aurelaweb.Mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import web.aurela.aurelaweb.Dtos.*;
import web.aurela.aurelaweb.Entities.Image;
import web.aurela.aurelaweb.Entities.Product;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final ModelMapper modelMapper;
    private final ImageMapper imageMapper;

    public ProductDto toDto(Product product) {
        ProductDto dto = modelMapper.map(product, ProductDto.class);

        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
        }
        if (product.getSubCategory() != null) {
            dto.setSubCategoryName(product.getSubCategory().getName());
        }

        List<ImageDto> imageDtos = product.getImages()
                .stream()
                .map(imageMapper::toDto)
                .collect(Collectors.toList());

        dto.setImages(imageDtos);
        return dto;
    }

    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .longDescription(request.getLongDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }

    public List<ProductDto> toDtoList(List<Product> products) {
        if (products == null) return Collections.emptyList();
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public MinimalProductDto toMinimalProductDto(Product product) {
        MinimalProductDto dto = modelMapper.map(product, MinimalProductDto.class);

        // Set principal image URL if present
        product.getImages().stream()
                .filter(Image::isPrincipal)
                .findFirst()
                .ifPresent(img -> dto.setPrincipalImageUrl(img.getUrl()));

        return dto;
    }

    public List<MinimalProductDto> toMinimalProductDtoList(List<Product> products) {
        if (products == null) return Collections.emptyList();
        return products.stream()
                .map(this::toMinimalProductDto)
                .collect(Collectors.toList());
    }
}
