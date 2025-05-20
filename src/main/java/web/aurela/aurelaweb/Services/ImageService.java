package web.aurela.aurelaweb.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.aurela.aurelaweb.Dtos.ImageDto;
import web.aurela.aurelaweb.Entities.Image;
import web.aurela.aurelaweb.Entities.Product;
import web.aurela.aurelaweb.Mappers.ImageMapper;
import web.aurela.aurelaweb.Repositories.ImageRepository;
import web.aurela.aurelaweb.Repositories.ProductRepository;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final ImageMapper imageMapper;

    public ImageDto uploadImageToProduct(String title, Long productId, MultipartFile image) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        String imageUrl = cloudinaryService.uploadFile(image, "product-images");
        if (imageUrl == null) throw new RuntimeException("Image upload failed.");

        boolean isFirstImage = product.getImages() == null || product.getImages().isEmpty();

        Image img = Image.builder()
                .title(title)
                .url(imageUrl)
                .isPrincipal(isFirstImage) // First image is the principal
                .product(product)
                .build();

        return imageMapper.toDto(imageRepository.save(img));
    }

}
