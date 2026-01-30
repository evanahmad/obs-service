package org.evan.project.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.evan.project.model.entity.Order;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {
}
