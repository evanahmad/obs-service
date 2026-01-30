package org.evan.project.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.evan.project.model.entity.Item;

@ApplicationScoped
public class ItemRepository implements PanacheRepository<Item> {
}
