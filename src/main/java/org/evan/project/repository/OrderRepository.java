package org.evan.project.repository;

import org.evan.project.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = """
        SELECT COALESCE(SUM(o.quantity), 0)
        FROM Order o
        WHERE o.item.id = :itemId
    """, nativeQuery = true)
    int calculateOrderedQuantity(Long itemId);
}
