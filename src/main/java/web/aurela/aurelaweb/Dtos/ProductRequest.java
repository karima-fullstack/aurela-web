package web.aurela.aurelaweb.Dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank
    @Size(min = 4, message = "Product name must be longer than 3 characters")
    private String name;

    @NotBlank
    @Size(max = 80, message = "Description must be at least 80 characters long")
    private String description;

    @NotBlank
    @Size(max = 500, message = "Long description must be at least 500 characters long")
    private String longDescription;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private BigDecimal price;

    @Min(value = 0, message = "Quantity must be zero or positive")
    private int quantity;

    private Long categoryId;

    private Long subCategoryId;
}
