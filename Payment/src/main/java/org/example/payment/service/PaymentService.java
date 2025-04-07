package org.example.payment.service;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.payment.model.Inventory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentService {

    private final Gson gson = new Gson(); // Gson instance for JSON conversion

    @KafkaListener(topics = "inventory-updates", groupId = "payment-group")
    public void processPayment(String inventoryJson) {
        try {
            Inventory inventory = gson.fromJson(inventoryJson, Inventory.class); // Convert JSON to Inventory object

            if (inventory.isInStock()) {
                log.info("Payment processing for order: {}", inventory.getOrderId());
            } else {
                log.info("Order {} canceled due to insufficient stock.", inventory.getOrderId());
            }
        } catch (Exception e) {
            log.error("Failed to process payment for message: {}", inventoryJson, e);
        }
    }
}
