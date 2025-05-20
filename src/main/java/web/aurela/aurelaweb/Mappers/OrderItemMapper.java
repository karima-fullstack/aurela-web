package web.aurela.aurelaweb.Mappers;

import org.springframework.stereotype.Service;
import web.aurela.aurelaweb.Dtos.OrderItemDto;
import web.aurela.aurelaweb.Entities.Image;
import web.aurela.aurelaweb.Entities.OrderItem;
import web.aurela.aurelaweb.Entities.Product;

import java.time.LocalDate;

@Service
public class OrderItemMapper {

    public OrderItemDto toDto(OrderItem orderItem) {
        Product product = orderItem.getProduct();

        String principalImageUrl = product.getImages().stream()
                .filter(Image::isPrincipal)
                .findFirst()
                .map(Image::getUrl)
                .orElse(null);

        return OrderItemDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .principalImageUrl(principalImageUrl)
                .quantity(orderItem.getQuantity())
                .priceAtPurchase(orderItem.getPriceAtPurchase())
                .createdAt(orderItem.getOrder().getCreatedAt().toLocalDate())
                .PaymentMethod(orderItem.getOrder().getPaymentMethod())
                .build();
    }
}
