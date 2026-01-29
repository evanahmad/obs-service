package org.evan.project.controller;

import lombok.RequiredArgsConstructor;
import org.evan.project.model.entity.Order;
import org.evan.project.model.response.ApiResponse;
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
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ApiResponse<Order> getById(
            @PathVariable Long id
    ) {
        return ApiResponse.of(
                orderService.getById(id)
        );
    }

    @GetMapping
    public ApiResponse<Page<Order>> getAll(
            Pageable pageable
    ) {
        return ApiResponse.of(
                orderService.getAll(pageable)
        );
    }

    @PostMapping
    public ApiResponse<Order> create(
            @RequestParam Long itemId,
            @RequestParam int quantity
    ) {
        return ApiResponse.of(
                orderService.createOrder(itemId, quantity)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<Order> update(
            @PathVariable Long id,
            @RequestParam int quantity
    ) {
        return ApiResponse.of(
                orderService.updateOrder(id, quantity)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable Long id
    ) {
        orderService.delete(id);
        return ApiResponse.of(null);
    }
}
