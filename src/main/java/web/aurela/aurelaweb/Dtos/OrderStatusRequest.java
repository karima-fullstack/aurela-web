package web.aurela.aurelaweb.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.aurela.aurelaweb.Entities.OrderStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusRequest {
    private OrderStatus status;
}
