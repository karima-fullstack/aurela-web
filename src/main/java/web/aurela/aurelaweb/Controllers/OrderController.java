package web.aurela.aurelaweb.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import web.aurela.aurelaweb.Dtos.OrderDto;
import web.aurela.aurelaweb.Dtos.OrderItemDto;
import web.aurela.aurelaweb.Dtos.OrderRequest;
import web.aurela.aurelaweb.Entities.OrderStatus;
import web.aurela.aurelaweb.Entities.User;
import web.aurela.aurelaweb.Services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/aurela/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Create a new order
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest request) {
        OrderDto createdOrder = orderService.createOrder(request);
        return ResponseEntity.ok(createdOrder);
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<Page<OrderDto>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    // Update order status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatus status
    ) {
        OrderDto updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    // Get orders of the authenticated user
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderDto>> getMyOrders(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.getOrdersForCurrentUser(user));
    }

    @GetMapping("/all")
    public Page<OrderItemDto> getAllOrderItems(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return orderService.getAllOrderItems(page, size);
    }

    @GetMapping("/my-order-items")
    public Page<OrderItemDto> getMyOrderItems(
            @AuthenticationPrincipal User user,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return orderService.getOrderItemsForCurrentUser(user, page, size);
    }

}

