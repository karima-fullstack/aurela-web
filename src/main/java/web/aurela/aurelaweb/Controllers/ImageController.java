package web.aurela.aurelaweb.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import web.aurela.aurelaweb.Dtos.ImageDto;
import web.aurela.aurelaweb.Services.ImageService;

@RestController
@RequestMapping("/aurela/api/products/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDto> uploadImage(
            @RequestParam("title") String title,
            @RequestParam("productId") Long productId,
            @Valid @RequestParam("file") MultipartFile image
            ) {
        ImageDto savedImage = imageService.uploadImageToProduct(title, productId, image);
        return ResponseEntity.ok(savedImage);
    }

}
