package org.evan.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.evan.project.model.entity.Order;
import org.evan.project.repository.OrderRepository;
import org.evan.project.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Long itemId, int quantity) {
        return null;
    }

    @Override
    public Order updateOrder(Long id, int quantity) {
        return null;
    }

    @Override
    public Order getById(Long aLong) {
        return null;
    }

    @Override
    public Page<Order> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
