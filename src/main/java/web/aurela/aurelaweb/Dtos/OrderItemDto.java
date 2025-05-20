package web.aurela.aurelaweb.Dtos;

import lombok.*;
import web.aurela.aurelaweb.Entities.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private Long productId;
    private String productName;
    private String principalImageUrl;
    private int quantity;
    private BigDecimal priceAtPurchase;
    private LocalDate createdAt;
    private PaymentMethod PaymentMethod;

}
