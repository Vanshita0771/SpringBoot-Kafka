package org.example.inventory.service;

import com.google.gson.Gson;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.inventory.model.Inventory;
import org.example.inventory.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {
    @NonNull
    private final KafkaTemplate<String, Inventory> kafkaTemplate;
    private final Gson gson = new Gson(); // Gson instance for JSON conversion

    public static int inventory = 100;

    public boolean checkInventory(int quantity) {
        if (inventory-quantity < 0) {
            log.info("Inventory is empty");
            return false;
        }
        return true;
    }

    @KafkaListener(topics = "orders-update", groupId = "order-group")
    public void processOrder(String orderJson) {
        try {
            Order order = gson.fromJson(orderJson, Order.class); // Convert JSON to Order object
            boolean inStock = checkInventory(order.getQuantity());

            if (inStock) {
                inventory-=order.getQuantity();
            }

            Inventory update = new Inventory(order.getOrderId(), inStock);
            kafkaTemplate.send("inventory-updates", update);
            log.info("Inventory Updated: Order ID = {}, In Stock = {}", order.getOrderId(), inStock);
        } catch (Exception e) {
            log.error("Failed to process order: {}", orderJson, e);
        }
    }
}
