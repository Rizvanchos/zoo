package ua.nure.ipz.zoo.service;

import java.util.UUID;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import ua.nure.ipz.zoo.dto.OrderDto;
import ua.nure.ipz.zoo.exception.ModifiableCartException;
import ua.nure.ipz.zoo.exception.OrderLifecycleException;
import ua.nure.ipz.zoo.service.validation.Phone;

public interface OrderService extends Service<OrderDto> {

    UUID create(@NotBlank String name, @Email String email, @Phone String contactPhone, String comment, @NotNull UUID cartId) throws ModifiableCartException;

    void setDiscount(@NotNull UUID orderId, @NotBlank String ticketType, @Min(1) int barrier, @DecimalMin("00.01") @DecimalMax("1.00") float coefficient);

    void setPaymentInfo(@NotNull UUID orderId, @NotBlank String cardType, @CreditCardNumber String cardNumber, @Length(min = 3, max = 4) String cardCvv);

    OrderDto findOrder(@NotNull UUID orderId);

    void process(@NotNull UUID orderId, @NotNull UUID operatorId) throws OrderLifecycleException;

    void cancel(@NotNull UUID orderId) throws OrderLifecycleException;
}
