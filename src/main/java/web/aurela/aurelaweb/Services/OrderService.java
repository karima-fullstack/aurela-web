package web.aurela.aurelaweb.Services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.aurela.aurelaweb.Dtos.OrderDto;
import web.aurela.aurelaweb.Dtos.OrderItemDto;
import web.aurela.aurelaweb.Dtos.OrderRequest;
import web.aurela.aurelaweb.Entities.*;
import web.aurela.aurelaweb.Mappers.OrderItemMapper;
import web.aurela.aurelaweb.Mappers.OrderMapper;
import web.aurela.aurelaweb.Repositories.OrderItemRepository;
import web.aurela.aurelaweb.Repositories.OrderRepository;
import web.aurela.aurelaweb.Repositories.ProductRepository;
import web.aurela.aurelaweb.Repositories.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final ProductRepository productRepository;
    private final UserDetailsServiceImpl userService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final EmailService emailService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    public OrderDto createOrder(OrderRequest request) {

        // get user
        User user = userService.getAuthenticatedUser();

        // Build OrderItems and check product availability
        List<OrderItem> orderItems = request.getItems().stream().map(itemDto -> {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemDto.getProductId()));

            if (product.getQuantity() < itemDto.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Update stock
            product.setQuantity(product.getQuantity() - itemDto.getQuantity());
            productRepository.save(product);

            return OrderItem.builder()
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .priceAtPurchase(product.getPrice())
                    .build();
        }).toList();

        // Calculate total amount
        BigDecimal shippingFee = new BigDecimal(10.00);

        BigDecimal totalAmount = orderItems.stream()
                .map(item -> item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalAmount = totalAmount.add(shippingFee);

        // Build and save Order
        Order order = Order.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .street(request.getStreet())
                .city(request.getCity())
                .state(request.getState())
                .zipCode(request.getZipCode())
                .country(request.getCountry())
                .phone(request.getPhone())

                .paymentMethod(request.getPaymentMethod())
                .status(OrderStatus.PENDING)
                .trackingNumber(UUID.randomUUID().toString())
                .totalAmount(totalAmount)
                .orderItems(orderItems)
                .user(user)
                .build();

        // Set reverse mapping
        orderItems.forEach(item -> item.setOrder(order));

        Order savedOrder = orderRepository.save(order);

        // Send HTML confirmation email
        emailService.sendOrderConfirmationEmail(savedOrder);

        // Return DTO
        return orderMapper.toDTO(savedOrder);
    }

    public Page<OrderDto> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toDTO);
    }

    public OrderDto updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);

        return orderMapper.toDTO(updatedOrder);
    }

    public List<OrderDto> getOrdersForCurrentUser(User user) {
        // Find orders for user
        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    // OrderItem get Methods
    public Page<OrderItemDto> getAllOrderItems(int page, int size) {
        return orderItemRepository.findAll(PageRequest.of(page, size))
                .map(orderItemMapper::toDto);
    }

    public Page<OrderItemDto> getOrderItemsForCurrentUser(User user, int page, int size) {
        return orderItemRepository.findByOrder_UserOrderByOrder_CreatedAtDesc(user, PageRequest.of(page, size))
                .map(orderItemMapper::toDto);
    }
}
