package web.aurela.aurelaweb.Dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MinimalProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private String principalImageUrl;
}
