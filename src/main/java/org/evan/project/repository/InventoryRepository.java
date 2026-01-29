package org.evan.project.repository;

import org.evan.project.model.entity.Inventory;
import org.evan.project.model.enums.InventoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("""
        select sum(i.quantity)
        from Inventory i
        where i.item.id = :itemId
        and i.type = :type
    """)
    Integer sumQuantityByItemIdAndType(Long itemId, InventoryType type);
}
