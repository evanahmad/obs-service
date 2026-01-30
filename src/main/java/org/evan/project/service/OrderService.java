package org.evan.project.service;

import org.evan.project.model.entity.Order;

public interface OrderService extends BaseService<Order, Long> {
    Order createOrder(Long itemId, int quantity);
}
