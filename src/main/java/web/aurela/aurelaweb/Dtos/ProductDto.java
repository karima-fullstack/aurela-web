package web.aurela.aurelaweb.Dtos;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String longDescription;
    private BigDecimal price;
    private int quantity;
    private boolean isAvailable;
    private String categoryName;
    private String subCategoryName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<ImageDto> images;
}
