package web.aurela.aurelaweb.Mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import web.aurela.aurelaweb.Dtos.OrderDto;
import web.aurela.aurelaweb.Dtos.OrderItemDto;
import web.aurela.aurelaweb.Entities.Order;
import web.aurela.aurelaweb.Entities.OrderItem;

import java.util.List;

@Service
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // Custom mapping for nested objects
        modelMapper.typeMap(OrderItem.class, OrderItemDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getProduct().getId(), OrderItemDto::setProductId);
            mapper.map(src -> src.getProduct().getName(), OrderItemDto::setProductName);
        });
    }

    public OrderDto toDTO(Order order) {
        OrderDto dto = modelMapper.map(order, OrderDto.class);
        List<OrderItemDto> itemDTOs = modelMapper.map(order.getOrderItems(), new TypeToken<List<OrderItemDto>>() {}.getType());
        dto.setOrderItems(itemDTOs);
        return dto;
    }
}
