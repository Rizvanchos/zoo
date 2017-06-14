package ua.nure.ipz.zoo.dto.builder;

import org.springframework.stereotype.Component;
import ua.nure.ipz.zoo.dto.OrderDto;
import ua.nure.ipz.zoo.entity.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class OrderDtoBuilder implements DtoBuilder<Order, OrderDto> {
    @Override
    public OrderDto toDto(Order source) {
        return new OrderDto(source.getDomainId(), source.getStatus().toString(), source.getContact().getName(),
                source.getContact().getEmail(), source.getContact().getContactPhone(), source.getBasicPrice(),
                source.getTotalPrice(), getFormattedTime(source.getPlacementTime()), source.getComment());
    }

    private String getFormattedTime(LocalDateTime time) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(time);
    }
}
