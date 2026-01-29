package org.evan.project.repository;

import org.evan.project.model.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query(value = """
        SELECT COALESCE(SUM(
            CASE 
                WHEN i.type = 'T' THEN i.quantity
                WHEN i.type = 'W' THEN -i.quantity
                ELSE 0
            END
        ), 0)
        FROM Inventory i
        WHERE i.item.id = :itemId
    """, nativeQuery = true)
    int calculateInventoryStock(Long itemId);
}
