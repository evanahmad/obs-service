package org.evan.project.controller;

import lombok.RequiredArgsConstructor;
import org.evan.project.model.entity.Order;
import org.evan.project.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @GetMapping
    public Page<Order> getAll(Pageable pageable) {
        return orderService.getAll(pageable);
    }

    @PostMapping
    public Order create(@RequestParam Long itemId,
                        @RequestParam int quantity) {

        return orderService.createOrder(itemId, quantity);
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable Long id,
                        @RequestParam int quantity) {

        return orderService.updateOrder(id, quantity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
}
