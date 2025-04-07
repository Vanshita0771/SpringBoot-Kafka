package org.example.orderservice.controller;

import lombok.AllArgsConstructor;
import org.example.orderservice.model.Order;
import org.example.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        orderService.sendOrder(order);
        return new ResponseEntity<>("Order sent successfully to kafka", HttpStatus.OK);
    }

}
