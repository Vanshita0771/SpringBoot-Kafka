package org.example.orderservice.model;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private String orderId;
    private String product;
    private int quantity;
}

